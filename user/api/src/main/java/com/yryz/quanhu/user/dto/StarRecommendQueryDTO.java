package com.yryz.quanhu.user.dto;

import java.io.Serializable;
/**
 * 达人推荐查询
 * @author danshiyu
 *
 */
@SuppressWarnings("serial")
public class StarRecommendQueryDTO implements Serializable {
	/**
	 * 用户昵称
	 */
	private String userNname;
	/**
	 * 用户联系方式
	 */
	private String phone;
	
	/**
	 * 认证方式 0:用户申请 1:平台设置
	 */
	private Byte authWay;
	
	/**
	 * 用户等级
	 */
	private Integer userLevel;

	public String getUserNname() {
		return userNname;
	}

	public void setUserNname(String userNname) {
		this.userNname = userNname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Byte getAuthWay() {
		return authWay;
	}

	public void setAuthWay(Byte authWay) {
		this.authWay = authWay;
	}

	public Integer getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(Integer userLevel) {
		this.userLevel = userLevel;
	}
}
