package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.annotation.UserBehaviorValidation;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.behavior.count.api.CountApi;
import com.yryz.quanhu.behavior.count.contants.BehaviorEnum;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.openapi.constants.ActivityCountConstant;
import com.yryz.quanhu.other.activity.api.ActivityVoteApi;
import com.yryz.quanhu.other.activity.dto.ActivityVoteDto;
import com.yryz.quanhu.other.activity.entity.ActivityUserPrizes;
import com.yryz.quanhu.other.activity.entity.ActivityVoteRecord;
import com.yryz.quanhu.other.activity.vo.ActivityPrizesVo;
import com.yryz.quanhu.other.activity.vo.ActivityUserPrizesVo;
import com.yryz.quanhu.other.activity.vo.ActivityVoteInfoVo;
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
import java.util.Map;

@Api(description = "投票活动")
@RestController
public class ActivityVoteController {

    @Reference(check = false, timeout = 30000)
    private ActivityVoteApi activityVoteApi;

    @Reference(check = false, timeout = 30000)
    private CountApi countApi;

    private static final Logger logger = LoggerFactory.getLogger(ActivityVoteController.class);

    @UserBehaviorValidation
    @ApiOperation("投票活动详情")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "services/app/{version}/activity/vote/detail")
    public Response<ActivityVoteInfoVo> detail(Long activityInfoId, HttpServletRequest request) {
        Long userId = null;
        if(!StringUtils.isEmpty(request.getHeader("userId"))){
            userId = Long.valueOf(request.getHeader("userId"));
        }
        Response<ActivityVoteInfoVo> activityVoteInfoVoResponse = activityVoteApi.detail(activityInfoId, userId);
        if(activityVoteInfoVoResponse.success()){
            try {
                countApi.commitCount(BehaviorEnum.RealRead,activityInfoId, ActivityCountConstant.VOTE_ACTIVITY_DETAIL,ActivityCountConstant.COUNT);
            } catch (Exception e) {
                logger.error("接入记数异常:", e);
            }
        }

        return activityVoteInfoVoResponse;
    }

    @UserBehaviorValidation(login=true)
    @ApiOperation("确认投票")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @PostMapping(value = "services/app/{version}/activity/vote/single")
    public Response<Map<String, Object>> single(@RequestBody ActivityVoteRecord record, HttpServletRequest request) {
        String userId = request.getHeader("userId");
        Assert.hasText(userId, "userId不能为空");
        record.setCreateUserId(Long.valueOf(userId));
        return activityVoteApi.single(record);
    }

    @UserBehaviorValidation(login=true)
    @ApiOperation("奖品列表")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "services/app/{version}/activity/vote/prizeslist")
    public Response<PageList<ActivityPrizesVo>> prizeslist(ActivityVoteDto activityVoteDto, HttpServletRequest request) {
        return activityVoteApi.prizeslist(activityVoteDto);
    }

    @UserBehaviorValidation(login=true)
    @ApiOperation("领取奖品")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @PostMapping(value = "services/app/{version}/activity/vote/getPrize")
    public Response<ActivityUserPrizesVo> getPrize(@RequestBody ActivityVoteDto activityVoteDto, HttpServletRequest request) {
        String userId = request.getHeader("userId");
        Assert.hasText(userId, "userId不能为空");
        return activityVoteApi.getPrize(activityVoteDto.getActivityInfoId(), activityVoteDto.getPhone(), Long.valueOf(userId));
    }

    @UserBehaviorValidation
    @ApiOperation("无奖品文案")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "services/app/{version}/activity/vote/noPrize")
    public Response<Map<String, String>> noPrize(Long activityInfoId, HttpServletRequest request) {
        return activityVoteApi.noPrize(activityInfoId);
    }

    /**
     * 我的卡劵
     * @param   activityVoteDto
     * @return
     * */
    @UserBehaviorValidation(login=true)
    @ApiOperation("我的卡劵")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "services/app/{version}/activity/vote/myPrizeslist")
    public Response<PageList<ActivityUserPrizes>> userPrizesList(ActivityVoteDto activityVoteDto, HttpServletRequest request) {
        String userId = request.getHeader("userId");
        Assert.hasText(userId, "userId不能为空");
        activityVoteDto.setCreateUserId(Long.valueOf(userId));
        return activityVoteApi.userPrizesList(activityVoteDto);
    }

}
