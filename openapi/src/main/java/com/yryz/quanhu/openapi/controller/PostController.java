package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.entity.RequestHeader;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.utils.WebUtil;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.resource.topic.api.TopicPostApi;
import com.yryz.quanhu.resource.topic.dto.TopicPostDto;
import com.yryz.quanhu.resource.topic.vo.TopicAndPostVo;
import com.yryz.quanhu.resource.topic.vo.TopicPostVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(description = "话题跟帖接口")
@RestController
public class PostController {

	@Reference
	private TopicPostApi topicPostApi;


	@ApiOperation("查询帖子详情")
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@GetMapping(value = "/{version}/post/single")
	public Response<TopicAndPostVo> queryTopicDetail(Long kid, HttpServletRequest request) {
		RequestHeader header = WebUtil.getHeader(request);
		return topicPostApi.quetyDetail(kid,null);
	}

	@ApiOperation("查询帖子列表")
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@GetMapping(value = "/{version}/post/list")
	public Response<PageList<TopicPostVo>> queryTopicPostList(Integer pageNum,Integer 	pageSize,Long topicId,
														  String orderBy,HttpServletRequest request) {
		RequestHeader header = WebUtil.getHeader(request);
		TopicPostDto dto=new TopicPostDto();
		dto.setPageNum(pageNum);
		dto.setPageSize(pageSize);
		dto.setTopicId(topicId);
		dto.setOrderBy(orderBy);
		return topicPostApi.listPost(dto);
	}


	@ApiOperation("发布帖子")
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues =ApplicationOpenApi.CURRENT_VERSION, required = true)
	@PostMapping(value = "/{version}/post/add")
	public Response<TopicPostVo> saveTopic(@RequestBody  TopicPostDto topicPostDto, HttpServletRequest request) {
		RequestHeader header = WebUtil.getHeader(request);
		topicPostDto.setCreateUserId(Long.valueOf(header.getUserId()));
		return topicPostApi.savePost(topicPostDto);
	}



}
