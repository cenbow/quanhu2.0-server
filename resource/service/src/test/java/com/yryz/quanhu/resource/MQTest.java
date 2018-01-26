/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月17日
 * Id: MQTest.java, 2018年1月17日 下午3:26:27 yehao
 */
package com.yryz.quanhu.resource;


import java.io.IOException;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yryz.quanhu.resource.mq.AmqpConstant;
import com.yryz.quanhu.resource.vo.ResourceVo;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月17日 下午3:26:27
 * @Description MQ测试队列
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MQTest {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	
	@Test
	public void directSend(){
		String msg = "hello dirct demo mq";
		rabbitTemplate.setExchange(AmqpConstant.RESOURCE_DYNAMIC_FANOUT_EXCHANGE);
		rabbitTemplate.setRoutingKey(AmqpConstant.RESOURCE_COMMIT_QUEUE);
		rabbitTemplate.convertAndSend(msg);
	}
	
	@Test
	public void fanoutSend(){
		String msg = "hello fanout demo mq";
		rabbitTemplate.setExchange(AmqpConstant.RESOURCE_DYNAMIC_FANOUT_EXCHANGE);
//		rabbitTemplate.setRoutingKey(AmqpConfig.DEMO_QUEUE);
		rabbitTemplate.convertAndSend(msg);
	}

}
