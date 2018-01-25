/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月17日
 * Id: DirectExchangeConsumer.java, 2018年1月17日 下午2:57:23 yehao
 */
package com.yryz.quanhu.dymaic.mq;

import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.dymaic.service.DymaicService;
import com.yryz.quanhu.dymaic.service.DymaicServiceImpl;
import com.yryz.quanhu.dymaic.vo.Dymaic;
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
	private DymaicServiceImpl dymaicServiceImpl;
	
	/**
	 * QueueBinding: exchange和queue的绑定
	 * Queue:队列声明
	 * Exchange:声明exchange
	 * key:routing-key
	 * @param data
	 */
	@RabbitListener(bindings = @QueueBinding(
			value= @Queue(value=AmqpConstant.DYMAIC_QUEUE,durable="true"),
			exchange=@Exchange(value=AmqpConstant.DYMAIC_DIRECT_EXCHANGE,ignoreDeclarationExceptions="true",type=ExchangeTypes.DIRECT),
			key=AmqpConstant.DYMAIC_QUEUE)
	)
	public void handleMessage(String data) {
		if (logger.isDebugEnabled()) {
			logger.debug("mqConsumer recive " + data);
		}
		Dymaic dymaic = GsonUtils.json2Obj(data, Dymaic.class);
		dymaicServiceImpl.pushTimeLine(dymaic);
	}

}
