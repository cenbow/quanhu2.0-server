package com.yryz.quanhu.message.sms.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.message.sms.api.SmsApi;
import com.yryz.quanhu.message.sms.dto.SmsDTO;
import com.yryz.quanhu.message.sms.dto.SmsDTO.SmsType;
import com.yryz.quanhu.message.sms.service.SmsService;

@Service(interfaceClass=SmsApi.class)
public class SmsProvider implements SmsApi {
	private Logger logger = LoggerFactory.getLogger(SmsProvider.class);
	
	@Autowired
	private SmsService smsService;
	
	@Override
	public Response<Boolean> sendSms(SmsDTO smsDTO) {
		try {
			checkSmsDTO(smsDTO);
			return ResponseUtils.returnObjectSuccess(smsService.sendSms(smsDTO));
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("短信验证码发送异常", e);
			return ResponseUtils.returnException(e);
		}
	}

	private void checkSmsDTO(SmsDTO smsDTO) {
		if (smsDTO == null) {
			throw QuanhuException.busiError(ExceptionEnum.PARAM_MISSING.getCode(), "传参不合法");
		}
		if (StringUtils.isEmpty(smsDTO.getPhone())) {
			throw QuanhuException.busiError(ExceptionEnum.PARAM_MISSING.getCode(), "手机号为空");
		}
		if (smsDTO.getSmsType() == null) {
			throw QuanhuException.busiError(ExceptionEnum.PARAM_MISSING.getCode(), "功能码不能为空");
		}
		if(StringUtils.isBlank(smsDTO.getAppId())){
			throw QuanhuException.busiError(ExceptionEnum.PARAM_MISSING.getCode(), "应用id不能为空");
		}
		if(smsDTO.getSmsType() == SmsType.SMS_MSG && smsDTO.getMsgTemplateId() == null){
			throw QuanhuException.busiError(ExceptionEnum.PARAM_MISSING.getCode(), "短信通知必须填模板id");
		}
	}
}
