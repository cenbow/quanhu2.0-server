/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年9月8日
 * Id: MessageActionCode.java, 2017年9月8日 上午11:18:13 yehao
 */
package com.yryz.quanhu.message.message;

/**
 * @author yehao
 * @version 1.0
 * @date 2017年9月8日 上午11:18:13
 * @Description 消息响应类型枚举
 */
public class MessageActionCode {
	
	/**
	 * 无跳转
	 */
	public static final String NONE = "0";
	
	/**
	 * 消费账户明细
	 */
	public static final String ACCOUNT = "1";
	
	/**
	 * 奖励账户明细
	 */
	public static final String INTEGRAL = "2";
	
	/**
	 * 我的卡券
	 */
	public static final String MYCARD = "3";
	
	/**
	 * 我的私圈
	 */
	public static final String MYCOTERIE = "4";
	
	/**
	 * 达人认证
	 */
	public static final String MYTALENT = "5";
	
	/**
	 * 内部链接
	 */
	public static final String INNER_URL = "7";
	
	/**
	 * 外部链接
	 */
	public static final String EXTERNAL_URL = "8";
	
	/**
	 * 公告链接
	 */
	public static final String NOTICE_URL = "9";
	
	/**
	 * 个人主页
	 */
	public static final String HOME_PAGE = "10";
	
	/**
	 * 私圈首页
	 */
	public static final String COTERIE_HOME = "8001";
	
	/**
	 * 话题主页
	 */
	public static final String TOPIC_HOME = "8002";
	
	/**
	 * 悬赏详情页
	 */
	public static final String REWARD_DETAIL = "8003";
	
	/**
	 * 普通详情页
	 */
	public static final String COMMON_DETAIL = "8004";
	
	/**
	 * 成员审核页
	 */
	public static final String APPLY_LIST = "8005";
	
	/**
	 * 活动，报名页
	 */
	public static final String ACTIVITY_SIGNUP = "8006";
	
	/**
	 * 活动，投票页
	 */
	public static final String ACTIVITY_VOTE = "8007";
	
	/**
	 * 活动，中奖结果页
	 */
	public static final String ACTIVITY_RESULT = "8008";

}
