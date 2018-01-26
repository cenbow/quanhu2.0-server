/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年9月11日
 * Id: MessagerManager.java, 2017年9月11日 下午2:30:52 Administrator
 */
package com.yryz.quanhu.user.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;
import com.google.common.collect.Lists;
import com.yryz.common.constant.AppConstants;
import com.yryz.common.context.Context;
import com.yryz.common.message.MessageActionCode;
import com.yryz.common.message.MessageLabel;
import com.yryz.common.message.MessageType;
import com.yryz.common.message.MessageViewCode;
import com.yryz.common.message.MsgEnumType;
import com.yryz.common.response.Response;
import com.yryz.common.utils.DateUtils;
import com.yryz.common.utils.IdGen;
import com.yryz.common.utils.JsonUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.message.message.api.MessageAPI;
import com.yryz.quanhu.message.message.vo.MessageVo;
import com.yryz.quanhu.message.push.api.PushAPI;
import com.yryz.quanhu.message.push.entity.PushReqVo;
import com.yryz.quanhu.message.push.entity.PushReqVo.CommonPushType;
import com.yryz.quanhu.user.utils.ThreadPoolUtil;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年9月11日 下午2:30:52
 * @Description 消息管理
 */
@Component
public class MessageManager {
	private static final Logger logger = LoggerFactory.getLogger(MessageManager.class);
	private static final String STAR_TITLE = "达人认证";

	/** 会员注册成功 */
	private static final String REGISTER_TITLE = "会员注册成功";

	/** 警告 */
	private static final String WARN_TITLE = "警告通知";

	/** 禁言通知 */
	private static final String DISTALK_TITLE = "禁言通知";

	/** 头像审核通知 */
	private static final String IMG_AUDIT_FAIL_TITLE = "头像审核通知";

	/** 达人认证成功 */
	private static final String STAR_SUCCESS_CONTENT = "您申请的圈乎认证已审核通过!";

	/** 达人认证取消 */
	private static final String STAR_CANCEL_CONTENT = "您的认证已被取消，有任何疑问请联系客服";

	/** 达人认证失败 */
	private static final String STAR_FAIL_CONTENT = "您申请的认证审核未通过，原因:%s。";

	/** 注册 */
	private static final String REGISTER_CONTENT = "恭喜，您已成功注册为圈乎会员，获300积分.开启您的圈乎之旅吧~";

	/** 警告 */
	private static final String WARN_CONTENT = "您的账号因发布可能包含人身攻击、敏感话题或无意义灌水的内容被警告1次。警告三次将会禁言8小时，请自觉遵守平台的言论发布规则，多谢合作。";

	/** 禁言 */
	private static final String DISTAIL_CONTENT = "您的账号因违反平台相关规则被禁言8小时，禁言期间您的账号会使用受限，如有疑问请联系客服。";

	/** 头像审核失败 */
	private static final String IMG_AUDIT_FAIL_CONTENT = "您上传的头像不符合要求，请重新上传。";

	@Reference
	private MessageAPI messagerApi;
	@Reference
	private PushAPI pushApi;

	/**
	 * 达人认证通过
	 * 
	 * @author danshiyu
	 * @date 2017年9月11日
	 * @param custId
	 * @param toUserId
	 * @Description 达人认证通过发送持久化消息和推送消息
	 */
	public void starSuccess(String toUserId) {
		MessageVo messageVo = new MessageVo();
		messageVo.setActionCode(MessageActionCode.MYTALENT);
		messageVo.setContent(STAR_SUCCESS_CONTENT);
		messageVo.setMessageId(IdGen.uuid());
		messageVo.setTitle(STAR_TITLE);
		messageVo.setLabel(MessageLabel.SYSTEM_TALENT);
		messageVo.setToCust(toUserId);
		messageVo.setType(MessageType.SYSTEM_TYPE);
		messageVo.setCreateTime(DateUtils.getDateTime());
		messageVo.setViewCode(MessageViewCode.SYSTEM_MESSAGE_1);
		sendMessage(messageVo, true);

	}

