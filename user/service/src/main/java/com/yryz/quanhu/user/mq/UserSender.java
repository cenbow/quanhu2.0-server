package com.yryz.quanhu.user.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class UserSender {
	/**
	 * rabbitTemplate 可以直接注入，由spring-boot负责维护连接池对象
	 */
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	/**
	 * direct exchange 单一消息指定发送，需同时指定exchange-key和queue的routing-key
	 */
	public void userCreate(String msg){
		rabbitTemplate.setExchange(MqConstants.USER_CREATE_QUEUE);
		rabbitTemplate.setRoutingKey(MqConstants.USER_DIRECT_EXCHANGE);
		rabbitTemplate.convertAndSend(msg);
	}
}
