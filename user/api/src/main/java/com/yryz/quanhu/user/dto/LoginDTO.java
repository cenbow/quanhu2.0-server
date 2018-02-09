/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年11月9日
 * Id: LoginDTO.java, 2017年11月9日 下午7:05:41 Administrator
 */
package com.yryz.quanhu.user.dto;

import java.io.Serializable;

import com.yryz.common.constant.DevType;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年11月9日 下午7:05:41
 * @Description 普通登录请求dto
 */
@SuppressWarnings("serial")
public class LoginDTO implements Serializable {
	/**
	 * 极光设备id（用于极光registrationId推送）（非必填）
	 */
	private String registrationId;
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 登录密码
	 */
	private String password;
	/**
	 * 短信验证码
	 */
	private String verifyCode;
	/**
	 * 推送设备号
	 */
	private String deviceId;
	/**
	 * token类型
	 */
	private DevType devType;
	
	/**
	 * 地区名称
	 */
	private String userLocation;
	/**
	 * 活动渠道码
	 */
	private String activityChannelCode;
	/**
	 * 登录ip
	 */
	private String ip;
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	public DevType getDevType() {
		return devType;
	}
	public void setDevType(DevType devType) {
		this.devType = devType;
	}
	public String getUserLocation() {
		return userLocation;
	}
	public void setUserLocation(String userLocation) {
		this.userLocation = userLocation;
	}
	public String getActivityChannelCode() {
		return activityChannelCode;
	}
	public void setActivityChannelCode(String activityChannelCode) {
		this.activityChannelCode = activityChannelCode;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getRegistrationId() {
		return registrationId;
	}
	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}
	@Override
	public String toString() {
		return "LoginDTO [phone=" + phone + ", email=" + email + ", password=" + password + ", verifyCode=" + verifyCode
				+ ", deviceId=" + deviceId + "]";
	}
}
