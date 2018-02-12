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
	/**
	 * ip地址
	 */
	private String ip;

	/**
	 * 阿里验证码请求参数
	 */
	private String session;
	private String sig;
	private String token;
	private String scene;

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String getSig() {
		return sig;
	}

	public void setSig(String sig) {
		this.sig = sig;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getScene() {
		return scene;
	}

	public void setScene(String scene) {
		this.scene = scene;
	}

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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
