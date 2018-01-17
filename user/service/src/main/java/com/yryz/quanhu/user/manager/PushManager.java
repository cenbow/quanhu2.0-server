/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年11月9日
 * Id: PushManager.java, 2017年11月9日 下午2:05:51 Administrator
 */
package com.yryz.quanhu.user.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年11月9日 下午2:05:51
 * @Description 推送
 */
@Component
public class PushManager {
	private static final Logger logger = LoggerFactory.getLogger(PushManager.class);
	private static final Logger Logger = LoggerFactory.getLogger("push.logger");
	/** 警告 */
	private static final String WARN_TITLE = "警告通知";
	
	/** 警告 */
	private static final String WARN_CONTENT = "您的账号因发布可能包含人身攻击、敏感话题或无意义灌水的内容被警告1次。警告三次将会禁言8小时，请自觉遵守平台的言论发布规则，多谢合作。";
	
	/** 订单 */
	private static final String ORDER_TITLE = "下单通知通知";
	
	/** 订单 */
	private static final String ORDER_CONTENT = "订单支付成功";
	
	/** 和app端约定的活动tag */
	private static final String ACTIVITY_TAG  = "activityTag";
	

}
