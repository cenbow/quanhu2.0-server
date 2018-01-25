package com.yryz.quanhu.message.message.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.message.message.api.MessageAPI;
import com.yryz.quanhu.message.message.dto.MessageDto;
import com.yryz.quanhu.message.message.service.MessageService;
import com.yryz.quanhu.message.message.vo.MessageStatusVo;
import com.yryz.quanhu.message.message.vo.MessageVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description:
 * @Date: Created in 2018 2018/1/20 17:53
 * @Author: pn
 */
@Service(interfaceClass = MessageAPI.class)
public class MessageProvider implements MessageAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageProvider.class);

    @Autowired
    private MessageService messageService;

    @Override
    public Response<Map<String, String>> getUnread(Long userId) {
        try {
            return ResponseUtils.returnObjectSuccess(messageService.getUnread(userId));
        } catch (QuanhuException e) {
            LOGGER.error("获取未读消息异常", e);
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            LOGGER.error("获取未读消息异常", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<MessageStatusVo> clearUnread(MessageDto messageDto) {
        try {
            return ResponseUtils.returnObjectSuccess(messageService.clearUnread(messageDto));
        } catch (QuanhuException e) {
            LOGGER.error("清理未读消息异常", e);
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            LOGGER.error("清理未读消息异常", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<List<MessageVo>> getList(MessageDto messageDto) {
        try {
            return ResponseUtils.returnListSuccess(messageService.getList(messageDto));
        } catch (QuanhuException e) {
            LOGGER.error("查询消息列表异常", e);
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            LOGGER.error("查询消息列表异常", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Map<Integer, MessageVo>> getMessageCommon(Long userId) {
        try {
            return ResponseUtils.returnObjectSuccess(messageService.getMessageCommon(userId));
        } catch (QuanhuException e) {
            LOGGER.error("查询消息总览异常", e);
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            LOGGER.error("查询消息总览异常", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Boolean> sendMessage(MessageVo messageVo, boolean flag) {
        try {
            return ResponseUtils.returnObjectSuccess(messageService.sendMessage(messageVo, flag));
        } catch (QuanhuException e) {
            LOGGER.error("推送消息异常", e);
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            LOGGER.error("推送消息异常", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<MessageVo> get(String messageId) {
        try {
            return ResponseUtils.returnObjectSuccess(messageService.get(messageId));
        } catch (QuanhuException e) {
            LOGGER.error("推送消息异常", e);
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            LOGGER.error("推送消息异常", e);
            return ResponseUtils.returnException(e);
        }
    }
}
