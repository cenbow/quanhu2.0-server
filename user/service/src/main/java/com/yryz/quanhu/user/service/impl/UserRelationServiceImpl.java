package com.yryz.quanhu.user.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.PageList;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.dao.UserRelationCacheDao;
import com.yryz.quanhu.user.dao.UserRelationDao;
import com.yryz.quanhu.user.dao.UserRelationRemarkDao;
import com.yryz.quanhu.user.dto.UserRelationCountDto;
import com.yryz.quanhu.user.dto.UserRelationDto;
import com.yryz.quanhu.user.service.UserRelationApi;
import com.yryz.quanhu.user.service.UserRelationService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.yryz.quanhu.user.service.UserRelationApi.NO;
import static com.yryz.quanhu.user.service.UserRelationApi.YES;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/18 15:27
 * Created by huangxy
 */
@Service
@Transactional
public class UserRelationServiceImpl implements UserRelationService{

    @Autowired
    private UserRelationCacheDao userRelationCacheDao;

    @Autowired
    private UserRelationDao userRelationDao;

    @Autowired
    private UserRelationRemarkDao userRelationRemarkDao;

    @Reference
    private IdAPI idAPI;

    @Override
    public boolean setRelation(String sourceUserId, String targetUserId,UserRelationApi.EVENT event){
        try{

            /**
             * ★★★★★关键验证★★★★★
             * 自己不允许对自己设置任何关系
             * ★★★★★★★★★★★★★★
             */

            if(sourceUserId.equalsIgnoreCase(targetUserId)){
                throw new RuntimeException("don't allow set relation to self");
            }

            /**
             * 查询双方用户关系，进行更新
             * 更新缓存，及时响应结果信息
             */
            UserRelationDto sourceDto = userRelationCacheDao.getUserRelation(sourceUserId,targetUserId);
            UserRelationDto targetDto = userRelationCacheDao.getUserRelation(targetUserId,sourceUserId);
            if(sourceDto == null){
                sourceDto = new UserRelationDto();
                sourceDto.setNewRecord(true);
            }
            if(targetDto == null){
                targetDto = new UserRelationDto();
                targetDto.setNewRecord(true);
            }
            /**
             * 判断关系
             * 1，用户是否存在，防止刷接口
             * 2，任何一方存在拉黑关系，不允许操作(除取消拉黑)
             */
            this.verifyRelationCondition(sourceDto,targetDto,event);

            //设置关系
            this.setRelationMapping(sourceDto,targetDto,event);

            /**
             *
             * 指定方式 入库存储
             *
             * 用户第一次建立关系，采用DB存储，保证实时准确唯一性，
             * 后续采用MQ消息方式
             */
            if(sourceDto.isNewRecord()){
                sourceDto.setSourceUserId(sourceUserId);
                sourceDto.setTargetUserId(targetUserId);

                sourceDto.setDelFlag(NO);
                sourceDto.setVersion(0);
                sourceDto.setCreateUserId(sourceUserId);
                sourceDto.setLastUpdateUserId(targetUserId);

                sourceDto.setKid(System.currentTimeMillis());
//                sourceDto.setKid(idAPI.getKid("user_relation"));
                userRelationDao.insert(sourceDto);
            }else{
                userRelationCacheDao.sendMQ(sourceDto);
            }

            /**
             * 只有关注，取消关注的时候，并且target关系存在的情况下，才涉及到target数据更新
             * 更新好友关系
             */
            if(!targetDto.isNewRecord()&&
                    (UserRelationApi.EVENT.SET_FOLLOW == event || UserRelationApi.EVENT.CANCEL_FOLLOW == event)){
                targetDto.setLastUpdateUserId(sourceUserId);
                userRelationCacheDao.sendMQ(targetDto);
            }

            //同步单向关系至redis
            userRelationCacheDao.setUserRelation(sourceDto,targetDto);

        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            /**
             * 异步记录日志
             */

        }
        return true;
    }

