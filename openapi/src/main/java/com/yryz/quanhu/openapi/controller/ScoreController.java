/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月18日
 * Id: ResourceController.java, 2018年1月18日 下午6:02:50 yehao
 */
package com.yryz.quanhu.openapi.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.annotation.NotLogin;
import com.yryz.common.entity.RequestHeader;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.common.utils.WebUtil;
import com.yryz.quanhu.grow.entity.GrowFlowQuery;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.resource.api.ResourceApi;
import com.yryz.quanhu.score.entity.ScoreFlowQuery;
import com.yryz.quanhu.score.enums.EventEnum;
import com.yryz.quanhu.score.service.EventAPI;
import com.yryz.quanhu.score.service.EventAcountApiService;
import com.yryz.quanhu.score.service.EventSignApiService;
import com.yryz.quanhu.score.vo.EventAcount;
import com.yryz.quanhu.score.vo.EventInfo;
import com.yryz.quanhu.score.vo.EventReportVo;
import com.yryz.quanhu.score.vo.EventSign;
import com.yryz.quanhu.score.vo.GrowFlowReportVo;
import com.yryz.quanhu.score.vo.ScoreFlowReportVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @author syc
 * @version 2.0
 * @date 2018年1月25日 上午11:02:50
 * @Description 积分成长值管理API实现
 */
@Api(tags = "积分管理")
@RestController
@RequestMapping(value="services/app")
public class ScoreController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Reference
	private EventAPI eventAPI;
	@Reference
	private ResourceApi resourceApi;

	@Reference
	EventAcountApiService eventAcountApiService;
	
	@Reference
	EventSignApiService eventSignApiService;
	
	
	
	@NotLogin
    @ApiOperation("积分事件")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@PostMapping(value = "/{version}/score/commit")
	public void commit(@RequestBody EventInfo event){
		//EventInfo eventInfo = new EventInfo();
		eventAPI.commit(event);
	}
	
	@NotLogin
    @ApiOperation("积分统计查询")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/{version}/score/getScoreFlowList")
	public Response<EventReportVo> getScoreFlowList(EventInfo event){
		return eventAPI.getScoreFlowList(event);
	}


    @ApiOperation("获取用户事件账户记录")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/{version}/score/acount")
	public Response<?> getEventAcount(String userId){
		EventAcount ea = eventAcountApiService.getEventAcount(userId);
		return ResponseUtils.returnObjectSuccess(ea);
	}
	
	@NotLogin
    @ApiOperation("签到")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@GetMapping(value = "/{version}/sign")
//	@RequestMapping(path="/sign"  , method = { RequestMethod.POST, RequestMethod.OPTIONS })
//	@ResponseBody
	public Response<?> sign(HttpServletRequest request){
		RequestHeader header = WebUtil.getHeader(request);
		EventInfo ei  = new EventInfo();
		if(header.getUserId() == null || StringUtils.isEmpty(header.getUserId())){

			return ResponseUtils.returnCommonException("用户id不能为空");
		}
		ei.setUserId(header.getUserId().toString().trim());
		ei.setEventCode(EventEnum.SIGN_IN.getCode());
		ei.setEventNum(1);
		eventSignApiService.commitSignEvent(ei);
		return  ResponseUtils.returnSuccess();
	}
	
	
	@NotLogin
    @ApiOperation("获取签到状态")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/{version}/sign/status")
//	@RequestMapping(path="/sign/status" , method = { RequestMethod.POST, RequestMethod.OPTIONS })
//	@ResponseBody
	public Response<?> getSignInfo(HttpServletRequest request){
		RequestHeader header = WebUtil.getHeader(request);
		EventSign es = new EventSign();
	   if(header.getUserId() == null || StringUtils.isEmpty(header.getUserId())){
			return ResponseUtils.returnCommonException("用户id不能为空");
	    }
	   es = eventAcountApiService.getEventSign(header.getUserId().toString().trim(), "15");
	try{
		return ResponseUtils.returnObjectSuccess(es);
	} catch (Exception e) {
		logger.error("unKown Exception", e);
		return ResponseUtils.returnException(e);
	}	
	}

	@NotLogin
    @ApiOperation("获取积分明细")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@GetMapping(value = "/{version}/score/flow")
//	@RequestMapping(path="/score/flow" , method = { RequestMethod.POST, RequestMethod.OPTIONS })
//	@ResponseBody
	public Response<PageList<ScoreFlowReportVo>> getScoreFlow( ScoreFlowQuery sfq ){
		//PageList<ScoreFlow> sfslist = eventAcountApiService.getScoreFlow(sfq);
		//return ReturnModel.listToString(sfs);
		return eventAcountApiService.getScoreFlow(sfq);
		 //  return ResponseUtils.returnObjectSuccess(sfslist);
	}
	
	
	@NotLogin
    @ApiOperation("获取全部积分明细")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@GetMapping(value = "/{version}/score/flow/list")
	public Response<List<ScoreFlowReportVo>> getScoreFlowALL( ScoreFlowQuery sfq ){
		return eventAcountApiService.getScoreFlowAll( sfq );
	}
	
	
	@NotLogin
    @ApiOperation("获取成长明细")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@GetMapping(value = "/{version}/grow/flow")
	public Response<PageList<GrowFlowReportVo>> getGrowFlow( GrowFlowQuery gfq){
		return eventAcountApiService.getGrowFlow(gfq);
	}
	
	
	@NotLogin
    @ApiOperation("获取全部成长明细")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@GetMapping(value = "/{version}/grow/flow/list")
	public Response<List<GrowFlowReportVo>> getGrowFlowALL( GrowFlowQuery gfq){
		return eventAcountApiService.getGrowFlowAll(gfq);
	}
	


}
