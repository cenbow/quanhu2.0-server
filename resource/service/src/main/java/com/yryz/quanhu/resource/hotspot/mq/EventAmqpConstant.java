/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月29日
 * Id: EventAmqpConstant.java, 2018年1月29日 下午3:54:30 yehao
 */
package com.yryz.quanhu.resource.hotspot.mq;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月29日 下午3:54:30
 * @Description 事件的MQ枚举类
 */
public class EventAmqpConstant {
	
	public static final String SCORE_RECEIVE_QUEUE = "SCORE_RECEIVE_QUEUE";  //积分
	
	public static final String SCORE_RECEIVE_QUEUE_DEMO = "SCORE_RECEIVE_QUEUE_DEMO";  //积分

	public static final String GROW_RECEIVE_QUEUE = "GROW_RECEIVE_QUEUE";  //成长值
	
	public static final String EVENT_DIRECT_EXCHANGE = "EVENT_DIRECT_EXCHANGE";//exchange
	
	
	public static final String EVENT_DIRECT_EXCHANGE_DEMO = "EVENT_DIRECT_EXCHANGE_DEMO";//exchange
	
	public static final String HOT_SPOT_QUEUE = "HOT_SPOT_QUEUE";  //热度计算

}
