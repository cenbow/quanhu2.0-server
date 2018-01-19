package com.yryz.quanhu.user.service;

import com.yryz.quanhu.user.dto.UserRelationRemarkDto;

import java.util.List;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/19 14:46
 * Created by huangxy
 */
public interface UserRelationRemarkService {

    public boolean setRemarkName(UserRelationRemarkDto remarkDto);

    public String getRemarkName(UserRelationRemarkDto remarkDto);

    public List<UserRelationRemarkDto> selectBy(UserRelationRemarkDto remarkDto);

}
