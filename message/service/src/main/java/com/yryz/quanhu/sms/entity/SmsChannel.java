/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年8月10日
 * Id: SmsChannel.java, 2017年8月10日 下午5:09:48 Administrator
 */
package com.yryz.quanhu.sms.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * 短信通道表
 * 
 * @author danshiyu
 * @version 1.0 2017-07-24
 * @since 1.0
 */
@SuppressWarnings("serial")
public class SmsChannel implements Serializable{
    /**
     * 主键
     */
    private Integer id;
    /**
     * 短信通道名
     */
    private String channel;
    /**
     * 短信签名
     */
    private String smsSign;
    /**
     * 短信发送总量
     */
    private Integer totalCount;
    /**
     * 成功总数
     */
    private Integer successCount;
    /**
     * 失败总数
     */
    private Integer failCount;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新时间
     */
    private Date updateDate;
    
    /**
     * 短信appkey
     */
    private String appKey;
    
    /**
     * 短信appSecret
     */
    private String appSecret;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getChannel() {
        return channel;
    }
    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }
    public String getSmsSign() {
        return smsSign;
    }
    public void setSmsSign(String smsSign) {
        this.smsSign = smsSign == null ? null : smsSign.trim();
    }
    public Integer getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
    public Integer getSuccessCount() {
        return successCount;
    }
    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }
    public Integer getFailCount() {
        return failCount;
    }
    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
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
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	@Override
	public String toString() {
		return "SmsChannel [id=" + id + ", channel=" + channel + ", smsSign=" + smsSign + ", totalCount=" + totalCount
				+ ", successCount=" + successCount + ", failCount=" + failCount + ", createDate=" + createDate
				+ ", updateDate=" + updateDate + ", appKey=" + appKey + ", appSecret=" + appSecret + "]";
	}
    
    
}