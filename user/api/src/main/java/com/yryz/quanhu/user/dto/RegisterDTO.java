/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年11月9日
 * Id: RegisterDTO.java, 2017年11月9日 下午6:57:03 Administrator
 */
package com.yryz.quanhu.user.dto;

import java.io.Serializable;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年11月9日 下午6:57:03
 * @Description 普通注册请求dto
 */
@SuppressWarnings("serial")
public class RegisterDTO implements Serializable {
	/**
	 * 	城市代码
	 */
	private String cityCode;
	/**
	 * 	注册渠道
	 */
	private String userChannel;
	/**
	 * 地区名称
	 */
	private String userLocation;
	/**
	 * 昵称
	 */
	private String userNickName;
	/**
	 * 手机号
	 */
	private String userPhone;
	/**
	 * 邮箱
	 */
	private String userEmail;
	/**
	 * 登录密码
	 */
	private String userPwd;
	/**
	 * 邀请码
	 */
	private String userRegInviterCode;
	/**
	 * 验证码（包含短信验证码和邮箱验证码）
	 */
	private String veriCode;
	/**
	 * 推送设备号
	 */
	private String deviceId;
	
	/**
	 * 活动渠道码
	 */
	private String activityChannelCode;
	/**
	 * 是否马甲 10-否 11-是
	 */
	private Integer isVest;
	/**
	 * 用户头像
	 */
	private String userImg;
	/**
	 * 性别
	 */
	private Integer userGenders;
	/**
	 * 用户简介
	 */
	private String userDesc;
	/**
	 * 用户签名
	 */
	private String userSign;
	/**
	 * 注册日志对象
	 */
	private UserRegLogDTO regLogDTO;
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getUserChannel() {
		return userChannel;
	}
	public void setUserChannel(String UserChannel) {
		this.userChannel = UserChannel;
	}
	public String getUserLocation() {
		return userLocation;
	}
	public void setUserLocation(String UserLocation) {
		this.userLocation = UserLocation;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String UserNickName) {
		this.userNickName = UserNickName;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String UserPhone) {
		this.userPhone = UserPhone;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String UserEmail) {
		this.userEmail = UserEmail;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String UserPwd) {
		this.userPwd = UserPwd;
	}
	public String getUserRegInviterCode() {
		return userRegInviterCode;
	}
	public void setUserRegInviterCode(String UserRegInviterCode) {
		this.userRegInviterCode = UserRegInviterCode;
	}
	public String getVeriCode() {
		return veriCode;
	}
	public void setVeriCode(String veriCode) {
		this.veriCode = veriCode;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public UserRegLogDTO getRegLogDTO() {
		return regLogDTO;
	}
	public void setRegLogDTO(UserRegLogDTO regLogDTO) {
		this.regLogDTO = regLogDTO;
	}
	public String getActivityChannelCode() {
		return activityChannelCode;
	}
	public void setActivityChannelCode(String activityChannelCode) {
		this.activityChannelCode = activityChannelCode;
	}
	public Integer getIsVest() {
		return isVest;
	}
	public void setIsVest(Integer isVest) {
		this.isVest = isVest;
	}
	public String getUserImg() {
		return userImg;
	}
	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}
	public String getUserDesc() {
		return userDesc;
	}
	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}
	public String getUserSign() {
		return userSign;
	}
	public void setUserSign(String userSign) {
		this.userSign = userSign;
	}
	public Integer getUserGenders() {
		return userGenders;
	}
	public void setUserGenders(Integer userGenders) {
		this.userGenders = userGenders;
	}
	/**
	 * 
	 */
	public RegisterDTO() {
		super();
	}
	/**
	 * @param UserChannel
	 * @param UserNickName
	 * @param UserPhone
	 * @param UserEmail
	 * @param UserPwd
	 */
	public RegisterDTO(String UserChannel, String UserNickName, String UserPhone, String UserEmail, String UserPwd) {
		super();
		this.userChannel = UserChannel;
		this.userNickName = UserNickName;
		this.userPhone = UserPhone;
		this.userEmail = UserEmail;
		this.userPwd = UserPwd;
	}
	public RegisterDTO(String userChannel, String userPhone, String userPwd, UserRegLogDTO regLogDTO) {
		super();
		this.userChannel = userChannel;
		this.userPhone = userPhone;
		this.userPwd = userPwd;
		this.regLogDTO = regLogDTO;
	}
	public RegisterDTO(String userChannel, String userNickName, String userPhone, String userPwd, Integer isVest,
			String userImg, String userDesc, String userSign, UserRegLogDTO regLogDTO) {
		super();
		this.userChannel = userChannel;
		this.userNickName = userNickName;
		this.userPhone = userPhone;
		this.userPwd = userPwd;
		this.isVest = isVest;
		this.userImg = userImg;
		this.userDesc = userDesc;
		this.userSign = userSign;
		this.regLogDTO = regLogDTO;
	}
	@Override
	public String toString() {
		return "RegisterDTO [cityCode=" + cityCode + ", UserChannel=" + userChannel + ", UserLocation=" + userLocation
				+ ", UserNickName=" + userNickName + ", UserPhone=" + userPhone + ", UserEmail=" + userEmail
				+ ", UserPwd=" + userPwd + ", UserRegInviterCode=" + userRegInviterCode + ", veriCode=" + veriCode
				+ ", deviceId=" + deviceId + "]";
	}
	
}
