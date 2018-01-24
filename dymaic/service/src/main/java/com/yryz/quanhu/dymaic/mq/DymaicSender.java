/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月17日
 * Id: DemoSender.java, 2018年1月17日 下午2:57:35 yehao
 */
package com.yryz.quanhu.dymaic.mq;

import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.dymaic.service.DymaicService;
import com.yryz.quanhu.dymaic.vo.Dymaic;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月17日 下午2:57:35
 * @Description MQ的发送示例方法
 */
@Service
public class DymaicSender {
	
	/**
	 * rabbitTemplate 可以直接注入，由spring-boot负责维护连接池对象
	 */
	@Autowired
	private RabbitTemplate rabbitTemplate;

	/**
	 * direct exchange 单一消息指定发送，需同时指定exchange-key和queue的routing-key
	 */
	public void directSend(Dymaic dymaic){
		String msg = GsonUtils.parseJson(dymaic).toString();

		rabbitTemplate.setExchange(AmqpConstant.DYMAIC_DIRECT_EXCHANGE);
		rabbitTemplate.setRoutingKey(AmqpConstant.DYMAIC_QUEUE);
		rabbitTemplate.convertAndSend(msg);
	}

}
