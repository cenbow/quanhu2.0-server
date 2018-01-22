package com.yryz.quanhu.user.service;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
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

    public boolean setRelation(String sourceUserId,String targetUserId, UserRelationApi.EVENT event);

    public UserRelationDto getCacheRelation(String sourceUserId, String targetUserId);

    public UserRelationDto getForceRelation(String sourceUserId, String targetUserId);

    public PageList<UserRelationDto> selectByPage(UserRelationDto dto);

    public List<UserRelationDto> selectBy(String sourceUserId, String[] targetUserIds);

    public Set<String> selectBy(String sourceUserId, UserRelationApi.STATUS status);

    public UserRelationCountDto totalBy(String userId);


}