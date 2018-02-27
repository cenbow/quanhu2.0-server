/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月18日
 * Id: OrderConstant.java, 2018年1月18日 下午1:32:30 yehao
 */
package com.yryz.quanhu.order.enums;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月18日 下午1:32:30
 * @Description 资金管理枚举类
 */
public class OrderConstant {
	
	public static int PAY_PASSWORD_ERROR_COUNT=5;
	
	/**订单来源：WEB**/
	public static final String ORDER_SRC_WEB = "1";
	/**订单来源：android**/
	public static final String ORDER_SRC_ANDROID = "2";
	/**订单来源：IOS**/
	public static final String ORDER_SRC_IOS = "3";
	/**订单来源：WAP**/
	public static final String ORDER_SRC_WAP = "4";
	
	/**系统实际账户类型：支付宝**/
	public static final String SYS_ACCOUNT_TYPE_ALIPAY = "2";
	/**系统实际账户类型：微信**/
	public static final String SYS_ACCOUNT_TYPE_WXPAY = "3";
	/**系统实际账户类型：苹果IAP**/
	public static final String SYS_ACCOUNT_TYPE_IOS_IAP = "4";
	
	/**支付方式:账户余额支付**/
	public static final String PAY_WAY_ACCOUNT = "2";
	/**支付方式:支付宝支付**/
	public static final String PAY_WAY_ALIPAY = "3";
	/**支付方式:微信支付**/
	public static final String PAY_WAY_WXPAY = "4";
	/**支付方式:苹果内购**/
	public static final String PAY_WAY_IOS_IAP = "5";
	/**支付方式:提现**/
	public static final String PAY_WAY_CASH_TRANS = "6";
	/**支付方式:线下提现**/
	public static final String PAY_WAY_GET_CASH_MANUAL = "7";
	/**支付方式:连连支付-借记卡网银支付**/
	public static final String PAY_WAY_LLPAY_WEB_DEBIT = "8";
	/**支付方式:连连支付-信用卡网银支付**/
	public static final String PAY_WAY_LLPAY_WEB_CREDIT = "9";
	/**支付方式:连连支付-借记卡快捷支付**/
	public static final String PAY_WAY_LLPAY_FAST_DEBIT = "10";
	/**支付方式:连连支付-信用卡快捷支付**/
	public static final String PAY_WAY_LLPAY_FAST_CREDIT = "11";
	/**支付方式:银联支付**/
	public static final String PAY_WAY_UNIONPAY = "12";
	/**支付方式:微信提现到银行卡**/
	public static final String PAY_WAY_WX_CASH_CARD = "13";

	
}
