package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.annotation.NotLogin;
import com.yryz.common.response.Response;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.support.activity.api.ActivitySignUpApi;
import com.yryz.quanhu.support.activity.entity.ActivityEnrolConfig;
import com.yryz.quanhu.support.activity.entity.ActivityRecord;
import com.yryz.quanhu.support.activity.vo.ActivitySignUpHomeAppVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by dell on 2018/1/22.
 */
@Api(description = "报名类活动相关接口")
@RestController
public class ActivitySignUpController {
    @Reference(check = false, timeout = 30000)
    private ActivitySignUpApi activitySignUpApi;

    @NotLogin
    @ApiOperation("报名活动详情")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = " /services/app/{version}/activity/signUp/activitySignUpHome")
    public Response<ActivitySignUpHomeAppVo> getActivityInfoVo(Long activityInfoId, @RequestHeader("custId") String custId, HttpServletRequest request) {
        Assert.notNull(activityInfoId, "activityInfoId is null");
        Assert.notNull(custId, "custId is null");
        return activitySignUpApi.getActivitySignUpHome(activityInfoId, custId);
    }

    @ApiOperation("确认报名-提交报名信息(token)")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @PostMapping(value = " /services/app/{version}/activity/signUp/activitySignUpSubmit")
    public Response<ActivityRecord> activitySignUpSubmit(@RequestBody ActivityRecord activityRecord, HttpServletRequest request) {
        Assert.notNull(activityRecord, "activityRecord is null");
        String custId = request.getHeader("custId");
        Assert.notNull(custId, "custId is null");
        return activitySignUpApi.activitySignUpSubmit(activityRecord, custId);
    }

    @ApiOperation("参与报名-获取活动配置")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = " /services/app/{version}/activity/signUp/activitySignUpFrom")
    public Response<ActivityEnrolConfig> getActivitySignUpFrom(Long activityInfoId, HttpServletRequest request) {
        Assert.notNull(activityInfoId, "activityInfoId is null");
        String custId = request.getHeader("custId");
        Assert.notNull(custId, "custId is null");
        return activitySignUpApi.getActivitySignUpFrom(activityInfoId, custId);
    }

    @ApiOperation("查询报名状态(token)")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = " /services/app/{version}/activity/signUp/activitySignUpStatus")
    public Response<Map<String,Integer>> getActivitySignUpStatus(Long activityInfoId, HttpServletRequest request) {
        Assert.notNull(activityInfoId, "activityInfoId is null");
        String custId = request.getHeader("custId");
        Assert.notNull(custId, "custId is null");
        return activitySignUpApi.getActivitySignUpStatus(activityInfoId,custId);
    }
}

