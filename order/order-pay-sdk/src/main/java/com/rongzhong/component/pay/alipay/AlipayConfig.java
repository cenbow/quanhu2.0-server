package com.rongzhong.component.pay.alipay;

import com.yryz.common.context.Context;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：
 *修改日期：2016-04-11
 *说明：
 */

public class AlipayConfig {
	/** 支付宝提供给商户的服务接入网关URL **/
	public static final String gateway_url = Context.getProperty("alipay_gateway_url");
	
	/** 合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串 **/
	public static final String partner = Context.getProperty("alipay_pid");
	
	/** 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号 **/
	public static final String seller_id = partner;
	
	/** 应用id **/
	public static final String app_id = Context.getProperty("alipay_appid");
	
	/** 支付宝公钥路径 **/
	public static final String ali_public_key_path = Context.getWebRootRealPath()+Context.getProperty("alipay_ali_public_key_path");
	
	/** 商户pkcs8私钥路径 **/
	public static final String rsa_private_key_pkcs8_path = Context.getWebRootRealPath()+Context.getProperty("alipay_rsa_private_key_pkcs8_path");

	/** MD5密钥，安全检验码，由数字和字母组成的32位字符串 **/
    public static final String md5_key = Context.getProperty("alipay_md5_key");

	/** 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 **/
	public static final String notify_url = Context.getProperty("alipay_notify_url");

	/** 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 **/
	public static final String return_url = Context.getProperty("alipay_return_url");
	
	/** 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。 **/
	public static final String log_path = Context.getProperty("alipay_log_path");
		
	/** 字符编码格式 目前支持 gbk 或 utf-8 **/
	public static final String input_charset = Context.getProperty("alipay_input_charset");
		
	/** 支付类型 ，无需修改 **/
	public static final String payment_type = "1";
	
	/** 签名方式 **/
	public static final String sign_type = Context.getProperty("alipay_sign_type");
	
	/** 即时付款接口名 **/
	public static final String direct_pay_name = Context.getProperty("alipay_direct_pay_name");
	
	/** 手机支付接口名 **/
	public static final String app_pay_name = Context.getProperty("alipay_app_pay_name");
	
// 请在这里配置防钓鱼信息，如果没开通防钓鱼功能，为空即可 
	
	/** 防钓鱼时间戳  若要使用请调用类文件submit中的query_timestamp函数 **/
	public static final String anti_phishing_key = "";
	
	/** 客户端的IP地址 非局域网的外网IP地址，如：221.0.0.1 **/
	public static final String exter_invoke_ip = "";
		
// 请在这里配置防钓鱼信息，如果没开通防钓鱼功能，为空即可
	
}

