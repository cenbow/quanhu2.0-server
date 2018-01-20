/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年8月10日
 * Id: PushServiceImpl.java, 2017年8月10日 下午6:22:09 Administrator
 */
package com.yryz.quanhu.message.push.service.impl;

import cn.jpush.api.report.ReceivedsResult.Received;

import com.alibaba.dubbo.rpc.RpcContext;
import com.fasterxml.jackson.core.type.TypeReference;
import com.yryz.common.constant.Constants;
import com.yryz.common.context.Context;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.message.push.entity.PushConfigDTO;
import com.yryz.quanhu.message.push.entity.PushReceived;
import com.yryz.quanhu.message.push.entity.PushReqVo;
import com.yryz.quanhu.message.push.entity.PushReqVo.CommonPushType;
import com.yryz.quanhu.message.push.entity.PushParamsDTO;
import com.yryz.quanhu.message.push.exception.ServiceException;
import com.yryz.quanhu.message.push.mq.PushMsgSender;
import com.yryz.quanhu.message.push.remote.ConfigRemote;
import com.yryz.quanhu.message.push.service.JPushService;
import com.yryz.quanhu.message.push.service.PushService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年8月10日 下午6:22:09
 * @Description 推送业务
 */
@Service
public class PushServiceImpl implements PushService {
//	@Autowired
//	MqSenderManager sendMq;

	@Autowired
	private PushMsgSender pushSender;

	@Autowired
	private ConfigRemote configRemote;

//	@Autowired
//	CircleRemot circleRemot;
//
//	@Autowired
//	CustRemote custRemote;

	@Override
	public void sendByAlias(String userId, String notification, String msg) {
		/*PushConfigDTO configDTO = getPushConfig(RpcUtils.getAppId());
		if (checkPushConfig(configDTO)) {
			List<String> list = new ArrayList<>(1);
			list.add(userId);
			List<String> custIds = filterDisUser(list);
			if (CollectionUtils.isEmpty(custIds)) {
				return;
			}
			sendMq.pushMessage(new PushParamsDTO(configDTO.getPushKey(), configDTO.getPushSecret(),
					CommonPushType.BY_ALIAS.getPushType(), userId, notification, msg, false, true, null,
					configDTO.isPushEvn()));
		}*/
	}

	@Override
	public void sendByTag(String tag, String notification, String msg) {
		/*PushConfigDTO configDTO = getPushConfig(RpcUtils.getAppId());
		if (checkPushConfig(configDTO)) {
			sendMq.pushMessage(new PushParamsDTO(configDTO.getPushKey(), configDTO.getPushSecret(),
					CommonPushType.BY_TAG.getPushType(), tag, notification, msg, false, true, null,
					configDTO.isPushEvn()));
		}*/
	}

	@Override
	public void sendByAlias(List<String> userIds, String notification, String msg) {
		/*PushConfigDTO configDTO = getPushConfig(RpcUtils.getAppId());
		if (checkPushConfig(configDTO)) {
			List<String> custIds = filterDisUser(userIds);
			if (CollectionUtils.isEmpty(custIds)) {
				return;
			}
			sendMq.pushMessage(new PushParamsDTO(configDTO.getPushKey(), configDTO.getPushSecret(),
					CommonPushType.BY_ALIASS.getPushType(), JsonUtils.toFastJson(custIds), notification, msg, false,
					true, null, configDTO.isPushEvn()));
		}*/
	}

	@Override
	public void sendByTag(List<String> tags, String notification, String msg) {
		/*PushConfigDTO configDTO = getPushConfig(RpcUtils.getAppId());
		if (checkPushConfig(configDTO)) {
			sendMq.pushMessage(new PushParamsDTO(configDTO.getPushKey(), configDTO.getPushSecret(),
					CommonPushType.BY_TAGS.getPushType(), JsonUtils.toFastJson(tags), notification, msg, false, true,
					null, configDTO.isPushEvn()));
		}*/
	}

