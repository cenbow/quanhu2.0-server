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
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.resource.api.ResourceApi;
import com.yryz.quanhu.score.service.EventAPI;
import com.yryz.quanhu.score.vo.EventInfo;
import com.yryz.quanhu.score.vo.EventReportVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author syc
 * @version 2.0
 * @date 2018年1月18日 下午6:02:50
 * @Description 资源管理API实现
 */
@Api(tags = "积分管理")
@RestController
public class ScoreController {
	
	@Reference
	private EventAPI eventAPI;
	@Reference
	private ResourceApi resourceApi;
	
	
	@NotLogin
    @ApiOperation("积分事件")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/{version}/commit")
	public void commit(@ApiParam("事件对象")EventInfo event){
		EventInfo eventInfo = new EventInfo();
		eventAPI.commit(eventInfo);
	}
	
	@NotLogin
    @ApiOperation("积分流水查询")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/{version}/getScoreFlowList")
	public List<EventReportVo> getScoreFlowList(@ApiParam("事件对象")EventInfo event){
		EventInfo eventInfo = new EventInfo();
	    return eventAPI.getScoreFlowList(eventInfo);
	}
	

    
}
