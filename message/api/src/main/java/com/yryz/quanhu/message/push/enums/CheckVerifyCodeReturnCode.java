/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年12月11日
 * Id: CheckVerifyCodeReturnCode.java, 2017年12月11日 上午11:30:05 Administrator
 */
package com.yryz.quanhu.message.push.enums;

/**
 * 验证码验证码返回码
 * @author danshiyu
 * @version 1.0
 * @date 2017年12月11日 上午11:30:05
 */
public enum CheckVerifyCodeReturnCode {
	/** 成功 */
	SUCCESS(0),
	/** 失败 */
	FAIL(1),
	/** 过期 */
	EXPIRE(2);
	private int code;
	
	CheckVerifyCodeReturnCode(int code) {
		this.code = code;
	}
	
	public int getCode(){
		return code;
	}
}
