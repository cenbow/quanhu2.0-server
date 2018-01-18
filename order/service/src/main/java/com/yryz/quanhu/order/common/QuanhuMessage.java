///**
// * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
// * All rights reserved.
// * 
// * Created on 2017年9月11日
// * Id: QuanhuPush.java, 2017年9月11日 上午11:45:03 yehao
// */
//package com.yryz.quanhu.order.common;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.stereotype.Service;
//
//import com.rongzhong.component.common.utils.DateUtils;
//import com.rongzhong.component.common.utils.IdGen;
//import com.rongzhong.component.common.utils.StringUtils;
//import com.yryz.service.api.basic.api.MessageAPI;
//import com.yryz.service.api.basic.api.PushAPI;
//import com.yryz.service.api.basic.entity.PushReqVo;
//import com.yryz.service.api.basic.entity.PushReqVo.CommonPushType;
//import com.yryz.service.api.basic.message.MessageType;
//import com.yryz.service.api.basic.message.MessageVo;
//import com.yryz.service.order.commom.utils.GsonUtils;
//
///**
// * @author yehao
// * @version 1.0
// * @date 2017年9月11日 上午11:45:03
// * @Description 圈乎消息
// */
//@Service
//public class QuanhuMessage {
//	
//	private Logger logger = LoggerFactory.getLogger(getClass());
//	
////	private static ThreadPool pool = ThreadPool.getThreadPool(10);
//	
//	@Autowired
//	private ThreadPoolTaskExecutor pool ; 
//	
//	@Autowired
//	private PushAPI pushAPI;
//	
//	@Autowired
//	private MessageAPI messageAPI;
//	
//	public void sendMessage(MessageConstant constant , String custId , String msg){
//		MessageVo messageVo = new MessageVo();
//		messageVo.setMessageId(IdGen.uuid());
//		messageVo.setActionCode(constant.getMessageActionCode());
//		String content = constant.getContent();
//		if(StringUtils.isNotEmpty(msg)){
//			try {
//				int icount = Integer.parseInt(msg);
//				msg = (icount / 100) +"";
//			} catch (Exception e) {
//			}
//			content = content.replaceAll("\\{count\\}", msg);
//		}
//		messageVo.setContent(content);
//		messageVo.setCreateTime(DateUtils.getDateTime());
//		messageVo.setLabel(constant.getLabel());
//		messageVo.setType(constant.getType());
//		messageVo.setTitle(constant.getTitle());
//		messageVo.setToCust(custId);
//		messageVo.setViewCode(constant.getMessageViewCode());
//		if(constant != MessageConstant.EDIT_PAY_PASSWORD){
//			logger.info("send message :" + GsonUtils.parseJson(messageVo));
//			sendMessage(messageVo, true);
//		} else {
//			sendMessage(messageVo, false);
//		}
//	}
//	
//	/**
//	 * 发送持久化消息
//	 * @param message
//	 */
//	public void sendMessage(MessageVo message , boolean flag){
//		pool.execute(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					messageAPI.sendMessage(message , flag);
//				} catch (Exception e) {
//					logger.warn("[message] send message faild ...",e);
//				}
//			}
//		});
//	}
//	
//	/**
//	 * 推送持久化消息
//	 * @param message
//	 */
//	public void pushMessage(MessageVo message){
//		pool.execute(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					PushReqVo reqVo = new PushReqVo();
//					List<String> custIds = new ArrayList<>();
//					custIds.add(message.getToCust());
//					reqVo.setCustIds(custIds);
//					int itype = message.getType() == null ? 0 : message.getType().intValue();
//					if(itype == MessageType.NOTICE_TYPE){
//						reqVo.setPushType(CommonPushType.BY_ALL);
//					} else {
//						reqVo.setPushType(CommonPushType.BY_ALIAS);
//					}
//					reqVo.setMsg(GsonUtils.parseJson(message));
//					pushAPI.commonSendAlias(reqVo);
//				} catch (Exception e) {
//					logger.warn("[message] push message faild ...",e);
//				}
//			}
//		});
//	}
//	
//	public static void main(String[] args) {
//		String msg = "3.698";
//		String content = MessageConstant.CASH.getContent();
//		if(StringUtils.isNotEmpty(msg)){
//			content = content.replaceAll("\\{count\\}", msg);
//		}
//		System.out.println(content);
//	}
//
//}
