/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月17日
 * Id: DemoSender.java, 2018年1月17日 下午2:57:35 yehao
 */
package com.yryz.quanhu.demo.mq;

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
	 * 发送并回复，该方法调用时会锁定当前线程，并且有可能会造成MQ的性能下降或者服务端/客户端出现死循环现象，请谨慎使用。
	 */
	public void sendAndReceive(){
		String msg = "hello dirct demo mq";
		rabbitTemplate.setExchange(AmqpConstant.DEMO_DIRECT_EXCHANGE);
		rabbitTemplate.setRoutingKey(AmqpConstant.DEMO_RECEIVE_QUEUE);
		Object back = rabbitTemplate.convertSendAndReceive(msg);
		System.out.println("back msg : " + back.toString());
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
