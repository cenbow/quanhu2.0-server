package com.yryz.quanhu.sms.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.sms.dto.SmsDTO;
import com.yryz.quanhu.sms.dto.SmsDTO.SmsType;
import com.yryz.quanhu.sms.entity.SmsChannel;
import com.yryz.quanhu.sms.entity.SmsTemplate;
import com.yryz.quanhu.sms.service.AlidayuMsg;
import com.yryz.quanhu.sms.service.SmsChannelService;
import com.yryz.quanhu.sms.service.SmsService;
import com.yryz.quanhu.sms.service.SmsTemplateService;
import com.yryz.quanhu.sms.vo.SmsConfigVO;
import com.yryz.quanhu.support.id.api.ConfigApi;
import com.yryz.quanhu.support.id.constants.ConfigConstants;

@Service
public class SmsServiceImpl implements SmsService {
	private static final Logger logger = LoggerFactory.getLogger(SmsTemplateServiceImpl.class);
	@Autowired
	private SmsChannelService smsChannelService;

	@Autowired
	private SmsTemplateService smsTemplateService;
	
	@Reference
	private ConfigApi configApi;
	
	@Override
	public boolean sendVerifyCode(SmsDTO smsDTO) {
		SmsConfigVO configVO = new SmsConfigVO();
		String configType = String.format("%s.%s", ConfigConstants.SMS_CONFIG,smsDTO.getAppId());
		configVO = configApi.getConfig(configType, configVO).getData();
		SmsChannel channel = smsChannelService.getByParams(configVO.getSmsChannel(), null);
		SmsTemplate template = smsTemplateService.getByParams(configVO.getVerifyTemplateId());
		if(smsDTO.getSmsType() != SmsType.VERIFY_CODE){
			template = smsTemplateService.getByParams(smsDTO.getMsgTemplateId());
		}
		boolean result = AlidayuMsg.sendVerifyCode(channel.getAppKey(), channel.getAppSecret(), smsDTO.getPhone(), JsonUtils.toFastJson(smsDTO.getSmsParams()), channel.getAppKey(),template.getSmsTemplateCode());
		return result;
	}
	
}
