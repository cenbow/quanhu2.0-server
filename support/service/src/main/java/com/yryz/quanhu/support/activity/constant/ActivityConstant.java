package com.yryz.quanhu.support.activity.constant;

public class ActivityConstant {

	/**
	 * 活动枚举
	 */
	public static final String ACTIVITY_ENUM = "1006";
	
	/**
	 * 活动作品枚举
	 */
	public static final String ACTIVITY_WORKS_ENUM = "1007";
	
	/**
	 * 活动开始至结束固定票数
	 */
	public static final Integer ACTIVITY_FIXED_VOTE = 1;
	/**
	 *每天可投
	 */
	public static final Integer ACTIVITY_DAYS_VOTE = 2;
	
	/**
	 * 参加活动
	 */
	public static final Integer ACTIVITY_JION = 1;
	/**
	 *投票
	 */
	public static final Integer ACTIVITY_VOTE = 2;
	
	/**
	 * app内
	 */
	public static final Integer IN_APP = 0;
	/**
	 *app外
	 */
	public static final Integer OTHER_APP = 1;
	
	/**
	 * 免费投票
	 */
	public static final Integer FREE_VOTE = 0;
	/**
	 *投票券投票
	 */
	public static final Integer NO_FREE_VOTE = 1;
	/**
	 *可参与 
	 */
	public static final Integer ACTIVITY_ENROL_STATUS_TAKE_JOIN = 11;
	/**
	 *已报名
	 */
	public static final Integer ACTIVITY_ENROL_STATUS_ALREADY_JOIN = 12;
	/**
	 *支付货币参加活动异常
	 */
	public static final Integer ACTIVITY_ENROL_STATUS_EXCE_JOIN = 13;
	/**
	 *报名活动类型:支付货币
	 */
	public static final Integer ACTIVITY_ENROL_TYPE_MONEY = 11;
	/**
	 *报名活动类型:支付积分
	 */
	public static final Integer ACTIVITY_ENROL_TYPE_INTEGRAL = 12;
	/**
	 *报名活动类型:免费
	 */
	public static final Integer ACTIVITY_ENROL_TYPE_FREE = 13;
	/**
	 *订单状态:创建
	 */
	public static final Integer ACTIVITY_ENROL_ORDER_STATE_CREATER = 0;
	/**
	 *订单状态:成功
	 */
	public static final Integer ACTIVITY_ENROL_ORDER_STATE_SUCCESS = 1;
	
}
