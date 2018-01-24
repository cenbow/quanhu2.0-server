package com.yryz.quanhu.openapi.order.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/24 17:54
 * Created by lifan
 * 验证IOS支付DTO
 */
public class CheckIOSPayDTO implements Serializable{

    private static final long serialVersionUID = -3429680413754147134L;

    @ApiModelProperty("订单ID")
    private String orderId;

    @ApiModelProperty("订单金额")
    private Long orderAmount;

    @ApiModelProperty("凭据")
    private String receipt;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Long orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }
}
