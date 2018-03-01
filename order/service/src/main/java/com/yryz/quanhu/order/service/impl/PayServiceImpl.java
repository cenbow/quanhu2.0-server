package com.yryz.quanhu.order.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.rongzhong.component.pay.alipay.Alipay;
import com.rongzhong.component.pay.alipay.AlipayConfig;
import com.rongzhong.component.pay.api.YryzPaySDK;
import com.rongzhong.component.pay.entity.OrderInfo;
import com.rongzhong.component.pay.entity.PayResponse;
import com.rongzhong.component.pay.iospay.IosVerify;
import com.rongzhong.component.pay.wxpay.Wxpay;
import com.rongzhong.component.pay.wxpay.WxpayConfig;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.DateUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.order.api.OrderApi;
import com.yryz.quanhu.order.enums.*;
import com.yryz.quanhu.order.service.PayService;
import com.yryz.quanhu.order.util.BankUtil;
import com.yryz.quanhu.order.vo.*;
import com.yryz.quanhu.support.config.api.BasicConfigApi;
import com.yryz.quanhu.support.id.api.IdAPI;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/2/28 14:02
 * Created by lifan
 */
@Service
@Transactional
public class PayServiceImpl implements PayService {

    private static final Logger logger = LoggerFactory.getLogger(PayServiceImpl.class);

    @Reference
    private IdAPI idAPI;

    @Reference
    private OrderApi orderApi;

    @Reference
    private BasicConfigApi basicConfigApi;

