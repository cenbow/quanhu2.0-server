/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年11月10日
 * Id: LoginMethodVO.java, 2017年11月10日 下午1:17:10 Administrator
 */
package com.yryz.quanhu.user.vo;

import java.io.Serializable;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年11月10日 下午1:17:10
 * @Description 登录方式
 */
@SuppressWarnings("serial")
public class LoginMethodVO implements Serializable {

    /**
     * 用户账户id
     */
    private Long userId;
    /**
     * 第三方id 或者手机号、邮箱
     */
    private String thirdId;
    /**
     * 登录类型1,wx  2,wb  3,qq 4,手机号 5,邮箱
     */
    private Integer loginType;
    /**
     * 是否拥有登录密码
     */
    private Boolean havePwd;
    
    public Long getuserId() {
        return userId;
    }
    public void setuserId(Long userId) {
        this.userId = userId;
    }
    public Integer getLoginType() {
        return loginType;
    }
    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }

	public boolean isHavePwd() {
		return havePwd;
	}
	public void setHavePwd(Boolean havePwd) {
		this.havePwd = havePwd == null ? false : true;
	}
	public String getThirdId() {
		return thirdId;
	}
	public void setThirdId(String thirdId) {
		this.thirdId = thirdId;
	}
	/**
	 * @param id
	 * @param userId
	 * @param thirdId
	 * @param loginType
	 * @param nickname
	 * @param createDate
	 * @exception 
	 */
	public LoginMethodVO(Long userId,String thirdId, Integer loginType, boolean havePwd) {
		super();
		this.userId = userId;
		this.thirdId = thirdId;
		this.loginType = loginType;
		this.havePwd = havePwd;
	}
	/**
	 * 
	 * @exception 
	 */
	public LoginMethodVO() {
		super();
	}
	@Override
	public String toString() {
		return "LoginMethodVO [userId=" + userId + ", loginType=" + loginType + ", havePwd="
				+ havePwd + "]";
	}
	
}
