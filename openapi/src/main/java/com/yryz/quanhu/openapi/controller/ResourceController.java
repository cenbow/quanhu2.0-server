/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018年1月18日
 * Id: ResourceController.java, 2018年1月18日 下午6:02:50 yehao
 */
package com.yryz.quanhu.openapi.controller;

import java.util.List;

import com.yryz.common.constant.ModuleContants;
import com.yryz.quanhu.resource.enums.ResourceEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.annotation.NotLogin;
import com.yryz.common.response.Response;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.resource.api.ResourceApi;
import com.yryz.quanhu.resource.vo.ResourceVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月18日 下午6:02:50
 * @Description 资源管理API实现
 */
@Api(tags = "资源管理")
@RestController
@RequestMapping(value = "/services/app")
public class ResourceController {

    @Reference(check = false)
    private ResourceApi resourceApi;


    @NotLogin
    @ApiOperation("首页资源推荐")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @GetMapping(value = "/{version}/resource/appRecommend")
    public Response<List<ResourceVo>> appRecommend(@ApiParam("开始长度") Integer pageNo, @ApiParam("列表长度") Integer pageSize) {
        int start = 0;
        if (pageSize == null) {
            pageSize = 10;
        }
        if (pageNo != null && pageNo > 0) {
            start = pageNo * pageSize;
        }
        return resourceApi.appRecommend(start, pageSize);
    }

    @NotLogin
    @ApiOperation("私圈首页动态")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @GetMapping(value = "/{version}/resource/coterieRecommend")
    public Response<List<ResourceVo>> coterieRecommend(@ApiParam("私圈ID") String coterieId, @ApiParam("开始长度") Integer pageNo, @ApiParam("列表长度") Integer pageSize) {
        int start = 0;
        if (pageSize == null) {
            pageSize = 10;
        }
        if (pageNo != null && pageNo > 0) {
            start = pageNo * pageSize;
        }
        ResourceVo resourceVo = new ResourceVo();
        resourceVo.setPublicState(ResourceEnum.PUBLIC_STATE_FALSE);
        resourceVo.setCoterieId(coterieId);
        return resourceApi.getResources(resourceVo, "createTime", start, pageSize, null, null);
    }
}