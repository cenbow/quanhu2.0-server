/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年9月8日
 * Id: MessageLabel.java, 2017年9月8日 上午11:17:32 yehao
 */
package com.yryz.quanhu.basic.message;

/**
 * @author yehao
 * @version 1.0
 * @date 2017年9月8日 上午11:17:32
 * @Description 消息类型二级类型
 */
public class MessageLabel {
	
	/**
	 * 1001 通知公告
	 */
	public static final int NOTICE_ANNOUNCEMENT = 1001;
	/**
	 * 2001 达人认证
	 */
	public static final int SYSTEM_TALENT = 2001;
	/**
	 * 2002 账号安全
	 */
	public static final int SYSTEM_ACCOUNT = 2002;
	/**
	 * 2003 违规
	 */
	public static final int SYSTEM_VIOLATION = 2003;
	/**
	 * 2004 审核
	 */
	public static final int SYSTEM_REVIEW = 2004;
	/**
	 * 2005 通知
	 */
	public static final int SYSTEM_NOTICE = 2005;
	/**
	 * 3001 充值
	 */
	public static final int ORDER_RECHARGE = 3001;
	/**
	 * 3002 支付
	 */
	public static final int ORDER_PAY = 3002;
	/**
	 * 3003 提现
	 */
	public static final int ORDER_CASH = 3003;
	/**
	 * 3004 退回
	 */
	public static final int ORDER_RETURN = 3004;
	/**
	 * 3005 到账
	 */
	public static final int ORDER_ACCOUNT = 3005;
	/**
	 * 评论 
	 */
	public static final int INTERACTIVE_COMMENT = 4001;
	
	/**
	 * 问答
	 */
	public static final int INTERACTIVE_ANSWER = 4002;
	
	/**
	 * 私圈
	 */
	public static final int INTERACTIVE_COTERIE = 4003;
	
	/**
	 * 活动
	 */
	public static final int INTERACTIVE_ACTIVITY = 4004;
	
	/**
	 * 推荐内容
	 */
	public static final int RECOMMEND_CONTENT = 5001;
	
	/**
	 * 推荐活动
	 */
	public static final int RECOMMEND_ACTIVITY = 5002;

}