	/**
	 * 达人认证取消
	 * 
	 * @author danshiyu
	 * @date 2017年9月11日
	 * @param custId
	 * @param toUserId
	 * @Description 达人认证被取消发送持久化消息和推送消息
	 */
	public void starCancel(String toUserId) {
		MessageVo messageVo = new MessageVo();
		messageVo.setActionCode(MessageActionCode.MYTALENT);
		messageVo.setContent(STAR_CANCEL_CONTENT);
		messageVo.setMessageId(IdGen.uuid());
		messageVo.setTitle(STAR_TITLE);
		messageVo.setLabel(MessageLabel.SYSTEM_TALENT);
		messageVo.setToCust(toUserId);
		messageVo.setType(MessageType.SYSTEM_TYPE);
		messageVo.setCreateTime(DateUtils.getDateTime());
		messageVo.setViewCode(MessageViewCode.SYSTEM_MESSAGE_1);
		sendMessage(messageVo, true);
	}

	/**
	 * 达人认证失败
	 * 
	 * @author danshiyu
	 * @date 2017年9月11日
	 * @param toUserId
	 * @Description 达人认证失败消息推送以及持久化
	 */
	public void starFail(String toUserId, String reason) {
		MessageVo messageVo = new MessageVo();
		messageVo.setActionCode(MessageActionCode.MYTALENT);
		messageVo.setContent(String.format(STAR_FAIL_CONTENT, reason));
		messageVo.setMessageId(IdGen.uuid());
		messageVo.setTitle(STAR_TITLE);
		messageVo.setLabel(MessageLabel.SYSTEM_TALENT);
		messageVo.setToCust(toUserId);
		messageVo.setType(MessageType.SYSTEM_TYPE);
		messageVo.setCreateTime(DateUtils.getDateTime());
		messageVo.setViewCode(MessageViewCode.SYSTEM_MESSAGE_1);
		sendMessage(messageVo, true);
	}

	/**
	 * 注册
	 * 
	 * @author danshiyu
	 * @date 2017年9月11日
	 * @param toUserId
	 * @Description 注册持久化消息
	 */
	public void register(String toUserId,String appId) {
		MessageVo messageVo = new MessageVo();
		messageVo.setActionCode(MessageActionCode.NONE);
		messageVo.setContent(REGISTER_CONTENT);
		messageVo.setMessageId(IdGen.uuid());
		messageVo.setTitle(REGISTER_TITLE);
		messageVo.setLabel(MessageLabel.SYSTEM_ACCOUNT);
		messageVo.setToCust(toUserId);
		messageVo.setType(MessageType.SYSTEM_TYPE);
		messageVo.setCreateTime(DateUtils.getDateTime());
		messageVo.setViewCode(MessageViewCode.SYSTEM_MESSAGE_1);
		sendMessage(messageVo, false,appId);
	}

	/**
	 * 警告
	 * 
	 * @author danshiyu
	 * @date 2017年9月11日
	 * @param toUserId
	 * @Description 警告推送消息、持久化消息
	 */
	public void warn(String toUserId) {
		MessageVo messageVo = new MessageVo();
		messageVo.setContent(WARN_CONTENT);
		messageVo.setMessageId(IdGen.uuid());
		messageVo.setTitle(WARN_TITLE);
		messageVo.setActionCode(MessageActionCode.NONE);
		messageVo.setLabel(MessageLabel.SYSTEM_VIOLATION);
		messageVo.setToCust(toUserId);
		messageVo.setType(MessageType.SYSTEM_TYPE);
		messageVo.setCreateTime(DateUtils.getDateTime());
		messageVo.setViewCode(MessageViewCode.SYSTEM_MESSAGE_1);
		sendMessage(messageVo, true);
	}

