package com.yryz.quanhu.sms.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.configuration.SmsConfigVO;
import com.yryz.quanhu.sms.dto.SmsDTO;
import com.yryz.quanhu.sms.dto.SmsDTO.SmsType;
import com.yryz.quanhu.sms.entity.SmsChannel;
import com.yryz.quanhu.sms.entity.SmsSign;
import com.yryz.quanhu.sms.entity.SmsTemplate;
import com.yryz.quanhu.sms.service.AlidayuMsg;
import com.yryz.quanhu.sms.service.SmsChannelService;
import com.yryz.quanhu.sms.service.SmsService;
import com.yryz.quanhu.sms.service.SmsTemplateService;

@Service
public class SmsServiceImpl implements SmsService {
	private static final Logger logger = LoggerFactory.getLogger(SmsTemplateServiceImpl.class);
	@Autowired
	private SmsChannelService smsChannelService;

	@Autowired
	private SmsTemplateService smsTemplateService;
	@Autowired
	private SmsConfigVO configVO;
	
	@Override
	public boolean sendVerifyCode(SmsDTO smsDTO) {
		SmsSign sign = smsChannelService.getSign(configVO.getSmsSignId());
		SmsChannel channel = smsChannelService.get(sign.getSmsChannelId());
		SmsTemplate template = smsTemplateService.get(configVO.getVerifyTemplateId());
		if(smsDTO.getSmsType() != SmsType.VERIFY_CODE){
			template = smsTemplateService.get(smsDTO.getMsgTemplateId());
		}
		boolean result = AlidayuMsg.sendVerifyCode(channel.getSmsAppKey(), channel.getSmsAppSecret(), smsDTO.getPhone(), JsonUtils.toFastJson(smsDTO.getSmsParams()), sign.getSmsSign(),template.getSmsTemplateCode());
		return result;
	}
	
}
