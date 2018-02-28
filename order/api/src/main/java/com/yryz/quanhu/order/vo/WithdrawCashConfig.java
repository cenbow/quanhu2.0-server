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
    //提现开始时间
    private String startTime = "23:00";
    //提现截止时间
    private String endTime = "9:00";
    //提现时间限制提示消息
    private String timeLimitMsg = "提现系统维护中，23:00-9:00 是系统维护时间。";
    //单次最大提现金额，单位分
    private long onceMaxAmount = 200000L;
    //单次提现超出最大金额提示消息
    private String amountLimitMsg = "单次提现限额2000，有问题请联系客服。";

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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTimeLimitMsg() {
        return timeLimitMsg;
    }

    public void setTimeLimitMsg(String timeLimitMsg) {
        this.timeLimitMsg = timeLimitMsg;
    }

    public long getOnceMaxAmount() {
        return onceMaxAmount;
    }

    public void setOnceMaxAmount(long onceMaxAmount) {
        this.onceMaxAmount = onceMaxAmount;
    }

    public String getAmountLimitMsg() {
        return amountLimitMsg;
    }

    public void setAmountLimitMsg(String amountLimitMsg) {
        this.amountLimitMsg = amountLimitMsg;
    }
}
