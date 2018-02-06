package com.yryz.quanhu.message.sms.service;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.yryz.common.context.Context;

/**
 * 阿里大鱼短信接口
 * @author Pxie
 *
 */
public class AlidayuMsg {
	
	private static Logger logger = Logger.getLogger(AlidayuMsg.class);
	
	public static boolean sendVerifyCode(String appKey,String appSecret,String phone, String message,String sign,String templateCode) {
		
		boolean result = true;
		
		TaobaoClient client = new DefaultTaobaoClient(Context.getProperty("alidayu_http_host"), 
				//Context.getProperty("alidayu_app_key"), 
				//Context.getProperty("alidayu_app_secret")
				appKey,appSecret);
		
//		TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", "23389927", "a7c4b97d0878d013d47f7896392fa879");
		
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend("");
		req.setSmsType("normal");
		req.setSmsFreeSignName(sign);
//		req.setSmsFreeSignName("\u5927\u9c7c\u6d4b\u8bd5");
		req.setSmsParamString(message);
		req.setRecNum(phone);
		req.setSmsTemplateCode(templateCode);
		
//		req.setSmsTemplateCode("SMS_10647430");
		AlibabaAliqinFcSmsNumSendResponse rsp = null;
		
		try {
			rsp = client.execute(req);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AlidayuMsg.sendVerifyCode", e);
			result = false;
		} 
		
		if (logger.isDebugEnabled()) {
			logger.info("send sms: " + req.getSmsParam());
			if (rsp != null) {
				logger.info("send result: " + rsp.getErrorCode() + ", " + rsp.getMsg() + ", " + rsp.getBody());
			}
		} 
		
		// if (rsp == null || !"0".equals(rsp.getErrorCode())) {
		// result = false;
		// }
			
		return result;
	}

	public static String sendVoiceCode(String phone, String code) {
//		TaobaoClient client = new DefaultTaobaoClient(Context.getProperty("alidayu_http_host"), 
//				Context.getProperty("alidayu_app_key"), 
//				Context.getProperty("alidayu_app_secret"));
//		AlibabaAliqinFcTtsNumSinglecallRequest req = new AlibabaAliqinFcTtsNumSinglecallRequest();
//		req.setExtend("");
//		req.setTtsParamString("{verifyCode:'" + code + "',expire:'" + Context.getProperty("alidayu_expire") + "'}");
//		req.setCalledNum(phone);
//		req.setCalledShowNum(Context.getProperty("alidayu_voice_number"));
//		req.setTtsCode(Context.getProperty("alidayu_voice_template_code"));
//		AlibabaAliqinFcTtsNumSinglecallResponse rsp = null;
//		try {
//			rsp = client.execute(req);
//		} catch (ApiException e) {
//			e.printStackTrace();
//			return null;
//		}
//		System.out.println(rsp.getBody());
//		return rsp.getBody();
		
		return null;
	}
	
	public static void main(String[] args) {
		AlidayuMsg msg = new AlidayuMsg();
//		
		String sign = "\u5927\u9c7c\u6d4b\u8bd5";
		try {
			String str = new String(sign.getBytes(), "utf-8");
			System.out.println(str);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
//		msg.sendVerifyCode("13627148006", "869512",null,null);
//		msg.sendVoiceCode("13627148006", "869512");
		
	}
	
}
