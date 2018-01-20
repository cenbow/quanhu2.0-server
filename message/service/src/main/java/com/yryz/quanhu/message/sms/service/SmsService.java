package com.yryz.quanhu.message.sms.service;

import com.yryz.quanhu.message.sms.dto.SmsDTO;

public interface SmsService {
	/**
	 * 发送验证码
	 * @param smsDTO
	 * @return
	 */
	public boolean sendVerifyCode(SmsDTO smsDTO);
	
}
