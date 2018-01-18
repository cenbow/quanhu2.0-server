package com.yryz.quanhu.openapi.controller;

import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.demo.entity.Product;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@Api(description = "产品管理 示例")
@RestController
public class ProductController {
    protected final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @ApiOperation("查询所有产品 已废弃")
    @GetMapping(value = "/v1/product")
    @Deprecated
    public Response<List<Product>> queryV1(Integer currentPage, Integer pageSize, Long count) {
        // 在数据结构设计完成后即可提供restful API
        return ResponseUtils.returnListSuccess(Arrays.asList(
                new Product(1001L, 1L, "测试产品1", "产品说明", "/images/1001.jpg"),
                new Product(1002L, 2L, "测试产品2", "产品说明", "/images/1002.jpg"),
                new Product(1003L, 1L, "测试产品3", "产品说明", "/images/1003.jpg")
        ));
    }

    @ApiOperation("查询所有产品")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/{version}/product")
    public Response<List<Product>> query(Integer currentPage, Integer pageSize, Long count) {
        return ResponseUtils.returnListSuccess(Arrays.asList(
                new Product(1001L, 1L, "测试产品1", "产品说明", "/images/1001.jpg"),
                new Product(1002L, 2L, "测试产品2", "产品说明", "/images/1002.jpg"),
                new Product(1003L, 1L, "测试产品3", "产品说明", "/images/1003.jpg")
        ));
    }

    @ApiOperation("查询产品详情")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @GetMapping(value = "/{version}/product/{id:\\d+}")
    public Response<Product> query(@PathVariable Long id) {
        return ResponseUtils.returnObjectSuccess(new Product(1001L, 1L, "测试产品1", "产品说明", "/images/1001.jpg"));
    }

    @ApiOperation("按条件AND查询产品")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @PostMapping(value = "/{version}/product/search")
    public Response<List<Product>> query(@RequestBody Product product) {
        return ResponseUtils.returnListSuccess(Arrays.asList(
                new Product(1001L, 1L, "测试产品1", "产品说明", "/images/1001.jpg"),
                new Product(1002L, 2L, "测试产品2", "产品说明", "/images/1002.jpg"),
                new Product(1003L, 1L, "测试产品3", "产品说明", "/images/1003.jpg")
        ));
    }

    @ApiOperation("添加产品")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @PostMapping(value = "/{version}/product")
    public Response<Product> insert(@RequestBody Product product) {
        return ResponseUtils.returnObjectSuccess(new Product(1001L, 1L, "测试产品1", "产品说明", "/images/1001.jpg"));
    }

    @ApiOperation("删除产品")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @DeleteMapping(value = "/{version}/product/{id:\\d+}")
    public Response<Product> delete(@PathVariable Long id) {
        return ResponseUtils.returnObjectSuccess(new Product(1001L, 1L, "测试产品1", "产品说明", "/images/1001.jpg"));
    }

    @ApiOperation("修改产品")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @PutMapping(value = "/{version}/product")
    public Response<Product> update(@RequestBody Product demoVo) {
        return ResponseUtils.returnObjectSuccess(new Product(1001L, 1L, "测试产品1", "产品说明", "/images/1001.jpg"));
    }
}