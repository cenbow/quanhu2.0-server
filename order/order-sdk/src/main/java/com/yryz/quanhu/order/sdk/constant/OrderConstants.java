package com.yryz.quanhu.order.sdk.constant;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/23 22:08
 * Created by lifan
 */
public class OrderConstants {

    public static class PayType {
        public static final Integer MINUS = 10;
        public static final Integer PLUS = 11;
    }

    public static class OrderType {
        //消费
        public static final Integer CONSUMPTION = 11;
        //收益
        public static final Integer PROFIT = 12;
        //混合
        public static final Integer MIXED = 13;
    }

    public static class OrderState {
        //创建
        public static final Integer CREATE = 10;
        //成功
        public static final Integer SUCCESS = 11;
    }
}
