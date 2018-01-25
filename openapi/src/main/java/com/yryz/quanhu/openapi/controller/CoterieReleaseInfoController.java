package com.yryz.quanhu.openapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.annotation.NotLogin;
import com.yryz.common.response.Response;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.resource.coterie.release.info.api.CoterieReleaseInfoApi;
import com.yryz.quanhu.resource.coterie.release.info.vo.CoterieReleaseInfoVo;
import com.yryz.quanhu.resource.release.info.api.ReleaseInfoApi;
import com.yryz.quanhu.resource.release.info.entity.ReleaseInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author wangheng
 * @Description: 私圈文章接口
 * @date 2018年1月23日 下午3:58:17
 */
@Api(description = "私圈文章接口")
@RestController
@RequestMapping(value = "/services/app")
public class CoterieReleaseInfoController {

    @Reference(lazy = true, check = false, timeout = 10000)
    private CoterieReleaseInfoApi coterieReleaseInfoApi;

    @Reference(lazy = true, check = false, timeout = 10000)
    private ReleaseInfoApi ReleaseInfoApi;

    @ApiOperation("文章发布")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "record", paramType = "body", required = true),
            @ApiImplicitParam(name = "userId", paramType = "header", required = true) })
    @PostMapping(value = "{version}/coterie/release/info/single")
    public Response<ReleaseInfo> release(@RequestBody ReleaseInfo record, @RequestHeader("userId") Long headerUserId) {

        record.setCreateUserId(headerUserId);

        return coterieReleaseInfoApi.release(record);
    }

    @NotLogin
    @ApiOperation("文章详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "userId", paramType = "header", required = true) })
    @GetMapping(value = "{version}/coterie/release/info/detail")
    public Response<CoterieReleaseInfoVo> infoByKid(Long kid, @RequestHeader("userId") Long headerUserId) {
        return coterieReleaseInfoApi.infoByKid(kid, headerUserId);
    }

    @ApiOperation("文章删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "userId", paramType = "header", required = true) })
    @PostMapping(value = "{version}/coterie/release/info/delete")
    public Response<Integer> deleteBykid(Long kid, @RequestHeader("userId") Long headerUserId) {
        ReleaseInfo upInfo = new ReleaseInfo();
        upInfo.setKid(kid);
        upInfo.setLastUpdateUserId(headerUserId);

        return ReleaseInfoApi.deleteBykid(upInfo);
    }
}
