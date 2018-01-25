package com.yryz.quanhu.user.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SmsVerifyCodeDTO implements Serializable{
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 功能码
	 */
	private String code;
	/**
	 * 应用id
	 */
	private String appId;
	/**
	 * 校验验证码
	 */
	private String veriCode;
	/**
	 * 用户id
	 */
	private Long userId;
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
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getVeriCode() {
		return veriCode;
	}
	public void setVeriCode(String veriCode) {
		this.veriCode = veriCode;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
