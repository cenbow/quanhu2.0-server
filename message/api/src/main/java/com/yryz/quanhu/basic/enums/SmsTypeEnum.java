/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年10月17日
 * Id: SmsTypeEunm.java, 2017年10月17日 下午2:10:53 Administrator
 */
package com.yryz.quanhu.basic.enums;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年10月17日 下午2:10:53
 * @Description 短信功能码
 */
public enum SmsTypeEnum {
	/**
	 * check 注册 、 找回密码
	 */
	CODE_UNKNOW(0, "0"),
	/**
	 * 注册
	 */
	CODE_REGISTER(1, "1"),
	/**
	 * 找回密码
	 */
	CODE_FIND_PWD(2, "2"),
	/**
	 * 实名认证
	 */
	CODE_IDENTITY(3, "3"),
	/**
	 * 设置支付密码
	 */
	CODE_SET_PAYPWD(4, "4"),
	/**
	 * 变更手机
	 */
	CODE_CHANGE_PHONE(5, "5"),
	/**
	 * 找回支付密码
	 */
	CODE_CHANGE_PAYPWD(6, "6"),
	/**
	 * 提现
	 */
	CODE_TAKE_CASH(7, "7"),

	/**
	 * 其他（只取验证码）
	 */
	CODE_OTHER(8, "8"),

	/**
	 * 验证码登录
	 */
	CODE_LOGIN(9, "9"),
	
	/** 活动页面登录注册 */
	CODE_ACTIVITY_LOGIN(10,"10");
	private Integer value;

	private String code;

	SmsTypeEnum(Integer value, String code) {
		this.value = value;
		this.code = code;
	}

	public Integer getValue() {
		return this.value;
	}

	public String getCode() {
		return this.code;
	}
}
