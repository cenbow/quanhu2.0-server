/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月5日
 * Id: RedisConstants.java, 2018年1月5日 下午3:01:16 Administrator
 */
package com.yryz.quanhu.user.contants;

/**
 * redis前缀
 * @author danshiyu
 * @version 1.0
 * @date 2018年1月5日 下午3:01:16
 */
public class RedisConstants {
	/**
	 * 令牌
	 */
	public static final String AUTH_TOKEN = "TOKEN";
	/**
	 * 用户账户
	 */
	public static final String ACCOUNT_USER = "ACUSER";
	/**
	 * 用户登录方式
	 */
	public static final String USER_LOGIN_METHOD = "UTLMD";
	/**
	 * 用户被警告次数
	 */
	public static final String USER_WARN_TIMES = "UWARN";
	/**
	 * 用户基础信息
	 */
	public static final String USER_INFO = "UINFO";
	/**
	 * 用户手机号信息
	 */
	public static final String USER_PHONE_INFO = "UPINFO";
}
