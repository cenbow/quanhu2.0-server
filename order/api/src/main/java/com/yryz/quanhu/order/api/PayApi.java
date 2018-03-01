package com.yryz.quanhu.order.api;

import com.yryz.common.response.Response;
import com.yryz.quanhu.order.vo.PayVO;

import java.util.Map;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/2/28 14:00
 * Created by lifan
 * 支付相关dubbo的API
 */
public interface PayApi {

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
    Response<PayVO> createPay(Long userId, String payWay, String orderSrc, Long orderAmount, String currency, String ipAddress);

    /**
     * 支付宝回调
     *
     * @param params
     * @return
     */
    Response<String> alipayNotify(Map<String, String> params);

    /**
     * 微信回调
     *
     * @param paramStr
     * @return
     */
    Response<String> wxpayNotify(String paramStr);

    /**
     * 苹果支付回调
     *
     * @param userId
     * @param orderId
     * @param orderAmount
     * @param receipt
     * @return
     */
    Response<?> checkIOSPay(Long userId, String orderId, Long orderAmount, String receipt);

    /**
     * 提现
     *
     * @param appId
     * @param userId
     * @param cost
     * @param cust2BankId
     * @param password
     * @return
     */
    Response<?> getCash(String appId, Long userId, String cost, String cust2BankId, String password);

    /**
     * 微信提现到银行卡
     *
     * @param orderId
     * @param userName
     * @param bankCardNo
     * @param bankId
     * @param amount
     * @return
     */
    Response<?> wxpayCash(String orderId, String userName, String bankCardNo, String bankId, String amount);

    /**
     * 查询微信提现到银行卡的结果
     * @param orderId
     * @return
     */
    Response<Map<String, String>> wxpayCashQuery(String orderId);
}
