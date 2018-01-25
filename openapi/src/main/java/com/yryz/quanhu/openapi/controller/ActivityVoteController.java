package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.annotation.NotLogin;
import com.yryz.common.response.Response;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.support.activity.api.ActivityVoteApi;
import com.yryz.quanhu.support.activity.entity.ActivityVoteRecord;
import com.yryz.quanhu.support.activity.vo.ActivityVoteInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Api(description = "投票活动")
@RestController
public class ActivityVoteController {

    @Reference(check = false)
    private ActivityVoteApi activityVoteApi;

    @NotLogin
    @ApiOperation("投票活动详情")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "services/app/{version}/activity/vote/detail")
    public Response<ActivityVoteInfoVo> detail(Long activityInfoId, HttpServletRequest request) {
        String userId = request.getHeader("userId");
        return activityVoteApi.detail(activityInfoId, Long.valueOf(userId));
    }

    @ApiOperation("确认投票")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @PostMapping(value = "services/app/{version}/activity/vote/single")
    public Response<Map<String, Object>> single(@RequestBody ActivityVoteRecord record, HttpServletRequest request) {
        String userId = request.getHeader("userId");
        Assert.hasText(userId, "userId不能为空");
        record.setCreateUserId(Long.valueOf(userId));
        return activityVoteApi.single(record);
    }

}
