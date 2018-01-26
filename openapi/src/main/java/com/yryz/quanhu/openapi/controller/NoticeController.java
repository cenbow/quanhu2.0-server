package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.annotation.NotLogin;
import com.yryz.common.response.Response;
import com.yryz.quanhu.message.notice.api.NoticeAPI;
import com.yryz.quanhu.message.notice.dto.NoticeDto;
import com.yryz.quanhu.message.notice.vo.NoticeDetailVo;
import com.yryz.quanhu.message.notice.vo.NoticeVo;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("services/app")
public class NoticeController {

    public static final Logger LOGGER = LoggerFactory.getLogger(NoticeController.class);

    @Reference(check = false, timeout = 30000)
    private NoticeAPI noticeAPI;

    @ApiOperation("获取公告列表")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping("/{version}/notice/list")
    @NotLogin
    public Response<NoticeVo> list(NoticeDto noticeDto) {
        Assert.hasText(noticeDto.getSearchDate(), "查询时间点不能为空！");
        return noticeAPI.list(noticeDto);
    }

    @ApiOperation("获取公告详情")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping("/{version}/notice/detail")
    @NotLogin
    public Response<NoticeDetailVo> detail(NoticeDto noticeDto) {
        Assert.notNull(noticeDto.getKid(), "公告id不能为空！");
        return noticeAPI.detail(noticeDto);
    }


}
