/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年8月10日
 * Id: SmsTemplate.java, 2017年8月10日 下午5:11:11 Administrator
 */
package com.yryz.quanhu.sms.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * 短信模板表
 * 
 * @author danshiyu
 * @version 1.0 2017-07-24
 * @since 1.0
 */
@SuppressWarnings("serial")
public class SmsTemplate implements Serializable{
    /**
     * 主键
     */
    private Integer id;
    /**
     * 模板名称
     */
    private String smsTemplateName;
    
    /**
     * 模板类型 0:短信验证码 1:通知
     */
    private Byte templateType;
    
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
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 操作人名称
     */
    private String operational;
    
    /**
     * 短信appKey
     */
    private String appKey;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
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
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public String getOperational() {
        return operational;
    }
    public void setOperational(String operational) {
        this.operational = operational == null ? null : operational.trim();
    }
    
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public Byte getTemplateType() {
		return templateType;
	}
	public void setTemplateType(Byte templateType) {
		this.templateType = templateType;
	}
	@Override
	public String toString() {
		return "SmsTemplate [id=" + id + ", smsTemplateName=" + smsTemplateName + ", templateType=" + templateType
				+ ", smsTemplateCode=" + smsTemplateCode + ", smsTemplateContent=" + smsTemplateContent
				+ ", smsTemplateParams=" + smsTemplateParams + ", createTime=" + createTime + ", updateTime="
				+ updateTime + ", operational=" + operational + ", appKey=" + appKey + "]";
	}
    
}
