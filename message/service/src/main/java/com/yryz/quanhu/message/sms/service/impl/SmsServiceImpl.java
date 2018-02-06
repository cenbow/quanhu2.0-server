package com.yryz.quanhu.message.sms.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yryz.common.config.SmsConfigVO;
import com.yryz.common.config.VerifyCodeConfigVO;
import com.yryz.common.constant.IdConstants;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.message.common.constant.ConfigConstants;
import com.yryz.quanhu.message.common.remote.MessageCommonConfigRemote;
import com.yryz.quanhu.message.sms.dao.SmsLogDao;
import com.yryz.quanhu.message.sms.dto.SmsDTO;
import com.yryz.quanhu.message.sms.dto.SmsDTO.SmsType;
import com.yryz.quanhu.message.sms.entity.SmsChannel;
import com.yryz.quanhu.message.sms.entity.SmsLog;
import com.yryz.quanhu.message.sms.entity.SmsSign;
import com.yryz.quanhu.message.sms.entity.SmsTemplate;
import com.yryz.quanhu.message.sms.service.AlidayuMsg;
import com.yryz.quanhu.message.sms.service.SmsChannelService;
import com.yryz.quanhu.message.sms.service.SmsService;
import com.yryz.quanhu.message.sms.service.SmsTemplateService;
import com.yryz.quanhu.support.id.api.IdAPI;

@Service
public class SmsServiceImpl implements SmsService {
	private static final Logger logger = LoggerFactory.getLogger(SmsTemplateServiceImpl.class);
	@Autowired
	private SmsChannelService smsChannelService;
	@Autowired
	private SmsLogDao logDao;
	@Reference
	private IdAPI idApi;
	@Autowired
	private MessageCommonConfigRemote configService;
	@Autowired
	private SmsTemplateService smsTemplateService;
	@Autowired
	private SmsConfigVO configVO;

	@Override
	public boolean sendSms(SmsDTO smsDTO) {
		SmsConfigVO rangeConfigVO = JSON.parseObject(
				configService.getConfig(ConfigConstants.VERIFY_CODE_CONFIG_NAME, smsDTO.getAppId()),
				new TypeReference<SmsConfigVO>() {
				});
		if(rangeConfigVO == null){
			rangeConfigVO = configVO;
		}
		SmsSign sign = smsChannelService.getSign(rangeConfigVO.getSmsSignId());
		SmsChannel channel = smsChannelService.get(sign.getSmsChannelId());
		SmsTemplate template = smsTemplateService.get(rangeConfigVO.getVerifyTemplateId());
		if (smsDTO.getSmsType() != SmsType.VERIFY_CODE) {
			template = smsTemplateService.get(smsDTO.getMsgTemplateId());
		}
		try {
			SmsLog log = new SmsLog(smsDTO.getPhone(), (byte) smsDTO.getSmsType().getType(), channel.getSmsAppKey(),
					channel.getSmsChannelName(), sign.getSmsSign(), template.getSmsTemplateCode());
			log.setKid(ResponseUtils.getResponseData(idApi.getKid(IdConstants.QUANHU_SMS_LOG)));
			log.setCreateDate(new Date());
			logDao.insert(log);
		} catch (Exception e) {
			logger.error("[sms_log_save]",e);
		}
		
		boolean result = AlidayuMsg.sendVerifyCode(channel.getSmsAppKey(), channel.getSmsAppSecret(), smsDTO.getPhone(),
				JsonUtils.toFastJson(smsDTO.getSmsParams()), sign.getSmsSign(), template.getSmsTemplateCode());
		return result;
	}

}
