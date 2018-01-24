/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018年1月17日
 * Id: DirectExchangeConsumer.java, 2018年1月17日 下午2:57:23 yehao
 */
package com.yryz.quanhu.order.sdk.listener;

import com.alibaba.fastjson.JSON;
import com.yryz.quanhu.order.sdk.service.NotifyService;
import com.yryz.quanhu.order.vo.OrderInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月17日 下午2:57:23
 * @Description direct的队列示例
 * POM文件声明配置：
 * <dependency>
 * <groupId>org.springframework.boot</groupId>
 * <artifactId>spring-boot-starter-amqp</artifactId>
 * </dependency>
 */
@Service
public class OrderNotifyListener {

    private static final Logger logger = LoggerFactory.getLogger(OrderNotifyListener.class);

    @Value("order.notify.queue")
    private String notifyQueue;

    @Autowired
    private NotifyService notifyService;

    /**
     * QueueBinding: exchange和queue的绑定
     * Queue:队列声明
     * Exchange:声明exchange
     * key:routing-key
     *
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${order.notify.queue}", durable = "true"),
            exchange = @Exchange(value = "ORDER_NOTIFY_DIRECT_EXCHANGE", ignoreDeclarationExceptions = "true"),
            key = "${order.notify.queue}")
    )
    public void handleMessage(String message) {
        logger.info("Receive order notify message, msg:{}", message);
        OrderInfo orderInfo = null;
        try {
            orderInfo = JSON.parseObject(message, OrderInfo.class);
        } catch (Exception e) {
            logger.error("解析订单回调消息失败", e);
        }
        if (null != orderInfo) {
            notifyService.notify(orderInfo);
        }
    }

}
