package com.yryz.quanhu.user.vo;

import java.io.Serializable;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/22 17:00
 * Created by huangxy
 */
public class UserRelationRemarkVo implements Serializable{

    private int remarkType;
    private String remarkValue;
    private String sourceUserId;
    private String targetUserId;


    public int getRemarkType() {
        return remarkType;
    }

    public void setRemarkType(int remarkType) {
        this.remarkType = remarkType;
    }

    public String getRemarkValue() {
        return remarkValue;
    }

    public void setRemarkValue(String remarkValue) {
        this.remarkValue = remarkValue;
    }

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
}
