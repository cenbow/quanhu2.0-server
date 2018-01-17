/*
 * UserThirdLogin.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2018-01-12 Created
 */
package com.yryz.quanhu.user.entity;

import com.yryz.common.entity.GenericEntity;

/**
 * 第三方账户表
 * 
 * @author xxx
 * @version 1.0 2018-01-12
 * @since 1.0
 */
@SuppressWarnings("serial")
public class UserThirdLogin extends GenericEntity{
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 第三方账户id
     */
    private String thirdId;

    /**
     * 登录类型10-weixin  11-xinna  12-qq 13-phone 14-email
     */
    private Byte loginType;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 删除标识 10-正常 11-删除
     */
    private Byte delFlag;

	/**
	 * 
	 * @exception 
	 */
	public UserThirdLogin() {
		super();
	}

	/**
	 * @param userId
	 * @param thirdId
	 * @param loginType
	 * @param nickName
	 * @exception 
	 */
	public UserThirdLogin(Long userId, String thirdId, Byte loginType, String nickName) {
		super();
		this.userId = userId;
		this.thirdId = thirdId;
		this.loginType = loginType;
		this.nickName = nickName;
	}

	/**
	 * @param userId
	 * @param thirdId
	 * @param loginType
	 * @param nickName
	 * @param delFlag
	 * @exception 
	 */
	public UserThirdLogin(Long userId, String thirdId, Byte loginType, String nickName, Byte delFlag) {
		super();
		this.userId = userId;
		this.thirdId = thirdId;
		this.loginType = loginType;
		this.nickName = nickName;
		this.delFlag = delFlag;
	}

	public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getThirdId() {
        return thirdId;
    }

    public void setThirdId(String thirdId) {
        this.thirdId = thirdId == null ? null : thirdId.trim();
    }

    public Byte getLoginType() {
        return loginType;
    }

    public void setLoginType(Byte loginType) {
        this.loginType = loginType;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public Byte getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Byte delFlag) {
        this.delFlag = delFlag;
    }

}