	/**
	 * 禁言
	 * 
	 * @author danshiyu
	 * @date 2017年9月11日
	 * @param toUserId
	 * @Description 禁言推送消息、持久化消息
	 */
	public void disTalk(String toUserId) {
		MessageVo messageVo = new MessageVo();
		messageVo.setContent(DISTAIL_CONTENT);
		messageVo.setActionCode(MessageActionCode.NONE);
		messageVo.setMessageId(IdGen.uuid());
		messageVo.setTitle(DISTALK_TITLE);
		messageVo.setLabel(MessageLabel.SYSTEM_VIOLATION);
		messageVo.setToCust(toUserId);
		messageVo.setType(MessageType.SYSTEM_TYPE);
		messageVo.setCreateTime(DateUtils.getDateTime());
		messageVo.setViewCode(MessageViewCode.SYSTEM_MESSAGE_1);
		sendMessage(messageVo, true);
	}

	/**
	 * 头像审核失败
	 * 
	 * @author danshiyu
	 * @date 2017年9月11日
	 * @param toUserId
	 * @Description 头像审核持久化消息
	 */
	public void userImgAudit(String toUserId) {
		MessageVo messageVo = new MessageVo();
		messageVo.setContent(IMG_AUDIT_FAIL_CONTENT);
		messageVo.setMessageId(IdGen.uuid());
		messageVo.setTitle(IMG_AUDIT_FAIL_TITLE);
		messageVo.setActionCode(MessageActionCode.NONE);
		messageVo.setLabel(MessageLabel.SYSTEM_REVIEW);
		messageVo.setToCust(toUserId);
		messageVo.setType(MessageType.SYSTEM_TYPE);
		messageVo.setCreateTime(DateUtils.getDateTime());
		messageVo.setViewCode(MessageViewCode.SYSTEM_MESSAGE_1);
		messageVo.setMsgEnumType(MsgEnumType.USER_IMG_AUDIT_FAIL.getType());
		sendMessage(messageVo, false);
		pushMessage(messageVo);
	}
	
	/**
	 * push消息发送
	 * @param messageVo
	 */
	private void pushMessage(MessageVo messageVo) {
		ThreadPoolUtil.execue(new Runnable() {
			@Override
			public void run() {
				PushReqVo reqVo = new PushReqVo();
				reqVo.setMsg(JsonUtils.toFastJson(messageVo));
				reqVo.setNotifyStatus(false);
				reqVo.setPushType(CommonPushType.BY_ALIAS);
				reqVo.setCustIds(Lists.newArrayList(messageVo.getToCust()));
				try {
					RpcContext.getContext().setAttachment(AppConstants.APP_ID, Context.getProperty(AppConstants.APP_ID));
					Response<Boolean> response = pushApi.commonSendAlias(reqVo);
					logger.info("[pushMessage]:params:{},result:{}", JsonUtils.toFastJson(messageVo),
							JsonUtils.toFastJson(response));
				} catch (Exception e) {
					logger.error("[pushMessage]", e);
				}
			}
		});

	}
	
	/**
	 * 圈乎消息发消息
	 * @param messageVo
	 * @param cache
	 */
	private void sendMessage(MessageVo messageVo, Boolean cache){
		sendMessage(messageVo, cache,Context.getProperty(AppConstants.APP_ID));
	}
	
	/**
	 * 异步发送消息
	 * 
	 * @param messageVo
	 */
	private void sendMessage(MessageVo messageVo, Boolean cache,String appId) {
		//非圈乎业务不发消息
		if (messageVo == null || StringUtils.isBlank(appId)
				|| !StringUtils.equals(appId, Context.getProperty(AppConstants.APP_ID))) {
			return;
		}
		ThreadPoolUtil.execue(new Runnable() {
			@Override
			public void run() {
				try {
					RpcContext.getContext().setAttachment(AppConstants.APP_ID, appId);
					Response<Boolean> response = messagerApi.sendMessage(messageVo, cache);
					logger.info("[message]:params:{},result:{}", JsonUtils.toFastJson(messageVo),
							JsonUtils.toFastJson(response));
				} catch (Exception e) {
					logger.error("[message]", e);
				}
			}
		});
	}
}
