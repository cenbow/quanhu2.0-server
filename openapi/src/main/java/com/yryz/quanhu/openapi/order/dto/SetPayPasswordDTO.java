package com.yryz.quanhu.openapi.order.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/2/5 18:24
 * Created by lifan
 */
public class SetPayPasswordDTO implements Serializable {
    private static final long serialVersionUID = 4195362097163961195L;

    /**
     * 原密码
     */
    @ApiModelProperty(value = "原密码")
    private String oldPayPassword;

    /**
     * 新密码
     */
    @ApiModelProperty(value = "新密码")
    private String payPassword;

    public String getOldPayPassword() {
        return oldPayPassword;
    }

    public void setOldPayPassword(String oldPayPassword) {
        this.oldPayPassword = oldPayPassword;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }
}
