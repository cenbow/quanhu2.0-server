package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.annotation.NotLogin;
import com.yryz.common.response.Response;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.other.category.api.CategoryAPI;
import com.yryz.quanhu.other.category.vo.CategoryCheckedVo;
import com.yryz.quanhu.other.category.vo.CategoryDiscoverVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author chengyunfei
 * @date 2018/1/23.
 */
@Api(description = "标签分类相关接口")
@RestController
public class CategoryController {
    @Reference(check = false, timeout = 30000)
    private CategoryAPI categoryAPI;

    @ApiOperation("分类列表(包含多个一二级分类用于发现页达人主页)")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/services/app/{version}/other/category/list")
    public Response<List<CategoryDiscoverVo>> list() {
        return categoryAPI.list();
    }

    @ApiOperation("通过分类ID查找相关分类列表(包含一个一级分类与多个二级分类用于发现页达人子页)")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/services/app/{version}/other/category/listById")
    public Response<CategoryDiscoverVo> listById(Long categoryId) {
        return categoryAPI.listById(categoryId);
    }

    @NotLogin
    @ApiOperation("获取向用户推荐的标签分类(引导页)")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/services/app/{version}/other/category/recommend")
    public Response<List<CategoryCheckedVo>> recommend() {
        return categoryAPI.recommend();
    }

}
