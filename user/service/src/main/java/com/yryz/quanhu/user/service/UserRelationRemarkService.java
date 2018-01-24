package com.yryz.quanhu.user.service;

import com.yryz.quanhu.user.contants.UserRelationConstant;
import com.yryz.quanhu.user.dto.UserRelationRemarkDto;

import java.util.List;
import java.util.Set;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/19 14:46
 * Created by huangxy
 */
public interface UserRelationRemarkService {

    public Boolean setRemarkName(UserRelationRemarkDto remarkDto);

    public Boolean resetRemarkName(UserRelationRemarkDto remarkDto);

    public UserRelationRemarkDto getRemarkDto(String sourceUserId, String targetUserId, UserRelationConstant.TYPE source);

    public List<UserRelationRemarkDto> selectBy(String sourceUserId, UserRelationConstant.TYPE source);

    public List<UserRelationRemarkDto> selectBy(String sourceUserId, UserRelationConstant.TYPE type,Set<String> targetUserIds);

}
