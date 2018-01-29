package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.resource.release.buyrecord.api.ReleaseBuyRecordApi;
import com.yryz.quanhu.resource.release.buyrecord.dto.ReleaseBuyRecordDto;
import com.yryz.quanhu.resource.release.buyrecord.entity.ReleaseBuyRecord;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/27 18:02
 * Created by lifan
 */
@Api(tags = "资源购买记录")
@RestController
@RequestMapping(value = "/services/app")
public class ReleaseBuyRecordController {

    @Reference
    private ReleaseBuyRecordApi releaseBuyRecordApi;

    @ApiOperation("查询购买记录分页列表")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @GetMapping(value = "{version}/release/buyrecord/list")
    public Response<PageList<ReleaseBuyRecord>> list(ReleaseBuyRecordDto releaseBuyRecordDto, @RequestHeader("userId") Long userId) {
        if (null == releaseBuyRecordDto) {
            throw new QuanhuException(ExceptionEnum.ValidateException.getCode(),
                    ExceptionEnum.ValidateException.getShowMsg(), "参数不能为空");
        }
        if (null == userId) {
            throw new QuanhuException(ExceptionEnum.ValidateException.getCode(),
                    ExceptionEnum.ValidateException.getShowMsg(), "用户ID不能为空");
        }
        if (null == releaseBuyRecordDto.getPageNo()) {
            throw new QuanhuException(ExceptionEnum.ValidateException.getCode(),
                    ExceptionEnum.ValidateException.getShowMsg(), "页码不能为空");
        }
        if (null == releaseBuyRecordDto.getPageSize()) {
            throw new QuanhuException(ExceptionEnum.ValidateException.getCode(),
                    ExceptionEnum.ValidateException.getShowMsg(), "每页条数不能为空");
        }
        releaseBuyRecordDto.setUserId(userId);
        return releaseBuyRecordApi.listPage(releaseBuyRecordDto);
    }
}
