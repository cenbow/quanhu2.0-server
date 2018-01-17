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
	 * 邮箱
	 */
	private String userEmail;
	/**
	 * 登录密码
	 */
	private String userPwd;
	/**
	 * 是否马甲 0-否 1-是
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
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
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
	 * @param userNickName
	 * @param userPhone
	 * @param userEmail
	 * @param userPwd
	 */
	public AgentRegisterDTO(String userNickName, String userPhone, String userEmail, String userPwd) {
		super();
		this.userNickName = userNickName;
		this.userPhone = userPhone;
		this.userEmail = userEmail;
		this.userPwd = userPwd;
	}
	/**
	 * @param userNickName
	 * @param userPhone
	 * @param userPwd
	 */
	public AgentRegisterDTO(String userNickName, String userPhone, String userPwd) {
		super();
		this.userNickName = userNickName;
		this.userPhone = userPhone;
		this.userPwd = userPwd;
	}
	/**
	 * 马甲注册
	 * @param userLocation
	 * @param userNickName
	 * @param userPhone
	 * @param userEmail
	 * @param userPwd
	 * @param isVest
	 */
	public AgentRegisterDTO(String userLocation, String userNickName, String userPhone, String userEmail,
			String userPwd, Integer isVest) {
		super();
		this.userLocation = userLocation;
		this.userNickName = userNickName;
		this.userPhone = userPhone;
		this.userEmail = userEmail;
		this.userPwd = userPwd;
		this.isVest = isVest;
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
				+ userPhone + ", userEmail=" + userEmail + ", userPwd=" + userPwd + "]";
	}
}
