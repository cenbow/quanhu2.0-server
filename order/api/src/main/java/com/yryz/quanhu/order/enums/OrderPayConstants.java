package com.yryz.quanhu.order.enums;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/2/3 17:31
 * Created by lifan
 */
public class OrderPayConstants {

    /**
     * 订单支付类型
     */
    public static class OrderType {
        //提现
        public static final Integer WITHDRAW_CASH = 0;
        //充值
        public static final Integer RECHARGE = 1;
    }

    /**
     * 订单状态
     */
    public static class OrderState {
        //创建
        public static final Integer CREATE = 0;
        //成功
        public static final Integer SUCCESS = 1;
        //失败
        public static final Integer FAILURE = 2;
    }
}
