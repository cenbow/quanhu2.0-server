/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月18日
 * Id: ResourceController.java, 2018年1月18日 下午6:02:50 yehao
 */
package com.yryz.quanhu.openapi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
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
public class ResourceController {
	
	@Reference
	private ResourceApi resourceApi;
	
	
	@NotLogin
    @ApiOperation("首页资源推荐")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @GetMapping(value = "/{version}/appRecommend")
	public Response<List<ResourceVo>> appRecommend(@ApiParam("列表长度")String limit){
    	ResourceVo resourceVo = new ResourceVo();
    	return resourceApi.getResources(resourceVo, null, 0, 10, null, null);
	}
    
}
