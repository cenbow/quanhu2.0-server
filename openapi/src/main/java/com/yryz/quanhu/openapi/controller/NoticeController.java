package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.Response;
import com.yryz.quanhu.message.notice.api.NoticeAPI;
import com.yryz.quanhu.message.notice.dto.NoticeDto;
import com.yryz.quanhu.message.notice.vo.NoticeVo;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description:
 * @Date: Created in 2018 2018/1/20 17:37
 * @Author: pn
 */
@Api("公告HTTP接口")
@RestController
public class NoticeController {

    public static final Logger LOGGER = LoggerFactory.getLogger(NoticeController.class);

    @Reference(check = false)
    private NoticeAPI noticeAPI;

    @ApiOperation("获取公告列表")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping("/{version}/notice/list")
    public Response<NoticeVo> list(NoticeDto noticeDto){
        // TODO
        return null;
    }

    @ApiOperation("获取公告详情")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping("/{version}/notice/detail")
    public Response<NoticeVo> detail(NoticeDto noticeDto){
        // TODO
        return null;
    }
}
