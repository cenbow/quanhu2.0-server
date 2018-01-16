package com.yryz.common.mq;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName AbsMQlistener
 * @author admin
 * @date 2015年3月26日 下午5:39:43
 * @Description MQ监听抽象类
 */
public abstract class AbsMQlistener implements MessageListener {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * @author admin
	 * @date 2015年3月26日
	 * @param message
	 * @Description 回复密信
	 * @see org.springframework.amqp.core.MessageListener#onMessage(org.springframework.amqp.core.Message)
	 */
	@Override
	public void onMessage(Message message) {
		String data = null;
		try {
			data = new String(message.getBody(),"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			logger.warn("回复消息未知编码,请检查是否支持UTF-8", e1 );
			data = new String(message.getBody());
		}
		
		String replyTo = message.getMessageProperties().getReplyTo();
        
		if(StringUtils.isEmpty(replyTo)){
			logger.debug("收到异步步消息...data:" + data);
			handleAsynMessage(toMap(data));
			logger.debug("处理异步消息完毕...data:" + data );
		} 
	}
	
	/**
	 * @author admin
	 * @date 2015年3月26日
	 * @param body
	 * @return
	 * @Description 将字符串改为Map
	 */
	private Map<String, String> toMap(String body){
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		try {
			Map<String, String> map = gson.fromJson(body, new TypeToken<Map<String, String>>(){}.getType());
			return map;
		} catch (Exception e) {
			logger.warn("参数处理异常.." + body , e);
			return new HashMap<String, String>();
		}
	}
	
	/**
	 * @author admin
	 * @date 2015年3月26日
	 * @param path
	 * @param data
	 * @Description 收到异步消息
	 */
	public abstract void handleAsynMessage(Map<String, String> data);
	
}
