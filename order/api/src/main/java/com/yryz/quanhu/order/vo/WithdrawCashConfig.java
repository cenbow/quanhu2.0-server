package com.yryz.quanhu.order.vo;

import java.io.Serializable;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/2/3 16:36
 * Created by lifan
 */
public class WithdrawCashConfig implements Serializable {

    private static final long serialVersionUID = -478078323610003643L;

    public static final int NOT_ALLOWED = 0;
    //0 不允许 1允许
    private int allowFlag = 1;
    //不允许时客户端的提示消息
    private String msg = "提现系统维护中，请联系客服。";

    public int getAllowFlag() {
        return allowFlag;
    }

    public void setAllowFlag(int allowFlag) {
        this.allowFlag = allowFlag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
