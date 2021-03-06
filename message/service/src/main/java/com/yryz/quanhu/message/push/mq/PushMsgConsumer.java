/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年8月10日
 * Id: PushListener.java, 2017年8月10日 下午3:06:44 Administrator
 */
package com.yryz.quanhu.message.push.mq;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.yryz.common.message.MessageVo;
import com.yryz.common.utils.GsonUtils;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.message.message.dto.MessageAdminDto;
import com.yryz.quanhu.message.message.mongo.MessageAdminMongo;
import com.yryz.quanhu.message.message.mongo.MessageMongo;
import com.yryz.quanhu.message.message.vo.MessageAdminVo;
import com.yryz.quanhu.message.push.entity.PushReqVo.CommonPushType;
import com.yryz.quanhu.message.push.constants.AmqpConstants;
import com.yryz.quanhu.message.push.entity.PushParamsDTO;
import com.yryz.quanhu.message.push.service.JPushService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/1/19
 * @description 极光推送消息PushMsgConsumer
 */
@Service
public class PushMsgConsumer {

	private static final Logger logger = LoggerFactory.getLogger(PushMsgConsumer.class);

	@Autowired
	private MessageMongo messageMongo;
	@Autowired
	private MessageAdminMongo messageAdminMongo;

	/**
	 * QueueBinding: exchange和queue的绑定
	 * Queue:队列声明
	 * Exchange:声明exchange
	 * key:routing-key
	 * @param data
	 */
	@RabbitListener(
			bindings =
			@QueueBinding(
					value = @Queue(value = AmqpConstants.JPUSH_QUANHU_DIRECT_QUEUE, durable = "true"),
					exchange = @Exchange(value = AmqpConstants.JPUSH_QUANHU_DIRECT_EXCHANGE, ignoreDeclarationExceptions = "true", type = ExchangeTypes.DIRECT),
					key = AmqpConstants.JPUSH_QUANHU_DIRECT_QUEUE)
	)
	public void onMessage(String data) {
		logger.info("get push mq: {}", data);
		PushParamsDTO paramsDTO = GsonUtils.json2Obj(data, PushParamsDTO.class);
		if (StringUtils.isEmpty(paramsDTO.getTo()) || StringUtils.isEmpty(paramsDTO.getPushType())
				|| StringUtils.isEmpty(paramsDTO.getMsg()) || StringUtils.isEmpty(paramsDTO.getAppKey())
				|| StringUtils.isEmpty(paramsDTO.getAppSecret())) {
			return;
		}
		exceute(paramsDTO);
	}

	//但哥建议先注释掉
//	@Autowired
//	private MessageMongo messageService;


	public void exceute(PushParamsDTO paramsDTO) {
		String msg_id = null;

		// 在通知body不存在的时候设置通知body内容
		try {
			if (StringUtils.isBlank(paramsDTO.getNotificationBody())) {
				JSONObject jsonObject = JSONObject.fromObject(paramsDTO.getMsg());
				if (!jsonObject.isEmpty()) {
					String content = jsonObject.getString("content");
					if (StringUtils.isBlank(content)) {
						content = jsonObject.getString("context");
					}
					paramsDTO.setNotificationBody(content);
				}
			}
		} catch (Exception e) {

		}

		List<String> to = Lists.newArrayList(paramsDTO.getTo());
		// 批量推送时，to经json转成list
		if (CommonPushType.BY_ALIASS.getPushType().equals(paramsDTO.getPushType())
				|| CommonPushType.BY_ALIASS.getPushType().equals(paramsDTO.getPushType())
				|| CommonPushType.BY_REGISTRATIONIDS.getPushType().equals(paramsDTO.getPushType())) {
			to = getListByJson(paramsDTO.getTo());
		}
		msg_id = JPushService.sendPushMessage(paramsDTO, to);
		logger.info("push message params:{}, msg_id:{}  ", JsonUtils.toFastJson(paramsDTO), msg_id);


		try {
			MessageVo messageVo = JsonUtils.fromJson(paramsDTO.getMsg(), new TypeReference<MessageVo>() {});
			if (messageVo != null && StringUtils.isNotBlank(messageVo.getMessageId())) {
				logger.info("start update push message status");

				//更新每个用户mongo数据
				MessageVo vo = messageMongo.get(messageVo.getMessageId());
				if (vo != null && StringUtils.isBlank(vo.getJpId())) {
					MessageVo toUpdate = new MessageVo(messageVo.getMessageId());
					toUpdate.setJpId(msg_id);
					messageMongo.update(toUpdate);
					logger.info("finish update api messageMongo status");
				}

				//更新adminMongo数据
				MessageAdminDto dto = new MessageAdminDto();
				dto.setMessageId(messageVo.getMessageId());
				MessageAdminVo adminVo = messageAdminMongo.findOne(dto);
				if (adminVo != null && StringUtils.isBlank(adminVo.getJpId())) {
					MessageAdminVo adminUpdate = new MessageAdminVo();
					adminUpdate.setMessageId(messageVo.getMessageId());
					adminUpdate.setJpId(msg_id);
					messageAdminMongo.update(adminUpdate);
					logger.info("finish update api messageAdminMongo status");
				}

			}
		} catch (Throwable e) {
			logger.error("update message status error", e);
		}

	}

	/**
	 * json转list
	 * 
	 * @param to
	 * @return
	 */
	private List<String> getListByJson(String to) {
		return JsonUtils.fromJson(to, new TypeReference<List<String>>() {
		});
	}

}
