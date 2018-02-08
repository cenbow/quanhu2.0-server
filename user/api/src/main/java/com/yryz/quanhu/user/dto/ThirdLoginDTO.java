/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年11月9日
 * Id: ThirdLoginDTO.java, 2017年11月9日 下午7:11:25 Administrator
 */
package com.yryz.quanhu.user.dto;

import java.io.Serializable;

import com.yryz.common.constant.DevType;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年11月9日 下午7:11:25
 * @Description 第三方登录请求dto
 */
@SuppressWarnings("serial")
public class ThirdLoginDTO implements Serializable {
	/**
	 * 极光设备id（用于极光registrationId推送）（非必填）
	 */
	private String registrationId;
	/**
	 * 第三方令牌
	 */
	private String accessToken;
	/**
	 * 城市代码
	 */
	private String cityCode;
	/**
	 * 注册渠道
	 */
	private String userChannel;
	/**
	 * 注册邀请码
	 */
	private String userRegInviterCode;
	/**
	 * 第三方唯一id 
	 */
	private String openId;
	/**
	 * 第三方类型 	1，微信 2，微博 3，qq
	 */
	private Integer type;
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 验证码
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
	 * 地区
	 */
	private String location;
	private UserRegLogDTO regLogDTO;
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getUserChannel() {
		return userChannel;
	}
	public void setUserChannel(String userChannel) {
		this.userChannel = userChannel;
	}
	public String getUserRegInviterCode() {
		return userRegInviterCode;
	}
	public void setUserRegInviterCode(String userRegInviterCode) {
		this.userRegInviterCode = userRegInviterCode;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public DevType getDevType() {
		return devType;
	}
	public void setDevType(DevType devType) {
		this.devType = devType;
	}
	public UserRegLogDTO getRegLogDTO() {
		return regLogDTO;
	}
	public void setRegLogDTO(UserRegLogDTO regLogDTO) {
		this.regLogDTO = regLogDTO;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getRegistrationId() {
		return registrationId;
	}
	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}
	@Override
	public String toString() {
		return "ThirdLoginDTO [accessToken=" + accessToken + ", cityCode=" + cityCode + ", userChannel=" + userChannel
				+ ", userRegInviterCode=" + userRegInviterCode + ", openId=" + openId + ", type=" + type + ", phone="
				+ phone + ", verifyCode=" + verifyCode + ", deviceId=" + deviceId + "]";
	}
}
