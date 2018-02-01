/**
 * 
 */
package com.yryz.quanhu.user.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author danshiyu
 * 管理后台查询用户dto
 */
@SuppressWarnings("serial")
public class AdminUserInfoDTO implements Serializable{
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 是否马甲 不为空即搜索马甲
	 */
	private String isVest;
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 注册起始时间
	 */
	private String startDate;
	/**
	 * 注册结束时间
	 */
	private String endDate;
	/**
	 * 应用id 必填
	 */
	private String appId;
	/**
	 * 用户状态 0-正常 1-禁言 2-冻结 3-注销
	 */
	private Integer userStatus;
	
	private List<Long> userIds;
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public List<Long> getUserIds() {
		return userIds;
	}
	public void setUserIds(List<Long> userIds) {
		this.userIds = userIds;
	}
	public String getIsVest() {
		return isVest;
	}
	public void setIsVest(String isVest) {
		this.isVest = isVest;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public Integer getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}
	public AdminUserInfoDTO() {
		super();
	}
	public AdminUserInfoDTO(String nickName, String isVest, String phone, String startDate, String endDate,
			List<Long> userIds) {
		super();
		this.nickName = nickName;
		this.isVest = isVest;
		this.phone = phone;
		this.startDate = startDate;
		this.endDate = endDate;
		this.userIds = userIds;
	}
	@Override
	public String toString() {
		return "AdminUserInfoDTO [nickName=" + nickName + ", phone=" + phone + ", startDate=" + startDate + ", endDate="
				+ endDate + "]";
	}
}
