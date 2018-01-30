package com.yryz.quanhu.openapi.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.annotation.UserBehaviorValidation;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.behavior.reward.api.RewardCountApi;
import com.yryz.quanhu.behavior.reward.api.RewardInfoApi;
import com.yryz.quanhu.behavior.reward.constants.RewardConstants;
import com.yryz.quanhu.behavior.reward.dto.RewardInfoDto;
import com.yryz.quanhu.behavior.reward.entity.RewardCount;
import com.yryz.quanhu.behavior.reward.entity.RewardInfo;
import com.yryz.quanhu.behavior.reward.vo.RewardInfoVo;
import com.yryz.quanhu.openapi.ApplicationOpenApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
* @Description: 打赏
* @author wangheng
* @date 2018年1月29日 下午3:34:56
*/
@Api(description = "打赏接口")
@RestController
@RequestMapping(value = "/services/app")
public class RewardController {

    @Reference(lazy = true, check = false, timeout = 10000)
    private RewardInfoApi rewardInfoApi;

    @Reference(lazy = true, check = false, timeout = 10000)
    private RewardCountApi rewardCountApi;

    @ApiOperation("资源打赏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "record", paramType = "body", required = true),
            @ApiImplicitParam(name = "userId", paramType = "header", required = true) })
    @UserBehaviorValidation(event = "资源打赏", login = true)
    @PostMapping(value = "{version}/reward")
    public Response<Map<String, Object>> reward(@RequestBody RewardInfo record,
            @RequestHeader("userId") Long headerUserId) {

        record.setCreateUserId(headerUserId);

        return rewardInfoApi.reward(record);
    }

    @ApiOperation("打赏列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "userId", paramType = "header", required = true) })
    @UserBehaviorValidation(event = "打赏列表", login = true)
    @GetMapping(value = "{version}/reward/list")
    public Response<PageList<RewardInfoVo>> list(RewardInfoDto dto, @RequestHeader("userId") Long headerUserId) {

        if (RewardConstants.QueryType.my_reward_user_list.equals(dto.getQueryType())
                || RewardConstants.QueryType.my_reward_resource_list.equals(dto.getQueryType())) {
            dto.setCreateUserId(headerUserId);
        } else if (RewardConstants.QueryType.reward_my_user_list.equals(dto.getQueryType())
                || RewardConstants.QueryType.reward_my_resource_list.equals(dto.getQueryType())) {
            dto.setToUserId(headerUserId);
        }

        return rewardInfoApi.pageByCondition(dto, true);
    }

    @ApiOperation("我打赏/收到打赏 金额")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "userId", paramType = "header", required = true) })
    @UserBehaviorValidation(event = "我打赏/收到打赏 金额", login = true)
    @GetMapping(value = "{version}/reward/amount")
    public Response<RewardCount> amountCount(@RequestHeader("userId") Long headerUserId) {
        RewardCount rewardCount = ResponseUtils.getResponseData(rewardCountApi.selectByTargetId(headerUserId));
        return ResponseUtils.returnObjectSuccess(rewardCount);
    }
}
