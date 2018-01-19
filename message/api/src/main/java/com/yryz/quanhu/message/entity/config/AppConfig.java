/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年8月10日
 * Id: AppConfig.java, 2017年8月10日 上午11:50:42 Administrator
 */
package com.yryz.quanhu.message.entity.config;

import java.io.Serializable;
import java.util.Date;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年8月10日 上午11:50:42
 * @Description 应用配置
 */
@SuppressWarnings("serial")
public class AppConfig implements Serializable {
	 /**
     * 主键
     */
    private Integer id;

    /**
     * 应用id
     */
    private String appId;

    /**
     * 短信通道，对应签名
     */
    private String smsChannel;
    
    /**
     * 短信模板配置
     */
    private SmsTemplateCofigDTO templateCofig;

    /**
     * 短信开关 0:开 1:关
     */
    private Byte smsStatus;

    /**
     * 推送开关
     */
    private Byte pushStatus;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 操作人名称
     */
    private String operational;

    /**
     * 推送渠道配置
     */
    private PushConfigDTO pushConfigInfo;
    
    /**
     * 第三方登录配置
     */
    private ThirdLoginConfigDTO loginConfig;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public String getSmsChannel() {
        return smsChannel;
    }

    public void setSmsChannel(String smsChannel) {
        this.smsChannel = smsChannel == null ? null : smsChannel.trim();
    }

    public Byte getSmsStatus() {
        return smsStatus;
    }

    public void setSmsStatus(Byte smsStatus) {
        this.smsStatus = smsStatus;
    }

    public Byte getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(Byte pushStatus) {
        this.pushStatus = pushStatus;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getOperational() {
        return operational;
    }

    public void setOperational(String operational) {
        this.operational = operational == null ? null : operational.trim();
    }


	public PushConfigDTO getPushConfigInfo() {
		return pushConfigInfo;
	}

	public void setPushConfigInfo(PushConfigDTO pushConfigInfo) {
		this.pushConfigInfo = pushConfigInfo;
	}

	public ThirdLoginConfigDTO getLoginConfig() {
		return loginConfig;
	}

	public void setLoginConfig(ThirdLoginConfigDTO loginConfig) {
		this.loginConfig = loginConfig;
	}

	public SmsTemplateCofigDTO getTemplateCofig() {
		return templateCofig;
	}

	public void setTemplateCofig(SmsTemplateCofigDTO templateCofig) {
		this.templateCofig = templateCofig;
	}

	@Override
	public String toString() {
		return "AppConfig [id=" + id + ", appId=" + appId + ", smsChannel=" + smsChannel + ", templateCofig="
				+ templateCofig + ", smsStatus=" + smsStatus + ", pushStatus=" + pushStatus + ", createDate="
				+ createDate + ", updateDate=" + updateDate + ", operational=" + operational + ", pushConfigInfo="
				+ pushConfigInfo + ", loginConfig=" + loginConfig + "]";
	}
    
}
