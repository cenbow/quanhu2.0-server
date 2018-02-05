package com.yryz.quanhu.message.message.dto;

import java.io.Serializable;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description:
 * @Date: Created in 2018 2018/1/31 17:03
 * @Author: pn
 */
public class MessageAdminDto implements Serializable {

    private Integer pageNo = 1;

    private Integer pageSize = 10;

    /**
     * 消息id
     */
    private String messageId;

    /**
     * 标题
     */
    private String title;

    /**
     * 持久化类型：1：代表在APP消息列表显示，0：不在消息列表显示
     */
    private Integer persistentType;

    /**
     * 消息推送状态分为三种：0:未推送，表示还没到推送时间的消息；1:进行中，表示正在进行推送的消息；2:已推送，表示已经推送过的消息；
     */
    private Integer pushStatus;

    private String startDate;

    private String endDate;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPersistentType() {
        return persistentType;
    }

    public void setPersistentType(Integer persistentType) {
        this.persistentType = persistentType;
    }

    public Integer getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(Integer pushStatus) {
        this.pushStatus = pushStatus;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
