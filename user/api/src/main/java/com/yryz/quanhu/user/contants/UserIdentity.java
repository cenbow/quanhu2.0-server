package com.yryz.quanhu.user.contants;

/**
 * 用户身份
 * @author danshiyu
 *
 */
public enum UserIdentity {
	/**
	 * 普通用户
	 */
	NORMAL(0),
	/**
	 * 活动观察者
	 */
	ACTIVITY_VIEW(1);
	private int status;
	UserIdentity(int status) {
		this.status = status;
	}
	public int getStatus(){
		return this.status;
	}
}
