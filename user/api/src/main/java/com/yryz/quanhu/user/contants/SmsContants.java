/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年10月17日
 * Id: SmsContants.java, 2017年10月17日 下午2:21:56 Administrator
 */
package com.yryz.quanhu.user.contants;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年10月17日 下午2:21:56
 * @Description 短信常量
 */
public class SmsContants {
	/**
	 * check 注册 、 找回密码
	 */
	public static final String CODE_UNKNOW = "0";
	/**
	 * 注册
	 */
	public static final String CODE_REGISTER = "1";
	/**
	 * 找回密码
	 */
	public static final String CODE_FIND_PWD = "2";
	/**
	 * 实名认证
	 */
	public static final String CODE_IDENTITY = "3";
	/**
	 * 设置支付密码
	 */
	public static final String CODE_SET_PAYPWD = "4";
	/**
	 * 变更手机
	 */
	public static final String CODE_CHANGE_PHONE = "5";
	/**
	 * 找回支付密码
	 */
	public static final String CODE_CHANGE_PAYPWD = "6";
	/**
	 * 提现
	 */
	public static final String CODE_TAKE_CASH = "7";

	/**
	 * 其他（只取验证码）
	 */
	public static final String CODE_OTHER = "8";

	/**
	 * 验证码登录
	 */
	public static final String CODE_LOGIN = "9";
}
