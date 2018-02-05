package com.yryz.quanhu.message.message.mq;

import com.yryz.common.exception.QuanhuException;
import com.yryz.quanhu.message.message.constants.MessageMQConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description:
 * @Date: Created in 2018 2018/1/31 10:22
 * @Author: pn
 */
@Service
public class MessageSender {

    public static final Logger LOGGER = LoggerFactory.getLogger(MessageSender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 指定单一队列
     *
     * @param msg
     */
    public void send(String msg) {
        try {
            LOGGER.info("开始发送管理后台消息到MQ：" + msg);

            rabbitTemplate.setExchange(MessageMQConstant.MESSAGE_EXCHANGE);
            rabbitTemplate.setRoutingKey(MessageMQConstant.MESSAGE_QUEUE);
            rabbitTemplate.convertAndSend(msg);

            LOGGER.info("完成发送管理后台消息到MQ：" + msg);
        } catch (AmqpException e) {
            LOGGER.error("发送管理后台推送消息异常！", e);
            throw QuanhuException.busiError("发送管理后台推送消息异常!" + e);
        } catch (Exception e) {
            LOGGER.error("发送管理后台推送消息异常！", e);
            throw QuanhuException.busiError("发送管理后台推送消息异常!" + e);
        }
    }


}
