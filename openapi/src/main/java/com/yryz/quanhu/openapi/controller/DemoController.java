package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.user.service.DemoReadService;
import com.yryz.quanhu.user.service.DemoService;
import com.yryz.quanhu.user.vo.DemoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.yryz.common.constant.IdConstants.QUAN_HU_USER;


@Api(description = "示例管理")
@RestController
public class DemoController {
    protected final Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Reference(check = false)
    private DemoService demoService;

    @Reference(check = false)
    private IdAPI idAPI;

    @Autowired
    private DemoReadService demoReadService;

    @ApiOperation("测试 发号器服务")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/v1/demo/id")
    public Response<DemoVo> getId(String type) {
        for (int i = 0; i < 150; i++) {
            Long id = idAPI.getKid(QUAN_HU_USER);
            logger.info("getId type: {}, result: {}", type, id);
        }

        return ResponseUtils.returnObjectSuccess(new DemoVo());
    }


    @ApiOperation("测试 根据id列表批量获取信息 逗号分隔")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/{version}/demo/test")
    public Response<List<DemoVo>> query(String ids) {
        String[] strIds = ids.split(",");
        List<Long> idList = new ArrayList<>();
        for (String strId : strIds) {
            idList.add(Long.parseLong(strId));
        }
        return ResponseUtils.returnListSuccess(demoReadService.find(idList));
    }

    @ApiOperation("测试 mybatis")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/{version}/demo/mybatis")
    public Response<String> mybatis() {
        demoService.test();
        return ResponseUtils.returnObjectSuccess("time: " + System.currentTimeMillis());
    }

    @ApiOperation("查询所有示例 已废弃")
    @GetMapping(value = "/v1/demo")
    @Deprecated
    public Response<List<DemoVo>> queryV1(String custId, Integer start, Integer limit) {
        return ResponseUtils.returnListSuccess(new ArrayList<>());
    }

    @ApiOperation("查询所有示例")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/{version}/demo")
    public Response<List<DemoVo>> query(String custId, Integer start, Integer limit) {
        return ResponseUtils.returnListSuccess(new ArrayList<>());
    }

    @ApiOperation("查询示例详情")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @GetMapping(value = "/{version}/demo/{id:\\d+}")
    public Response<DemoVo> query(@PathVariable Long id) {
        return ResponseUtils.returnObjectSuccess(demoReadService.find(id));
    }

    @ApiOperation("按条件AND查询示例")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @PostMapping(value = "/{version}/demo/search")
    public Response<List<DemoVo>> query(@RequestBody DemoVo demoVo) {
        return ResponseUtils.returnListSuccess(new ArrayList<>());
    }

    @ApiOperation("添加示例")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @PostMapping(value = "/{version}/demo")
    public Response<DemoVo> insert(@RequestBody DemoVo demoVo) {
        return ResponseUtils.returnObjectSuccess(demoService.persist(demoVo));
    }

    @ApiOperation("删除示例")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @DeleteMapping(value = "/{version}/demo/{id:\\d+}")
    public Response<DemoVo> delete(@PathVariable Long id) {
        return ResponseUtils.returnObjectSuccess(demoService.remove(id));
    }

    @ApiOperation("修改示例")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @PutMapping(value = "/{version}/demo")
    public Response<DemoVo> update(@RequestBody DemoVo demoVo) {
        logger.info("update");
        return ResponseUtils.returnObjectSuccess(demoService.merge(demoVo));
    }
}