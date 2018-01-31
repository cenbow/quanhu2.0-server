package com.yryz.quanhu.user.service;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.user.contants.UserRelationConstant;
import com.yryz.quanhu.user.dto.UserRelationCountDto;
import com.yryz.quanhu.user.dto.UserRelationDto;

import java.util.List;
import java.util.Set;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/18 15:07
 * Created by huangxy
 */
public interface UserRelationService{

    public UserRelationDto setRelation(String sourceUserId,String targetUserId, UserRelationConstant.EVENT event);

    public UserRelationDto getRelation(String sourceUserId, String targetUserId);

    public UserRelationDto getRelationByTargetPhone(String sourceUserId, String targetPhoneNo);

    public PageList<UserRelationDto> selectByPage(UserRelationDto dto,UserRelationConstant.STATUS status);

    public List<UserRelationDto> selectByAll(UserRelationDto dto,UserRelationConstant.STATUS status);

    public List<UserRelationDto> selectBy(String sourceUserId, Set<String> targetUserIds);

    public Set<String> selectBy(String sourceUserId, UserRelationConstant.STATUS status);

    public UserRelationCountDto totalBy(String sourceUserId,String targetUserId);

    public long totalBy(String sourceUserId,String targetUserId,UserRelationConstant.STATUS status);
}
