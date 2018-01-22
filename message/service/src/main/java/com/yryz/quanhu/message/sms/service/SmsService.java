package com.yryz.quanhu.message.sms.service;

import com.yryz.quanhu.message.sms.dto.SmsDTO;

public interface SmsService {
	/**
	 * 短信发送<br/>
	 * 需要增加mq发送短信通知
	 * @param smsDTO
	 * @return
	 */
	public boolean sendSms(SmsDTO smsDTO);
	
}
