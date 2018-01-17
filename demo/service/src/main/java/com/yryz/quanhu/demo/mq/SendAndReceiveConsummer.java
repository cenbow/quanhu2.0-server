/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月17日
 * Id: SendAndReceiveConsummer.java, 2018年1月17日 下午4:56:29 yehao
 */
package com.yryz.quanhu.demo.mq;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Service;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月17日 下午4:56:29
 * @Description 回复消息示例，发送并回复，该方法调用时会锁定当前线程，并且有可能会造成MQ的性能下降或者服务端/客户端出现死循环现象，请谨慎使用。
 * POM文件声明配置：
 * <dependency>  
 *	<groupId>org.springframework.boot</groupId>  
 *	<artifactId>spring-boot-starter-amqp</artifactId>  
 * </dependency> 
 */
@Service
public class SendAndReceiveConsummer implements ReturnCallback , ConfirmCallback {
	
	/**
	 * QueueBinding: exchange和queue的绑定
	 * Queue:队列声明
	 * Exchange:声明exchange
	 * key:routing-key
	 * @param data
	 * @return
	 */
	@RabbitListener(bindings = @QueueBinding(
			value= @Queue(value=AmqpConstant.DEMO_RECEIVE_QUEUE,durable="true"),
			exchange=@Exchange(value=AmqpConstant.DEMO_DIRECT_EXCHANGE,ignoreDeclarationExceptions="true",type=ExchangeTypes.DIRECT),
			key=AmqpConstant.DEMO_RECEIVE_QUEUE)
	)
	public String getMsg(String data){
		System.out.println("hello SendAndReceive mq:" + data);
		return "yehao SendAndReceive back";
	}

	/**
	 * ReturnCallback接口用于实现消息发送到RabbitMQ交换器，但无相应队列与交换器绑定时的回调。
	 * @param message
	 * @param replyCode
	 * @param replyText
	 * @param exchange
	 * @param routingKey
	 * @see org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback#returnedMessage(org.springframework.amqp.core.Message, int, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
		System.out.println(message.getMessageProperties().getCorrelationId() + " 发送失败");
		
	}

	/**
	 * ConfirmCallback接口用于实现消息发送到RabbitMQ交换器后接收ack回调。
	 * @param correlationData
	 * @param ack
	 * @param cause
	 * @see org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback#confirm(org.springframework.amqp.rabbit.support.CorrelationData, boolean, java.lang.String)
	 */
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		if (ack) {  
            System.out.println("消息发送成功:" + correlationData);  
        } else {  
            System.out.println("消息发送失败:" + cause);  
        }
	}

}
