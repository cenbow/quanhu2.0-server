package com.yryz.quanhu.sms.api;


import com.yryz.common.response.Response;
import com.yryz.quanhu.sms.dto.SmsDTO;

/**
 * Dubbo Service(user) API
 * 短信 验证服务
 * @author Pxie
 */
public interface SmsApi {
	/**
	 * 短信发送
	 * @param smsDTO
	 * @return
	 */
	public Response<Boolean> sendVerifyCode(SmsDTO smsDTO);
}

