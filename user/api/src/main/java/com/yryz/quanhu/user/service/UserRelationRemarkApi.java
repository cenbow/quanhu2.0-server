package com.yryz.quanhu.user.service;

import com.yryz.common.response.Response;
import com.yryz.quanhu.user.contants.UserRelationConstant;
import com.yryz.quanhu.user.dto.UserRelationDto;
import com.yryz.quanhu.user.dto.UserRelationRemarkDto;

import java.util.List;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/18 13:23
 * Created by huangxy
 */
public interface UserRelationRemarkApi {

    /**
     * 设置备注名
     * @param dto
     * @return
     */
    Response<Boolean> setRemarkName(UserRelationRemarkDto dto);

    /**
     * 恢复备注名
     * @param dto
     * @return
     */
    Response<Boolean> resetRemarkName(UserRelationRemarkDto dto);

    /**
     * 查询备注信息
     * @param sourceUserId
     * @param targetUserId
     * @param type
     * @return
     */
    Response<UserRelationRemarkDto> getRemarkDto(String sourceUserId,String targetUserId,UserRelationConstant.TYPE type);

    /**
     * 查询用户所有备注信息
      * @param sourceUserId
     * @param type
     * @return
     */
    Response<List<UserRelationRemarkDto>> selectBy(String sourceUserId,UserRelationConstant.TYPE type);

}
