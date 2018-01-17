package com.yryz.quanhu.user.contants;

public enum LoginType {
	/** 手机号密码登录 */
	PHONE(1),
	/** 手机号验证码登录 */
	VERIFYCODE(2),
	/** 邮箱登录 */
	EMAIL(3);
	private int type;
	LoginType(int type) {
		this.type = type;
	}
	
	public int type(){
		return this.type;
	}
}
