package com.yryz.quanhu.user.dto;

import java.io.Serializable;
/**
 * 忘记密码更新dto
 * @author danshiyu
 *
 */
@SuppressWarnings("serial")
public class ForgotPasswordDTO implements Serializable {
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 应用id
	 */
	private String appId;
	/**
	 * 验证码
	 */
	private String verifyCode;
	/**
	 * 新密码
	 */
	private String password;
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public ForgotPasswordDTO() {
		super();
	}
	public ForgotPasswordDTO(String phone, String appId, String verifyCode, String password) {
		super();
		this.phone = phone;
		this.appId = appId;
		this.verifyCode = verifyCode;
		this.password = password;
	}
}
