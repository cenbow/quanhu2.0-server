/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年11月17日
 * Id: UserRegInviterLinkVO.java, 2017年11月17日 下午3:42:24 Administrator
 */
package com.yryz.quanhu.user.vo;

import java.io.Serializable;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年11月17日 下午3:42:24
 * @Description 邀请用户连接返回vo
 */
@SuppressWarnings("serial")
public class UserRegInviterLinkVO implements Serializable {
	/**
	 * 邀请码
	 */
	private String inviterCode;
	/**
	 * 邀请链接
	 */
	private String inviterLink;
	public String getInviterCode() {
		return inviterCode;
	}
	public void setInviterCode(String inviterCode) {
		this.inviterCode = inviterCode;
	}
	public String getInviterLink() {
		return inviterLink;
	}
	public void setInviterLink(String inviterLink) {
		this.inviterLink = inviterLink;
	}
	/**
	 * 
	 * @exception 
	 */
	public UserRegInviterLinkVO() {
		super();
	}
	/**
	 * @param inviterCode
	 * @param inviterLink
	 * @exception 
	 */
	public UserRegInviterLinkVO(String inviterCode, String inviterLink) {
		super();
		this.inviterCode = inviterCode;
		this.inviterLink = inviterLink;
	}
	@Override
	public String toString() {
		return "UserRegInviterLinkVO [inviterCode=" + inviterCode + ", inviterLink=" + inviterLink + "]";
	}
}
