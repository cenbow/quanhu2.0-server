package com.yryz.quanhu.order.score.service.impl;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.converter.json.GsonBuilderUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.gson.Gson;
import com.yryz.common.utils.GsonUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.order.common.AmqpConstant;
import com.yryz.quanhu.order.score.dao.mongo.EventLogDao;
import com.yryz.quanhu.order.score.service.EventAcountService;
import com.yryz.quanhu.order.score.service.EventService;
import com.yryz.quanhu.order.score.service.ScoreFlowService;
import com.yryz.quanhu.order.score.service.ScoreStatusService;
import com.yryz.quanhu.order.score.service.SysEventManageService;
import com.yryz.quanhu.order.score.type.EventTypeEnum;
import com.yryz.quanhu.score.entity.SysEventInfo;
import com.yryz.quanhu.score.vo.EventAcount;
import com.yryz.quanhu.score.vo.EventInfo;
import com.yryz.quanhu.score.vo.EventReportVo;

/**
 * @author syc
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
	ScoreFlowService scoreFlowService;

	@Autowired
	ConversionService conversionService;
	
	@Autowired
	ScoreStatusService scoreStatusService;
	
	@Autowired
	EventAcountService  eventAcountService;
	
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
	public EventReportVo getScoreFlowList(EventInfo log) {
		
		EventAcount eventAcount = eventAcountService.getLastAcount(log.getUserId());
		EventReportVo eventReportVo = new EventReportVo();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if (eventAcount!=null){
			
			if ( eventAcount.getScore()!=null && !"".equals(eventAcount.getScore()) ) {
				eventReportVo.setAllScore(eventAcount.getScore());
			}
			if ( eventAcount.getGrow()!=null && !"".equals(eventAcount.getGrow())  ) {
				eventReportVo.setGrow(eventAcount.getGrow());
			}
			if (  eventAcount.getGrowLevel()!=null && !"".equals(eventAcount.getGrowLevel()) ) {
				eventReportVo.setGrowLevel(eventAcount.getGrowLevel());
			}
			if ( eventAcount.getUserId()!=null && !"".equals(eventAcount.getUserId())  ) {
				eventReportVo.setUserId(Long.valueOf(eventAcount.getUserId()));
			}
			
			if (  eventAcount.getCreateTime()!=null && !"".equals(eventAcount.getCreateTime()) ) {
				eventReportVo.setCreateTime(sdf.format(eventAcount.getCreateTime()));
			}
			if (  eventAcount.getUpdateTime()!=null && !"".equals(eventAcount.getUpdateTime())  ) {
				eventReportVo.setUpdateTime(sdf.format(eventAcount.getUpdateTime()));
			}
		
		}
		
		return eventReportVo;
		//return scoreFlowService.getOne(log.getUserId());
		
//		ScoreStatus ss = new ScoreStatus();
//		try {
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			if (!StringUtils.isBlank(log.getCircleId())) {
//				ss.setCircleId(log.getCircleId());
//			}
//			if (!StringUtils.isBlank(log.getUserId())) {
//				ss.setUserId(log.getUserId());
//			}
//			if (!StringUtils.isBlank(log.getEventCode())) {
//				ss.setEventCode(log.getEventCode());
//			}
//			if (log.getCreateTime() != null && !"".equals(log.getCreateTime())) {
//				ss.setCreateTime(sdf.parse(log.getCreateTime()));
//			}
//		} catch (ParseException e) {
//			logger.info("getScoreFlowList is error : " + e);
//			//e.printStackTrace();
//		}
		//return scoreStatusService.getAll(ss);

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
			rabbitTemplate.setRoutingKey(AmqpConstant.GROW_RECEIVE_QUEUE);
			rabbitTemplate.convertAndSend(bodys);
			logger.info("-----------分发事件至成长队列---------,传入数据："+ bodys.toString());
		}

		if (typeCodes.contains(EventTypeEnum.Hot.getTypeCode())) {
			rabbitTemplate.setExchange(AmqpConstant.EVENT_DIRECT_EXCHANGE);
			rabbitTemplate.setRoutingKey(AmqpConstant.HOT_SPOT_QUEUE);
			rabbitTemplate.convertAndSend(bodys);
			logger.info("-----------分发事件至热度队列---------,传入数据："+ bodys.toString());
		}
//		mqSender.sendEvent(EventExchangeEnum.Fanout, null, bodys);
//		logger.info("-----------分发事件至统计分发队列---------,传入数据："+ bodys.toString());
	}
}
