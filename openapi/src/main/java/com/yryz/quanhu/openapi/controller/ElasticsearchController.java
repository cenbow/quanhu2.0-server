package com.yryz.quanhu.openapi.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.dymaic.service.ElasticsearchService;
import com.yryz.quanhu.dymaic.vo.CoterieInfoVo;
import com.yryz.quanhu.dymaic.vo.ResourceInfoVo;
import com.yryz.quanhu.dymaic.vo.UserSimpleVo;
import com.yryz.quanhu.openapi.ApplicationOpenApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(description = "全局搜索接口")
@RestController
public class ElasticsearchController {
	@Reference
	private ElasticsearchService elasticsearchService;
	
	@ApiOperation("搜索用户")
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@PostMapping(value = "/{version}/search/user")
	public Response<PageList<UserSimpleVo>> searchUser(String keyWord,Integer page,Integer size, HttpServletRequest request) {
		if(StringUtils.isBlank(keyWord)){
			PageList<UserSimpleVo> list=new PageList<UserSimpleVo>();
			list.setCurrentPage(0);
			list.setEntities(new ArrayList<>());
			return ResponseUtils.returnObjectSuccess(list);
		}
		if(page==null ||page<0 ||page>5000){
			page=0;
		}
		if(size==null || size<1 ||size>100){
			size=10;
		}
		PageList<UserSimpleVo> list=elasticsearchService.searchUser(keyWord, page, size);
		return ResponseUtils.returnObjectSuccess(list);
	}
	
	@ApiOperation("搜索文章")
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@PostMapping(value = "/{version}/search/release")
	public Response<PageList<ResourceInfoVo>> searchArticle(String keyWord,Integer page,Integer size,HttpServletRequest request) {
		if(StringUtils.isBlank(keyWord)){
			PageList<ResourceInfoVo> list=new PageList<ResourceInfoVo>();
			list.setCurrentPage(0);
			list.setEntities(new ArrayList<>());
			return ResponseUtils.returnObjectSuccess(list);
		}
		if(page==null ||page<0 ||page>5000){
			page=0;
		}
		if(size==null || size<1 ||size>100){
			size=10;
		}
		PageList<ResourceInfoVo> list=elasticsearchService.searchReleaseInfo(keyWord, page, size);
		return ResponseUtils.returnObjectSuccess(list);
	}
	
	@ApiOperation("搜索话题")
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@PostMapping(value = "/{version}/search/topic")
	public Response<PageList<ResourceInfoVo>> searchTopic(String keyWord,Integer page,Integer size, HttpServletRequest request) {
		if(StringUtils.isBlank(keyWord)){
			PageList<ResourceInfoVo> list=new PageList<ResourceInfoVo>();
			list.setCurrentPage(0);
			list.setEntities(new ArrayList<>());
			return ResponseUtils.returnObjectSuccess(list);
		}
		if(page==null ||page<0 ||page>5000){
			page=0;
		}
		if(size==null || size<1 ||size>100){
			size=10;
		}
		PageList<ResourceInfoVo> list=elasticsearchService.searchTopicInfo(keyWord, page, size);
		return ResponseUtils.returnObjectSuccess(list);
	}
	
	@ApiOperation("搜索私圈")
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@PostMapping(value = "/{version}/search/coterie")
	public Response<PageList<CoterieInfoVo>> searchCoterie(String keyWord,Integer page,Integer size, HttpServletRequest request) {
		if(StringUtils.isBlank(keyWord)){
			PageList<CoterieInfoVo> list=new PageList<CoterieInfoVo>();
			list.setCurrentPage(0);
			list.setEntities(new ArrayList<>());
			return ResponseUtils.returnObjectSuccess(list);
		}
		if(page==null ||page<0 ||page>5000){
			page=0;
		}
		if(size==null || size<1 ||size>100){
			size=10;
		}
		PageList<CoterieInfoVo> list=elasticsearchService.searchCoterieInfo(keyWord, page, size);
		return ResponseUtils.returnObjectSuccess(list);
	}
}
