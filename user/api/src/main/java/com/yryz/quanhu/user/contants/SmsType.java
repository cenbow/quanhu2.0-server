/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年11月10日
 * Id: SmsType.java, 2017年11月10日 下午1:48:41 Administrator
 */
package com.yryz.quanhu.user.contants;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年11月10日 下午1:48:41
 * @Description 短信功能码类型
 */
public enum SmsType {
	/** check 注册 、 找回密码 */
	CODE_UNKNOW(0),
	/** 注册 */
	CODE_REGISTER(1),
	/** 找回密码 */
	CODE_FIND_PWD(2),
	/** 实名认证  */
	CODE_IDENTITY(3),
	/** 设置支付密码 */
	CODE_SET_PAYPWD(4),
	/** 变更手机 */
	CODE_CHANGE_PHONE(5),
	/** 找回支付密码 */
	CODE_CHANGE_PAYPWD(6),
	/** 提现 */
	CODE_TAKE_CASH(7),
	/** 其他（只取验证码） */
	CODE_OTHER(8),
	/** 验证码登录 */
	CODE_LOGIN(9),
	/** 活动绑定手机号 */
	ACTIVITY_BIND_PHONE(10);
	
	private int type;
	
	SmsType(int type) {
		this.type = type;
	}
	public int getType(){
		return this.type;
	}
}
