/**
 * 
 */
package com.yryz.quanhu.sms.entity;

import java.util.Date;

/**
 * 短信信息
 * @author pxie
 * @version 2016-08-18
 */
public class SmsInfoModel {
	
	private String phone;		// 手机号码
	private String code;		// 功能码
	private String verifyCode;	// 验证码
	private Date createDate;
	

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}