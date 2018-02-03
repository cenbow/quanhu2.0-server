/*
 * UserOperateInfo.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2018-01-12 Created
 */
package com.yryz.quanhu.user.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.yryz.common.entity.GenericEntity;

/**
 * 用户运营信息表
 * 
 * @author xxx
 * @version 1.0 2018-01-12
 * @since 1.0
 */
@SuppressWarnings("serial")
public class UserOperateInfo extends GenericEntity{
    /**
     * 用户id
     */
	@JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    /**
     * 账户注册渠道
     */
    private String userRegChannel;

    /**
     * 账户注册邀请码
     */
    private String userRegInviterCode;
    /**
     * 邀请人用户id
     */
    private String userInviterId;
    /**
     * 账户邀请码
     */
    private String userInviterCode;

    /**
     * 邀请人数
     */
    private Integer userRegInviterNum;

	public UserOperateInfo() {
		super();
	}

	public UserOperateInfo(Long userId, String userRegChannel, String userRegInviterCode) {
		super();
		this.userId = userId;
		this.userRegChannel = userRegChannel;
		this.userRegInviterCode = userRegInviterCode;
	}

	/**
	 * @param userId
	 * @param userRegChannel
	 * @param userRegInviterCode
	 * @param userInviterId
	 * @param userInviterCode
	 * @param userRegInviterNum
	 * @exception 
	 */
	public UserOperateInfo(Long userId, String userRegChannel, String userRegInviterCode, String userInviterId,
			String userInviterCode, Integer userRegInviterNum) {
		super();
		this.userId = userId;
		this.userRegChannel = userRegChannel;
		this.userRegInviterCode = userRegInviterCode;
		this.userInviterId = userInviterId;
		this.userInviterCode = userInviterCode;
		this.userRegInviterNum = userRegInviterNum;
	}

	public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserRegChannel() {
        return userRegChannel;
    }

    public void setUserRegChannel(String userRegChannel) {
        this.userRegChannel = userRegChannel == null ? null : userRegChannel.trim();
    }

    public String getUserRegInviterCode() {
        return userRegInviterCode;
    }

    public void setUserRegInviterCode(String userRegInviterCode) {
        this.userRegInviterCode = userRegInviterCode == null ? null : userRegInviterCode.trim();
    }

    public String getUserInviterCode() {
        return userInviterCode;
    }

    public void setUserInviterCode(String userInviterCode) {
        this.userInviterCode = userInviterCode == null ? null : userInviterCode.trim();
    }

    public Integer getUserRegInviterNum() {
        return userRegInviterNum;
    }

    public void setUserRegInviterNum(Integer userRegInviterNum) {
        this.userRegInviterNum = userRegInviterNum;
    }

	public String getUserInviterId() {
		return userInviterId;
	}

	public void setUserInviterId(String userInviterId) {
		this.userInviterId = userInviterId;
	}
}