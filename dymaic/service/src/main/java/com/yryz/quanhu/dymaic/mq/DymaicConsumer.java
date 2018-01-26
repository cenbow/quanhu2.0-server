/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月17日
 * Id: DirectExchangeConsumer.java, 2018年1月17日 下午2:57:23 yehao
 */
package com.yryz.quanhu.dymaic.mq;

import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.dymaic.provider.DymaicProvider;
import com.yryz.quanhu.dymaic.service.DymaicService;
import com.yryz.quanhu.dymaic.service.DymaicServiceImpl;
import com.yryz.quanhu.dymaic.vo.Dymaic;
import com.yryz.quanhu.resource.vo.ResourceTotal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月17日 下午2:57:23
 * @Description direct的队列示例
 * POM文件声明配置：
 * <dependency>  
 *	<groupId>org.springframework.boot</groupId>  
 *	<artifactId>spring-boot-starter-amqp</artifactId>  
 * </dependency> 
 */
@Service
public class DymaicConsumer {
	Logger logger = LoggerFactory.getLogger(DymaicConsumer.class);

	@Autowired
	private DymaicProvider dymaicProvider;


	/**
	 * 动态发布队列
	 * @param data
	 */
	@RabbitListener(bindings = @QueueBinding(
			value= @Queue(value=AmqpConstant.DYMAIC_QUEUE,durable="true"),
			exchange=@Exchange(value=AmqpConstant.DYMAIC_FANOUT_EXCHANGE,ignoreDeclarationExceptions="true",type=ExchangeTypes.FANOUT),
			key=AmqpConstant.DYMAIC_QUEUE)
	)
	public void handleReciveDymaic(String data) {
		if (logger.isDebugEnabled()) {
			logger.debug("dymaicQueue recive " + data);
		}
		//1000私圈,1001用户,1002转发,1003文章,1004话题,1005帖子,1006问题,1007答案
		try {
			Dymaic dymaic = GsonUtils.json2Obj(data, Dymaic.class);
			Integer modelEnum = dymaic.getModuleEnum();
			if (modelEnum != null && (modelEnum.equals(1000) || modelEnum.equals(1002) || modelEnum.equals(1003)
				|| modelEnum.equals(1004) || modelEnum.equals(1005)) ) {
				dymaicProvider.send(dymaic);
			}
		} catch (Exception e) {
			logger.error(data, e);
		}
	}

	/**
	 * 动态TimeLine推送队列
	 * @param data
	 */
	@RabbitListener(bindings = @QueueBinding(
			value= @Queue(value=AmqpConstant.DYMAIC_TIMELINE_QUEUE,durable="true"),
			exchange=@Exchange(value=AmqpConstant.DYMAIC_DIRECT_EXCHANGE,ignoreDeclarationExceptions="true",type=ExchangeTypes.DIRECT),
			key=AmqpConstant.DYMAIC_TIMELINE_QUEUE)
	)
	public void handlePushTimeLine(String data) {
		if (logger.isDebugEnabled()) {
			logger.debug("timeLineQueue recive " + data);
		}
		Dymaic dymaic = GsonUtils.json2Obj(data, Dymaic.class);
		dymaicProvider.pushTimeLine(dymaic);
	}



}
