package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.annotation.UserBehaviorArgs;
import com.yryz.common.annotation.UserBehaviorValidation;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.behavior.transmit.api.TransmitApi;
import com.yryz.quanhu.behavior.transmit.dto.TransmitInfoDto;
import com.yryz.quanhu.behavior.transmit.entity.TransmitInfo;
import com.yryz.quanhu.behavior.transmit.vo.TransmitInfoVo;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.score.service.EventAPI;
import com.yryz.quanhu.user.service.AccountApi;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Api(description = "转发")
@RestController
public class TransmitController {

    private Logger logger = LoggerFactory.getLogger(TransmitController.class);

    @Reference(check = false, timeout = 30000)
    TransmitApi transmitApi;

    @Reference(check = false, timeout = 30000)
    EventAPI eventAPI;

    @Reference(check = false, timeout = 30000)
    AccountApi accountApi;

    /**
     * 转发
     * @param   transmitInfo
     * */
    @UserBehaviorArgs(contexts = {"object.TransmitInfo.content"}, sourceUserId="object.TransmitInfo.targetUserId")
    @UserBehaviorValidation(login = true, blacklist = true, illegalWords = true)
    @ApiOperation("转发")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @PostMapping(value = "services/app/{version}/transmit/single")
    public Response single(@RequestBody TransmitInfo transmitInfo, HttpServletRequest request) {
        String userId = request.getHeader("userId");
        Assert.hasText(userId, "userId不能为空");
        transmitInfo.setCreateUserId(Long.valueOf(userId));
        Assert.isTrue(this.matcher(transmitInfo.getModuleEnum(), "1003|1004|1005|1000"), "moduleEnum格式有误");
        if(!StringUtils.isEmpty(transmitInfo.getContent()) ) {
            if(transmitInfo.getContent().length() > 140) {
                throw new QuanhuException(ExceptionEnum.TRANSMIT_CONTENT_ERROR);
            }
            boolean flag = false;
            try {
                //判断当前用户是否被平台禁言
                Response<Boolean> rpc = accountApi.checkUserDisTalk(transmitInfo.getCreateUserId());
                if(rpc.success()){
                    flag = rpc.getData();
                }
            } catch (Exception e) {
                logger.error("调用禁言接口失败：", e);
            }
            if(flag) {
                throw new QuanhuException(ExceptionEnum.USER_NO_TALK);
            }
        }

        return transmitApi.single(transmitInfo);
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
