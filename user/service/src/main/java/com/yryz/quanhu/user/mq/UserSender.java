package com.yryz.quanhu.user.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yryz.common.utils.JsonUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.user.dto.RegisterDTO;
import com.yryz.quanhu.user.entity.UserBaseInfo;

@Service
public class UserSender {
	private static final Logger logger = LoggerFactory.getLogger(UserSender.class);
	/**
	 * rabbitTemplate 可以直接注入，由spring-boot负责维护连接池对象
	 */
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	/**
	 * 用户创建
	 * direct exchange 单一消息指定发送，需同时指定exchange-key和queue的routing-key
	 */
	public void userCreate(RegisterDTO registerDTO){
		String msg = JsonUtils.toFastJson(registerDTO);
		logger.info("[UserSender.userCreate.sendMQ]={} start",msg);
		rabbitTemplate.setExchange(MqConstants.USER_DIRECT_EXCHANGE);
		rabbitTemplate.setRoutingKey(MqConstants.USER_CREATE_QUEUE);
		rabbitTemplate.convertAndSend(msg);
	}
	
	/**
	 * 用户更新
	 * direct exchange 单一消息指定发送，需同时指定exchange-key和queue的routing-key
	 */
	public void userUpdate(UserBaseInfo baseInfo){
		if(baseInfo == null || (StringUtils.isBlank(baseInfo.getUserImg()) && StringUtils.isBlank(baseInfo.getUserNickName()))){
			return;
		}
		String msg = JsonUtils.toFastJson(baseInfo);
		logger.info("[UserSender.userUpdate.sendMQ]={} start",msg);
		rabbitTemplate.setExchange(MqConstants.USER_DIRECT_EXCHANGE);
		rabbitTemplate.setRoutingKey(MqConstants.USER_UPDATE_QUEUE);
		rabbitTemplate.convertAndSend(msg);
	}
}
