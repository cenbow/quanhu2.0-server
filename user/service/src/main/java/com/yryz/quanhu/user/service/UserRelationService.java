package com.yryz.quanhu.user.service;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.user.dto.UserRelationCountDto;
import com.yryz.quanhu.user.dto.UserRelationDto;

import java.util.List;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/18 15:07
 * Created by huangxy
 */
public interface UserRelationService{

    public boolean setRelation(UserRelationDto dto);

    public boolean checkRelation(UserRelationDto dto);

    public boolean setRemarkName(UserRelationDto dto);

    public boolean recoverRemarkName(UserRelationDto dto);

    public List<UserRelationDto> selectBy(UserRelationDto dto);

    public PageList<UserRelationDto> selectByPage(UserRelationDto dto);

    public List<UserRelationDto> selectBy(String userSourceKid, String[] userTargetKids);

    public UserRelationCountDto totalBy(String userSourceKid);


}
