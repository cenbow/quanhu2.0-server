package com.yryz.quanhu.order.score.consumer;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName AbsMQlistener
 * @author 
 * @date 2015年3月26日 下午5:39:43
 * @Description MQ监听抽象类
 */
public  abstract class AbsMQlistener   {
	
	
	//private  static Logger logger = LoggerFactory.getLogger(getClass());
    private static final Logger logger = LoggerFactory.getLogger(AbsMQlistener.class);
    

	/**
	 * @author admin
	 * @date 2015年3月26日
	 * @param message
	 * @Description 回复密信
	 * @see org.springframework.amqp.core.MessageListener#onMessage(org.springframework.amqp.core.Message)
	 */
	public  static Map<String, String> onMessage(String data) {
		Map<String, String>  map = null;
		if(!StringUtils.isEmpty(data)){
			logger.debug("收到异步步消息...data:" + data);
		    map =  toMap(data);
			logger.debug("处理异步消息完毕...data:" + data );
			
		} 
		return map;
	}
	
	/**
	 * @author admin
	 * @date 2015年3月26日
	 * @param body
	 * @return
	 * @Description 将字符串改为Map
	 */
	private static Map<String, String> toMap(String body){
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
	
	
//	/**
//	 * @author admin
//	 * @date 2015年3月26日
//	 * @param path
//	 * @param data
//	 * @Description 收到异步消息
//	 */
//	public abstract void handleMessage(String data);
//	

}
