/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月17日
 * Id: DemoSender.java, 2018年1月17日 下午2:57:35 yehao
 */
package com.yryz.quanhu.message.push.mq;

import com.fasterxml.jackson.core.type.TypeReference;
import com.yryz.common.utils.GsonUtils;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.message.push.constants.AmqpConstants;
import com.yryz.quanhu.message.push.entity.PushParamsDTO;
import org.apache.commons.collections.MapUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/1/19
 * @description 极光推送消息PushMsgSender
 */
@Service
public class PushMsgSender {
	
	/**
	 * rabbitTemplate 可以直接注入，由spring-boot负责维护连接池对象
	 */
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	/**
	 * direct exchange 单一消息指定发送，需同时指定exchange-key和queue的routing-key
	 * @param msg
	 */
	public void directSend(String msg){
		rabbitTemplate.setExchange(AmqpConstants.JPUSH_QUANHU_DIRECT_EXCHANGE);
		rabbitTemplate.setRoutingKey(AmqpConstants.JPUSH_QUANHU_DIRECT_QUEUE);
		rabbitTemplate.convertAndSend(msg);
	}

	/**
	 * mq不通，先直接调用，后续删除
	 */
	@Autowired
	private PushMsgConsumer pushMsgConsumer;

	/**
	 * 消息推送
	 * @param paramsDTO
	 */
	public void pushMessage(PushParamsDTO paramsDTO) {
		if (null != paramsDTO) {
//			String body = GsonUtils.parseJson(paramsDTO);
//			this.directSend(body);
			pushMsgConsumer.exceute(paramsDTO);
		}
	}
}
