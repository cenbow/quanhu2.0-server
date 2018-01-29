package com.yryz.quanhu.message.commonsafe.constants;

public enum CheckSlipCodeReturn {
	/** 成功或者无需滑动验证码 */
	SUCCESS(0),
	/** 验证失败 */
	FAIL(1),
	/** 需要滑动验证码 */
	NEED_CODE(2);
	private int code;
	
	CheckSlipCodeReturn(int code) {
		this.code = code;
	}
	
	public int getCode(){
		return code;
	}
}
