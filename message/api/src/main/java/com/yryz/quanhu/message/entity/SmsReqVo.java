/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年6月26日
 * Id: SmsReqVo.java, 2017年6月26日 下午3:51:46 Administrator
 */
package com.yryz.quanhu.message.entity;


import java.io.Serializable;
import java.util.Map;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年6月26日 下午3:51:46
 * @Description 短信发送请求参数<br/>
 *              自定义消息不为空，不处理功能码，只处理自定义消息
 */
@SuppressWarnings("serial")
public class SmsReqVo implements Serializable {
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 功能码
	 */
	private String code;

	/**
	 * 短信通道
	 * 
	 * @see SmsConstants
	 */
	private String channel;

	/**
	 * 短信消息模板类型 @see {@link SmsConstants.SmsTemplateType}
	 */
	private int templateType;

	/**
	 * 动态参数,发送自定义消息时使用,根据阿里大鱼后台短信模板变量定义传参<br/>
	 * 例如：templateType：2(contents:"854584")
	 */
	private Map<String, String> smsParam;
	
	/**
	 * 应用id
	 */
	private String appId;
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Map<String, String> getSmsParam() {
		return smsParam;
	}

	public void setSmsParam(Map<String, String> smsParam) {
		this.smsParam = smsParam;
	}

	public int getTemplateType() {
		return templateType;
	}

	public void setTemplateType(int templateType) {
		this.templateType = templateType;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@Override
	public String toString() {
		return "SmsReqVo [phone=" + phone + ", code=" + code + ", channel=" + channel + ", templateType=" + templateType
				+ ", smsParam=" + smsParam + ", appId=" + appId + "]";
	}

}
