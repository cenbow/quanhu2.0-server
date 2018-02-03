package com.yryz.quanhu.order.vo;

import java.io.Serializable;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/2/3 16:27
 * Created by lifan
 */
public class NopassPayConfig implements Serializable {

    private static final long serialVersionUID = -2925514395564971271L;
    //最大免密支付金额，单位分
    private long maxAmount = 50000L;
    //超过最大值，客户端提示消息
    private String msg = "超过免密支付最大金额限制。";

    public long getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(long maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
