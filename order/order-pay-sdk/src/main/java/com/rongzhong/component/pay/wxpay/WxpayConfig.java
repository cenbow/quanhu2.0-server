package com.rongzhong.component.pay.wxpay;

import com.yryz.common.context.Context;

public class WxpayConfig {
	/** 统一下单url **/
	public static final String UNIFIED_ORDER_URL = Context.getProperty("wxpay_server_host") + "/pay/unifiedorder";
	
	/** 订单查询地址 **/
	public static final String QUERY_ORDER_URL = Context.getProperty("wxpay_server_host") + "/pay/orderquery";
	
	/** 通知地址 **/
	public static final String NOTIFY_URL = Context.getProperty("wxpay_notify_url");
	
	/** 微信手机支付APP ID **/
	public static final String APP_ID = Context.getProperty("wxpay_app_id");
	
	/** 微信手机支付商户号 **/
	public static final String MCH_ID = Context.getProperty("wxpay_mch_id");
	
	/** 微信手机支付MD5秘钥 **/
	public static final String MD5_KEY = Context.getProperty("wxpay_md5_key");

	//微信支付商务证书
	public static final String MERCHANT_KEY_PATH = Context.getProperty("wxpay_merchant_key_path");

	//微信提现加密公钥证书（PKCS#8）
	public static final String PUBLIC_KEY_PATH = Context.getProperty("wxpay_public_key_path");

	
	/** 微信公众号APP ID **/
	public static final String GZH_APP_ID = Context.getProperty("wxpay_gzh_app_id");
	
	/** 微信公众号商户号 **/
	public static final String GZH_MCH_ID = Context.getProperty("wxpay_gzh_mch_id");
	
	/** 微信公众号MD5秘钥 **/
	public static final String GZH_MD5_KEY = Context.getProperty("wxpay_gzh_md5_key");
	/**
	 * 微信公众号登陆密码
	 */
	public static final String GZH_SECRET = Context.getProperty("wxpay_gzh_secret");
	
	/**
	 * 微信认证type
	 */
	public static final String GZH_GRANT_TYPE = Context.getProperty("wxpay_gzh_grant_type");
	/**
	 * 获得微信公众号URL
	 */
	public static final String GZH_ASCCESSURL = Context.getProperty("wxpay_gzh_accessUrl");
	/**
	 * 获得微信公众号URL(ticket)
	 */
	public static final String GZH_TICKETURL = Context.getProperty("wxpay_gzh_ticketUrl");
	/**
	 * 获取公众号openid grant_type
	 */
	public static final String GZH_OPENID_GRANT_TYPE = Context.getProperty("wxpay_gzh_openid_grant_type");
	/**
	 * 获取公众号openid url
	 */
	public static final String GZH_OPENID_ACCESSURL = Context.getProperty("wxpay_gzh_openid_accessUrl");
}
