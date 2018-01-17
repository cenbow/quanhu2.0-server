/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月5日
 * Id: ThirdConstants.java, 2018年1月5日 上午11:31:10 Administrator
 */
package com.yryz.quanhu.user.contants;

import com.yryz.common.context.Context;

/**
 * 第三方配置
 * @author danshiyu
 * @version 1.0
 * @date 2018年1月5日 上午11:31:10
 */
public class ThirdConstants {
	/**
	 * 微信 开放api地址 //sns/userinfo
	 */
	public static final String WX_GET_USER_URL = Context.getProperty("WX_GET_USER_URL");
	/** 微信web开放认证地址 */
	public static final String WX_OPEN_URL = Context.getProperty("WX_OPEN_URL");
	/** 微信获取token地址 */
	public static final String WX_GET_TOKEN_URL = Context.getProperty("WX_GET_TOKEN_URL");
	/** 微信WAP认证地址 */
	public static final String WX_OPEN_OAUTH_URL = Context.getProperty("WX_OPEN_OAUTH_URL");
	public static final String WX_WEB_APP_ID = Context.getProperty("WX_WEB_APP_ID");
	public static final String WX_WEB_APP_SECRET = Context.getProperty("WX_WEB_APP_SECRET");
	public static final String WX_WEB_OAUTH_APP_ID = Context.getProperty("WX_WEB_OAUTH_APP_ID");
	public static final String WX_WEB_OAUTH_APP_SECRET = Context.getProperty("WX_WEB_OAUTH_APP_SECRET");

	/**
	 * QQ 开放api地址 //user/get_user_info
	 */
	public static final String QQ_GET_USER_URL = Context.getProperty("QQ_GET_USER_URL");
	public static final String QQ_APP_ID = Context.getProperty("QQ_APP_ID");
	public static final String QQ_APP_SECRET = Context.getProperty("QQ_APP_SECRET");

	/**
	 * 微博 开放api地址 //2/users/show.json
	 */
	public static final String WB_GET_USER_URL = Context.getProperty("WB_GET_USER_URL");
	public static final String WB_OPEN_URL = Context.getProperty("WB_OPEN_URL");
	public static final String WB_GET_TOKEN_URL = Context.getProperty("WB_GET_TOKEN_URL");
	public static final String WB_WEB_APP_ID = Context.getProperty("WB_WEB_APP_ID");
	public static final String WB_WEB_APP_SECRET = Context.getProperty("WB_WEB_APP_SECRET");

}
