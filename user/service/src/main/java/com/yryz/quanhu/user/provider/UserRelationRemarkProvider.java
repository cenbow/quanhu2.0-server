package com.yryz.quanhu.user.provider;

import com.yryz.common.response.Response;
import com.yryz.quanhu.user.dto.UserRelationDto;
import com.yryz.quanhu.user.dto.UserRelationRemarkDto;
import com.yryz.quanhu.user.service.UserRelationRemarkApi;

import java.util.List;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/19 19:18
 * Created by huangxy
 */
public class UserRelationRemarkProvider implements UserRelationRemarkApi{

    @Override
    public Response<Boolean> setRemarkName(UserRelationDto dto) {
        return null;
    }

    @Override
    public Response<Boolean> cancelRemarkName(UserRelationDto dto) {
        return null;
    }

    @Override
    public Response<List<UserRelationRemarkDto>> selectBy(String sourceUserId, SOURCE source) {
        return null;
    }
}
