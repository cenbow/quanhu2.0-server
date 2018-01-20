package com.yryz.quanhu.order.score.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.converter.json.GsonBuilderUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.gson.Gson;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.order.score.consumer.AmqpConstant;
import com.yryz.quanhu.order.score.dao.mongo.EventLogDao;
import com.yryz.quanhu.order.score.service.EventService;
import com.yryz.quanhu.order.score.service.SysEventManageService;
import com.yryz.quanhu.order.score.type.EventTypeEnum;
import com.yryz.quanhu.score.entity.SysEventInfo;
import com.yryz.quanhu.score.vo.EventInfo;

/**
 * @author xiepeng
 * @version 1.0
 */
@Service
public class EventServiceImpl implements EventService {
	
	private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);
	
	private static final Gson gson = GsonBuilderUtils.gsonBuilderWithBase64EncodedByteArrays().create();

//	@Autowired
//	MQSenderManager mqSender;

	@Autowired
	EventLogDao eventLogDao;

	@Autowired
	SysEventManageService sysEventManageService;

	@Autowired
	ConversionService conversionService;
	
	/**
	 * rabbitTemplate 可以直接注入，由spring-boot负责维护连接池对象
	 */
	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Override
	public void saveEvent(EventInfo log) {
//		String logJson = gson.toJson(log);
//		@SuppressWarnings("unchecked")
//		Map<String , String > bodys = gson.fromJson(logJson, Map.class);
//		//mqSender.sendEvent(EventExchangeEnum.Fanout, EventQueueEnum.QUEUE_EVENT , bodys);
//		rabbitTemplate.setExchange(AmqpConstant.Fanout);
//		rabbitTemplate.setRoutingKey(AmqpConstant.QUEUE_EVENT);
//		rabbitTemplate.convertAndSend(bodys);
		eventLogDao.saveLog(log);
	}

	@Override
	public List<EventInfo> getEvent(EventInfo log) {
		return null;
	}

	@Override
	public void processEvent(EventInfo ei) {
		String logJson = GsonUtils.parseJson(ei);
		logger.info("receive event : " + logJson);
//		@SuppressWarnings("unchecked")
//		Map<String , String > eiMap = GsonUtils.json2Obj(logJson, Map.class);
		SysEventInfo sei = sysEventManageService.getByCode(String.valueOf(ei.getEventCode()));
		if (sei != null && sei.getId() != null) {
			String typeCodes = sei.getEventType();
			dispatchEvent(typeCodes, logJson);
			saveEvent(ei);
		}
	}
		

	private void dispatchEvent(String typeCodes, String bodys) {

		if (typeCodes.contains(EventTypeEnum.Score.getTypeCode())) {
//			mqSender.sendEvent(EventExchangeEnum.Direct, EventQueueEnum.QUEUE_EVENT_SCORE, bodys);
//			logger.info("-----------分发事件至积分队列---------,传入数据："+ bodys.toString());
			rabbitTemplate.setExchange(AmqpConstant.EVENT_DIRECT_EXCHANGE);
			rabbitTemplate.setRoutingKey(AmqpConstant.SCORE_RECEIVE_QUEUE);
			rabbitTemplate.convertAndSend(bodys);
			logger.info("-----------分发事件至积分队列---------,传入数据："+ bodys.toString());
		}
		if (typeCodes.contains(EventTypeEnum.Grow.getTypeCode())) {
			//mqSender.sendEvent(EventExchangeEnum.Direct, EventQueueEnum.QUEUE_EVENT_GROW, bodys);
			rabbitTemplate.setExchange(AmqpConstant.EVENT_DIRECT_EXCHANGE);
			rabbitTemplate.setRoutingKey(AmqpConstant.SCORE_RECEIVE_GROW);
			rabbitTemplate.convertAndSend(bodys);
			logger.info("-----------分发事件至成长队列---------,传入数据："+ bodys.toString());
		}

//		if (typeCodes.contains(EventTypeEnum.Hot.getTypeCode())) {
//			mqSender.sendEvent(EventExchangeEnum.Direct, EventQueueEnum.QUEUE_EVENT_HOTSPOT, bodys);
//			logger.info("-----------分发事件至热度队列---------,传入数据："+ bodys.toString());
//		}
//		mqSender.sendEvent(EventExchangeEnum.Fanout, null, bodys);
//		logger.info("-----------分发事件至统计分发队列---------,传入数据："+ bodys.toString());
	}
}
