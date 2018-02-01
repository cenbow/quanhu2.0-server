package com.yryz.quanhu.message.message.mq;

import com.alibaba.fastjson.JSON;
import com.yryz.quanhu.message.message.constants.MessageMQConstant;
import com.yryz.quanhu.message.message.service.MessageAdminService;
import com.yryz.quanhu.message.message.vo.MessageAdminVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description:
 * @Date: Created in 2018 2018/1/31 12:03
 * @Author: pn
 */
@Service
public class MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageListener.class);

    @Autowired
    private MessageAdminService messageAdminService;

    @RabbitListener(bindings =
    @QueueBinding(value = @Queue(value = MessageMQConstant.MESSAGE_QUEUE, durable = "true"),
            exchange = @Exchange(value = MessageMQConstant.MESSAGE_EXCHANGE, ignoreDeclarationExceptions = "true"),
            key = MessageMQConstant.MESSAGE_QUEUE))
    public void handleMessage(String msg) {
        try {
            LOGGER.info("接收管理后台推送消息！" + msg);
            if (StringUtils.isNotBlank(msg)) {
                MessageAdminVo messageAdminVo = JSON.parseObject(msg, MessageAdminVo.class);
                if (messageAdminVo != null) {
                    messageAdminService.handleMessage(messageAdminVo);
                    LOGGER.info("管理后台推送消息处理完成！" + msg);
                }
            }
        } catch (Exception e) {
            LOGGER.error("=============处理管理后台推送消息异常！==========" + msg, e);
        }
    }

}
