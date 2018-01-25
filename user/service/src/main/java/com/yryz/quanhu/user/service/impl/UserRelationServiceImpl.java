package com.yryz.quanhu.user.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.utils.PageModel;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.contants.UserRelationConstant;
import com.yryz.quanhu.user.dao.UserRelationCacheDao;
import com.yryz.quanhu.user.dao.UserRelationDao;
import com.yryz.quanhu.user.dao.UserRelationRemarkDao;
import com.yryz.quanhu.user.dto.UserRelationCountDto;
import com.yryz.quanhu.user.dto.UserRelationDto;
import com.yryz.quanhu.user.entity.UserStarAuth;
import com.yryz.quanhu.user.service.UserRelationService;
import com.yryz.quanhu.user.service.UserService;
import com.yryz.quanhu.user.service.UserStarService;
import com.yryz.quanhu.user.vo.UserBaseInfoVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.yryz.quanhu.user.entity.UserStarAuth.StarAuditStatus.AUDIT_SUCCESS;

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

    private static final String TABLE_NAME = "qh_user_relation";
    @Autowired
    private UserRelationCacheDao userRelationCacheDao;

    @Autowired
    private UserRelationDao userRelationDao;

    @Autowired
    private UserRelationRemarkDao userRelationRemarkDao;

    @Reference
    private IdAPI idAPI;

    @Autowired
    private UserService userService;
    @Autowired
    private UserStarService userStarService;

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

                sourceDto.setKid(idAPI.getKid(TABLE_NAME).getData());
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

            return mergeRelation(sourceDto,targetDto);
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
            throw new QuanhuException("","","您不能对着自己设置关系");
        }

        /**
         * 判断关注人数是否已达到上线
         */
        Long followCount = userRelationDao.selectTotalCount(sourceUserId,UserRelationConstant.STATUS.FOLLOW.getCode());

        if(followCount>=maxFollowCount){
            throw new QuanhuException("","","您已添加最大关注人数: "+maxFollowCount);
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
            throw new QuanhuException("","","目标用户已将您拉入黑名单");
        }

        if(UserRelationConstant.YES == sourceDto.getBlackStatus()&&UserRelationConstant.EVENT.CANCEL_BLACK != event){
            throw new QuanhuException("","","您已将目标用户拉入黑名单");
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
            throw new QuanhuException("","","系统参数异常");
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

        int finalStatus = UserRelationConstant.STATUS.NONE.getCode();

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
        /**
         * 转换为统一状态
         */

        return newDto;
    }




    @Override
    public PageList<UserRelationDto> selectByPage(UserRelationDto dto,UserRelationConstant.STATUS status) {

        List<UserRelationDto> outList = new ArrayList<>();

        String sourceUserId = dto.getSourceUserId();
        String targetUserId = dto.getTargetUserId();
        /**
         * 先查询sourceUserId的相关关系
         */
        PageHelper.startPage(dto.getCurrentPage(),dto.getPageSize());

        List<UserRelationDto> targetList =
                userRelationDao.selectByUserStatus(UserRelationDto.class,targetUserId,status.getCode());

        PageHelper.clearPage();


        /**
         * ★★★★★★★★★★
         * 如果结果集为空，或者当前登录用户和源用户一致，则直接返回，否则继续查询当前登录用户和列表之间的关系
         * ★★★★★★★★★★
         */
        if(CollectionUtils.isEmpty(targetList)){
            return new PageModel<UserRelationDto>().getPageList(targetList);
        }

        //构建查询条件
        Set<String> targetUserIds = this.buildTargetUserIds(targetList,status);

        //查询 用户与目标集合的关系
        List<UserRelationDto> userAlls= userRelationDao.selectByUserAll(UserRelationDto.class,sourceUserId,targetUserIds);

        //查询用户基本信息
        Map<String,UserBaseInfoVO> userMaps = userService.getUser(targetUserIds);

        //查询达人信息
        List<UserStarAuth> starAuths = userStarService.get(Lists.newArrayList(targetUserIds));

        /**
         * 匹配他人结果集与自己结果集
         * 如果有重复，则取自己结果集，不匹配则将他人结果集设置成无关系
         */
        boolean hasRelation = false;
        for (int i = 0 ; i < targetList.size() ; i++){

            String _targetUserId = null;

            //相关状态，取值字段不一样
            if(UserRelationConstant.STATUS.FANS == status||UserRelationConstant.STATUS.FROM_BLACK == status) {
                _targetUserId = targetList.get(i).getSourceUserId();
            }else{
                _targetUserId = targetList.get(i).getTargetUserId();
            }

            UserRelationDto user2TargetDto = null;
            UserRelationDto target2UserDto = null;

            for (int j = 0 ; j < userAlls.size(); j++){
                UserRelationDto _dto = userAlls.get(j);
                if(_targetUserId.equalsIgnoreCase(_dto.getSourceUserId())){
                    target2UserDto = _dto;
                    break;
                }
                if(_targetUserId.equalsIgnoreCase(_dto.getTargetUserId())){
                    user2TargetDto = _dto;
                    break;
                }
            }

            /**
             * 合并双向关系，
             * 主要为了赋值，to/from关系
             */
            UserRelationDto newDto = this.mergeRelation(user2TargetDto,target2UserDto);
            newDto.setSourceUserId(dto.getSourceUserId());
            newDto.setTargetUserId(targetUserId);

            //合并用户基本信息
            newDto.setUserId(_targetUserId);
            UserBaseInfoVO userInfo = userMaps.get(_targetUserId);
            if(userInfo==null){
                continue;
            }

            newDto.setUserHeadImg(userInfo.getUserImg());
            newDto.setUserName(userInfo.getUserNickName());
            newDto.setUserSummary(userInfo.getUserDesc());

            //判断是否为达人信息
            for(int k = 0 ; k < starAuths.size() ; k++){
                UserStarAuth starAuth = starAuths.get(k);
                String starAuthUserId = String.valueOf(starAuth.getUserId());
                if (_targetUserId.equalsIgnoreCase(starAuthUserId)&&
                        starAuth.getAuditStatus()==AUDIT_SUCCESS.getStatus()){
                    newDto.setUserStarFlag(1);
                }
            }
            outList.add(newDto);
        }
        return new PageModel<UserRelationDto>().getPageList(outList);
    }

    @Override
    public List<UserRelationDto> selectByAll(UserRelationDto dto, UserRelationConstant.STATUS status) {
        dto.setCurrentPage(1);
        dto.setPageSize(Integer.parseInt(String.valueOf(maxFollowCount)));
        return selectByPage(dto,status).getEntities();
    }

    @Override
    public List<UserRelationDto> selectBy(String sourceUserId, Set<String> targetUserIds) {
        return userRelationDao.selectUserToTarget(UserRelationDto.class,sourceUserId,targetUserIds);
    }

    @Override
    public Set<String> selectBy(String sourceUserId, UserRelationConstant.STATUS status) {

        List<UserRelationDto> array = userRelationDao.selectByUserStatus(UserRelationDto.class,sourceUserId,status.getCode());
        Set<String> newArray = new HashSet<>();
        for(int i = 0 ; i < array.size() ; i++){
            newArray.add(array.get(i).getTargetUserId());
        }
        return newArray;
    }

    @Override
    public UserRelationCountDto totalBy(String userId) {
        /**
         * 统计全量
         * 先查询缓存，再查询数据库
         */
        UserRelationCountDto dto = userRelationCacheDao.getCacheCount(userId);
        if(dto!=null){
            return dto;
        }
        return userRelationCacheDao.forceTotalCount(userId);
    }


    private Set<String> buildTargetUserIds(List<UserRelationDto> queryArray,UserRelationConstant.STATUS status){
        Set<String> targetUserIds = new HashSet<>();
        if(UserRelationConstant.STATUS.FANS == status||UserRelationConstant.STATUS.FROM_BLACK == status) {
            //粉丝，或者被拉黑，则以用户维度查询
            for (int i = 0 ; i < queryArray.size() ; i++){
                targetUserIds.add(String.valueOf(queryArray.get(i).getSourceUserId()));
            }
        }else{
            //其他以目标维度查询
            for (int i = 0 ; i < queryArray.size() ; i++){
                targetUserIds.add(String.valueOf(queryArray.get(i).getTargetUserId()));
            }
        }
        return targetUserIds;
    }




}
