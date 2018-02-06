package com.yryz.quanhu.user.contants;

/**
 * 用户管理查询枚举
 * @author danshiyu
 *
 */
public enum AdminQueryUserStatus {
	/** 正常 */
	NORMAL(0),
	/** 违规 */
	VIOLATE(1),
	/** 禁言 */
	NOTALK(2),
	/** 冻结 */
	FREEZE(3),
	/** 注销 */
	DISTORY(4);
	private int status;
	
	private AdminQueryUserStatus(int status) {
		this.status = status;
	}
	public int getStatus(){
		return this.status;
	}
}
