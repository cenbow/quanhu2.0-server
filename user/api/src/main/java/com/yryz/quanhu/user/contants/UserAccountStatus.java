package com.yryz.quanhu.user.contants;
/**
 * 用户账户账户枚举
 * @author danshiyu
 *
 */
public enum UserAccountStatus {
	/** 正常 */
	NORMAL(10),
	/** 冻结 */
	FREEZE(11),
	/** 注销 */
	DISTORY(12);
	private int status;
	
	UserAccountStatus(int status) {
		this.status = status;
	}
	public int getStatus(){
		return this.status;
	}
}
