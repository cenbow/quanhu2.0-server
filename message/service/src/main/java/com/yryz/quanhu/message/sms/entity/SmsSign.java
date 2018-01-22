/*
 * SmsSign.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2018-01-20 Created
 */
package com.yryz.quanhu.message.sms.entity;

import com.yryz.common.entity.GenericEntity;

/**
 * 短信签名表
 * 
 * @author xxx
 * @version 1.0 2018-01-20
 * @since 1.0
 */
@SuppressWarnings("serial")
public class SmsSign extends GenericEntity{

    /**
     * 短信签名
     */
    private String smsSign;

    /**
     * 短信渠道id
     */
    private Long smsChannelId;

    /**
     * 操作人名称
     */
    private String operateName;

    public String getSmsSign() {
        return smsSign;
    }

    public void setSmsSign(String smsSign) {
        this.smsSign = smsSign == null ? null : smsSign.trim();
    }

    public Long getSmsChannelId() {
        return smsChannelId;
    }

    public void setSmsChannelId(Long smsChannelId) {
        this.smsChannelId = smsChannelId;
    }
    public String getOperateName() {
        return operateName;
    }

    public void setOperateName(String operateName) {
        this.operateName = operateName == null ? null : operateName.trim();
    }

}