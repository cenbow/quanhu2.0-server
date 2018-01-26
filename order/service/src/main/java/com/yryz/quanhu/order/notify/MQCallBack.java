/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月26日
 * Id: MQCallBack.java, 2018年1月26日 下午3:55:01 yehao
 */
package com.yryz.quanhu.order.notify;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月26日 下午3:55:01
 * @Description MQ回调队列
 */
@Component
public class MQCallBack implements ApplicationContextAware {
	
	/**
	 * 资金平台默认exchange
	 */
	private static final String ORDER_EXCHANGE = "ORDER_NOTIFY_DIRECT_EXCHANGE";
	
	private static RabbitTemplate rabbitTemplate;

	/**
	 * @param arg0
	 * @throws BeansException
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		rabbitTemplate = arg0.getBean(RabbitTemplate.class);
	}
	
	public static void doSend(String queue ,String data){
		rabbitTemplate.setExchange(ORDER_EXCHANGE);
		rabbitTemplate.setRoutingKey(queue);
		rabbitTemplate.convertAndSend(data);
	}

}
