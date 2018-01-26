package com.yryz.quanhu.order.sdk.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/23 21:22
 * Created by lifan
 */
public class OutputOrder implements Serializable {

    private static final long serialVersionUID = 7050479619561450604L;

    //功能枚举
    private String moduleEnum;
    //私圈ID
    private Long coterieId;
    //资源ID
    private Long resourceId;
    //扩展信息，预设回调返回的数据
    private String bizContent;
    //创建人ID
    private Long createUserId;
    //创建时间
    private Date createDate;

    public String getModuleEnum() {
        return moduleEnum;
    }

    public void setModuleEnum(String moduleEnum) {
        this.moduleEnum = moduleEnum;
    }

    public Long getCoterieId() {
        return coterieId;
    }

    public void setCoterieId(Long coterieId) {
        this.coterieId = coterieId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getBizContent() {
        return bizContent;
    }

    public void setBizContent(String bizContent) {
        this.bizContent = bizContent;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
