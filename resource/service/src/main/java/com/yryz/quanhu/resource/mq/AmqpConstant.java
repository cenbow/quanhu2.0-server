/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月17日
 * Id: AmqpConstant.java, 2018年1月17日 下午3:01:00 yehao
 */
package com.yryz.quanhu.resource.mq;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月17日 下午3:01:00
 * @Description 消息队列声明枚举
 */
public class AmqpConstant {
	
	/**
	 * 资源提交队列
	 */
	public static final String RESOURCE_COMMIT_QUEUE = "RESOURCE_COMMIT_QUEUE";
	
	/**
	 * 资源-动态提交EXCHANGE
	 */
	public static final String RESOURCE_DYNAMIC_FANOUT_EXCHANGE = "RESOURCE_DYNAMIC_FANOUT_EXCHANGE";

}
