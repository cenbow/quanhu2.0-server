package com.yryz.quanhu.user.entity;

import com.yryz.common.entity.GenericEntity;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/18 13:40
 * Created by huangxy
 */
public class UserRelationRemarkEntity extends GenericEntity {

    /**
     * 用户源
     */
    private String sourceUserId;
    /**
     * 用户目标
     */
    private String targetUserId;

    /**
     * 备注类型
     */
    private Integer remarkType;

    /**
     * 备注值
     */
    private String remarkValue;


    public String getSourceUserId() {
        return sourceUserId;
    }

    public void setSourceUserId(String sourceUserId) {
        this.sourceUserId = sourceUserId;
    }

    public String getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
    }

    public Integer getRemarkType() {
        return remarkType;
    }

    public void setRemarkType(Integer remarkType) {
        this.remarkType = remarkType;
    }

    public String getRemarkValue() {
        return remarkValue;
    }

    public void setRemarkValue(String remarkValue) {
        this.remarkValue = remarkValue;
    }
}
