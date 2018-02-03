/*
 * UserViolation.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2018-01-12 Created
 */
package com.yryz.quanhu.user.entity;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.yryz.common.entity.GenericEntity;

/**
 * 用户违规表
 * 
 * @author xxx
 * @version 1.0 2018-01-12
 * @since 1.0
 */
@SuppressWarnings("serial")
public class UserViolation extends GenericEntity{

    /**
     * 用户id
     */
	@JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    /**
     * 违规范围
     */
    private String scopeDesc;

    /**
     * 图片
     */
    private String picUrl;

    /**
     * 理由
     */
    private String reason;

    /**
     * 推送自定义消息
     */
    private String pushMessage;

    /**
     * 违规类型10-警告 11-禁言 12-冻结 13-解除禁言 14-解冻
     */
    private Byte violationType;

    /**
     * 操作人名称
     */
    private String operational;

    /**
     * 禁言时间点
     */
    private Date banPostTime;

    /**
     * 违规是否对账户有效10:有效 11:无效
     */
    private Byte status;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getScopeDesc() {
        return scopeDesc;
    }

    public void setScopeDesc(String scopeDesc) {
        this.scopeDesc = scopeDesc == null ? null : scopeDesc.trim();
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getPushMessage() {
        return pushMessage;
    }

    public void setPushMessage(String pushMessage) {
        this.pushMessage = pushMessage == null ? null : pushMessage.trim();
    }

    public Byte getViolationType() {
        return violationType;
    }

    public void setViolationType(Byte violationType) {
        this.violationType = violationType;
    }

    public String getOperational() {
        return operational;
    }

    public void setOperational(String operational) {
        this.operational = operational == null ? null : operational.trim();
    }

    public Date getBanPostTime() {
        return banPostTime;
    }

    public void setBanPostTime(Date banPostTime) {
        this.banPostTime = banPostTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

}