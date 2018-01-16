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
	@Override
	public String toString() {
		return "AgentRegisterDTO [userLocation=" + userLocation + ", userNickName=" + userNickName + ", userPhone="
				+ userPhone + ", userEmail=" + userEmail + ", userPwd=" + userPwd + "]";
	}
}
