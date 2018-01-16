/**
 * 
 */
package com.yryz.quanhu.user.dto;

import java.io.Serializable;

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
	@Override
	public String toString() {
		return "AdminCustInfoDTO [nickName=" + nickName + ", phone=" + phone + ", startDate=" + startDate + ", endDate="
				+ endDate + "]";
	}
}
