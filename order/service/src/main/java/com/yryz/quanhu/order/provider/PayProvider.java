package com.yryz.quanhu.order.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.order.api.PayApi;
import com.yryz.quanhu.order.service.PayService;
import com.yryz.quanhu.order.service.WxpayCashService;
import com.yryz.quanhu.order.vo.PayVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/2/28 14:00
 * Created by lifan
 */
@Service(interfaceClass = PayApi.class)
public class PayProvider implements PayApi {

    private static final Logger logger = LoggerFactory.getLogger(PayProvider.class);

    @Autowired
    private PayService payService;

    @Autowired
    private WxpayCashService wxpayCashService;

    @Override
    public Response<PayVO> createPay(Long userId, String payWay, String orderSrc, Long orderAmount, String currency, String ipAddress) {
        try {
            return ResponseUtils.returnObjectSuccess(payService.createPay(userId, payWay, orderSrc, orderAmount, currency, ipAddress));
        } catch (Exception e) {
            logger.error("创建支付订单失败", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<String> alipayNotify(Map<String, String> params) {
        try {
            return ResponseUtils.returnObjectSuccess(payService.alipayNotify(params));
        } catch (Exception e) {
            logger.error("支付宝回调失败", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<String> wxpayNotify(String paramStr) {
        try {
            return ResponseUtils.returnObjectSuccess(payService.wxpayNotify(paramStr));
        } catch (Exception e) {
            logger.error("微信回调失败", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<?> checkIOSPay(Long userId, String orderId, Long orderAmount, String receipt) {
        try {
            payService.checkIOSPay(userId, orderId, orderAmount, receipt);
            return ResponseUtils.returnSuccess();
        } catch (Exception e) {
            logger.error("苹果支付回调失败", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<?> getCash(String appId, Long userId, String cost, String cust2BankId, String password) {
        try {
            payService.getCash(appId, userId, cost, cust2BankId, password);
            return ResponseUtils.returnSuccess();
        } catch (Exception e) {
            logger.error("提现失败", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<?> wxpayCash(String orderId, String userName, String bankCardNo, String bankId, String amount) {
        try {
            wxpayCashService.wxpayCash(orderId, userName, bankCardNo, bankId, amount);
            return ResponseUtils.returnSuccess();
        } catch (Exception e) {
            logger.error("执行微信提现到银行卡失败", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Map<String, String>> wxpayCashQuery(String orderId) {
        try {
            return ResponseUtils.returnObjectSuccess(wxpayCashService.wxpayCashQuery(orderId));
        } catch (Exception e) {
            logger.error("查询微信提现到银行卡结果失败", e);
            return ResponseUtils.returnException(e);
        }
    }
}
