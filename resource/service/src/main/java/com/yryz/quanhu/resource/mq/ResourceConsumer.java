/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月17日
 * Id: FanoutExchangeConsumer.java, 2018年1月17日 下午3:03:07 yehao
 */
package com.yryz.quanhu.resource.mq;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.resource.entity.ResourceModel;
import com.yryz.quanhu.resource.enums.ResourceConsumerCollection;
import com.yryz.quanhu.resource.service.ResourceService;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月17日 下午3:03:07
 * @Description 资源提交队列消费
 */
@Service
public class ResourceConsumer {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	
	
	@Autowired
	private ResourceService resourceService;
	
	/**
	 * QueueBinding: exchange和queue的绑定
	 * Queue:队列声明
	 * Exchange:声明exchange
	 * @param data
	 */
	@RabbitListener(bindings = @QueueBinding(
			value= @Queue(value=AmqpConstant.RESOURCE_COMMIT_QUEUE,durable="true"),
			exchange=@Exchange(value=AmqpConstant.RESOURCE_DYNAMIC_FANOUT_EXCHANGE,ignoreDeclarationExceptions="true",type=ExchangeTypes.FANOUT))
	)
	public void handleMessage(String data){
		logger.info("handle resource message : " + data);
		ResourceModel resourceModel = GsonUtils.json2Obj(data, ResourceModel.class);
		if(!ResourceConsumerCollection.check(resourceModel.getModuleEnum())){
			return ;
		}
		List<ResourceModel> list = new ArrayList<>();
		list.add(resourceModel);
		resourceService.commitResource(list);
	}


}
