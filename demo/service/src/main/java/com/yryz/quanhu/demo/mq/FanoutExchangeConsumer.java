/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月17日
 * Id: FanoutExchangeConsumer.java, 2018年1月17日 下午3:03:07 yehao
 */
package com.yryz.quanhu.demo.mq;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月17日 下午3:03:07
 * @Description 广播队列示例
 * POM文件声明配置：
 * <dependency>  
 *	<groupId>org.springframework.boot</groupId>  
 *	<artifactId>spring-boot-starter-amqp</artifactId>  
 * </dependency> 
 */
@Service
public class FanoutExchangeConsumer {
	
	/**
	 * QueueBinding: exchange和queue的绑定
	 * Queue:队列声明
	 * Exchange:声明exchange
	 * @param data
	 */
	@RabbitListener(bindings = @QueueBinding(
			value= @Queue(value=AmqpConstant.DEMO_FANOUT_QUEUE,durable="true"),
			exchange=@Exchange(value=AmqpConstant.DEMO_FANOUT_EXCHANGE,ignoreDeclarationExceptions="true",type=ExchangeTypes.FANOUT))
	)
	public void handleMessage(String data){
		System.out.println("hello Fanout Message:" + data);
	}


}
