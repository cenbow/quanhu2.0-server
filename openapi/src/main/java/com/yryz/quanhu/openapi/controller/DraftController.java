/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年2月6日
 * Id: DraftController.java, 2018年2月6日 下午5:44:14 yehao
 */
package com.yryz.quanhu.openapi.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.annotation.NotLogin;
import com.yryz.common.annotation.UserBehaviorValidation;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.resource.draft.api.DraftApi;
import com.yryz.quanhu.resource.release.info.dto.ReleaseInfoDto;
import com.yryz.quanhu.resource.release.info.entity.ReleaseInfo;
import com.yryz.quanhu.resource.release.info.vo.ReleaseInfoVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年2月6日 下午5:44:14
 * @Description 草稿箱发布接口API
 */
@Api(description = "平台草稿箱")
@RestController
@RequestMapping(value = "/services/app")
public class DraftController {
	
	@Reference
	DraftApi draftApi;
	
    @ApiOperation("文章发布")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "record", paramType = "body", required = true),
            @ApiImplicitParam(name = "userId", paramType = "header", required = true),
            @ApiImplicitParam(name = "token", paramType = "header", required = true) })
    @UserBehaviorValidation(event = "文章发布", login = true, mute = true)
    @PostMapping(value = "{version}/draft/info/single")
    public Response<ReleaseInfo> release(HttpServletRequest request, @RequestBody ReleaseInfo record,
            @RequestHeader("userId") Long headerUserId) {

        record.setCreateUserId(headerUserId);

        return draftApi.release(record);
    }
    
    @ApiOperation("文章编辑(kid必填，其他值跟insert一样)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "record", paramType = "body", required = true),
            @ApiImplicitParam(name = "userId", paramType = "header", required = true),
            @ApiImplicitParam(name = "token", paramType = "header", required = true) })
    @UserBehaviorValidation(event = "文章编辑", login = true, mute = true)
    @PostMapping(value = "{version}/draft/info/edit")
    public Response<ReleaseInfo> edit(HttpServletRequest request, @RequestBody ReleaseInfo record,
            @RequestHeader("userId") Long headerUserId) {

//        record.setCreateUserId(headerUserId);

        return draftApi.edit(record);
    }

    
    @ApiOperation("文章详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "userId", paramType = "header", required = false),
            @ApiImplicitParam(name = "kid", paramType = "query", required = true) })
    @GetMapping(value = "{version}/draft/info/detail")
    public Response<ReleaseInfoVo> infoByKid(@RequestParam Long kid,
            @RequestHeader(name = "userId", required = false) Long headerUserId) {
        return draftApi.infoByKid(kid, headerUserId);
    }

    @ApiOperation("文章删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "userId", paramType = "header", required = true),
            @ApiImplicitParam(name = "token", paramType = "header", required = true),
            @ApiImplicitParam(name = "kid", paramType = "query", required = true) })
    @UserBehaviorValidation(event = "文章删除", login = true)
    @PostMapping(value = "{version}/draft/info/delete")
    public Response<Integer> deleteBykid(HttpServletRequest request, @RequestParam Long kid,
            @RequestHeader("userId") Long headerUserId) {
        ReleaseInfo upInfo = new ReleaseInfo();
        upInfo.setKid(kid);
        upInfo.setLastUpdateUserId(headerUserId);

        return draftApi.deleteBykid(upInfo);
    }

    @ApiOperation("文章列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "userId", paramType = "header", required = true) })
    @UserBehaviorValidation(event = "文章列表", login = true)
    @GetMapping(value = "{version}/draft/info/list")
    public Response<PageList<ReleaseInfoVo>> list(ReleaseInfoDto dto, @RequestHeader("userId") Long headerUserId) {
        // 只查询 平台文章
        dto.setCoterieId(0L);
        return draftApi.pageByCondition(dto, headerUserId, false, true);
    }

}
