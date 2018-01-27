package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.entity.RequestHeader;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.common.utils.WebUtil;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.resource.topic.api.TopicApi;
import com.yryz.quanhu.resource.topic.dto.TopicDto;
import com.yryz.quanhu.resource.topic.vo.TopicVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(description = "话题接口")
@RestController
public class TopicController {

	@Reference
	private TopicApi topicApi;

	@ApiOperation("查询话题主页")
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@GetMapping(value = "/services/app/{version}/topic/single")
	public Response<TopicVo> queryTopicDetail(Long kid, HttpServletRequest request) {
		RequestHeader header = WebUtil.getHeader(request);
		return topicApi.queryDetail(kid,null);
	}

	@ApiOperation("查询话题列表")
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@GetMapping(value = "/services/app/{version}/topic/list")
	public Response<PageList<TopicVo>> queryTopicList(Integer pageNum,Integer pageSize, Byte recommend ,String orderBy,Long coteriaId ,HttpServletRequest request) {
		TopicDto dto=new TopicDto();
		dto.setPageSize(pageSize);
		dto.setPageNum(pageNum);
		dto.setOrderBy(orderBy);
		dto.setCoterieId(String.valueOf(coteriaId));
		dto.setRecommend(recommend);
		return topicApi.queryTopicList(dto);
	}


	@ApiOperation("圈主删除话题")
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@PostMapping(value = "/services/app/{version}/topic/single/delete")
	public Response<Integer> deleteTopic(@RequestBody TopicDto dto, HttpServletRequest request) {
		RequestHeader header = WebUtil.getHeader(request);
		String userId=header.getUserId();
		if(StringUtils.isBlank(userId)){
			return ResponseUtils.returnException(QuanhuException.busiError(ExceptionEnum.PARAM_MISSING.getCode(),"缺失用户编号"));
		}
		return topicApi.deleteTopic(dto.getKid(),Long.valueOf(userId));
	}






}
