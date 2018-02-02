/**
 * 
 */
package com.yryz.quanhu.user.dto;

import java.io.Serializable;

/**
 * @author danshiyu
 * 管理后台代理注册
 */
@SuppressWarnings("serial")
public class AgentRegisterDTO implements Serializable{
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
	 * 用户签名（备注名）
	 */
	private String userSignature;
	/**
	 * 用户简介
	 */
	private String userDesc;
	/**
	 * 用户头像
	 */
	private String userImg;
	/**
	 * 登录密码
	 */
	private String userPwd;
	/**
	 * 是否马甲 10-否 11-是
	 */
	private Integer isVest;
	/**
	 * 应用id
	 */
	private String appId;
	public String getUserLocation() {
		return userLocation;
	}
	public void setUserLocation(String userLocation) {
		this.userLocation = userLocation;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getUserSignature() {
		return userSignature;
	}
	public void setUserSignature(String userSignature) {
		this.userSignature = userSignature;
	}
	public String getUserDesc() {
		return userDesc;
	}
	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}
	public String getUserImg() {
		return userImg;
	}
	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public Integer getIsVest() {
		return isVest;
	}
	public void setIsVest(Integer isVest) {
		this.isVest = isVest;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	/**
	 * 
	 */
	public AgentRegisterDTO() {
		super();
	}
	/**
	 * 马甲注册
	 * @param userLocation
	 * @param userNickName
	 * @param userPhone
	 * @param userSignature
	 * @param userDesc
	 * @param userImg
	 * @param userPwd
	 * @param isVest
	 * @param appId
	 */
	public AgentRegisterDTO(String userLocation, String userNickName, String userPhone, String userSignature,
			String userDesc, String userImg, String userPwd, Integer isVest, String appId) {
		super();
		this.userLocation = userLocation;
		this.userNickName = userNickName;
		this.userPhone = userPhone;
		this.userSignature = userSignature;
		this.userDesc = userDesc;
		this.userImg = userImg;
		this.userPwd = userPwd;
		this.isVest = isVest;
		this.appId = appId;
	}
	/**
	 * 
	 * @param userLocation
	 * @param userNickName
	 * @param userPhone
	 * @param userPwd
	 * @param isVest
	 * @param appId
	 */
	public AgentRegisterDTO(String userLocation, String userNickName, String userPhone, String userPwd, Integer isVest,
			String appId) {
		super();
		this.userLocation = userLocation;
		this.userNickName = userNickName;
		this.userPhone = userPhone;
		this.userPwd = userPwd;
		this.isVest = isVest;
		this.appId = appId;
	}
	@Override
	public String toString() {
		return "AgentRegisterDTO [userLocation=" + userLocation + ", userNickName=" + userNickName + ", userPhone="
				+ userPhone + ", userSignature=" + userSignature + ", userDesc=" + userDesc + ", userImg=" + userImg
				+ ", userPwd=" + userPwd + ", isVest=" + isVest + ", appId=" + appId + "]";
	}
}
