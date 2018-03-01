package com.yryz.quanhu.order.service;

import com.yryz.quanhu.order.vo.PayVO;

import java.util.Map;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/2/28 14:01
 * Created by lifan
 */
public interface PayService {

    /**
     * 创建支付订单
     *
     * @param userId
     * @param payWay
     * @param orderSrc
     * @param orderAmount
     * @param currency
     * @param ipAddress
     * @return
     */
    PayVO createPay(Long userId, String payWay, String orderSrc, Long orderAmount, String currency, String ipAddress);

    /**
     * 支付宝回调
     *
     * @param params
     * @return
     */
    String alipayNotify(Map<String, String> params);

    /**
     * 微信回调
     *
     * @param paramStr
     * @return
     */
    String wxpayNotify(String paramStr);

    /**
     * 苹果支付回调
     *
     * @param userId
     * @param orderId
     * @param orderAmount
     * @param receipt
     */
    void checkIOSPay(Long userId, String orderId, Long orderAmount, String receipt);

    /**
     * 提现
     *
     * @param appId
     * @param userId
     * @param cost
     * @param cust2BankId
     * @param password
     */
    void getCash(String appId, Long userId, String cost, String cust2BankId, String password);

}
