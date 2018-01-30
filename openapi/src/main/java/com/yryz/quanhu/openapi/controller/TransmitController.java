package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.annotation.UserBehaviorArgs;
import com.yryz.common.annotation.UserBehaviorValidation;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.behavior.transmit.api.TransmitApi;
import com.yryz.quanhu.behavior.transmit.dto.TransmitInfoDto;
import com.yryz.quanhu.behavior.transmit.entity.TransmitInfo;
import com.yryz.quanhu.behavior.transmit.vo.TransmitInfoVo;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.score.service.EventAPI;
import com.yryz.quanhu.score.vo.EventInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Api(description = "转发")
@RestController
public class TransmitController {

    private Logger logger = LoggerFactory.getLogger(ShareController.class);

    @Reference(check = false)
    TransmitApi transmitApi;

    @Reference(check = false)
    EventAPI eventAPI;

    /**
     * 转发
     * @param   transmitInfo
     * */
    @UserBehaviorArgs(sourceContexts = {"object.TransmitInfo.content"}, sourceUserId="object.TransmitInfo.targetUserId")
    @UserBehaviorValidation(login = true, mute = true, blacklist = true, illegalWords = true)
    @ApiOperation("转发")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @PostMapping(value = "services/app/{version}/transmit/single")
    public Response single(@RequestBody TransmitInfo transmitInfo, HttpServletRequest request) {
        String userId = request.getHeader("userId");
        Assert.hasText(userId, "userId不能为空");
        transmitInfo.setCreateUserId(Long.valueOf(userId));
        Assert.isTrue(this.matcher(transmitInfo.getModuleEnum(), "1003|1004|1005|1000"), "moduleEnum格式有误");
        Response result = transmitApi.single(transmitInfo);
        if(result.success()) {
            try {
                //提交事件
                EventInfo event = new EventInfo();
                event.setUserId(userId);
                event.setEventCode("6");
//                event.setResourceId(transmitInfo.getResourceId().toString());
                eventAPI.commit(event);
            } catch (Exception e) {
                logger.error("提交eventApi 失败", e);
            }
        }

        return result;
    }

    /**
     * 转发列表
     * @param   transmitInfoDto
     * @return
     * */
    @UserBehaviorValidation
    @ApiOperation("转发列表")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "services/app/{version}/transmit/single")
    public Response<PageList<TransmitInfoVo>> list(TransmitInfoDto transmitInfoDto, HttpServletRequest request) {
        return transmitApi.list(transmitInfoDto);
    }

    private boolean matcher(Object obj, String regEx) {
        if(obj == null || regEx == null){
            return false;
        }
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(obj.toString());
        return matcher.matches();
    }

}