	@Override
	public void commonSendAlias(PushReqVo reqVo) {
		String appId = RpcContext.getContext().getAttachment(Constants.APP_ID);
		// 获取推送配置
		PushConfigDTO configDTO = getPushConfig(appId);
		if (!checkPushConfig(configDTO)) {
			return;
		}

		if (reqVo.getPushType() == PushReqVo.CommonPushType.BY_ALL) {
			pushSender.pushMessage(new PushParamsDTO(configDTO.getPushKey(), configDTO.getPushSecret(),
					reqVo.getPushType().getPushType(), JsonUtils.toFastJson(reqVo.getCustIds()),
					reqVo.getNotification(), reqVo.getMsg(), reqVo.getNotifyStatus(), reqVo.getMsgStatus(),
					reqVo.getNofiticationBody(), configDTO.isPushEvn()));
			return;
		}

		// custId别名推送
		if (reqVo.getPushType() == CommonPushType.BY_ALIAS || reqVo.getPushType() == PushReqVo.CommonPushType.BY_ALIASS) {
			pushSender.pushMessage(new PushParamsDTO(configDTO.getPushKey(), configDTO.getPushSecret(),
					CommonPushType.BY_ALIASS.getPushType(), JsonUtils.toFastJson(reqVo.getCustIds()),
					reqVo.getNotification(), reqVo.getMsg(), reqVo.getNotifyStatus(), reqVo.getMsgStatus(),
					reqVo.getNofiticationBody(), configDTO.isPushEvn()));
			return;
		}

		// tag推送
		if (reqVo.getPushType() == CommonPushType.BY_TAG || reqVo.getPushType() == CommonPushType.BY_TAGS) {
			pushSender.pushMessage(new PushParamsDTO(configDTO.getPushKey(), configDTO.getPushSecret(),
					CommonPushType.BY_TAGS.getPushType(), JsonUtils.toFastJson(reqVo.getTags()),
					reqVo.getNotification(), reqVo.getMsg(), reqVo.getNotifyStatus(), reqVo.getMsgStatus(),
					reqVo.getNofiticationBody(), configDTO.isPushEvn()));
			return;
		}


		// 设备号推送
		if (CollectionUtils.isNotEmpty(reqVo.getRegistrationIds())) {
			pushSender.pushMessage(new PushParamsDTO(configDTO.getPushKey(), configDTO.getPushSecret(),
					CommonPushType.BY_REGISTRATIONIDS.getPushType(), JsonUtils.toFastJson(reqVo.getRegistrationIds()),
					reqVo.getNotification(), reqVo.getMsg(), reqVo.getNotifyStatus(), reqVo.getMsgStatus(),
					reqVo.getNofiticationBody(), configDTO.isPushEvn()));
		}
	}

	/**
	 * 
	 * 获取推送配置<br/>
	 * 若appId存在就根据应用配置获取
	 * 
	 * @param appId
	 * @return
	 */
	private PushConfigDTO getPushConfig(String appId) {
		PushConfigDTO configDTO = null;
		if (StringUtils.isBlank(appId)) {
			appId = Context.getProperty("appId.default");
		}
		configDTO = configRemote.getPushConfig(appId);
		/*
		if (StringUtils.isNotEmpty(appId)) {
			AppConfig config = null;//get..... //circleRemot.get(appId);
			if (config != null && config.getPushStatus() != null
					&& config.getPushStatus().intValue() == CircleStatus.SmsStatus.OPEN.getStatus()) {
				configDTO = config.getPushConfigInfo();
			}
		}*/

		return configDTO;
	}

	/**
	 * 检查推送配置
	 * 
	 * @param configDTO
	 * @return
	 */
	private boolean checkPushConfig(PushConfigDTO configDTO) {
		if (configDTO == null) {
			throw new ServiceException("push config get fail", "极光配置获取失败");
		}
		if (StringUtils.isEmpty(configDTO.getPushKey()) || StringUtils.isEmpty(configDTO.getPushSecret())) {
			throw new ServiceException("push config get fail", "极光配置获取失败");
		}
		return true;
	}


	@Override
	public List<PushReceived> getPushReceived(List<String> msgIds) {
		String appId = null;//RpcUtils.getAppId();
		// 获取推送配置
		PushConfigDTO configDTO = getPushConfig(appId);

		checkPushConfig(configDTO);
		List<Received> list = JPushService.getReceivedStatus(configDTO.getPushKey(), configDTO.getPushSecret(), msgIds);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		List<PushReceived> receiveds = JsonUtils.fromJson(JsonUtils.toFastJson(list),
				new TypeReference<List<PushReceived>>() {
				});

		return receiveds;
	}

}
