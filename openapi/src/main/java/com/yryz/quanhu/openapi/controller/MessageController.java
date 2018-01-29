package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.message.MessageVo;
import com.yryz.common.response.Response;
import com.yryz.quanhu.message.message.api.MessageAPI;
import com.yryz.quanhu.message.message.constants.MessageContants;
import com.yryz.quanhu.message.message.dto.MessageDto;
import com.yryz.quanhu.message.message.vo.MessageStatusVo;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description:
 * @Date: Created in 2018 2018/1/20 17:50
 * @Author: pn
 */
@Api("消息HTTP接口")
@RestController
@RequestMapping("services/app")
public class MessageController {

    public static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    @Reference(check = false, timeout = 30000)
    private MessageAPI messageAPI;


    @ApiOperation("查询未读消息")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping("/{version}/message/unread")
    public Response<Map<String, String>> getUnread(@RequestHeader Long userId) {
        Assert.notNull(userId, "缺少用户Id");
        return messageAPI.getUnread(userId);
    }


    @ApiOperation("清理未读消息")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @PostMapping("/{version}/message/clearUnread")
    public Response<MessageStatusVo> clearUnread(@RequestBody MessageDto messageDto, @RequestHeader Long userId) {
        Assert.notNull(messageDto, "缺少参数或参数错误！");
        Assert.notNull(userId, "缺少用户Id！");
        Assert.hasText(messageDto.getReadType(), "动态字段为空！");
        messageDto.setUserId(userId);
        return messageAPI.clearUnread(messageDto);
    }


    @ApiOperation("查询消息列表")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping("/{version}/message/list")
    public Response<List<MessageVo>> getList(@RequestHeader Long userId, Integer type, Integer label, Integer start, Integer limit) {
        Assert.notNull(userId, "缺少userId");
        Assert.notNull(type, "缺少type");
        Assert.notNull(label, "缺少label");
        Assert.notNull(start, "缺少start");
        Assert.notNull(limit, "缺少limit");
        if (type <= 0) {
            throw QuanhuException.busiError("type参数错误！");
        }
        if (label <= 0) {
            throw QuanhuException.busiError("label参数错误！");
        }
        if (start < 0) {
            throw QuanhuException.busiError("start参数错误！");
        }
        if (limit <= 0) {
            throw QuanhuException.busiError("limit参数错误！");
        }

        MessageDto messageDto = new MessageDto();
        messageDto.setUserId(MessageContants.NOTICE_TYPE == type ? null : userId);
        messageDto.setLabel(label);
        messageDto.setLimit(limit);
        messageDto.setStart(start);
        messageDto.setType(type);
        return messageAPI.getList(messageDto);

    }


    @ApiOperation("查询消息总览")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping("/{version}/message/common")
    public Response<Map<Integer, MessageVo>> getMessageCommon(@RequestHeader Long userId) {
        Assert.notNull(userId,"userId不能为空！");
        return messageAPI.getMessageCommon(userId);
    }
}
