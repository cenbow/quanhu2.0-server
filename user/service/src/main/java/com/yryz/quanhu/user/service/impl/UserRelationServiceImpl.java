package com.yryz.quanhu.user.service.impl;

import static com.yryz.quanhu.user.contants.UserRelationConstant.STATUS.BOTH_BLACK;
import static com.yryz.quanhu.user.contants.UserRelationConstant.STATUS.FANS;
import static com.yryz.quanhu.user.contants.UserRelationConstant.STATUS.FOLLOW;
import static com.yryz.quanhu.user.contants.UserRelationConstant.STATUS.FRIEND;
import static com.yryz.quanhu.user.contants.UserRelationConstant.STATUS.FROM_BLACK;
import static com.yryz.quanhu.user.contants.UserRelationConstant.STATUS.NONE;
import static com.yryz.quanhu.user.contants.UserRelationConstant.STATUS.TO_BLACK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yryz.common.constant.ExceptionEnum;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.utils.PageModel;
import com.yryz.quanhu.user.contants.UserRelationConstant;
import com.yryz.quanhu.user.dao.UserRelationCacheDao;
import com.yryz.quanhu.user.dao.UserRelationDao;
import com.yryz.quanhu.user.dao.UserRelationRemarkDao;
import com.yryz.quanhu.user.dto.UserRelationCountDto;
import com.yryz.quanhu.user.dto.UserRelationDto;
import com.yryz.quanhu.user.dto.UserRelationRemarkDto;
import com.yryz.quanhu.user.entity.UserBaseInfo;
import com.yryz.quanhu.user.entity.UserStarAuth;
import com.yryz.quanhu.user.service.UserRelationService;
import com.yryz.quanhu.user.service.UserService;
import com.yryz.quanhu.user.service.UserStarService;
import com.yryz.quanhu.user.vo.UserBaseInfoVO;

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

    @Autowired
    private UserRelationCacheDao userRelationCacheDao;

    @Autowired
    private UserRelationDao userRelationDao;

    @Autowired
    private UserRelationRemarkDao userRelationRemarkDao;

    @Autowired
    private UserService userService;

    @Autowired
    private UserStarService userStarService;
    /**
     * appId
     */
    @Value("${appId}")
    private String appId;
    /**
     * 用户最大关注数
     */
    @Value("${user.relation.maxFollowCount}")
    private long maxFollowCount;

    @Override
    public UserRelationDto setRelation(String sourceUserId, String targetUserId,UserRelationConstant.EVENT event){
        UserRelationDto sourceDto = null;
        UserRelationDto targetDto = null;
        try{
            /**
             * 验证基本信息
             */
            this.verifyRelationBasic(sourceUserId,targetUserId,event);

            /**
             * 查询双方用户关系，进行更新
             * 更新缓存，及时响应结果信息
             */
            sourceDto = userRelationCacheDao.getCacheRelation(sourceUserId,targetUserId);
            targetDto = userRelationCacheDao.getCacheRelation(targetUserId,sourceUserId);

            if(sourceDto == null){
                sourceDto = new UserRelationDto();
                sourceDto.setRelationStatus(NONE.getCode());
                sourceDto.setOrgRelationStatus(NONE.getCode());
                sourceDto.setSourceUserId(sourceUserId);
                sourceDto.setTargetUserId(targetUserId);
            }else{
                sourceDto.setOrgRelationStatus(sourceDto.getRelationStatus());
            }
            if(targetDto == null){
                targetDto = new UserRelationDto();
                targetDto.setRelationStatus(NONE.getCode());
                targetDto.setOrgRelationStatus(NONE.getCode());
                targetDto.setSourceUserId(targetUserId);
                targetDto.setTargetUserId(sourceUserId);
            }else{
                targetDto.setOrgRelationStatus(targetDto.getRelationStatus());
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
             * 异步保存
             */
            userRelationCacheDao.asyncSave(sourceDto);
            userRelationCacheDao.asyncSave(targetDto);

        }catch (Exception e){
            throw e;
        }
        return sourceDto;
    }
    /**
     *
     * 查询缓存>数据库
     * @param userSourceKid
     * @param userTargetKid
     * @return
     */
    @Override
    public UserRelationDto getRelation(String sourceUserId, String targetUserId) {
        UserRelationDto dto = userRelationCacheDao.getCacheRelation(sourceUserId,targetUserId);
        if(dto == null){
            dto = new UserRelationDto();
            dto.setRelationStatus(NONE.getCode());
        }
        return dto;
    }



    @Override
    public UserRelationDto getRelationByTargetPhone(String sourceUserId, String targetPhoneNo) {
        String targetUserId =  userService.getUserByPhone(targetPhoneNo,appId);
        if(StringUtils.isBlank(targetUserId)){
            UserRelationDto dto = new UserRelationDto();
            dto.setRelationStatus(NONE.getCode());
            return dto;
        }else{
            return getRelation(sourceUserId,targetUserId);
        }
    }

    /**
     * 基本预校验
     * @param sourceUserId
     * @param targetUserId
     * @param event
     * @return
     */
    private boolean verifyRelationBasic(String sourceUserId, String targetUserId,UserRelationConstant.EVENT event){

        /**
         * ★★★★★关键验证★★★★★
         * 自己不允许对自己设置任何关系
         * ★★★★★★★★★★★★★★
         */

        if(sourceUserId.equalsIgnoreCase(targetUserId)){
            throw new QuanhuException(ExceptionEnum.USER_RELATION_TO_SELF_ERROR);
        }
        /**
         * 判断关注人数是否已达到上线
         * 查询缓存>查询数据库
         */
        UserRelationCountDto countDto = this.totalBy(sourceUserId,sourceUserId);
        if(countDto.getFollowCount()>=maxFollowCount){
            throw new QuanhuException(ExceptionEnum.USER_FOLLOW_MAX_COUNT_ERROR);
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
            throw new QuanhuException(ExceptionEnum.USER_MISSING.getCode(),"目标用户不存在或已注销："+targetUserId,"目标用户不存在或已注销");
        }
        return false;
    }

    /**
     * 判断是否有拉黑关系存在
     * @param sourceDto
     * @param targetDto
     * @param event
     * @return
     */
    private boolean verifyRelationCondition(UserRelationDto sourceDto, UserRelationDto targetDto, UserRelationConstant.EVENT event){

        /**
         * 1，判断自己是否在对方黑名单中，如果在，不允许做任何操作
         * 2，判断对方是否在自己黑名单中，如果在，除取消拉黑，不允许做任何操作
         */
        if(TO_BLACK.getCode() == sourceDto.getRelationStatus()){
            if(UserRelationConstant.EVENT.CANCEL_BLACK != event){
                throw new QuanhuException(ExceptionEnum.USER_BLACK_TARGETUSER_ERROR.getCode(),"你已将该用户加入黑名单","你已将该用户加入黑名单");
            }

        }else if(FROM_BLACK.getCode() == sourceDto.getRelationStatus()){
            if(UserRelationConstant.EVENT.SET_BLACK != event&&UserRelationConstant.EVENT.CANCEL_BLACK != event){
                throw new QuanhuException(ExceptionEnum.TARGETUSER_BLACK_USER_ERROR.getCode(),"对方已将你加入黑名单","对方已将你加入黑名单");
            }
        }
        return true;
    }

    /**
     * 重新设置关系
     * @param source
     * @param target
     * @param event
     */
    private void rebuildRelation(UserRelationDto source, UserRelationDto target, UserRelationConstant.EVENT event){

        UserRelationConstant.STATUS ss = UserRelationConstant.STATUS.get(source.getRelationStatus());
        UserRelationConstant.STATUS ts = UserRelationConstant.STATUS.get(target.getRelationStatus());

        /**
         * 设置source逻辑
         */
        if(UserRelationConstant.EVENT.SET_FOLLOW == event){            //关注
            /**
             * 如果target也关注我，则设置双向好友关系
             * 否则，我关注别人，别人的粉丝
             */
            if(ts == FOLLOW){
                ss = FRIEND;ts = FRIEND;
            }else if(ss == NONE && ts == NONE){
                ss = FOLLOW;ts = FANS;
            }
        }else if(UserRelationConstant.EVENT.CANCEL_FOLLOW == event){   //取消关注
            /**
             * 如果target是好友关系，则设置别人是我的粉丝，别人关注我
             * 否则，取消双向所有关系
             */
            if (ts == FRIEND) {
                ss = FANS; ts = FOLLOW;
            }else if(ss == FOLLOW && ts == FANS){
                ts = NONE;ss = NONE;
            }
        }else if(UserRelationConstant.EVENT.SET_BLACK == event){       //拉黑
            /**
             * 如果target拉黑我，则设置双向拉黑关系
             * 否则，设置我拉黑别人，别人被拉黑
             */
            if (ts == TO_BLACK) {
                ss = BOTH_BLACK;ts = BOTH_BLACK;
            }else{
                ss = TO_BLACK;ts = FROM_BLACK;
            }
        }else if(UserRelationConstant.EVENT.CANCEL_BLACK == event){    //取消拉黑
            /**
             * 如果target是双向拉黑，则设置我被别人拉黑，别人拉黑我
             * 否则 取消双向所有关系
             */
            if (ts == BOTH_BLACK) {
                ss = FROM_BLACK;ts = TO_BLACK;
            }else if(ss == TO_BLACK && ts == FROM_BLACK){
                ss = NONE;ts = NONE;
            }
        }else{
            throw new QuanhuException(ExceptionEnum.ValidateException.getCode(),"系统参数异常:"+event,"系统参数异常");
        }
        //根据target状态判断逻辑
        source.setRelationStatus(ss.getCode());
        target.setRelationStatus(ts.getCode());
    }


    /**
     * 校验查询条件
     * @param sourceUserId
     * @param targetUserId
     * @param status
     */
    private void verifyQueryCondition(String sourceUserId,String targetUserId,UserRelationConstant.STATUS status){
        /**
         * 判断查询是否合法
         * 非本人用户，只能查询目标用户的     粉丝，关注
         * 本人用户，只能查询        好友，粉丝，关注，黑名单
         */

        if(sourceUserId.equalsIgnoreCase(targetUserId)){
            //查看本人
            if(FANS!=status && FRIEND!=status && FOLLOW!=status && TO_BLACK!=status){
                throw new QuanhuException(ExceptionEnum.ValidateException.getCode(),"查询参数非法,不允许操作："+status,"查询参数非法,不允许操作");
            }
        }else{
            //查看他人
            if(FANS!=status && FOLLOW!=status){
                throw new QuanhuException(ExceptionEnum.ValidateException.getCode(),"查询参数非法,不允许操作："+status,"查询参数非法,不允许操作");
            }
        }
    }


    /**
     * 分页查询
     * @param dto
     * @param status
     * @return
     */
    @Override
    public PageList<UserRelationDto> selectByPage(UserRelationDto dto,UserRelationConstant.STATUS status) {

        String sourceUserId = dto.getSourceUserId();
        String targetUserId = dto.getTargetUserId();

        //校验查询条件
        this.verifyQueryCondition(sourceUserId,targetUserId,status);
        /**
         * 先查询sourceUserId的相关关系
         */
        PageHelper.startPage(dto.getCurrentPage(),dto.getPageSize());

        List<UserRelationDto> targetList =
                userRelationDao.selectStatus(UserRelationDto.class,targetUserId,status.getCode());

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
        Set<String> targetUserIds = new HashSet<>();
        /**
         * 匹配他人结果集与自己结果集
         * 如果有重复，则取自己结果集，不匹配则将他人结果集设置成无关系
         */
        for (int i = 0 ; i < targetList.size() ; i++){
            targetUserIds.add(targetList.get(i).getTargetUserId());
        }
        List<UserRelationDto> outArray = this.selectBy(sourceUserId,targetUserIds);
        return new PageModel<UserRelationDto>().getPageList(outArray);
    }

    private void mergeRelation(List<UserRelationDto> list,UserRelationDto _dto){
        for(int i = 0 ; i < list.size() ; i++){
            UserRelationDto dto = list.get(i);
            if(_dto.getTargetUserId().equalsIgnoreCase(dto.getTargetUserId())){
                _dto.setRelationStatus(dto.getRelationStatus());
                break;
            }
        }
    }

    private boolean mergeUserInfo(Map<String,UserBaseInfoVO> map,UserRelationDto _dto){
        UserBaseInfoVO info = map.get(_dto.getTargetUserId());
        if(info!=null){
            _dto.setUserId(_dto.getTargetUserId());
            _dto.setUserName(info.getUserNickName());
            _dto.setUserHeadImg(info.getUserImg());
            _dto.setUserSummary(info.getUserDesc());
            _dto.setUserStarFlag(info.getUserRole());
            return true;
        }else{
            return false;
        }
    }

    private void mergeUserRemark(List<UserRelationRemarkDto> list,UserRelationDto _dto){
        for (int i = 0 ; i < list.size() ; i++){
            UserRelationRemarkDto remarkDto = list.get(i);
            if(remarkDto.getTargetUserId().equalsIgnoreCase(_dto.getTargetUserId())){
                _dto.setUserRemarkName(remarkDto.getRemarkValue());
                break;
            }
        }
    }

    @Override
    public List<UserRelationDto> selectByAll(UserRelationDto dto, UserRelationConstant.STATUS status) {
        dto.setCurrentPage(1);
        dto.setPageSize(Integer.parseInt(String.valueOf(maxFollowCount)));
        return selectByPage(dto,status).getEntities();
    }

    @Override
    public List<UserRelationDto> selectBy(String sourceUserId, Set<String> targetUserIds) {

        List<UserRelationDto> outArray = new ArrayList<>();
        //查询 用户与目标集合的关系
        List<UserRelationDto> userAlls= userRelationDao.selectTargets(UserRelationDto.class,sourceUserId,targetUserIds);
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

        Iterator<String> iterator = targetUserIds.iterator();
        while(iterator.hasNext()){
            String _targetUserId = iterator.next();

            //默认无关系
            UserRelationDto newDto = new UserRelationDto();
            newDto.setRelationStatus(NONE.getCode());
            newDto.setTargetUserId(_targetUserId);

            //合并用户基本信息
            if(!this.mergeUserInfo(userMaps,newDto)){
                continue;
            }

            //合并关系
            this.mergeRelation(userAlls,newDto);
            //合并备注信息
            this.mergeUserRemark(remarkDtos,newDto);

            outArray.add(newDto);
        }
        return outArray;
    }

    @Override
    public Set<String> selectBy(String sourceUserId, UserRelationConstant.STATUS status) {
        List<UserRelationDto> array = userRelationDao.selectStatus(UserRelationDto.class,sourceUserId,status.getCode());
        Set<String> newArray = new HashSet<>();
        for(int i = 0 ; i < array.size() ; i++){
            newArray.add(array.get(i).getTargetUserId());
        }
        return newArray;
    }

    @Override
    public UserRelationCountDto totalBy(String sourceUserId,String targetUserId) {
        /**
         * 统计全量
         * 先查询缓存，再查询数据库
         */
        UserRelationCountDto dto = userRelationCacheDao.getCacheTotalCount(targetUserId);
        if(dto==null){

            //从数据库查询
            dto = userRelationCacheDao.selectTotalCount(targetUserId);
            if(dto==null){
                return dto;
            }

            //刷新至缓存
            userRelationCacheDao.refreshCacheCount(targetUserId,dto);
        }
        /**
         * 判断用户是否当前用户，如果不是，则把其他关系数量设置0，不对外可见
         * 拉黑，被拉黑，互相拉黑，好友，不对外可见
         */
        if(!sourceUserId.equalsIgnoreCase(targetUserId)){
            dto.setBothBlackCount(0);
            dto.setFromBlackCount(0);
            dto.setToBlackCount(0);
            dto.setFriendCount(0);
        }
        return dto;
    }

    @Override
    public long totalBy(String sourceUserId, String targetUserId, UserRelationConstant.STATUS status) {
        /**
         * 判断状态查询条件
         */
        this.verifyQueryCondition(sourceUserId,targetUserId,status);
        // 查询
        return userRelationDao.selectCount(targetUserId,status.getCode());
    }
}
