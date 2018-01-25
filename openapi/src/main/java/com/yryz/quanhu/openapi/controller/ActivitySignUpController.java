package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.annotation.NotLogin;
import com.yryz.common.response.Response;
import com.yryz.quanhu.behavior.count.api.CountApi;
import com.yryz.quanhu.behavior.count.contants.BehaviorEnum;
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
    @Reference(check = false, timeout = 30000)
    private CountApi countApi;

    public static final String SIGNUP_ACTIVITY_DETAIL = "SIGNUP-ACTIVITY-DETAIL";
    public static final Long COUNT = 1L;
    public static final String ACTIVITY_RECORD_COUNT = "ACTIVITY-RECORD-COUNT";
    public static final Long ACTIVITY_COUNT = 1L;
    @NotLogin
    @ApiOperation("报名活动详情")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/services/app/{version}/activity/signUp/activitySignUpHome")
    public Response<ActivitySignUpHomeAppVo> getActivityInfoVo(Long activityInfoId, @RequestHeader("userId") String userId, HttpServletRequest request) {
        Assert.notNull(activityInfoId, "activityInfoId is null");
        Assert.notNull(userId, "userId is null");
        Response<ActivitySignUpHomeAppVo> activitySignUpHomeAppVo = activitySignUpApi.getActivitySignUpHome(activityInfoId, userId);
        countApi.commitCount(BehaviorEnum.RealRead,activityInfoId,SIGNUP_ACTIVITY_DETAIL,COUNT);
        return activitySignUpHomeAppVo;
    }

    @ApiOperation("确认报名-提交报名信息(token)")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @PostMapping(value = "/services/app/{version}/activity/signUp/activitySignUpSubmit")
    public Response<ActivityRecord> activitySignUpSubmit(@RequestBody ActivityRecord activityRecord,@RequestHeader("userId") String userId, HttpServletRequest request) {
        Assert.notNull(activityRecord, "activityRecord is null");
        Assert.notNull(userId, "userId is null");
        Response<ActivityRecord> activityRecordResponse = activitySignUpApi.activitySignUpSubmit(activityRecord, userId);
        countApi.commitCount(BehaviorEnum.Activity,Long.valueOf(userId),ACTIVITY_RECORD_COUNT,ACTIVITY_COUNT);
        return activityRecordResponse;
    }

    @ApiOperation("参与报名-获取活动配置")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/services/app/{version}/activity/signUp/activitySignUpFrom")
    public Response<ActivityEnrolConfig> getActivitySignUpFrom(Long activityInfoId,@RequestHeader("userId") String userId, HttpServletRequest request) {
        Assert.notNull(activityInfoId, "activityInfoId is null");
        Assert.notNull(userId, "userId is null");
        return activitySignUpApi.getActivitySignUpFrom(activityInfoId, userId);
    }

    @ApiOperation("查询报名状态(token)")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/services/app/{version}/activity/signUp/activitySignUpStatus")
    public Response<Map<String,Integer>> getActivitySignUpStatus(Long activityInfoId,@RequestHeader("userId") String userId, HttpServletRequest request) {
        Assert.notNull(activityInfoId, "activityInfoId is null");
        Assert.notNull(userId, "userId is null");
        return activitySignUpApi.getActivitySignUpStatus(activityInfoId,userId);
    }
}

