/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018年1月22日
 * Id: FindPayPasswordDTO.java, 2018年1月22日 下午2:57:40 yehao
 */
package com.yryz.quanhu.openapi.order.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月22日 下午2:57:40
 * @Description 找回支付密码
 */
public class FindPayPasswordDTO implements Serializable {

    private static final long serialVersionUID = -626126416798016133L;
    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码")
    private String veriCode;

    public String getVeriCode() {
        return veriCode;
    }

    public void setVeriCode(String veriCode) {
        this.veriCode = veriCode;
    }
}
