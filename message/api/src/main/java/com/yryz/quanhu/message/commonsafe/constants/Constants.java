package com.yryz.quanhu.message.commonsafe.constants;

public class Constants {
	/**
	 * 每个ip每天限制总数
	 */
	public static final int IP_LIMIT_MAX_NUM = 1000;
	/**
	 * 每个ip执行业务的时间(缓存过期时间)
	 */
	public static final int IP_TIME_EXPIRE = 600;
	/**
	 * 系统配置缓存过期时间
	 */
	public final static int CONFIG_EXPIRE_TIME = 300;
	/**
	 * 触发图像验证码默认次数
	 */
	public static final int DEFAULT_IMG_VIOLATE_TIMES = 3;
	/**
	 * 默认触发图像验证码校验次数缓存过期时间
	 */
	public static final int DEFAULT_IMG_VIOLATE_EXPIRE = 600;
}
