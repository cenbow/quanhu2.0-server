package com.yryz.quanhu.message.common.remote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.support.config.api.BasicConfigApi;

@Service
public class MessageCommonConfigRemote {
	private static final Logger logger = LoggerFactory.getLogger(MessageCommonConfigRemote.class);
	
	@Reference
	private BasicConfigApi configApi;
		
	public String getConfig(String configName,String appId){
		logger.info("[messageService getConfig]:configName:{},appId:{}",configName,appId);
		configName = String.format("%s.%s", configName,appId);
		String configValue = null;
		try {
			configValue = ResponseUtils.getResponseData(configApi.getValue(configName));
			logger.info("[messageService getConfig]:configName:{},appId:{},configValue:{}",configName,appId,configValue);
		} catch (Exception e) {
			logger.error("[messageService getConfig]",e);
		}
		return configValue;
	}
}
