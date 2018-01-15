package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.user.service.DemoReadService;
import com.yryz.quanhu.user.service.DemoService;
import com.yryz.quanhu.user.vo.DemoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Api(description = "示例管理")
@RestController
public class DemoController {
    protected final Log logger = LogFactory.getLog(DemoController.class);

    @Reference
    private DemoService demoService;

    @Autowired
    private DemoReadService demoReadService;

    @ApiOperation("测试 根据id列表批量获取信息 逗号分隔")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/{version}/demo/test")
    public ReturnCode<List<DemoVo>> query(String ids) {
        String[] strIds = ids.split(",");
        List<Long> idList = new ArrayList<>();
        for (String strId : strIds) {
            idList.add(Long.parseLong(strId));
        }
        return new ReturnCode(demoReadService.find(idList));
    }

    @ApiOperation("测试 mybatis")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/{version}/demo/mybatis")
    public ReturnCode<String> mybatis() {
        demoService.test();
        return new ReturnCode("time: "+System.currentTimeMillis());
    }

    @ApiOperation("查询所有示例 已废弃")
    @GetMapping(value = "/v1/demo")
    @Deprecated
    public ReturnCode<List<DemoVo>> queryV1(String custId, Integer start, Integer limit) {
        return new ReturnCode();
    }

    @ApiOperation("查询所有示例")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/{version}/demo")
    public ReturnCode<List<DemoVo>> query(String custId, Integer start, Integer limit) {
        return new ReturnCode();
    }

    @ApiOperation("查询示例详情")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @GetMapping(value = "/{version}/demo/{id:\\d+}")
    public ReturnCode<DemoVo> query(@PathVariable Long id) {
        return new ReturnCode(demoReadService.find(id));
    }

    @ApiOperation("按条件AND查询示例")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @PostMapping(value = "/{version}/demo/search")
    public ReturnCode<List<DemoVo>> query(@RequestBody DemoVo demoVo) {
        return new ReturnCode();
    }

    @ApiOperation("添加示例")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @PostMapping(value = "/{version}/demo")
    public ReturnCode<DemoVo> insert(@RequestBody DemoVo demoVo) {
        return new ReturnCode(demoService.persist(demoVo));
    }

    @ApiOperation("删除示例")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @DeleteMapping(value = "/{version}/demo/{id:\\d+}")
    public ReturnCode<DemoVo> delete(@PathVariable Long id) {
        return new ReturnCode(demoService.remove(id));
    }

    @ApiOperation("修改示例")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @PutMapping(value = "/{version}/demo")
    public ReturnCode<DemoVo> update(@RequestBody DemoVo demoVo) {
        logger.info("update");
        return new ReturnCode(demoService.merge(demoVo));
    }
}