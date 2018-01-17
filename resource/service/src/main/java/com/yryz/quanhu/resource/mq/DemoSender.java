/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月17日
 * Id: DemoSender.java, 2018年1月17日 下午2:57:35 yehao
 */
package com.yryz.quanhu.resource.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月17日 下午2:57:35
 * @Description MQ的发送示例方法
 */
public class DemoSender {
	
	/**
	 * rabbitTemplate 可以直接注入，由spring-boot负责维护连接池对象
	 */
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	/**
	 * direct exchange 单一消息指定发送，需同时指定exchange-key和queue的routing-key
	 */
	public void directSend(){
		String msg = "hello dirct demo mq";
		rabbitTemplate.setExchange(AmqpConstant.DEMO_DIRECT_EXCHANGE);
		rabbitTemplate.setRoutingKey(AmqpConstant.DEMO_QUEUE);
		rabbitTemplate.convertAndSend(msg);
	}
	
	/**
	 * fanout exchange 广播，指定exchange-key即可
	 */
	public void fanoutSend(){
		String msg = "hello fanout demo mq";
		rabbitTemplate.setExchange(AmqpConstant.DEMO_FANOUT_EXCHANGE);
		rabbitTemplate.convertAndSend(msg);
	}

}
