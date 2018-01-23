package com.yryz.quanhu.openapi.controller;

import javax.ws.rs.HeaderParam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.annotation.NotLogin;
import com.yryz.common.constant.CommonConstants;
import com.yryz.common.response.Response;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.resource.release.constants.ReleaseConstants;
import com.yryz.quanhu.resource.release.info.api.ReleaseInfoApi;
import com.yryz.quanhu.resource.release.info.entity.ReleaseInfo;
import com.yryz.quanhu.resource.release.info.vo.ReleaseInfoVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
* @Description: 平台文章发布
* @author wangheng
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
            @ApiImplicitParam(name = "userId", paramType = "header", required = true) })
    @PostMapping(value = "{version}/release/info/single")
    public Response<ReleaseInfo> release(@RequestBody ReleaseInfo record, @HeaderParam("userId") Long headerUserId) {
        record.setClassifyId(ReleaseConstants.APP_DEFAULT_CLASSIFY_ID);

        record.setCreateUserId(headerUserId);
        record.setDelFlag(CommonConstants.DELETE_NO);
        record.setShelveFlag(CommonConstants.SHELVE_YES);
        return releaseInfoApi.release(record);
    }

    @NotLogin
    @ApiOperation("文章详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "userId", paramType = "header", required = true) })
    @GetMapping(value = "{version}/release/info/detail")
    public Response<ReleaseInfoVo> infoByKid(Long kid, @HeaderParam("userId") Long headerUserId) {
        return releaseInfoApi.infoByKid(kid, headerUserId);
    }

    @ApiOperation("文章删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "userId", paramType = "header", required = true) })
    @PostMapping(value = "{version}/release/info/delete")
    public Response<Integer> deleteBykid(Long kid, @HeaderParam("userId") Long headerUserId) {
        ReleaseInfo upInfo = new ReleaseInfo();
        upInfo.setKid(kid);
        upInfo.setLastUpdateUserId(headerUserId);
        upInfo.setDelFlag(CommonConstants.DELETE_YES);
        return releaseInfoApi.deleteBykid(upInfo);
    }
}
