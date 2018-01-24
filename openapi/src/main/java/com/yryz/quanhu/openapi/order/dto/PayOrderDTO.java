package com.yryz.quanhu.openapi.order.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/24 17:59
 * Created by lifan
 * 创建充值支付订单DTO
 */
public class PayOrderDTO implements Serializable {
    private static final long serialVersionUID = 8973912552703173850L;

    @ApiModelProperty("支付方式")
    private String payWay;

    @ApiModelProperty("订单描述")
    private String orderSrc;

    @ApiModelProperty("订单金额")
    private Long orderAmount;

    @ApiModelProperty("币种")
    private String currency;

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getOrderSrc() {
        return orderSrc;
    }

    public void setOrderSrc(String orderSrc) {
        this.orderSrc = orderSrc;
    }

    public Long getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Long orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
