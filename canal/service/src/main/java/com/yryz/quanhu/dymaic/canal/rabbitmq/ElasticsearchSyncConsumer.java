/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 */
package com.yryz.quanhu.dymaic.canal.rabbitmq;

import java.io.IOException;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yryz.quanhu.dymaic.canal.constant.AmqpConstant;
import com.yryz.quanhu.dymaic.canal.rabbitmq.handler.SyncExecutor;
import com.yryz.common.entity.CanalMsgContent;
import com.yryz.framework.core.lock.RedisDistributedLock;

/**
 * @author jk
 * @version 2.0
 * @Description 同步mysql数据到es
 */
@Service
@EnableScheduling
public class ElasticsearchSyncConsumer {
	private static Logger logger = LoggerFactory.getLogger(ElasticsearchSyncConsumer.class);
	private static final ObjectMapper MAPPER = new ObjectMapper();
	private final String lockName="quanhu.canal.fanout.lock";
	private final String unlockKey=UUID.randomUUID().toString();
	private final long expireSeconds=120;
	private Boolean isListenerCreated = false;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Resource
	private SyncExecutor syncExecutor;
	@Resource
	private ConnectionFactory connectionFactory;
	
	@PostConstruct
	public void init(){
		RabbitAdmin admin = new RabbitAdmin(connectionFactory);
		Queue queue = new Queue(AmqpConstant.CANAL_TO_ES_QUEUE,true);
		admin.declareQueue(queue);
		FanoutExchange exchange = new FanoutExchange(AmqpConstant.CANAL_FANOUT_EXCHANGE);
		exchange.setIgnoreDeclarationExceptions(true);
		admin.declareExchange(exchange);
		admin.declareBinding(BindingBuilder.bind(queue).to(exchange));
		createRabbitListener();
	}
	
	@Scheduled(fixedRate=30*1000)
	public void createRabbitListener(){
		boolean success=RedisDistributedLock.lockAndHold(stringRedisTemplate.opsForValue(), lockName, unlockKey, expireSeconds);
		logger.info("-----lock and hold----："+success);
		if(success && !isListenerCreated){
			SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
			container.setConnectionFactory(connectionFactory);
			container.setQueueNames(AmqpConstant.CANAL_TO_ES_QUEUE);
			MessageHandle listener = new MessageHandle();
			MessageListenerAdapter adapter = new MessageListenerAdapter(listener);
			container.setMessageListener(adapter);
			container.start();
			isListenerCreated = true;
			logger.info("-----isListenerCreated------");
		}
	}
	
	public class MessageHandle{
		public void handleMessage(String data) {
			logger.debug("canal Message:{}",data);
			CanalMsgContent canalMsg = null;
			try {
				// 解析不了的垃圾信息忽略
				canalMsg = MAPPER.readValue(data, CanalMsgContent.class);
			} catch (IOException e) {
				canalMsg = null;
				e.printStackTrace();
			}

			if (canalMsg != null) {
				syncExecutor.process(canalMsg);
			}
		}
	}
}
