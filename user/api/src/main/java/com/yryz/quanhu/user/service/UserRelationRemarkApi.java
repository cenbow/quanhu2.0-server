package com.yryz.quanhu.user.service;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.user.dto.UserRelationCountDto;
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

    public static enum SOURCE{
        PLATFORM,                               //平台
        COTERIE,                                //私圈
        IM                                      //云信IM
    }
    /**
     * 设置备注名
     * @param dto
     * @return
     */
    Response<Boolean> setRemarkName(UserRelationDto dto);

    /**
     * 恢复备注名
     * @param dto
     * @return
     */
    Response<Boolean> cancelRemarkName(UserRelationDto dto);

    /**
     * 查询用户所有备注信息
      * @param sourceUserId
     * @param source
     * @return
     */
    Response<List<UserRelationRemarkDto>> selectBy(String sourceUserId,SOURCE source);

}
