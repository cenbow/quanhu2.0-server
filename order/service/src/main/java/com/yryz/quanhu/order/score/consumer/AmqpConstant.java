/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月17日
 * Id: AmqpConstant.java, 2018年1月17日 下午3:01:00 yehao
 */
package com.yryz.quanhu.order.score.consumer;

/**
 * @author syc
 * @version 2.0
 * @date 2018年1月18日 下午3:01:00
 * @Description 消息队列声明枚举
 */
public class AmqpConstant {
	
	public static final String SCORE_QUEUE = "SCORE_QUEUE";
	
	public static final String SCORE_RECEIVE_QUEUE = "SCORE_RECEIVE_QUEUE";  //积分

	public static final String SCORE_RECEIVE_GROW = "SCORE_RECEIVE_GROW";  //成长值
	
	public static final String EVENT_DIRECT_EXCHANGE = "EVENT_DIRECT_EXCHANGE";//exchange
	
	public static final String Fanout =  "Fanout" ;
	public static final String QUEUE_EVENT =   "QUEUE_EVENT";
	
	
	
	

	
//	public static final String SCORE_FANOUT_QUEUE = "SCORE_FANOUT_QUEUE";
//	
//	public static final String SCORE_DIRECT_EXCHANGE = "SCORE_DIRECT_EXCHANGE";
//	
//	public static final String SCORE_FANOUT_EXCHANGE = "SCORE_FANOUT_EXCHANGE";
	
	
	

}
