/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 */
package com.yryz.quanhu.dymaic.canal.rabbitmq;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yryz.quanhu.dymaic.canal.constant.AmqpConstant;
import com.yryz.quanhu.dymaic.canal.rabbitmq.handler.SyncExecutor;
import com.yryz.common.entity.CanalMsgContent;
/**
 * @author jk
 * @version 2.0
 * @Description 同步mysql数据到es
 */
@Service
public class ElasticsearchSyncConsumer {
	private static Logger logger = LoggerFactory.getLogger(ElasticsearchSyncConsumer.class);
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	@Resource
	private SyncExecutor syncExecutor;
	
	/**
	 * QueueBinding: exchange和queue的绑定
	 * Queue:队列声明
	 * Exchange:声明exchange
	 * @param data
	 */
	@RabbitListener(bindings = @QueueBinding(
			value= @Queue(value=AmqpConstant.CANAL_TO_ES_QUEUE,durable="true"),
			exchange=@Exchange(value=AmqpConstant.CANAL_FANOUT_EXCHANGE,ignoreDeclarationExceptions="true",type=ExchangeTypes.FANOUT))
	)
	public void handleMessage(String data){
		logger.info("canal Message:" + data);
		CanalMsgContent canalMsg=null;
		try {
			//解析不了的垃圾信息忽略
			canalMsg=MAPPER.readValue(data, CanalMsgContent.class);
		} catch (IOException e) {
			canalMsg=null;
			e.printStackTrace();
		}
		
		if(canalMsg!=null){
			syncExecutor.process(canalMsg);
		}
	}

}
