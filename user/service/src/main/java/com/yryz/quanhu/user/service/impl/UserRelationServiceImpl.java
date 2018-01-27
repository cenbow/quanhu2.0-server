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
import com.yryz.quanhu.user.dto.UserRelationRemarkDto;
import com.yryz.quanhu.user.entity.UserBaseInfo;
import com.yryz.quanhu.user.entity.UserStarAuth;
import com.yryz.quanhu.user.provider.UserRelationProvider;
import com.yryz.quanhu.user.service.UserRelationService;
import com.yryz.quanhu.user.service.UserService;
import com.yryz.quanhu.user.service.UserStarService;
import com.yryz.quanhu.user.vo.UserBaseInfoVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.yryz.quanhu.user.contants.UserRelationConstant.NO;
import static com.yryz.quanhu.user.contants.UserRelationConstant.YES;
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

    private static final Logger logger = LoggerFactory.getLogger(UserRelationServiceImpl.class);

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

    @Value("appId")
    private String appId;

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
                sourceDto.setBlackStatus(UserRelationConstant.NO);
                sourceDto.setFriendStatus(UserRelationConstant.NO);
                sourceDto.setFollowStatus(UserRelationConstant.NO);
                sourceDto.setNewRecord(true);
            }
            if(targetDto == null){
                targetDto = new UserRelationDto();
                targetDto.setBlackStatus(UserRelationConstant.NO);
                targetDto.setFriendStatus(UserRelationConstant.NO);
                targetDto.setFollowStatus(UserRelationConstant.NO);
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
    public UserRelationDto getRelation(String sourceUserId, String targetUserId) {
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



    @Override
    public UserRelationDto getRelationByTargetPhone(String sourceUserId, String targetPhoneNo) {

        String targetUserId =  userService.getUserByPhone(targetPhoneNo,appId);
        if(StringUtils.isBlank(targetUserId)){
            return mergeRelation(null,null);
        }else{
            return getRelation(sourceUserId,targetUserId);
        }

    }


    private boolean verifyRelationBasic(String sourceUserId, String targetUserId,UserRelationConstant.EVENT event){

        /**
         * ★★★★★关键验证★★★★★
         * 自己不允许对自己设置任何关系
         * ★★★★★★★★★★★★★★
         */

        if(sourceUserId.equalsIgnoreCase(targetUserId)){
            throw new QuanhuException("","","不允许与自己设置关系");
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
        UserBaseInfo targetUser =userService.getUser(Long.parseLong(targetUserId));
        if(null == targetUser){
            throw new QuanhuException("","","目标用户不存在或已注销");
        }
        return false;
    }

    private boolean verifyRelationCondition(UserRelationDto sourceDto, UserRelationDto targetDto, UserRelationConstant.EVENT event){

        /**
         * 1，判断自己是否在对方黑名单中，如果在，不允许做任何操作
         * 2，判断对方是否在自己黑名单中，如果在，除取消拉黑，不允许做任何操作
         */
        if(YES == sourceDto.getBlackStatus()){
            if(UserRelationConstant.EVENT.CANCEL_BLACK != event){
                throw new QuanhuException("","","你已将该用户加入黑名单");
            }
        }

        if(YES == targetDto.getBlackStatus()){
            if(UserRelationConstant.EVENT.SET_BLACK != event&&UserRelationConstant.EVENT.CANCEL_BLACK != event){
                throw new QuanhuException("","","对方已将你加入黑名单");
            }
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
            if(YES == targetDto.getFollowStatus()){
                targetDto.setFriendStatus(YES);
                sourceDto.setFriendStatus(YES);
            }else{
                sourceDto.setFriendStatus(UserRelationConstant.NO);
            }
            sourceDto.setBlackStatus(UserRelationConstant.NO);
            sourceDto.setFollowStatus(YES);

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
             * 设置 target 的 粉丝关系  否
             *
             * 设置 source 的 黑名单    是
             * 设置 source 的 好友关系   否
             * 设置 source 的 粉丝关系   否
             */
            targetDto.setFriendStatus(UserRelationConstant.NO);
            targetDto.setFollowStatus(UserRelationConstant.NO);

            sourceDto.setBlackStatus(YES);
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
     *
     * 状态合并是站在source角度上，所有返回的是source对象
     * @param sourceDto
     * @param targetDto
     * @return
     */
    private UserRelationDto mergeRelation(UserRelationDto s, UserRelationDto t){

        UserRelationDto newDto = new UserRelationDto();
        /**
         * 无关系
         */
        if(null==s){
            s = new UserRelationDto();
            s.setBlackStatus(UserRelationConstant.NO);
            s.setFollowStatus(UserRelationConstant.NO);
            s.setFriendStatus(UserRelationConstant.NO);
        }
        if(null==t){
            t = new UserRelationDto();
            t.setBlackStatus(UserRelationConstant.NO);
            t.setFollowStatus(UserRelationConstant.NO);
            t.setFriendStatus(UserRelationConstant.NO);
        }
        /**
         * 转换关系
         */
        if(s.getBlackStatus()==YES && t.getBlackStatus()==YES){         //互黑
            s.setRelationStatus(UserRelationConstant.STATUS.BOTH_BLACK.getCode());
        }else if(s.getBlackStatus()==YES && t.getBlackStatus()==NO){    //拉黑
            s.setRelationStatus(UserRelationConstant.STATUS.TO_BLACK.getCode());
        }else if(s.getBlackStatus()==NO && t.getBlackStatus()==YES){    //被拉黑
            s.setRelationStatus(UserRelationConstant.STATUS.FROM_BLACK.getCode());
        }else if(s.getFollowStatus()==YES && t.getFollowStatus()==YES){ //好友
            s.setRelationStatus(UserRelationConstant.STATUS.FRIEND.getCode());
        }else if(s.getFollowStatus()==YES && t.getFollowStatus()==NO){  //关注
            s.setRelationStatus(UserRelationConstant.STATUS.FOLLOW.getCode());
        }else if(s.getFollowStatus()==NO && t.getFollowStatus()==YES){  //粉丝
            s.setRelationStatus(UserRelationConstant.STATUS.FANS.getCode());
        }else if(s.getFollowStatus()==NO && t.getFollowStatus()==NO) {  //无状态
            s.setRelationStatus(UserRelationConstant.STATUS.NONE.getCode());
        }else {                                                         //异常默认无状态
            s.setRelationStatus(UserRelationConstant.STATUS.NONE.getCode());
        }
        return s;
    }


    private void verifyQueryCondition(String sourceUserId,String targetUserId,UserRelationConstant.STATUS status){

        /**
         * 判断查询是否合法
         * 非本人用户，只能查询目标用户的     粉丝，关注
         * 本人用户，只能查询        好友，粉丝，关注，黑名单
         */

        if(sourceUserId.equalsIgnoreCase(targetUserId)){
            //查看本人
            if(UserRelationConstant.STATUS.FANS!=status &&
                    UserRelationConstant.STATUS.FRIEND!=status &&
                    UserRelationConstant.STATUS.FOLLOW!=status &&
                    UserRelationConstant.STATUS.TO_BLACK!=status){

                throw new QuanhuException("","","查询参数非法,不允许操作");
            }
        }else{
            //查看他人
            if(UserRelationConstant.STATUS.FANS!=status &&
                    UserRelationConstant.STATUS.FOLLOW!=status){
                //异常
                throw new QuanhuException("","","查询参数非法,不允许操作");
            }
        }
    }


    @Override
    public PageList<UserRelationDto> selectByPage(UserRelationDto dto,UserRelationConstant.STATUS status) {

        List<UserRelationDto> outList = new ArrayList<>();

        String sourceUserId = dto.getSourceUserId();
        String targetUserId = dto.getTargetUserId();

        //校验查询条件
        this.verifyQueryCondition(sourceUserId,targetUserId,status);


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
        if(CollectionUtils.isEmpty(userAlls)){
            userAlls = new ArrayList<>();
        }
        //查询用户基本信息
        Map<String,UserBaseInfoVO> userMaps = userService.getUser(targetUserIds);
        if(null == userMaps){
            userMaps = new HashMap<>();
        }
        //查询达人信息
        List<UserStarAuth> starAuths = userStarService.get(Lists.newArrayList(targetUserIds));
        if(CollectionUtils.isEmpty(starAuths)){
            starAuths = new ArrayList<>();
        }

        //查询备注名信息(默认查询平台)
        List<UserRelationRemarkDto> remarkDtos = userRelationRemarkDao.selectByUserIds(UserRelationRemarkDto.class,
                UserRelationConstant.TYPE.PLATFORM.getCode(),sourceUserId,targetUserIds);
        if(CollectionUtils.isEmpty(remarkDtos)){
            remarkDtos = new ArrayList<>();
        }

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

            //合并用户基本信息(如果用户不存在，则不返回数据（异常数据）
            newDto.setUserId(_targetUserId);
            UserBaseInfoVO userInfo = userMaps.get(_targetUserId);
            if(userInfo!=null){
                newDto.setUserHeadImg(userInfo.getUserImg());
                newDto.setUserName(userInfo.getUserNickName());
                newDto.setUserSummary(userInfo.getUserDesc());
            }else{
                logger.warn("用户基本信息不存在：{}",_targetUserId);
                continue;
            }



            //判断是否为达人信息
            for(int k = 0 ; k < starAuths.size() ; k++){
                UserStarAuth starAuth = starAuths.get(k);
                String starAuthUserId = String.valueOf(starAuth.getUserId());
                if (_targetUserId.equalsIgnoreCase(starAuthUserId)&&
                        starAuth.getAuditStatus()==AUDIT_SUCCESS.getStatus()){
                    newDto.setUserStarFlag(1);
                    break;
                }
            }

            //匹配备注名
            for(int j = 0 ;j<remarkDtos.size() ; j++){
                UserRelationRemarkDto remarkDto = remarkDtos.get(j);
                if(_targetUserId.equalsIgnoreCase(remarkDto.getTargetUserId())){
                    newDto.setUserRemarkName(remarkDto.getRemarkValue());
                    break;
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
