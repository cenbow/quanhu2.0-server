/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年12月4日
 * Id: CommonServiceType.java, 2017年12月4日 下午3:31:10 Administrator
 */
package com.yryz.quanhu.message.push.enums;

/**
 * 通用业务类型
 * 
 * @author danshiyu
 * @version 1.0
 * @date 2017年12月4日 下午3:31:10
 */
public enum CommonServiceType {
	/** 手机验证码发送 */
	PHONE_VERIFYCODE_SEND(0, "phone_verifyCode_send"),
	/** 邮件验证码发送 */
	EMAIL_VERIFYCODE_SEND(1,"emial_verifyCode_send"),
	/** 注册 */
	REGISTER(2, "register");
	private int type;

	private String name;

	CommonServiceType(int type, String name) {
		this.type = type;
		this.name = name;
	}

	public int getType() {
		return this.type;
	}

	public String getName() {
		return this.name;
	}
	
	/**
	 * 根据业务类型值获取枚举对象
	 * @param type
	 * @return
	 */
	public static CommonServiceType getEnumByType(int type){
		CommonServiceType serviceType = null;
		switch (type) {
		case 0:
			serviceType = CommonServiceType.PHONE_VERIFYCODE_SEND;			
			break;
		case 1:
			serviceType = CommonServiceType.EMAIL_VERIFYCODE_SEND;			
			break;
		case 2:
			serviceType = CommonServiceType.REGISTER;			
			break;
		default:
			break;
		}
		return serviceType;
	}
}
