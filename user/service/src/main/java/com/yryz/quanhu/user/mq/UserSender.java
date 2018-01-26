package com.yryz.quanhu.user.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.user.dto.RegisterDTO;
import com.yryz.quanhu.user.entity.UserBaseInfo;

@Service
public class UserSender {
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
		rabbitTemplate.setExchange(MqConstants.USER_CREATE_QUEUE);
		rabbitTemplate.setRoutingKey(MqConstants.USER_DIRECT_EXCHANGE);
		rabbitTemplate.convertAndSend(JsonUtils.toFastJson(registerDTO));
	}
	
	/**
	 * 用户更新
	 * direct exchange 单一消息指定发送，需同时指定exchange-key和queue的routing-key
	 */
	public void userUpdate(UserBaseInfo baseInfo){
		rabbitTemplate.setExchange(MqConstants.USER_UPDATE_QUEUE);
		rabbitTemplate.setRoutingKey(MqConstants.USER_DIRECT_EXCHANGE);
		rabbitTemplate.convertAndSend(JsonUtils.toFastJson(baseInfo));
	}
}
