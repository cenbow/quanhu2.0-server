package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.annotation.NotLogin;
import com.yryz.common.response.Response;
import com.yryz.quanhu.behavior.count.api.CountApi;
import com.yryz.quanhu.behavior.count.api.CountFlagApi;
import com.yryz.quanhu.behavior.count.contants.BehaviorEnum;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: liupan
 * @Path: com.yryz.quanhu.openapi.controller
 * @Desc: 行为系统
 * @Date: 2018/1/24.
 */
@Api(tags = "行为系统")
@RestController
public class BehaviorController {
    @Reference
    private CountApi countApi;

    @Reference(check = false)
    private CountFlagApi countFlagApi;

    @NotLogin
    @ApiOperation("查询计数")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/services/app/{version}/behavior/getCount")
    public Response<Map<String, Long>> getCount(@RequestParam String countType, @RequestParam Long kid, HttpServletRequest request) {
        return countApi.getCount(countType, kid, null);
    }

    @NotLogin
    @ApiOperation("提交事件")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @PostMapping(value = "/services/app/{version}/behavior/commitCount")
    public Response<Object> countCommit(@RequestBody Map<String, Object> map) {
        return countApi.commitCount(BehaviorEnum.Read, new Long(map.get("kid").toString()), "", 20L);
    }

    @NotLogin
    @ApiOperation("查询统计以及状态")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @PostMapping(value = "/services/app/{version}/behavior/countFlag")
    Response<Map<String, Long>> getAllCountFlag(@RequestBody Map<String,Object> map,@RequestHeader Long userId){
        Map<String,Object> maps=new HashMap<String, Object>();
        maps.put("userId",userId);
        maps.put("resourceId",map.get("resourceId"));
        maps.put("moduleEnum",map.get("moduleEnum"));
        return  countFlagApi.getAllCountFlag(map.get("countType").toString(),Long.valueOf(map.get("kid").toString()),map.get("page").toString(),maps);
    }


}
