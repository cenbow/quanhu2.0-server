/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月22日
 * Id: PayMsgConstant.java, 2018年1月22日 下午4:03:47 yehao
 */
package com.yryz.quanhu.openapi.constants;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月22日 下午4:03:47
 * @Description 消息返回枚举
 */
public class PayMsgConstant {
	
	public static final String message_payType = "payType只能为1,为支付状态";
	public static final String message_pay_pass = "支付密码错误";
	public static final String message_pay_error = "支付异常";
	public static final String message_pay_50 = "50元以上或没开通免密支付需要输入支付密码";
	public static final String message_account_unexist = "账户不存在";
	public static final String message_account_lock = "账户锁定或账户异常";
	public static final String message_account_unenough = "余额不足";

}
