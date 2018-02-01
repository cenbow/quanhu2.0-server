package com.yryz.quanhu.user.mq;

import com.yryz.common.context.Context;

public class MqConstants {
	/**
	 * 用户创建mq队列
	 */
	public static final String USER_CREATE_QUEUE = Context.getProperty("user.user.mq.create.queue");
	/**
	 * 用户信息更新mq队列
	 */
	public static final String USER_UPDATE_QUEUE = Context.getProperty("user.user.mq.update.queue");
	
	public static final String USER_DIRECT_EXCHANGE = "quanhu.user.direct";
}
