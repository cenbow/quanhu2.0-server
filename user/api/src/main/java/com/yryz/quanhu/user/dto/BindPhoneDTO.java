package com.yryz.quanhu.user.dto;

import java.io.Serializable;

/**
 * 绑定手机号dto
 * @author danshiyu
 *
 */
@SuppressWarnings("serial")
public class BindPhoneDTO implements Serializable {
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 验证码
	 */
	private String verifyCode;
	/**
	 * 应用id
	 */
	private String appId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public BindPhoneDTO() {
		super();
	}
	public BindPhoneDTO(String userId, String phone, String verifyCode, String appId) {
		super();
		this.userId = userId;
		this.phone = phone;
		this.verifyCode = verifyCode;
		this.appId = appId;
	}
}
