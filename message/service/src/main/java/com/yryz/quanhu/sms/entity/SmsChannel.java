/*
 * SmsChannel.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2018-01-20 Created
 */
package com.yryz.quanhu.sms.entity;

import com.yryz.common.entity.GenericEntity;

/**
 * 短信渠道表
 * 
 * @author xxx
 * @version 1.0 2018-01-20
 * @since 1.0
 */
@SuppressWarnings("serial")
public class SmsChannel extends GenericEntity{

    /**
     * 短信渠道名称
     */
    private String smsChannelName;

    /**
     * 短信渠道key
     */
    private String smsAppKey;

    /**
     * 短信渠道证书
     */
    private String smsAppSecret;

    /**
     * 操作人名称
     */
    private String operateName;

    public String getSmsChannelName() {
        return smsChannelName;
    }

    public void setSmsChannelName(String smsChannelName) {
        this.smsChannelName = smsChannelName == null ? null : smsChannelName.trim();
    }

    public String getSmsAppKey() {
        return smsAppKey;
    }

    public void setSmsAppKey(String smsAppKey) {
        this.smsAppKey = smsAppKey == null ? null : smsAppKey.trim();
    }

    public String getSmsAppSecret() {
        return smsAppSecret;
    }

    public void setSmsAppSecret(String smsAppSecret) {
        this.smsAppSecret = smsAppSecret == null ? null : smsAppSecret.trim();
    }

    public String getOperateName() {
        return operateName;
    }

    public void setOperateName(String operateName) {
        this.operateName = operateName == null ? null : operateName.trim();
    }

	@Override
	public String toString() {
		return "SmsChannel [smsChannelName=" + smsChannelName + ", smsAppKey=" + smsAppKey + ", smsAppSecret="
				+ smsAppSecret + ", operateName=" + operateName + "]";
	}

}