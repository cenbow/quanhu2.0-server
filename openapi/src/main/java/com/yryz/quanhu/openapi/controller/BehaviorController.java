package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;

import com.yryz.common.annotation.UserBehaviorArgs;
import com.yryz.common.annotation.UserBehaviorValidation;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.behavior.count.api.CountApi;
import com.yryz.quanhu.behavior.count.api.CountFlagApi;
import com.yryz.quanhu.behavior.count.contants.BehaviorEnum;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.openapi.validation.valid.UnifyParameterValidHandler;
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

    
    @ApiOperation("查询计数")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/services/app/{version}/behavior/getCount")
    public Response<Map<String, Long>> getCount(@RequestParam String countType, @RequestParam Long kid, HttpServletRequest request) {
        return countApi.getCount(countType, kid, null);
    }

    
//    @ApiOperation("提交事件")
//    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
//    @PostMapping(value = "/services/app/{version}/behavior/commitCount")
//    public Response<Object> countCommit(@RequestBody Map<String, Object> map) {
//        return countApi.commitCount(BehaviorEnum.Read, new Long(map.get("kid").toString()), "", 20L);
//    }

    
    @ApiOperation("查询统计以及状态")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/services/app/{version}/behavior/getCountFlag")
    Response<Map<String, Long>> getCountFlag(@RequestHeader Long userId, @RequestParam String countType, @RequestParam Long kid, HttpServletRequest request) {
        if (userId == null) {
            return ResponseUtils.returnException(QuanhuException.busiError("用户ID为空"));
        }
        return countApi.getCountFlag(countType, kid, "", userId);
    }


    @ApiOperation("行为预校验")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @PostMapping(value = "/services/app/{version}/behavior/preverify")

    @UserBehaviorValidation(validClass = UnifyParameterValidHandler.class)
    @UserBehaviorArgs(contexts = {"map.login","map.mute","map.blacklist","map.isCoterieMember","map.isCoterieMute"},
            sourceUserId = "map.sourceUserId",coterieId = "map.coterieId")
    public Response<Boolean> behaviorPreVerify(HttpServletRequest request, @RequestBody Map<String,Object> map){
        /**
         * 校验在切面校验通过后，直接返回true
         */
        return ResponseUtils.returnObjectSuccess(true);
    }


}
