package com.yryz.quanhu.openapi.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.annotation.NotLogin;
import com.yryz.common.annotation.UserBehaviorValidation;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.resource.release.info.api.ReleaseInfoApi;
import com.yryz.quanhu.resource.release.info.dto.ReleaseInfoDto;
import com.yryz.quanhu.resource.release.info.entity.ReleaseInfo;
import com.yryz.quanhu.resource.release.info.vo.ReleaseInfoVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author wangheng
 * @Description: 平台文章发布
 * @date 2018年1月23日 下午3:58:17
 */
@Api(description = "平台文章发布接口")
@RestController
@RequestMapping(value = "/services/app")
public class ReleaseInfoController {

    @Reference(lazy = true, check = false, timeout = 10000)
    private ReleaseInfoApi releaseInfoApi;

    @ApiOperation("文章发布")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "record", paramType = "body", required = true),
            @ApiImplicitParam(name = "userId", paramType = "header", required = true),
            @ApiImplicitParam(name = "token", paramType = "header", required = true) })
    @UserBehaviorValidation(event = "文章发布", login = true, mute = true)
    @PostMapping(value = "{version}/release/info/single")
    public Response<ReleaseInfo> release(HttpServletRequest request, @RequestBody ReleaseInfo record,
            @RequestHeader("userId") Long headerUserId) {

        record.setCreateUserId(headerUserId);

        return releaseInfoApi.release(record);
    }

    @NotLogin
    @ApiOperation("文章详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "userId", paramType = "header", required = false) })
    @GetMapping(value = "{version}/release/info/detail")
    public Response<ReleaseInfoVo> infoByKid(Long kid,
            @RequestHeader(name = "userId", required = false) Long headerUserId) {
        return releaseInfoApi.infoByKid(kid, headerUserId);
    }

    @ApiOperation("文章删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "userId", paramType = "header", required = true),
            @ApiImplicitParam(name = "token", paramType = "header", required = true) })
    @UserBehaviorValidation(event = "文章删除", login = true)
    @PostMapping(value = "{version}/release/info/delete")
    public Response<Integer> deleteBykid(HttpServletRequest request, Long kid, @RequestHeader("userId") Long headerUserId) {
        ReleaseInfo upInfo = new ReleaseInfo();
        upInfo.setKid(kid);
        upInfo.setLastUpdateUserId(headerUserId);

        return releaseInfoApi.deleteBykid(upInfo);
    }

    @ApiOperation("文章列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "userId", paramType = "header", required = true) })
    @UserBehaviorValidation(event = "文章列表", login = true)
    @GetMapping(value = "{version}/release/info/list")
    public Response<PageList<ReleaseInfoVo>> list(ReleaseInfoDto dto, @RequestHeader("userId") Long headerUserId) {
        return releaseInfoApi.pageByCondition(dto, headerUserId, false, true);
    }
}