    /**
     *
     * 查询缓存
     * @param userSourceKid
     * @param userTargetKid
     * @return
     */
    @Override
    public UserRelationDto getCacheRelation(String sourceUserId, String targetUserId) {
        return userRelationCacheDao.getUserRelation(sourceUserId, targetUserId);
    }
    /**
     *
     * 查询数据库
     * @param userSourceKid
     * @param userTargetKid
     * @return
     */
    @Override
    public UserRelationDto getForceRelation(String sourceUserId, String targetUserId) {
        UserRelationDto _dto = new UserRelationDto();
        _dto.setSourceUserId(sourceUserId);
        _dto.setTargetUserId(targetUserId);

        List<UserRelationDto> array = userRelationDao.getList(_dto);
        if(null!=array&&array.size()>0){
            return array.get(0);
        }
        return null;
    }


    private boolean verifyRelationCondition(UserRelationDto sourceDto, UserRelationDto targetDto, UserRelationApi.EVENT event){

        /**
         * 1，判断目标用户是否存在
         * 2，判断自己是否在对方黑名单中，如果在，不允许做任何操作
         * 3，判断对方是否在自己黑名单中，如果在，除取消拉黑，不允许做任何操作
         */


        /**
         * 当前用户，调用前有api网关校验过用户token,相关有效性，
         * 为提高效率，本地不检查当前用户有效性（待议？）
         *
         * 目标用户，（★强制检查★）
         */



        if(YES == targetDto.getBlackStatus()){
            throw new RuntimeException("target user has set black to source");
        }

        if(YES == sourceDto.getBlackStatus()&&UserRelationApi.EVENT.CANCEL_BLACK != event){
            throw new RuntimeException("source user has set black to target,please cancel black first");
        }
        return true;
    }

    private void setRelationMapping(UserRelationDto sourceDto, UserRelationDto targetDto, UserRelationApi.EVENT event){

        /**
         * 主要判断逻辑，判断对方是否拉黑
         */
        if(UserRelationApi.EVENT.SET_FOLLOW == event){                //关注
            /**
             * 设置 target 的 黑名单    保持不变
             * 设置 target 的 好友关系  判断target粉丝状态 是：是，否：否
             * 设置 target 的 粉丝关系  保持不变
             *
             * 设置 source 的 黑名单    否
             * 设置 source 的 好友关系   判断target粉丝状态 是：是，否：否
             * 设置 source 的 粉丝关系   是
             */

            //判断对方是否关注,是则互为好友关系
            if(YES == targetDto.getFollowStatus()){
                targetDto.setFriendStatus(YES);
                sourceDto.setFriendStatus(YES);
            }
            sourceDto.setBlackStatus(UserRelationApi.NO);
            sourceDto.setFollowStatus(YES);

        }else if(UserRelationApi.EVENT.CANCEL_FOLLOW == event){   //取消关注
            /**
             * 设置 target 的 黑名单    保持不变
             * 设置 target 的 好友关系  否
             * 设置 target 的 粉丝关系  保持不变
             *
             * 设置 source 的 黑名单    否
             * 设置 source 的 好友关系   否
             * 设置 source 的 粉丝关系   否
             */
            targetDto.setFriendStatus(UserRelationApi.NO);

            sourceDto.setBlackStatus(UserRelationApi.NO);
            sourceDto.setFriendStatus(UserRelationApi.NO);
            sourceDto.setFollowStatus(UserRelationApi.NO);

        }else if(UserRelationApi.EVENT.SET_BLACK == event){           //拉黑
            /**
             * 设置 target 的 黑名单    保持不变
             * 设置 target 的 好友关系  保持不变
             * 设置 target 的 粉丝关系  保持不变
             *
             * 设置 source 的 黑名单    是
             * 设置 source 的 好友关系   否
             * 设置 source 的 粉丝关系   否
             */

            sourceDto.setBlackStatus(YES);
            sourceDto.setFriendStatus(UserRelationApi.NO);
            sourceDto.setFollowStatus(UserRelationApi.NO);

        }else if(UserRelationApi.EVENT.CANCEL_BLACK == event){    //取消拉黑

            /**
             * 设置 target 的 黑名单    保持不变
             * 设置 target 的 好友关系  保持不变
             * 设置 target 的 粉丝关系  保持不变
             *
             * 设置 source 的 黑名单    否
             * 设置 source 的 好友关系   否
             * 设置 source 的 粉丝关系   否
             */

            sourceDto.setBlackStatus(UserRelationApi.NO);
            sourceDto.setFriendStatus(UserRelationApi.NO);
            sourceDto.setFollowStatus(UserRelationApi.NO);

        }else{
            throw new RuntimeException("type not exist");
        }
    }

