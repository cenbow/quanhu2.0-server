package com.yryz.quanhu.sms.vo;

import java.io.Serializable;

/**
 * 短信数据
 * 
 * @author Pxie
 *
 */
@SuppressWarnings("serial")
public class SmsVerifyCode implements Serializable {

	private String code;
	private String phone;
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
}
