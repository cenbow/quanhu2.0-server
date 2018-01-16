package com.yryz.common.mq;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.yryz.common.utils.GsonUtils;

/**
 * @ClassName MQSender
 * @author admin
 * @date 2016年10月8日15:08:51
 * @Description 消息发送工具类
 */
public abstract class MQSender {
	
	private static Logger logger = LoggerFactory.getLogger(MQSender.class);
	
	private static String getUUID(){
		return UUID.randomUUID().toString();
	}
	
	/**
	 * @author admin
	 * @date 2015年3月26日
	 * @param app		发送目标服务器
	 * @param message	发送消息
	 * @Description 发送异步消息，异步消息无需回复，目标收到即可。
	 */
	private void sendMessage(int app , Message message) {
		try {
			RabbitTemplate rabbitTemplate = checkTemplate(app);
			rabbitTemplate.send(message);	
		} catch (Exception e) {
			logger.error("mq error ", e);
			throw new RuntimeException("sys error");
		}
	}
	
	/**
	 * @author admin
	 * @date 2016年10月8日
	 * @param app		发送目标服务器
	 * @param queue		队列名称
	 * @param message	发送消息
	 * @Description 发送异步消息
	 */
	private void sendMessage(int app, String queue, Message message) {
		try {
			RabbitTemplate rabbitTemplate = checkTemplate(app);
			rabbitTemplate.send(queue, message);
		} catch (Exception e) {
			logger.error("mq error ", e);
			throw new RuntimeException("sys error");
		}
	}
	
	/**
	 * @author admin
	 * @date 2015年3月26日
	 * @param app	发送目标服务器
	 * @param data 	发送数据
	 * @Description 发送异步消息
	 */
	public void send(int app, Map<String, String> data){
		MessageProperties messageProperties = MessagePropertiesBuilder.newInstance()
				.setCorrelationId(getUUID().getBytes())
				.build();
		String body = GsonUtils.parseJson(data);
		
		Message message = null;
		try {
			message = MessageBuilder.withBody(body.getBytes("UTF-8"))
					.andProperties(messageProperties).build();
			sendMessage(app, message);
		} catch (UnsupportedEncodingException e) {
			logger.warn("发送不支持的编码，将使用默认编码集",e);
			message = MessageBuilder.withBody(body.getBytes())
					.andProperties(messageProperties).build();
			sendMessage(app, message);
		}
	}
	
	/**
	 * @author admin
	 * @date 2016年10月8日
	 * @param app		发送目标服务器
	 * @param queue		队列名称
	 * @param message	发送消息
	 * @Description 发送异步消息
	 */
	public void send(int app, String queue, Map<String, String> data) {
		MessageProperties messageProperties = MessagePropertiesBuilder.newInstance()
				.setCorrelationId(getUUID().getBytes())
				.build();
		
		String body = GsonUtils.parseJson(data);
		Message message = null;
		try {
			message = MessageBuilder.withBody(body.getBytes("UTF-8"))
					.andProperties(messageProperties).build();
			sendMessage(app, queue, message);
		} catch (UnsupportedEncodingException e) {
			logger.warn("发送不支持的编码，将使用默认编码集",e);
			message = MessageBuilder.withBody(body.getBytes()).build();
			sendMessage(app, queue, message);
		}
	}
	
	/**
	 * @author admin
	 * @date 2015年3月26日
	 * @param app	发送目标服务器
	 * @param json 	发送数据
	 * @Description 发送异步消息
	 */
	public void send(int app, JSONObject json){
		Map<String, String> data = new HashMap<String, String>();
		for (Object key : json.keySet()) {
			data.put(key.toString(), json.get(key.toString()).toString());
		}
		send(app, data);
	}
	
	/**
	 * @author admin
	 * @date 2015年3月26日
	 * @param app	发送目标服务器
	 * @param queue	队列名称
	 * @param json 	发送数据
	 * @Description 发送异步消息
	 */
	public void send(int app, String queue, JSONObject json){
		Map<String, String> data = new HashMap<String, String>();
		for (Object key : json.keySet()) {
			data.put(key.toString(), json.get(key.toString()).toString());
		}
		send(app, queue, data);
	}
	
	
	/**
	 * @author admin
	 * @date 2015年3月26日
	 * @param app  连接池代号
	 * @return
	 * @Description 获取服务器发送连接池
	 */
	public abstract RabbitTemplate checkTemplate(int app);

}
