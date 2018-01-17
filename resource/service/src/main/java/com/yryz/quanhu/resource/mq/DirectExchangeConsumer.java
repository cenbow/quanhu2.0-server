/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月17日
 * Id: DirectExchangeConsumer.java, 2018年1月17日 下午2:57:23 yehao
 */
package com.yryz.quanhu.resource.mq;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月17日 下午2:57:23
 * @Description direct的队列示例
 * POM文件声明配置：
 * <dependency>  
 *	<groupId>org.springframework.boot</groupId>  
 *	<artifactId>spring-boot-starter-amqp</artifactId>  
 * </dependency> 
 */
@Service
public class DirectExchangeConsumer {
	
	/**
	 * QueueBinding: exchange和queue的绑定
	 * Queue:队列声明
	 * Exchange:声明exchange
	 * key:routing-key
	 * @param data
	 */
	@RabbitListener(bindings = @QueueBinding(
			value= @Queue(value=AmqpConstant.DEMO_DIRECT_EXCHANGE,durable="true"),
			exchange=@Exchange(value=AmqpConstant.DEMO_DIRECT_EXCHANGE,ignoreDeclarationExceptions="true",type=ExchangeTypes.DIRECT),
			key=AmqpConstant.DEMO_QUEUE)
	)
	public void handleMessage(String data){
		System.out.println("hello exchange mq:" + data);
	}

}
