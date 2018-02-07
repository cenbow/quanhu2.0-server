package com.yryz.quanhu.openapi.controller;

import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import com.yryz.common.response.Response;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.resource.release.config.api.ReleaseConfigApi;
import com.yryz.quanhu.resource.release.config.vo.ReleaseConfigVo;
import com.yryz.quanhu.resource.release.constants.ReleaseConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
* @Description: 文章发布 模板
* @author wangheng
* @date 2018年1月23日 下午3:20:25
*/
@Api(description = "文章发布模板接口")
@RestController
@RequestMapping(value = "/services/app")
public class ReleaseConfigController {

    @Reference(lazy = true, check = false, timeout = 10000)
    ReleaseConfigApi releaseConfigApi;

    
    @ApiOperation("平台发布文章模板")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "{version}/release/config/template/app")
    public Response<ReleaseConfigVo> appTemplate() {
        return releaseConfigApi.getTemplate(ReleaseConstants.APP_DEFAULT_CLASSIFY_ID);
    }

    
    @ApiOperation("私圈发布文章模板")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "{version}/release/config/template/coterie")
    public Response<ReleaseConfigVo> coterieTemplate() {
        return releaseConfigApi.getTemplate(ReleaseConstants.COTERIE_DEFAULT_CLASSIFY_ID);
    }

    
    @ApiOperation("发布文章模板")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "classifyId", paramType = "path", required = true) })
    @GetMapping(value = "{version}/release/config/template/{classifyId}")
    public Response<ReleaseConfigVo> coterieTemplate(@PathParam("classifyId") Long classifyId) {
        return releaseConfigApi.getTemplate(classifyId);
    }
}
