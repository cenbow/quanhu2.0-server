package com.rongzhong.component.pay.iospay;

import com.yryz.common.context.Context;

public class IosIapConfig {
	
	/** 内购验证测试地址 **/
	public static final String URL_SANDBOX = Context.getProperty("ios_iap_url_sandbox");
	
	/** 内购验证正式线上地址 **/
	public static final String URL_VERIFY = Context.getProperty("ios_iap_url_verify");
	
	/** iap启用状态 : 1-启用，0-禁用**/
	public static final String IAP_ENABLE = Context.getProperty("ios_iap_enable");

}
