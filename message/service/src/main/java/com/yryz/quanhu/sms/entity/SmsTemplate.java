/*
 * SmsTemplate.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2018-01-20 Created
 */
package com.yryz.quanhu.sms.entity;

import com.yryz.common.entity.GenericEntity;

/**
 * 短信模板表
 * 
 * @author xxx
 * @version 1.0 2018-01-20
 * @since 1.0
 */
@SuppressWarnings("serial")
public class SmsTemplate extends GenericEntity{

    /**
     * 短信渠道id
     */
    private Long smsChannelId;

    /**
     * 模板名称
     */
    private String smsTemplateName;

    /**
     * 模板编码
     */
    private String smsTemplateCode;

    /**
     * 模板内容
     */
    private String smsTemplateContent;

    /**
     * 模板包含变量
     */
    private String smsTemplateParams;

    /**
     * 操作人名称
     */
    private String operateName;

    /**
     * 短信类型 0:验证码 1:通知
     */
    private Byte templateType;


    public Long getSmsChannelId() {
        return smsChannelId;
    }

    public void setSmsChannelId(Long smsChannelId) {
        this.smsChannelId = smsChannelId;
    }

    public String getSmsTemplateName() {
        return smsTemplateName;
    }

    public void setSmsTemplateName(String smsTemplateName) {
        this.smsTemplateName = smsTemplateName == null ? null : smsTemplateName.trim();
    }

    public String getSmsTemplateCode() {
        return smsTemplateCode;
    }

    public void setSmsTemplateCode(String smsTemplateCode) {
        this.smsTemplateCode = smsTemplateCode == null ? null : smsTemplateCode.trim();
    }

    public String getSmsTemplateContent() {
        return smsTemplateContent;
    }

    public void setSmsTemplateContent(String smsTemplateContent) {
        this.smsTemplateContent = smsTemplateContent == null ? null : smsTemplateContent.trim();
    }

    public String getSmsTemplateParams() {
        return smsTemplateParams;
    }

    public void setSmsTemplateParams(String smsTemplateParams) {
        this.smsTemplateParams = smsTemplateParams == null ? null : smsTemplateParams.trim();
    }

    public String getOperateName() {
        return operateName;
    }

    public void setOperateName(String operateName) {
        this.operateName = operateName == null ? null : operateName.trim();
    }

    public Byte getTemplateType() {
        return templateType;
    }

    public void setTemplateType(Byte templateType) {
        this.templateType = templateType;
    }
}