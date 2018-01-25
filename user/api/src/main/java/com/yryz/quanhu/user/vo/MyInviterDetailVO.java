/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年11月16日
 * Id: MyInviterVO.java, 2017年11月16日 下午4:22:11 Administrator
 */
package com.yryz.quanhu.user.vo;

import java.io.Serializable;

/**
 * 我的邀请详情返回实体
 * @author danshiyu
 * @version 1.0
 * @date 2017年11月16日 下午4:22:11
 */
@SuppressWarnings("serial")
public class MyInviterDetailVO implements Serializable {
	/**
	 * 	被邀请人id（注册信息表主键）
	 */
	private Long inviterId;
	
	/**
	 * 用户昵称
	 */
	private String userNname;
	
	private Long userRegTime;

	public Long getInviterId() {
		return inviterId;
	}

	public void setInviterId(Long inviterId) {
		this.inviterId = inviterId;
	}

	public String getUserNname() {
		return userNname;
	}

	public void setUserNname(String userNname) {
		this.userNname = userNname;
	}

	public Long getUserRegTime() {
		return userRegTime;
	}

	public void setUserRegTime(Long userRegTime) {
		this.userRegTime = userRegTime;
	}

	/**
	 * 
	 * @exception 
	 */
	public MyInviterDetailVO() {
		super();
	}

	/**
	 * @param inviterId
	 * @param userNname
	 * @param userRegTime
	 * @exception 
	 */
	public MyInviterDetailVO(Long inviterId, String userNname, Long userRegTime) {
		super();
		this.inviterId = inviterId;
		this.userNname = userNname;
		this.userRegTime = userRegTime;
	}

	@Override
	public String toString() {
		return "MyInviterDetailVO [inviterId=" + inviterId + ", userNname=" + userNname + ", userRegTime=" + userRegTime
				+ "]";
	}
}
