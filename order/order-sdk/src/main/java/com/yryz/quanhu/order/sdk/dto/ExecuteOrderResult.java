package com.yryz.quanhu.order.sdk.dto;

import java.io.Serializable;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/3/1 19:03
 * Created by lifan
 */
public class ExecuteOrderResult implements Serializable{
    private static final long serialVersionUID = -796699546106751289L;

    //执行结果100系统异常，200成功，1000余额不足
    private String code;
    //订单ID
    private Long orderId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
