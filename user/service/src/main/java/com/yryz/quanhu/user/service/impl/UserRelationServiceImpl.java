package com.yryz.quanhu.user.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.PageList;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.contants.UserRelationConstant;
import com.yryz.quanhu.user.dao.UserRelationCacheDao;
import com.yryz.quanhu.user.dao.UserRelationDao;
import com.yryz.quanhu.user.dao.UserRelationRemarkDao;
import com.yryz.quanhu.user.dto.UserRelationCountDto;
import com.yryz.quanhu.user.dto.UserRelationDto;
import com.yryz.quanhu.user.service.UserRelationService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    /**
     * 用户最大关注数
     */
    @Value("${user.relation.maxFollowCount}")
    private long maxFollowCount;

    @Override
    public UserRelationDto setRelation(String sourceUserId, String targetUserId,UserRelationConstant.EVENT event){
        try{

            /**
             * 验证基本信息
             */
            this.verifyRelationBasic(sourceUserId,targetUserId,event);

            /**
             * 查询双方用户关系，进行更新
             * 更新缓存，及时响应结果信息
             */
            UserRelationDto sourceDto = userRelationCacheDao.getCacheRelation(sourceUserId,targetUserId);
            UserRelationDto targetDto = userRelationCacheDao.getCacheRelation(targetUserId,sourceUserId);
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
            this.rebuildRelation(sourceDto,targetDto,event);

            /**
             * 用户第一次建立关系，采用DB存储，保证实时准确唯一性，
             * 后续采用MQ消息方式
             */
            if(sourceDto.isNewRecord()){
                sourceDto.setSourceUserId(sourceUserId);
                sourceDto.setTargetUserId(targetUserId);

                sourceDto.setDelFlag(UserRelationConstant.NO);
                sourceDto.setVersion(0);
                sourceDto.setCreateUserId(Long.parseLong(sourceUserId));
                sourceDto.setLastUpdateUserId(Long.parseLong(targetUserId));

                sourceDto.setKid(System.currentTimeMillis());
                userRelationDao.insert(sourceDto);
            }else{
                userRelationCacheDao.sendMQ(sourceDto);
            }

            /**
             * 只有关注，取消关注的时候，并且target关系存在的情况下，才涉及到target数据更新
             * 更新好友关系
             */

            if(!targetDto.isNewRecord()){
                targetDto.setLastUpdateUserId(Long.parseLong(sourceUserId));

                userRelationCacheDao.sendMQ(targetDto);
                userRelationCacheDao.refreshCacheRelation(targetDto);
            }

            //同步单向关系至redis
            userRelationCacheDao.refreshCacheRelation(sourceDto);

            return sourceDto;
        }catch (Exception e){
            throw e;
        }
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

        UserRelationDto  sourceDto =  userRelationCacheDao.getCacheRelation(sourceUserId, targetUserId);
        UserRelationDto  targetDto =  userRelationCacheDao.getCacheRelation(targetUserId, sourceUserId);

        //合并结果集
        UserRelationDto newDto = this.mergeRelation(sourceDto,targetDto);
        newDto.setSourceUserId(sourceUserId);
        newDto.setTargetUserId(targetUserId);

        return newDto;

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
        //以用户维度查询
        UserRelationDto  sourceDto = userRelationDao.selectByUser(UserRelationDto.class,sourceUserId,targetUserId);

        //查询目标和自己的状态
        UserRelationDto targetDto = userRelationDao.selectByUser(UserRelationDto.class,targetUserId,sourceUserId);

        //合并结果集
        UserRelationDto newDto = this.mergeRelation(sourceDto,targetDto);
        newDto.setSourceUserId(sourceUserId);
        newDto.setTargetUserId(targetUserId);

        return newDto;
    }


    private boolean verifyRelationBasic(String sourceUserId, String targetUserId,UserRelationConstant.EVENT event){

        /**
         * ★★★★★关键验证★★★★★
         * 自己不允许对自己设置任何关系
         * ★★★★★★★★★★★★★★
         */

        if(sourceUserId.equalsIgnoreCase(targetUserId)){
            throw new RuntimeException("don't allow set relation to self");
        }

        /**
         * 判断关注人数是否已达到上线
         */
        final long wasFollowCount = userRelationCacheDao.totalByFollow(sourceUserId);
        if(wasFollowCount>=maxFollowCount){
            throw new RuntimeException("user follow number reached the upper limit : "+maxFollowCount);
        }

        /**
         * 判断目标用户是否存在
         *
         * 当前用户，调用前有api网关校验过用户token,相关有效性，
         * 为提高效率，本地不检查当前用户有效性（待议？）
         *
         * 目标用户，（★强制检查★）
         */
        if(true){

        }
        return false;
    }

    private boolean verifyRelationCondition(UserRelationDto sourceDto, UserRelationDto targetDto, UserRelationConstant.EVENT event){

        /**
         * 1，判断自己是否在对方黑名单中，如果在，不允许做任何操作
         * 2，判断对方是否在自己黑名单中，如果在，除取消拉黑，不允许做任何操作
         */

        if(UserRelationConstant.YES == targetDto.getBlackStatus()){
            throw new RuntimeException("target user has set black to source");
        }

        if(UserRelationConstant.YES == sourceDto.getBlackStatus()&&UserRelationConstant.EVENT.CANCEL_BLACK != event){
            throw new RuntimeException("source user has set black to target,please cancel black first");
        }

        return true;
    }

    private void rebuildRelation(UserRelationDto sourceDto, UserRelationDto targetDto, UserRelationConstant.EVENT event){

        /**
         * 主要判断逻辑，判断对方是否拉黑
         */
        if(UserRelationConstant.EVENT.SET_FOLLOW == event){                //关注
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
            if(UserRelationConstant.YES == targetDto.getFollowStatus()){
                targetDto.setFriendStatus(UserRelationConstant.YES);
                sourceDto.setFriendStatus(UserRelationConstant.YES);
            }else{
                sourceDto.setFriendStatus(UserRelationConstant.NO);
            }
            sourceDto.setBlackStatus(UserRelationConstant.NO);
            sourceDto.setFollowStatus(UserRelationConstant.YES);

        }else if(UserRelationConstant.EVENT.CANCEL_FOLLOW == event){   //取消关注
            /**
             * 设置 target 的 黑名单    保持不变
             * 设置 target 的 好友关系  否
             * 设置 target 的 粉丝关系  保持不变
             *
             * 设置 source 的 黑名单    否
             * 设置 source 的 好友关系   否
             * 设置 source 的 粉丝关系   否
             */
            targetDto.setFriendStatus(UserRelationConstant.NO);

            sourceDto.setBlackStatus(UserRelationConstant.NO);
            sourceDto.setFriendStatus(UserRelationConstant.NO);
            sourceDto.setFollowStatus(UserRelationConstant.NO);

        }else if(UserRelationConstant.EVENT.SET_BLACK == event){           //拉黑
            /**
             * 设置 target 的 黑名单    保持不变
             * 设置 target 的 好友关系  否
             * 设置 target 的 粉丝关系  保持不变
             *
             * 设置 source 的 黑名单    是
             * 设置 source 的 好友关系   否
             * 设置 source 的 粉丝关系   否
             */
            targetDto.setFriendStatus(UserRelationConstant.NO);

            sourceDto.setBlackStatus(UserRelationConstant.YES);
            sourceDto.setFriendStatus(UserRelationConstant.NO);
            sourceDto.setFollowStatus(UserRelationConstant.NO);

        }else if(UserRelationConstant.EVENT.CANCEL_BLACK == event){    //取消拉黑

            /**
             * 设置 target 的 黑名单    保持不变
             * 设置 target 的 好友关系  保持不变
             * 设置 target 的 粉丝关系  保持不变
             *
             * 设置 source 的 黑名单    否
             * 设置 source 的 好友关系   否
             * 设置 source 的 粉丝关系   否
             */

            sourceDto.setBlackStatus(UserRelationConstant.NO);
            sourceDto.setFriendStatus(UserRelationConstant.NO);
            sourceDto.setFollowStatus(UserRelationConstant.NO);

        }else{
            throw new RuntimeException("type not exist");
        }
    }

    /**
     * 合并用户关系，生产新对象
     * @param sourceDto
     * @param targetDto
     * @return
     */
    private UserRelationDto mergeRelation(UserRelationDto sourceDto, UserRelationDto targetDto){

        UserRelationDto newDto = new UserRelationDto();

        if(null==sourceDto){
            //用户未与目标用户建立关系，则设置所以状态为no,

            newDto.setToBlackStatus(UserRelationConstant.NO);
            newDto.setToFollowStatus(UserRelationConstant.NO);
            newDto.setBlackStatus(UserRelationConstant.NO);
            newDto.setFollowStatus(UserRelationConstant.NO);
            newDto.setFriendStatus(UserRelationConstant.NO);
        }else{
            //用户已与目标用户建立关系，则复制数据至新对象
            BeanUtils.copyProperties(sourceDto,newDto);

            newDto.setToFollowStatus(newDto.getFollowStatus());
            newDto.setToBlackStatus(newDto.getBlackStatus());
        }

        if(null==targetDto){
            //目标用户未与当前用户建立关系，则设置对象未被关注，和拉黑

            newDto.setFromBlackStatus(UserRelationConstant.NO);
            newDto.setFromFollowStatus(UserRelationConstant.NO);
        }else{
            //目标用户与当前用户建立关系，则过去目标用户关注，黑名单设置到新对象中

            newDto.setFromFollowStatus(targetDto.getFollowStatus());
            newDto.setFromBlackStatus(targetDto.getBlackStatus());
        }
        return newDto;
    }


    @Override
    public PageList<UserRelationDto> selectByPage(UserRelationDto dto,UserRelationConstant.STATUS status) {

        /**
         * 先查询sourceUserId的相关关系
         */
        PageList<UserRelationDto> pageList = userRelationCacheDao.selectByPage(
                dto.getCurrentPage(),dto.getPageSize(),dto.getTargetUserId(),status);

        /**
         * ★★★★★★★★★★
         * 如果结果集为空，或者当前登录用户和源用户一致，则直接返回，否则继续查询当前登录用户和列表之间的关系
         * ★★★★★★★★★★
         */
        if(CollectionUtils.isEmpty(pageList.getEntities())){
            return pageList;
        }


        //构建查询条件
        List<UserRelationDto> queryArray = pageList.getEntities();
        String [] targetUserIds = userRelationCacheDao.buildTargetUserIds(queryArray,status);


        //查询 用户与目标集合的关系
        List<UserRelationDto> user2TargetArray = userRelationCacheDao.selectBy(dto.getSourceUserId(),targetUserIds);
        //查询 目标集合和用户的关系
        List<UserRelationDto> target2UserArray = userRelationCacheDao.selectBy(targetUserIds,dto.getSourceUserId());

        /**
         * 匹配他人结果集与自己结果集
         * 如果有重复，则取自己结果集，不匹配则将他人结果集设置成无关系
         */
        boolean hasRelation = false;
        for (int i = 0 ; i < queryArray.size() ; i++){

            String targetUserId = null;

            //相关状态，取值字段不一样
            if(UserRelationConstant.STATUS.FANS == status||UserRelationConstant.STATUS.FROM_BLACK == status) {
                targetUserId = queryArray.get(i).getSourceUserId();
            }else{
                targetUserId = queryArray.get(i).getTargetUserId();
            }

            UserRelationDto user2TargetDto = null;
            UserRelationDto target2UserDto = null;

            if(!CollectionUtils.isEmpty(user2TargetArray)){

                //匹配判断他人结果集中的目标对象，和自己结果集中是否一致
                for (int j = 0 ; j < user2TargetArray.size() ; j++){
                    UserRelationDto _user2TargetDto = user2TargetArray.get(j);

                    if(targetUserId.equalsIgnoreCase(_user2TargetDto .getTargetUserId())){
                        user2TargetDto = _user2TargetDto;
                        break;
                    }

                }
            }

            if(!CollectionUtils.isEmpty(target2UserArray)){
                //匹配判断他人结果集中的和自己的关系
                for(int j = 0 ; j < target2UserArray.size(); j++){
                    UserRelationDto _target2UserDto = target2UserArray.get(j);

                    if(targetUserId.equalsIgnoreCase(_target2UserDto.getSourceUserId())){
                        target2UserDto = _target2UserDto;
                        break;
                    }
                }
            }
            /**
             * 合并双向关系，
             * 主要为了赋值，to/from关系
             */
            UserRelationDto newDto = this.mergeRelation(user2TargetDto,target2UserDto);
            newDto.setSourceUserId(dto.getSourceUserId());
            newDto.setTargetUserId(targetUserId);


            queryArray.set(i,newDto);
        }
        pageList.setEntities(queryArray);
        return pageList;
    }

    @Override
    public List<UserRelationDto> selectBy(String sourceUserId, String[] targetUserIds) {
        return userRelationCacheDao.selectBy(sourceUserId, targetUserIds);
    }

    @Override
    public Set<String> selectBy(String sourceUserId, UserRelationConstant.STATUS status) {

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
