/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年12月4日
 * Id: ConfigRemote.java, 2017年12月4日 下午5:41:11 Administrator
 */
package com.yryz.quanhu.commonsafe.service;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.context.Context;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.commonsafe.vo.VerifyCodeConfigVO;
import com.yryz.quanhu.message.entity.config.IpLimitConfigVO;
import com.yryz.quanhu.support.id.api.ConfigApi;

/**
 * 配置代理
 * @author danshiyu
 * @version 1.0
 * @date 2017年12月4日 下午5:41:11
 */
@Component
public class ConfigRemote {
	private static final String APPID_DEFAULT = "appId.default";
	@Reference
	private ConfigApi configApi;
	
	/**
	 * 获取验证码配置
	 * @param appId
	 * @param commonServiceType 接入方自定义
	 * @return
	 */
	public VerifyCodeConfigVO getVerifyCodeConfig(String appId,String commonServiceType){
		VerifyCodeConfigVO configVO = new VerifyCodeConfigVO();
		String configType =   commonServiceType ;
		getConfigByType(appId, configType, configVO);
		return configVO;
	}
	
	/**
	 * 获取ip限制配置
	 * @param appId
	 * @param commonServiceType 接入方自定义
	 * @return
	 */
	public IpLimitConfigVO getIpLimitConfig(String appId,String commonServiceType){
		IpLimitConfigVO configVO = new IpLimitConfigVO();
		getConfigByType(appId, commonServiceType, configVO);
		return configVO;
	}
	
	/**
	 * 根据业务类型得到配置(自定义类型)
	 * @param appId
	 * @param serviceType
	 * @param t
	 */
	private <T> T getConfigByType(String appId, String configType,T t){
		if(StringUtils.isBlank(appId)){
			appId = Context.getProperty(APPID_DEFAULT);
		}
		configType = String.format("%s_%s", configType,appId);
		return configApi.getConfig(configType, t).getData();
	}
}
