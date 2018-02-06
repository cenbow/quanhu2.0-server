/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月17日
 * Id: MQTest.java, 2018年1月17日 下午3:26:27 yehao
 */
package com.yryz.quanhu.demo;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yryz.common.utils.DateUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.demo.mq.AmqpConstant;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月17日 下午3:26:27
 * @Description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MQTest {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Test
	public void directSend(){
		String msg = "hello dirct demo mq";
		rabbitTemplate.setExchange(AmqpConstant.DEMO_DIRECT_EXCHANGE);
		rabbitTemplate.setRoutingKey(AmqpConstant.DEMO_QUEUE);
		rabbitTemplate.convertAndSend(msg);
	}
	
	@Test
	public void receiveAndSend(){
		String msg = "hello dirct demo mq";
		rabbitTemplate.setExchange(AmqpConstant.DEMO_DIRECT_EXCHANGE);
		rabbitTemplate.setRoutingKey(AmqpConstant.DEMO_RECEIVE_QUEUE);
		Object back = rabbitTemplate.convertSendAndReceive(msg);
		System.out.println("back msg : " + back.toString());
	}
	
	@Test
	public void fanoutSend(){
		ResourceTotal resourceTotal = new ResourceTotal();
		resourceTotal.setClassifyId(1);
		resourceTotal.setContent("测试正文2");
		resourceTotal.setCoterieId("TestCoterieId");
		resourceTotal.setCreateDate(DateUtils.getDateTime());
		resourceTotal.setExtJson("{\"resourceId\":1000211}");
		resourceTotal.setModuleEnum(1003);
		resourceTotal.setPublicState("10");
		resourceTotal.setResourceId(10002122L);
		resourceTotal.setTalentType("1");
		resourceTotal.setTitle("resource title test");
		resourceTotal.setUserId(10000L);
		resourceTotal.setTransmitType(1003);
		resourceTotal.setTransmitNote("transmitNote");
		
		String msg = GsonUtils.parseJson(resourceTotal);
		rabbitTemplate.setExchange("RESOURCE_DYNAMIC_FANOUT_EXCHANGE");
//		rabbitTemplate.setRoutingKey(AmqpConfig.DEMO_QUEUE);
		rabbitTemplate.convertAndSend(msg);
	}

}
