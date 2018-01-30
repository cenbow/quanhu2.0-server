/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月29日
 * Id: HotspotConsumer.java, 2018年1月29日 下午3:47:42 yehao
 */
package com.yryz.quanhu.resource.hotspot.mq;

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
import com.yryz.quanhu.resource.hotspot.entity.HotSpotEventInfo;
import com.yryz.quanhu.resource.hotspot.enums.EventTypeCollection;
import com.yryz.quanhu.resource.hotspot.service.HotspotService;
import com.yryz.quanhu.resource.mq.AmqpConstant;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月29日 下午3:47:42
 * @Description 热度消费
 */
@Service
public class HotspotConsumer {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private HotspotService hotspotService;
	
	/**
	 * QueueBinding: exchange和queue的绑定
	 * Queue:队列声明
	 * Exchange:声明exchange
	 * @param data
	 */
	@RabbitListener(bindings = @QueueBinding(
			value= @Queue(value=EventAmqpConstant.HOT_SPOT_QUEUE,durable="true"),
			exchange=@Exchange(value=EventAmqpConstant.EVENT_DIRECT_EXCHANGE,ignoreDeclarationExceptions="true",type=ExchangeTypes.DIRECT),
			key=EventAmqpConstant.HOT_SPOT_QUEUE)
	)
	public void handleMessage(String data){
		logger.info("handle resource message : " + data);
		HotSpotEventInfo eventInfo = GsonUtils.json2Obj(data, HotSpotEventInfo.class);
		//如果两个都没有执行。则直接返回
		if(!EventTypeCollection.checkResourceHotCalculation(eventInfo.getEventCode()) || !EventTypeCollection.checkUserHotCalculation(eventInfo.getEventCode())){
			return ;
		}
		try {
			hotspotService.processEvent(eventInfo);
		} catch (Exception e) {
			logger.info("处理热度事件失败,data:" + data , e);
		}
	}

}
