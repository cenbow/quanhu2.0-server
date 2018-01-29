package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.annotation.NotLogin;
import com.yryz.common.annotation.UserBehaviorArgs;
import com.yryz.common.annotation.UserBehaviorValidation;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.behavior.transmit.api.TransmitApi;
import com.yryz.quanhu.behavior.transmit.dto.TransmitInfoDto;
import com.yryz.quanhu.behavior.transmit.entity.TransmitInfo;
import com.yryz.quanhu.behavior.transmit.vo.TransmitInfoVo;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(description = "转发")
@RestController
public class TransmitController {

    @Reference
    TransmitApi transmitApi;

    /**
     * 转发
     * @param   transmitInfo
     * */
    @UserBehaviorArgs(sourceContexts = {"object.content"}, sourceUserId="object.targetUserId")
    @UserBehaviorValidation(login = true, mute = true, blacklist = true, illegalWords = true)
    @ApiOperation("转发")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @PostMapping(value = "services/app/{version}/transmit/single")
    public Response single(@RequestBody TransmitInfo transmitInfo, HttpServletRequest request) {
        String userId = request.getHeader("userId");
        Assert.hasText(userId, "userId不能为空");
        transmitInfo.setCreateUserId(Long.valueOf(userId));
        return transmitApi.single(transmitInfo);
    }

    /**
     * 转发列表
     * @param   transmitInfoDto
     * @return
     * */
    @NotLogin
    @ApiOperation("转发列表")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "services/app/{version}/transmit/single")
    public Response<PageList<TransmitInfoVo>> list(TransmitInfoDto transmitInfoDto) {
        return transmitApi.list(transmitInfoDto);
    }

}
