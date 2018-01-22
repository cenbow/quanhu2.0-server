/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年11月10日
 * Id: SmsVerifyCodeVO.java, 2017年11月10日 下午1:59:39 Administrator
 */
package com.yryz.quanhu.user.vo;

import java.io.Serializable;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年11月10日 下午1:59:39
 * @Description 短信验证码返回
 */
@SuppressWarnings("serial")
public class SmsVerifyCodeVO implements Serializable{
	/**
	 * 功能码
	 */
	private String code;
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 验证码过期时间
	 */
	private String expire;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getExpire() {
		return expire;
	}

	public void setExpire(String expire) {
		this.expire = expire;
	}

	/**
	 * 
	 * @exception 
	 */
	public SmsVerifyCodeVO() {
		super();
	}

	/**
	 * @param code
	 * @param phone
	 * @param expire
	 * @exception 
	 */
	public SmsVerifyCodeVO(String code, String phone, String expire) {
		super();
		this.code = code;
		this.phone = phone;
		this.expire = expire;
	}

	@Override
	public String toString() {
		return "SmsVerifyCodeVO [code=" + code + ", phone=" + phone + ", expire=" + expire + "]";
	}
}
