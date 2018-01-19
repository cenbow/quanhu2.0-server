/*
 * UserAccount.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2018-01-05 Created
 */
package com.yryz.quanhu.user.entity;

import com.yryz.common.entity.GenericEntity;

/**
 * 用户账户
 * 
 * @author danshiyu
 * @version 1.0 2018-01-05
 * @since 1.0
 */
@SuppressWarnings("serial")
public class UserAccount extends GenericEntity{

    /**
     * 应用id
     */
    private String appId;

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
     * 删除标识 0:有效  1:删除
     */
    private Byte delFlag;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail == null ? null : userEmail.trim();
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd == null ? null : userPwd.trim();
    }

    public Byte getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Byte delFlag) {
        this.delFlag = delFlag;
    }

	public UserAccount() {
		super();
	}

	/**
	 * @param appId
	 * @param userPhone
	 * @param userPwd
	 * @exception 
	 */
	public UserAccount(String appId, String userPhone, String userPwd) {
		super();
		this.appId = appId;
		this.userPhone = userPhone;
		this.userPwd = userPwd;
	}

	@Override
	public String toString() {
		return "UserAccount [appId=" + appId + ", userPhone=" + userPhone + ", userEmail=" + userEmail + ", userPwd="
				+ userPwd + ", delFlag=" + delFlag + ", createDate=" + createDate + ", lastUpdateDate=" + lastUpdateDate
				+ "]";
	}
}