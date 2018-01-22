///**
// * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
// * All rights reserved.
// * 
// * Created on 2017年9月11日
// * Id: EventUtil.java, 2017年9月11日 上午11:57:06 yehao
// */
//package com.yryz.quanhu.order.common;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.stereotype.Service;
//
//import com.rongzhong.component.common.utils.DateUtils;
//import com.yryz.service.api.event.api.EventAPI;
//import com.yryz.service.api.event.entity.EventInfo;
//import com.yryz.service.api.event.enums.EventEnum;
//import com.yryz.service.order.commom.utils.GsonUtils;
//
///**
// * @author yehao
// * @version 1.0
// * @date 2017年9月11日 上午11:57:06
// * @Description 事件管理
// */
//@Service
//public class EventManager {
//	
//	private Logger logger = LoggerFactory.getLogger(getClass());
//	
//	@Autowired
//	private ThreadPoolTaskExecutor threadPool ; 
//	
//	@Autowired
//	private EventAPI eventAPI;
//	
//	public void commitRecharge(String custId , long amount){
//		EventInfo eventInfo = new EventInfo();
//		eventInfo.setEventCode(EventEnum.RECHARGE.getCode());
//		eventInfo.setCustId(custId);
//		eventInfo.setAmount((double)amount);
//		eventInfo.setCreateTime(DateUtils.getDateTime());
//		eventInfo.setEventNum(1);
//		eventInfo.setOwnerId(custId);
//		commit(eventInfo);
//	}
//	
//    /**
//     * 提交事件
//     * @param event
//     */
//    public void commit(EventInfo event){
//    	threadPool.execute(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					logger.info("commit EventInfo ..." + GsonUtils.parseJson(event));
//					eventAPI.commit(event);
//				} catch (Exception e) {
//					logger.warn("[event] commit event faild ..." , e);
//				}
//			}
//		});
//    }
//
//}