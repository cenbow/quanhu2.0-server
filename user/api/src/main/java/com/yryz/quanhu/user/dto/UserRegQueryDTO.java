/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年11月17日
 * Id: UserRegQueryDTO.java, 2017年11月17日 下午1:57:39 Administrator
 */
package com.yryz.quanhu.user.dto;

import java.io.Serializable;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年11月17日 下午1:57:39
 * @Description 用户邀请信息查询dto
 */
@SuppressWarnings("serial")
public class UserRegQueryDTO implements Serializable {
	/**
	 * 本人用户id
	 */
	private String userId;
	/**
	 * 邀请人用户id
	 */
	private String userRegId;
	/**
	 * 注册起始时间
	 */
	private String startDate;
	/**
	 * 注册结束时间
	 */
	private String endDate;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserRegId() {
		return userRegId;
	}
	public void setUserRegId(String UserRegId) {
		this.userRegId = UserRegId;
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
	/**
	 * 
	 * @exception 
	 */
	public UserRegQueryDTO() {
		super();
	}
	/**
	 * @param userId
	 * @exception 
	 */
	public UserRegQueryDTO(String userId) {
		super();
		this.userId = userId;
	}
	/**
	 * @param UserRegId
	 * @param startDate
	 * @param endDate
	 * @exception 
	 */
	public UserRegQueryDTO(String UserRegId, String startDate, String endDate) {
		super();
		this.userRegId = UserRegId;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	@Override
	public String toString() {
		return "UserRegQueryDTO [userId=" + userId + ", UserRegId=" + userRegId + ", startDate=" + startDate
				+ ", endDate=" + endDate + "]";
	}
}
