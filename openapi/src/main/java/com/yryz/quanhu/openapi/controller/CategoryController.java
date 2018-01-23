package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.annotation.NotLogin;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.utils.PatternUtils;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.support.activity.api.ActivityInfoApi;
import com.yryz.quanhu.support.activity.vo.ActivityInfoAppListVo;
import com.yryz.quanhu.support.activity.vo.ActivityInfoVo;
import com.yryz.quanhu.support.category.api.CategoryAPI;
import com.yryz.quanhu.support.category.vo.CategoryCheckedVo;
import com.yryz.quanhu.support.category.vo.CategoryDiscoverVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author chengyunfei
 * @date 2018/1/23.
 */
@Api(description="标签分类相关接口")
@RestController
public class CategoryController {
    @Reference(check = false, timeout = 30000)
    private CategoryAPI categoryAPI;

    @NotLogin
    @ApiOperation("分类列表(包含多个一二级分类用于发现页达人主页)")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/services/app/{version}/support/category/list")
    public  Response<List<CategoryDiscoverVo>> list() {
       return categoryAPI.list();
    }

    @NotLogin
    @ApiOperation("通过分类ID查找相关分类列表(包含一个一级分类与多个二级分类用于发现页达人子页)")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/services/app/{version}/support/category/listById")
    public  Response<CategoryDiscoverVo> listById(Long categoryId) {
        return categoryAPI.listById(categoryId);
    }

    @ApiOperation("获取向用户推荐的标签分类(引导页)")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/services/app/{version}/support/category/recommend")
    public  Response<List<CategoryCheckedVo>> recommend() {
        return categoryAPI.recommend();
    }

    @ApiOperation("设置用户标签分类(引导页 button 选好了)")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @PostMapping(value = "/services/app/{version}/support/category/save")
    public  Response save(String ids) {
        return categoryAPI.save(ids);
    }
}
