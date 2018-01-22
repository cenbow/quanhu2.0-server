package com.yryz.quanhu.openapi.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.user.vo.UserSimpleVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(description = "全局搜索接口")
@RestController
public class ElasticsearchController {
	@ApiOperation("搜索用户")
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@PostMapping(value = "/{version}/search/user")
	public Response<UserSimpleVO> searchUser(String keyWord, HttpServletRequest request) {
		List<UserSimpleVO> list=Lists.newArrayList();
		list.add(new UserSimpleVO());
		return ResponseUtils.returnListSuccess(list);
	}
	
	@ApiOperation("搜索文章")
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@PostMapping(value = "/{version}/search/article")
	public Response<UserSimpleVO> searchArticle(String keyWord, HttpServletRequest request) {
		List<UserSimpleVO> list=Lists.newArrayList();
		list.add(new UserSimpleVO());
		return ResponseUtils.returnListSuccess(list);
	}
	
	@ApiOperation("搜索话题")
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@PostMapping(value = "/{version}/search/topic")
	public Response<UserSimpleVO> searchTopic(String keyWord, HttpServletRequest request) {
		List<UserSimpleVO> list=Lists.newArrayList();
		list.add(new UserSimpleVO());
		return ResponseUtils.returnListSuccess(list);
	}
	
	@ApiOperation("搜索私圈")
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@PostMapping(value = "/{version}/search/coterie")
	public Response<UserSimpleVO> searchCoterie(String keyWord, HttpServletRequest request) {
		List<UserSimpleVO> list=Lists.newArrayList();
		list.add(new UserSimpleVO());
		return ResponseUtils.returnListSuccess(list);
	}
}
