/*
 * SmsLog.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2018-01-20 Created
 */
package com.yryz.quanhu.message.sms.entity;

import com.yryz.common.entity.GenericEntity;

/**
 * 短信日志表
 * 
 * @author xxx
 * @version 1.0 2018-01-20
 * @since 1.0
 */
@SuppressWarnings("serial")
public class SmsLog extends GenericEntity{
    /**
     * 手机号
     */
    private String phone;

    /**
     * 短信类型 0:验证码 1:通知
     */
    private Byte type;

    /**
     * 短信渠道key
     */
    private String appKey;

    /**
     * 短信渠道名称
     */
    private String smsChannel;

    /**
     * 短信签名
     */
    private String smsSign;

    /**
     * 短信模板编码
     */
    private String smsTemplateCode;
    /**
     * ip地址
     */
    private String ip;
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey == null ? null : appKey.trim();
    }

    public String getSmsChannel() {
        return smsChannel;
    }

    public void setSmsChannel(String smsChannel) {
        this.smsChannel = smsChannel == null ? null : smsChannel.trim();
    }

    public String getSmsSign() {
        return smsSign;
    }

    public void setSmsSign(String smsSign) {
        this.smsSign = smsSign == null ? null : smsSign.trim();
    }

    public String getSmsTemplateCode() {
        return smsTemplateCode;
    }

    public void setSmsTemplateCode(String smsTemplateCode) {
        this.smsTemplateCode = smsTemplateCode == null ? null : smsTemplateCode.trim();
    }

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public SmsLog() {
		super();
	}

	public SmsLog(String phone, Byte type, String appKey, String smsChannel, String smsSign, String smsTemplateCode) {
		super();
		this.phone = phone;
		this.type = type;
		this.appKey = appKey;
		this.smsChannel = smsChannel;
		this.smsSign = smsSign;
		this.smsTemplateCode = smsTemplateCode;
	}

}