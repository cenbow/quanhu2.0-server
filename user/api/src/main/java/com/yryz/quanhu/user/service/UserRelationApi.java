package com.yryz.quanhu.user.service;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.user.contants.UserRelationConstant;
import com.yryz.quanhu.user.dto.UserRelationCountDto;
import com.yryz.quanhu.user.dto.UserRelationDto;

import java.util.List;
import java.util.Set;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/18 13:23
 * Created by huangxy
 */
public interface UserRelationApi {

    /**
     * 设置关系
     * @param sourceUserId
     * @param targetUserId
     * @param event
     * @return
     */
    Response<UserRelationDto> setRelation(String sourceUserId,String targetUserId, UserRelationConstant.EVENT event);

    /**
     * 获取关系
     * @param sourceUserId
     * @param targetUserId
     * @return
     */
    Response<UserRelationDto> getRelation(String sourceUserId,String targetUserId);

    /**
     * 通过手机号查询关系
     * @param sourceUserId
     * @param targetPhoneNo
     * @return
     */
    Response<UserRelationDto> getRelationByTargetPhone(String sourceUserId,String targetPhoneNo);


    /**
     * 查询用户关系(分页)
     * @param dto
     * @return
     */
    Response<PageList<UserRelationDto>> selectByPage(UserRelationDto dto,UserRelationConstant.STATUS status);


    /**
     * 查询用户关系（全量）
     * @param dto
     * @param status
     * @return
     */
    Response<List<UserRelationDto>> selectByAll(UserRelationDto dto,UserRelationConstant.STATUS status);

    /**
     * 查询用户指定关系
     * @param sourceUserId
     * @param status
     * @return  返回所有用户ID
     */
    Response<Set<String>> selectBy(String sourceUserId, UserRelationConstant.STATUS status);

    /**
     * 查询用户关系
     * @param sourceUserId     用户
     * @param targetUserIds    目标用户
     * @return
     */
    Response<List<UserRelationDto>> selectBy(String sourceUserId, Set<String> targetUserIds);



    /**
     * 统计用户所有关系数量
     * @param sourceUserId
     * @param targetUserId
     * @return
     */
    Response<UserRelationCountDto> totalBy(String sourceUserId,String targetUserId);

}