    @Override
    public PayVO createPay(Long userId, String payWay, String orderSrc, Long orderAmount, String currency, String ipAddress) {
        if (null == userId) {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(), "用户ID为必填", "用户ID为必填");
        }
        if (StringUtils.isEmpty(payWay)) {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(), "支付方式：payWay必填", "支付方式：payWay必填");
        }
        if (StringUtils.isEmpty(orderSrc)) {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(), "支付来源：orderSrc", "支付来源：orderSrc");
        }
        if (StringUtils.isEmpty(currency)) {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(), "币种：currency必填", "币种：currency必填");
        }
        if (null == orderAmount || orderAmount < 100 || orderAmount % 100 != 0) {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(), "请输入正常充值金额", "请输入正常充值金额");
        }
        String orderId = ResponseUtils.getResponseNotNull(idAPI.getOrderId());
        PayInfo payInfo = new PayInfo();
        payInfo.setCost(orderAmount);
        long feeAmount = DataEnum.countFee(payWay, orderAmount);
        if (payWay != null && payWay.equals(OrderConstant.PAY_WAY_IOS_IAP)) {
            IOSPayConfig config = DataEnum.getIosProductConfig(orderAmount / 100);
            if (config != null) {
                payInfo.setCostTrue(config.getCostTrue() * 100);
                payInfo.setCostFee(config.getCostFee() * 100);
            } else {
                payInfo.setCostTrue(orderAmount);
                payInfo.setCostFee(feeAmount);
            }
        } else {
            payInfo.setCostTrue(orderAmount);
            payInfo.setCostFee(feeAmount);
        }
        payInfo.setCreateTime(new Date());
        payInfo.setCurrency(currency);
        payInfo.setCustId(String.valueOf(userId));
        payInfo.setOrderChannel(payWay);
        payInfo.setOrderDesc("用户充值");
        payInfo.setOrderId(orderId);
        payInfo.setOrderState(OrderPayConstants.OrderState.CREATE);
        payInfo.setOrderType(OrderPayConstants.OrderType.RECHARGE);
        payInfo.setProductId(ProductEnum.RECHARGE_TYPE.getType() + "");
        payInfo.setProductType(ProductEnum.RECHARGE_TYPE.getType() + "");

        PayVO payVO = new PayVO();
        payVO.setOrderAmount(orderAmount);
        payVO.setOrderCurrency(currency);
        payVO.setOrderDatetime(DateUtils.getDate("yyyyMMddHHmmss"));
        payVO.setOrderId(orderId);
        payVO.setProductName("充值支付");

        if (payWay != null && payWay.equals(OrderConstant.PAY_WAY_ALIPAY) && (OrderConstant.ORDER_SRC_ANDROID.equals(orderSrc)
                || OrderConstant.ORDER_SRC_IOS.equals(orderSrc) || OrderConstant.ORDER_SRC_WAP.equals(orderSrc))) {
            try {
                OrderInfo orderInfo = buildOrderInfo(payVO);
                String orderStr = YryzPaySDK.buildAppAliPayOrder(orderInfo);
                Map<String, String> map = new HashMap<String, String>(1);
                map.put("orderStr", orderStr);
                payVO.setExt(map);
                payInfo.setStartDesc(GsonUtils.parseJson(map));
                payVO.setProductName("支付宝充值");
                payInfo.setProductDesc("支付宝移动充值");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 手机端的微信支付，设置扩展参数
        if (payWay != null && payWay.equals(OrderConstant.PAY_WAY_WXPAY) && (OrderConstant.ORDER_SRC_ANDROID.equals(orderSrc)
                || OrderConstant.ORDER_SRC_IOS.equals(orderSrc) || OrderConstant.ORDER_SRC_WAP.equals(orderSrc))) {
            SortedMap<String, String> wxReqMap;
            try {
                OrderInfo orderInfo = buildOrderInfo(payVO);
                wxReqMap = YryzPaySDK.buildAppWxPayOrder(orderInfo, ipAddress);
                payVO.setExt(wxReqMap);
                payInfo.setStartDesc(GsonUtils.parseJson(wxReqMap));
                payVO.setProductName("微信充值");
                payInfo.setProductDesc("微信移动充值");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Response<PayInfo> response = orderApi.executePay(payInfo, String.valueOf(userId), null, null);
        if (response.success()) {
            return payVO;
        } else {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(), response.getMsg(), response.getMsg());
        }
    }

    private OrderInfo buildOrderInfo(PayVO payVO) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setSn(payVO.getOrderId());
        orderInfo.setOrderAmount(payVO.getOrderAmount());
        orderInfo.setOrderCurrency(payVO.getOrderCurrency());
        orderInfo.setOrderDatetime(DateUtils.getDate("yyyyMMddHHmmss"));
        orderInfo.setProductName(payVO.getProductName());
        return orderInfo;
    }

    @Override
    public String alipayNotify(Map<String, String> params) {
        logger.info("receive alipayNotify");
        PayResponse payResp = null;
        try {
            payResp = Alipay.parsePayResult(params);
        } catch (Exception e) {
            logger.error("alipayNotify faild ", e);
            return "alipayNotify faild ";
        }
        logger.info("收到支付宝回调并解析成功，结果为：" + payResp);
        if (payResp.getResult() == PayResponse.SUCCESS || payResp.getResult() == PayResponse.FAILURE) {
            System.out.println("支付宝回调成功");

            int orderState = OrderPayConstants.OrderState.FAILURE;
            if (payResp.getResult() == PayResponse.SUCCESS) {
                orderState = OrderPayConstants.OrderState.SUCCESS;
            }
            completePay(payResp, OrderConstant.PAY_WAY_ALIPAY, orderState);
            return "success";
        }
        logger.info("支付宝回调结束");
        return "failure";
    }

    @Override
    public String wxpayNotify(String paramStr) {
        logger.info("receive wxpayNotify...");
        PayResponse payResp = null;
        try {
            payResp = Wxpay.parsePayResult(paramStr);
        } catch (Exception e) {
            logger.error("wxpayNotify faild ", e);
            return "wxpayNotify faild ";
        }
        logger.info("收到微信回调并解析成功，结果为：" + payResp);
        if (payResp.getResult() == PayResponse.SUCCESS || payResp.getResult() == PayResponse.FAILURE) {
            int orderState = OrderPayConstants.OrderState.FAILURE;
            if (payResp.getResult() == PayResponse.SUCCESS) {
                orderState = OrderPayConstants.OrderState.SUCCESS;
            }
            completePay(payResp, OrderConstant.PAY_WAY_WXPAY, orderState);
            return Wxpay.buildReturnXML("SUCCESS", "OK");
        }
        return "wxpayNotify faild ";
    }

    /**
     * 获取IOS沙箱配置
     *
     * @return
     */
    private IosSandboxConfig getIosSandboxConfig() {
        IosSandboxConfig iosSandboxConfig = new IosSandboxConfig();
        try {
            String value = ResponseUtils.getResponseData(basicConfigApi.getValue("account.ios.sandbox"));
            if (StringUtils.isNotBlank(value)) {
                iosSandboxConfig = JSON.parseObject(value, IosSandboxConfig.class);
            }
        } catch (Exception e) {
            logger.error("获取资金-IOS沙箱配置失败", e);
        }
        return iosSandboxConfig;
    }

    @Override
    public void checkIOSPay(Long userId, String orderId, Long orderAmount, String receipt) {
        if (null == userId) {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(), "用户ID不能为空", "用户ID不能为空");
        }
        if (orderAmount == null || orderAmount.intValue() < 100 || orderAmount.intValue() % 100 != 0) {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(), "请输入正常的兑换金额", "请输入正常的兑换金额");
        }

        boolean isSandbox = false;
        //获取IOS沙箱配置
        IosSandboxConfig iosSandboxConfig = getIosSandboxConfig();
        if (null != iosSandboxConfig && IosSandboxConfig.OPEN == iosSandboxConfig.getOpenFlag()) {
            if (String.valueOf(userId).equals(iosSandboxConfig.getUserId())) {
                isSandbox = true;
            }
        }
        String result = IosVerify.verifyReceipt(receipt, isSandbox);
        logger.info("ios check receipt : " + result);
        logger.info("ios check receive result : " + result);
        JSONObject json = new JSONObject(result);
        if (json.getInt("status") != 0) {
            // 苹果服务不可用，需要再次测试
            if (json.getInt("status") == 21005) {
                throw new QuanhuException(ExceptionEnum.BusiException.getCode(), "验证苹果服务超时", "验证苹果服务超时");
            } else {
                throw new QuanhuException(ExceptionEnum.BusiException.getCode(), "验证苹果服务失败", "验证苹果服务失败");
            }
        }
        String productId = json.getJSONObject("receipt").getJSONArray("in_app").getJSONObject(0)
                .getString("product_id");
        PayResponse payResp = new PayResponse();
        payResp.setSn(orderId);
        String payAmount = DataEnum.getIosProductConfig(productId).getCost() + "";
        payResp.setPayAmount(payAmount);
        int orderState = 1;
        payResp.setEndDesc(receipt);
        completePay(payResp, OrderConstant.PAY_WAY_IOS_IAP, orderState);
    }

    /**
     * 回调完成支付订单
     *
     * @param payResp
     * @param payWay
     * @param orderState
     */
    private void completePay(PayResponse payResp, String payWay, int orderState) {
        switch (payWay) {
            case OrderConstant.PAY_WAY_ALIPAY:
                // 支付宝回调中，商户号可能为空
                if (payResp.getMchId() != null &&
                        !AlipayConfig.partner.equals(payResp.getMchId())) {
                    logger.error("支付宝回调中商户号不一致，回调信息：" + payResp.toString());
                    throw new QuanhuException(ExceptionEnum.BusiException.getCode(),
                            ExceptionEnum.BusiException.getShowMsg(),
                            "支付宝回调中商户号不一致，回调信息：" + payResp.toString());
                }
                break;
            case OrderConstant.PAY_WAY_WXPAY:
                if (!WxpayConfig.MCH_ID.equals(payResp.getMchId()) && !WxpayConfig.GZH_MCH_ID.equals(payResp.getMchId())) {
                    logger.error("微信回调中商户号不一致，回调信息：" + payResp.toString());
                    throw new QuanhuException(ExceptionEnum.BusiException.getCode(),
                            ExceptionEnum.BusiException.getShowMsg(),
                            "微信回调中商户号不一致，回调信息：" + payResp.toString());
                }
                break;
            default:
                logger.warn("无效的支付方式");
        }

        // 回写数据库结果
        PayInfo payInfo = new PayInfo();
        payInfo.setOrderId(payResp.getSn());
        payInfo.setCompleteTime(new Date());
        payInfo.setEndDesc(GsonUtils.parseJson(payResp));
        // 如果金额不为空，则传入金额
        if (StringUtils.isNotEmpty(payResp.getPayAmount())) {
            payInfo.setCost(Long.parseLong(payResp.getPayAmount()));
        }
        payInfo.setOrderState(orderState);
        Response<PayInfo> response = orderApi.executePay(payInfo, null, null, null);
        if (!response.success()) {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(), response.getMsg(), response.getMsg());
        }
    }

    private WithdrawCashConfig getWithdrawCashConfig() {
        WithdrawCashConfig withdrawCashConfig = new WithdrawCashConfig();
        try {
            String value = ResponseUtils.getResponseData(basicConfigApi.getValue("account.nopass.pay"));
            if (StringUtils.isNotBlank(value)) {
                withdrawCashConfig = JSON.parseObject(value, WithdrawCashConfig.class);
            }
        } catch (Exception e) {
            logger.error("获取资金-提现配置失败", e);
        }
        return withdrawCashConfig == null ? new WithdrawCashConfig() : withdrawCashConfig;
    }

    @Override
    public void getCash(String appId, Long userId, String cost, String cust2BankId, String password) {

        if (null == userId) {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(), "用户ID为必填", "用户ID为必填");
        }
        long lcost = 0;
        if (StringUtils.isEmpty(cost)) {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(), "提现金额cost必填", "提现金额cost必填");
        } else {
            lcost = Long.parseLong(cost);
            if (lcost < 100) {
                throw new QuanhuException(ExceptionEnum.BusiException.getCode(), "请输入正常的提现金额", "请输入正常的提现金额");
            }
        }
        if (StringUtils.isEmpty(cust2BankId)) {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(), "银行卡必填", "银行卡必填");
        }
        if (StringUtils.isEmpty(password)) {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(), "支付密码必填", "支付密码必填");
        }
        //获取提现配置
        WithdrawCashConfig withdrawCashConfig = getWithdrawCashConfig();
        //判断提现控制开关
        if (WithdrawCashConfig.NOT_ALLOWED == withdrawCashConfig.getAllowFlag()) {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(),
                    withdrawCashConfig.getMsg(), withdrawCashConfig.getMsg());
        }
        //判断提现时段
        if (DateUtils.checkBetween(new Date(), withdrawCashConfig.getStartTime(), withdrawCashConfig.getEndTime())) {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(),
                    withdrawCashConfig.getTimeLimitMsg(),withdrawCashConfig.getTimeLimitMsg());
        }
        //判断额度合理
        if (lcost > withdrawCashConfig.getOnceMaxAmount()) {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(),
                    withdrawCashConfig.getAmountLimitMsg(), withdrawCashConfig.getAmountLimitMsg());
        }

        //验证银行卡
        CustBank custBank = orderApi.getCustBankById(cust2BankId).getData();
        if (custBank == null) {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(), "银行卡信息不存在", "银行卡信息不存在");
        }
        String bankCardNo = custBank.getBankCardNo();
        if (StringUtils.isBlank(bankCardNo)) {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(), "银行卡号信息不存在", "银行卡号信息不存在");
        }
        String wxBankId = BankUtil.getWxBankId(bankCardNo.replace(" ", ""));
        if (StringUtils.isBlank(wxBankId)) {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(),
                    "暂不支持提现到该银行卡，请选择已支持的银行", "暂不支持提现到该银行卡，请选择已支持的银行");
        }
        //验证密码
        Response<?> checkResponse = orderApi.checkUserPayPassword(String.valueOf(userId), password);
        if (!checkResponse.success()) {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(), checkResponse.getMsg(), checkResponse.getErrorMsg());
        }

        String orderId = ResponseUtils.getResponseNotNull(idAPI.getOrderId());
        PayInfo payInfo = new PayInfo();
        payInfo.setCost(Long.parseLong(cost));
        payInfo.setCostTrue(Long.parseLong(cost));
        // 手续费 固定2块
        payInfo.setCostFee(200L);
        payInfo.setCreateTime(new Date());
        payInfo.setCurrency("156");
        payInfo.setCustId(String.valueOf(userId));
        payInfo.setOrderChannel(OrderConstant.PAY_WAY_WX_CASH_CARD);
        payInfo.setOrderDesc(OrderDescEnum.INTEGRAL_CASH);
        payInfo.setOrderId(orderId);
        payInfo.setOrderState(OrderPayConstants.OrderState.CREATE);
        payInfo.setOrderType(OrderPayConstants.OrderType.WITHDRAW_CASH);
        payInfo.setProductId(ProductEnum.CASH_TYPE.getType() + "");
        payInfo.setProductType(ProductEnum.CASH_TYPE.getType() + "");
        payInfo.setRemark("custBankNo:" + custBank.getBankCardNo());
        //组装提现参数，存储
        Map<String, String> startDescMap = new HashMap<>();
        startDescMap.put("userName", custBank.getName());
        startDescMap.put("bankCardNo", custBank.getBankCardNo().replace(" ", ""));
        startDescMap.put("bankName", custBank.getBankCode());
        startDescMap.put("wxBankId", wxBankId);
        payInfo.setStartDesc(JSON.toJSONString(startDescMap));

        Response<PayInfo> response = orderApi.executePay(payInfo, String.valueOf(userId), null, null);
        if (!response.success()) {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(), checkResponse.getMsg(), checkResponse.getErrorMsg());
        }
    }

}