    @Override
    public PageList<UserRelationDto> selectByPage(UserRelationDto dto) {
        /**
         * 先查询sourceUserId的相关关系
         */
        PageList<UserRelationDto> pageList = userRelationCacheDao.selectByPage(
                dto.getCurrentPage(),dto.getPageSize(),dto.getTargetUserId(),dto.getStatusBy());

        /**
         * ★★★★★★★★★★
         * 如果结果集为空，或者当前登录用户和源用户一致，则直接返回，否则继续查询当前登录用户和列表之间的关系
         * ★★★★★★★★★★
         */
        if(CollectionUtils.isEmpty(pageList.getEntities())||
                dto.getSourceUserId().equalsIgnoreCase(dto.getTargetUserId())){
            return pageList;
        }

        //构建查询条件
        List<UserRelationDto> queryArray = pageList.getEntities();
        String [] targetUserIds = new String[pageList.getEntities().size()];
        for (int i = 0 ; i < queryArray.size() ; i++){
            targetUserIds[i]=String.valueOf(queryArray.get(i).getTargetUserId());
        }
        //查询
        List<UserRelationDto> currentArray = userRelationCacheDao.selectBy(dto.getSourceUserId(),targetUserIds);

        /**
         * 匹配他人结果集与自己结果集
         * 如果有重复，则取自己结果集，不匹配则将他人结果集设置成无关系
         */
        boolean hasRelation = false;
        for (int i = 0 ; i < queryArray.size() ; i++){
            String targetUserId = queryArray.get(i).getTargetUserId();

            //匹配判断他人结果集中的目标对象，和自己结果集中是否一致
            UserRelationDto replaceDto = null;
            for (int j = 0 ; j < currentArray.size() ; j++){
                replaceDto = currentArray.get(j);
                if(targetUserId.equalsIgnoreCase(replaceDto.getTargetUserId())){
                    continue;
                }
            }

            //不存在，则重置与目标关系
            if(null == replaceDto){
                replaceDto = new UserRelationDto();
                replaceDto.setSourceUserId(dto.getSourceUserId());
                replaceDto.setTargetUserId(targetUserId);
                replaceDto.setFollowStatus(UserRelationApi.NO);
                replaceDto.setFriendStatus(UserRelationApi.NO);
                replaceDto.setBlackStatus(UserRelationApi.NO);
            }

            queryArray.set(i,replaceDto);
        }
        pageList.setEntities(queryArray);
        return pageList;
    }

    @Override
    public List<UserRelationDto> selectBy(String sourceUserId, String[] targetUserIds) {
        return userRelationCacheDao.selectBy(sourceUserId, targetUserIds);
    }

    @Override
    public Set<String> selectBy(String sourceUserId, UserRelationApi.STATUS status) {

        List<UserRelationDto> array = userRelationCacheDao.selectBy(sourceUserId,status);
        Set<String> newArray = new HashSet<>();
        for(int i = 0 ; i < array.size() ; i++){
            newArray.add(array.get(i).getTargetUserId());
        }
        return newArray;
    }

    @Override
    public UserRelationCountDto totalBy(String userId) {
        return userRelationCacheDao.totalBy(userId);
    }

}
