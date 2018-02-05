package com.yryz.quanhu.openapi.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
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
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(description = "全局搜索接口")
@RestController
@RequestMapping(value = "/services/app")
public class ElasticsearchController {
    
    @Reference
    private ElasticsearchService elasticsearchService;

    @ApiOperation("搜索用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "keyWord", paramType = "query", required = true),
            @ApiImplicitParam(name = "currentPage", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", paramType = "query", required = true) })
    @GetMapping(value = "/{version}/search/user")
    public Response<PageList<UserSimpleVo>> searchUser(@RequestParam String keyWord, @RequestParam Integer currentPage,
            @RequestParam Integer pageSize, HttpServletRequest request) {
        if (StringUtils.isBlank(keyWord)) {
            PageList<UserSimpleVo> list = new PageList<UserSimpleVo>();
            list.setCurrentPage(0);
            list.setEntities(new ArrayList<>());
            return ResponseUtils.returnObjectSuccess(list);
        }
        if (currentPage == null || currentPage < 1 || currentPage > 5000) {
            currentPage = 1;
        }
        if (pageSize == null || pageSize < 1 || pageSize > 100) {
            pageSize = 10;
        }
        currentPage = currentPage - 1;
        try {
            PageList<UserSimpleVo> list = ResponseUtils
                    .getResponseData(elasticsearchService.searchUser(keyWord, currentPage, pageSize));
            return ResponseUtils.returnObjectSuccess(list);
        } catch (Exception e) {
            throw new QuanhuException(ExceptionEnum.BusiException);
        }
    }

    @ApiOperation("搜索文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "keyWord", paramType = "query", required = true),
            @ApiImplicitParam(name = "currentPage", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", paramType = "query", required = true) })
    @GetMapping(value = "/{version}/search/release")
    public Response<PageList<ResourceInfoVo>> searchArticle(@RequestParam String keyWord,
            @RequestParam Integer currentPage, @RequestParam Integer pageSize, HttpServletRequest request) {
        if (StringUtils.isBlank(keyWord)) {
            PageList<ResourceInfoVo> list = new PageList<ResourceInfoVo>();
            list.setCurrentPage(0);
            list.setEntities(new ArrayList<>());
            return ResponseUtils.returnObjectSuccess(list);
        }
        if (currentPage == null || currentPage < 1 || currentPage > 5000) {
            currentPage = 1;
        }
        if (pageSize == null || pageSize < 1 || pageSize > 100) {
            pageSize = 10;
        }
        currentPage = currentPage - 1;
        try {
            PageList<ResourceInfoVo> list = ResponseUtils
                    .getResponseData(elasticsearchService.searchReleaseInfo(keyWord, currentPage, pageSize));
            return ResponseUtils.returnObjectSuccess(list);
        } catch (Exception e) {
            throw new QuanhuException(ExceptionEnum.BusiException);
        }
    }

    @ApiOperation("搜索话题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "keyWord", paramType = "query", required = true),
            @ApiImplicitParam(name = "currentPage", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", paramType = "query", required = true) })
    @GetMapping(value = "/{version}/search/topic")
    public Response<PageList<ResourceInfoVo>> searchTopic(@RequestParam String keyWord,
            @RequestParam Integer currentPage, @RequestParam Integer pageSize, HttpServletRequest request) {
        if (StringUtils.isBlank(keyWord)) {
            PageList<ResourceInfoVo> list = new PageList<ResourceInfoVo>();
            list.setCurrentPage(0);
            list.setEntities(new ArrayList<>());
            return ResponseUtils.returnObjectSuccess(list);
        }
        if (currentPage == null || currentPage < 1 || currentPage > 5000) {
            currentPage = 1;
        }
        if (pageSize == null || pageSize < 1 || pageSize > 100) {
            pageSize = 10;
        }
        currentPage = currentPage - 1;
        try {
            PageList<ResourceInfoVo> list = ResponseUtils
                    .getResponseData(elasticsearchService.searchTopicInfo(keyWord, currentPage, pageSize));
            return ResponseUtils.returnObjectSuccess(list);
        } catch (Exception e) {
            throw new QuanhuException(ExceptionEnum.BusiException);
        }
    }

    @ApiOperation("搜索私圈")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "keyWord", paramType = "query", required = true),
            @ApiImplicitParam(name = "currentPage", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", paramType = "query", required = true) })
    @GetMapping(value = "/{version}/search/coterie")
    public Response<PageList<CoterieInfoVo>> searchCoterie(@RequestParam String keyWord,
            @RequestParam Integer currentPage, @RequestParam Integer pageSize, HttpServletRequest request) {
        if (StringUtils.isBlank(keyWord)) {
            PageList<CoterieInfoVo> list = new PageList<CoterieInfoVo>();
            list.setCurrentPage(0);
            list.setEntities(new ArrayList<>());
            return ResponseUtils.returnObjectSuccess(list);
        }
        if (currentPage == null || currentPage < 1 || currentPage > 5000) {
            currentPage = 1;
        }
        if (pageSize == null || pageSize < 1 || pageSize > 100) {
            pageSize = 10;
        }
        currentPage = currentPage - 1;
        try {
            PageList<CoterieInfoVo> list = ResponseUtils
                    .getResponseData(elasticsearchService.searchCoterieInfo(keyWord, currentPage, pageSize));
            return ResponseUtils.returnObjectSuccess(list);
        } catch (Exception e) {
            throw new QuanhuException(ExceptionEnum.BusiException);
        }
    }
}
