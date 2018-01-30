package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.annotation.UserBehaviorValidation;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.behavior.count.api.CountApi;
import com.yryz.quanhu.behavior.count.contants.BehaviorEnum;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.openapi.constants.ActivityCountConstant;
import com.yryz.quanhu.other.activity.api.ActivityCandidateApi;
import com.yryz.quanhu.other.activity.dto.ActivityVoteDto;
import com.yryz.quanhu.other.activity.vo.ActivityVoteConfigVo;
import com.yryz.quanhu.other.activity.vo.ActivityVoteDetailVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(description = "投票活动")
@RestController
public class ActivityCandidateController {

    @Reference(check = false, timeout = 30000)
    private ActivityCandidateApi activityCandidateApi;

    @Reference(check = false, timeout = 30000)
    private CountApi countApi;

    private static final Logger logger = LoggerFactory.getLogger(ActivityCandidateController.class);

    @UserBehaviorValidation
    @ApiOperation("确认参与")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @PostMapping(value = "services/app/{version}/activity/candidate/join")
    public Response join(@RequestBody ActivityVoteDto activityVoteDto, HttpServletRequest request) {
        String userId = request.getHeader("userId");
        Assert.hasText(userId, "userId不能为空");
        activityVoteDto.setCreateUserId(Long.valueOf(userId));
        Response response = activityCandidateApi.join(activityVoteDto);
        if(response.success()){
            try {
                countApi.commitCount(BehaviorEnum.Activity,Long.valueOf(userId), ActivityCountConstant.ACTIVITY_RECORD_COUNT,ActivityCountConstant.ACTIVITY_COUNT);
            } catch (Exception e) {
                logger.error("接入记数异常:", e);
            }
        }
        return response;
    }

    @UserBehaviorValidation
    @ApiOperation("参与投票活动")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "services/app/{version}/activity/candidate/config")
    public Response<ActivityVoteConfigVo> config(Long activityInfoId, HttpServletRequest request) {
        return activityCandidateApi.config(activityInfoId);
    }

    @UserBehaviorValidation
    @ApiOperation("参与者详情")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "services/app/{version}/activity/candidate/detail")
    public Response<ActivityVoteDetailVo> detail(ActivityVoteDto activityVoteDto, HttpServletRequest request) {
        String userId = request.getHeader("userId");
        if(!StringUtils.isEmpty(userId)){
            activityVoteDto.setCreateUserId(Long.valueOf(userId));
        }
        Response<ActivityVoteDetailVo> activityVoteDetailVoResponse = activityCandidateApi.detail(activityVoteDto);
        if(activityVoteDetailVoResponse.success()){
            try {
                countApi.commitCount(BehaviorEnum.RealRead,activityVoteDto.getCandidateId(),ActivityCountConstant.CANDIDATE_ACTIVITY_DETAIL,ActivityCountConstant.COUNT);
            } catch (Exception e) {
                logger.error("接入记数异常:", e);
            }
        }
        return activityVoteDetailVoResponse;
    }

    @UserBehaviorValidation
    @ApiOperation("参与者列表")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "services/app/{version}/activity/candidate/list")
    public Response<PageList<ActivityVoteDetailVo>> list(ActivityVoteDto activityVoteDto, HttpServletRequest request) {
        String userId = request.getHeader("userId");
        if(!StringUtils.isEmpty(userId)){
            activityVoteDto.setCreateUserId(Long.valueOf(userId));
        }
        return activityCandidateApi.list(activityVoteDto);
    }

    @UserBehaviorValidation
    @ApiOperation("排行榜")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "services/app/{version}/activity/candidate/rank")
    public Response<PageList<ActivityVoteDetailVo>> rank(ActivityVoteDto activityVoteDto, HttpServletRequest request) {
        String userId = request.getHeader("userId");
        if(!StringUtils.isEmpty(userId)){
            activityVoteDto.setCreateUserId(Long.valueOf(userId));
        }
        return activityCandidateApi.rank(activityVoteDto);
    }

}
