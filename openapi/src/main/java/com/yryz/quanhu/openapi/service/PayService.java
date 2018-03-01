/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018年1月22日
 * Id: PayService.java, 2018年1月22日 下午3:47:46 yehao
 */
package com.yryz.quanhu.openapi.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.RpcOptException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.openapi.constants.PayMsgConstant;
import com.yryz.quanhu.openapi.order.dto.OrderListDTO;
import com.yryz.quanhu.openapi.order.dto.PayInfoDTO;
import com.yryz.quanhu.openapi.order.dto.UserBankDTO;
import com.yryz.quanhu.openapi.order.dto.UserPhyDTO;
import com.yryz.quanhu.order.api.OrderApi;
import com.yryz.quanhu.order.api.OrderAsynApi;
import com.yryz.quanhu.order.enums.AccountEnum;
import com.yryz.quanhu.order.enums.OrderMsgEnum;
import com.yryz.quanhu.order.vo.*;
import com.yryz.quanhu.support.id.api.IdAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月22日 下午3:47:46
 * @Description 支付管理
 */
@Service
public class PayService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Reference(lazy = true)
    OrderApi orderAPI;

    @Reference(lazy = true)
    OrderAsynApi orderAsynAPI;

    @Reference(lazy = true)
    IdAPI idAPI;

//	@Reference
//	PushManager pushService;


    public String getOrderId() {
        try {
            Response<String> response = idAPI.getOrderId();
            if (response.success()) {
                //圈乎的API订单，圈乎订单加一个特殊标记
                return response.getData();
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.info("调用IDRPC失败", e);
            throw new RpcOptException("IDRPC，getOrderId调用异常", e.getCause());
        }
    }

    public Response<?> checkPayAvailable(String custId, Integer amount, String passWd) {
        UserAccount userAccount = null;
        Integer acc = 5000;
        try {
            userAccount = orderAPI.getUserAccount(custId).getData();
        } catch (Exception e) {
            throw new RpcOptException("资金RPC，getUserAccount调用异常", e.getCause());
        }

        if (userAccount == null) {
            return ResponseUtils.returnCommonException(PayMsgConstant.message_account_unexist);
        }

        // 账户锁定
        if (userAccount.getAccountState() != 1) {
            return ResponseUtils.returnCommonException(PayMsgConstant.message_account_lock);
        }

        // 验证支付密码
        if (userAccount.getSmallNopass() != 1 && StringUtils.isBlank(passWd)) {
            return ResponseUtils.returnCommonException(PayMsgConstant.message_pay_50);
        }
        if (amount > acc && StringUtils.isBlank(passWd)) {
            return ResponseUtils.returnCommonException(PayMsgConstant.message_pay_50);
        }

        if (!StringUtils.isBlank(passWd)) {
            Response<?> re = null;
            try {
                re = orderAPI.checkUserPayPassword(custId, passWd);
            } catch (Exception e) {
                throw new RpcOptException("资金RPC，dealCustBank调用异常", e.getCause());
            }

            if (re == null || !re.success()) {
                return ResponseUtils.returnCommonException(PayMsgConstant.message_pay_pass);
            }
        }

        // 余额不足
        if (amount > userAccount.getAccountSum()) {
            return ResponseUtils.returnCommonException(PayMsgConstant.message_account_unenough);
        }
        return ResponseUtils.returnSuccess();
    }

    /**
     * 获取账户消息
     *
     * @param custId
     * @return
     */
    public UserAccount getUserAccount(String custId) {
        try {
            UserAccount userAccount = orderAPI.getUserAccount(custId).getData();
            return userAccount;
        } catch (Exception e) {
            throw new RpcOptException("资金RPC，getUserAccount调用异常", e.getCause());
        }
    }

    /**
     * 绑定银行卡
     *
     * @param userBankDTO
     * @return
     */
    public UserBankDTO bindbankcard(UserBankDTO userBankDTO) {
        CustBank custBank = GsonUtils.parseObj(userBankDTO, CustBank.class);
        try {
            Response<?> return1 = orderAPI.dealCustBank(custBank, 1);
            if (return1.success()) {
                return GsonUtils.parseObj(return1.getData(), UserBankDTO.class);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RpcOptException("资金RPC，dealCustBank调用异常", e.getCause());
        }
    }

    public List<UserBankDTO> bankcardlist(String custId) {
        try {
            Response<List<CustBank>> response = orderAPI.getCustBanks(custId);
            List<CustBank> list = response.success() ? response.getData() : null;
            return (List<UserBankDTO>) GsonUtils.parseList(list, UserBankDTO.class);
        } catch (Exception e) {
            throw new RpcOptException("资金RPC，getCustBanks调用异常", e.getCause());
        }
    }

    public boolean unbindbankcard(UserBankDTO userBankDTO) {
        CustBank custBank = (CustBank) GsonUtils.parseObj(userBankDTO, CustBank.class);
        try {
            orderAPI.dealCustBank(custBank, 0);
            return true;
        } catch (Exception e) {
            logger.warn("资金RPC调用异常，解绑银行卡失败", e);
            return false;
        }
    }

    /**
     * 设置免密支付
     *
     * @param custId
     * @param type
     * @return
     */
    public boolean setFreePay(String custId, Integer type) {
        UserAccount account = new UserAccount();
        account.setCustId(custId);
        if (type.intValue() == 0) {
            account.setSmallNopass(0);
        } else {
            account.setSmallNopass(1);
        }
        try {
            orderAPI.dealUserAccount(account);
            return true;
        } catch (Exception e) {
            logger.warn("资金RPC调用异常，设置免密支付失败", e);
            return false;
        }
    }
//
//	public String buildAppPayOrderStr(OrderVO orderInfo) throws IOException {
//		Map<String, String> sParaTemp = new HashMap<String, String>(11);
//		sParaTemp.put("service", AlipayConfig.app_pay_name);
//		sParaTemp.put("partner", AlipayConfig.partner);
//		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
//		sParaTemp.put("notify_url", AlipayConfig.notify_url);
//		sParaTemp.put("out_trade_no", orderInfo.getOrderId());
//		sParaTemp.put("subject", orderInfo.getProductName());
//		sParaTemp.put("payment_type", AlipayConfig.payment_type);
//		sParaTemp.put("seller_id", AlipayConfig.seller_id);
//		sParaTemp.put("total_fee", String.valueOf(orderInfo.getOrderAmount() / 100.0));
//		sParaTemp.put("body", orderInfo.getProductName());
//		sParaTemp.put("it_b_pay", Global.getConfig("order_expired_time") + "m");
//
//		String linkStr = AlipayCore.createLinkString2(sParaTemp);
//		String sign = AlipaySubmit.buildRequestSign(linkStr, SignType.RSA);
//		sign = URLEncoder.encode(sign, AlipayConfig.input_charset);
//		linkStr = linkStr + "&sign=\"" + sign + "\"&";
//		linkStr = linkStr + "sign_type=\"" + SignType.RSA + "\"";
//		return linkStr;
//	}
//

    public Response<?> checkPassword(String custId, String password) {
        return orderAPI.checkUserPayPassword(custId, password);
    }

    public Response<?> lockUserAccount(String custId) {
        return orderAPI.lockUserAccount(custId);
    }

    public Response<?> unlockUserAccount(String custId) {
        return orderAPI.unlockUserAccount(custId);
    }

    public UserPhyDTO getUserPhy(String custId) {
        UserPhy userPhy = orderAPI.getUserPhy(custId).getData();
        try {
            if (userPhy != null) {
                return GsonUtils.parseObj(userPhy, UserPhyDTO.class);
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.warn("资金RPC调用异常，查询用户安全信息失败", e);
            return null;
        }
    }

    public List<OrderListDTO> getOrderList(String custId, String date, Integer productType, int type, long start,
                                           long limit) {
        try {
            List<OrderListVo> list = orderAPI.getOrderList(custId, date, productType, type, start, limit).getData();
            return GsonUtils.parseList(list, OrderListDTO.class);
        } catch (Exception e) {
            logger.warn("资金RPC调用异常，查询用户账单信息失败", e);
            return null;
        }
    }

//	@Override
//	public UserStatistics getIntegralStatistics(String custId) {
//		try {
//			UserIntegralStatistics integralStatistics = orderAPI.getStatistics(custId);
//			return (UserStatistics) GsonUtils.parseObj(integralStatistics, UserStatistics.class);
//		} catch (Exception e) {
//			logger.warn("资金RPC调用异常，查询用户统计信息失败", e);
//			return null;
//		}
//	}

//	public String buildAlipayRequest(String orderId, String custId, long orderAmount, long feeAmount, String currency,
//			String ipAddress, String returnUrl , boolean isWap) {
//		PayInfo payInfo = null;
//		if (StringUtils.isEmpty(orderId)) {
//			orderId = getOrderId();
//			feeAmount = DataEnum.countFee(OrderConstant.PAY_WAY_ALIPAY, orderAmount);
//			payInfo = new PayInfo();
//			payInfo.setCost(orderAmount);
//			payInfo.setCostTrue(orderAmount);
//			payInfo.setCostFee(feeAmount);
//			payInfo.setCreateTime(new Date());
//			payInfo.setCurrency(currency);
//			payInfo.setCustId(custId);
//			payInfo.setOrderChannel(OrderConstant.PAY_WAY_ALIPAY);
//			payInfo.setOrderDesc("用户充值");
//			payInfo.setOrderId(orderId);
//			payInfo.setOrderState(0);
//			payInfo.setOrderType(1);
//			payInfo.setProductId(ProductEnum.RECHARGE_TYPE.getType() + "");
//			payInfo.setProductType(ProductEnum.RECHARGE_TYPE.getType() + "");
//			payInfo.setProductDesc("支付宝即时到账");
//		} else {
//			payInfo = new PayInfo();
//			payInfo.setOrderId(orderId);
//			payInfo = orderAPI.executePay(payInfo, null, null, null).getData();
//			if (payInfo == null || payInfo.getCost() == null) {
//				return null;
//			}
//			orderAmount = payInfo.getCost();
//			custId = payInfo.getCustId();
//		}
//
//		com.rongzhong.component.pay.entity.OrderInfo orderInfo = new com.rongzhong.component.pay.entity.OrderInfo();
//		orderInfo.setOrderAmount(orderAmount);
//		orderInfo.setOrderCurrency(currency);
//		orderInfo.setOrderDatetime(DateUtils.getDate("yyyyMMddHHmmss"));
//		orderInfo.setSn(orderId);
//		orderInfo.setProductName("充值支付");
//
//		String requestHtml = "";
//		try {
//			if(isWap){
//				requestHtml = YryzPaySDK.buildWapAliPayHtml(orderInfo, returnUrl);
//			} else {
//				requestHtml = YryzPaySDK.buildWebAliPayHtml(orderInfo, returnUrl);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		if (StringUtils.isNotEmpty(requestHtml)) {
//			orderAPI.executePay(payInfo, custId, null, null);
//		}
//		return requestHtml;
//	}
//
//	public String generateWxQrpayUrl(String orderId, String custId, long orderAmount, long feeAmount, String currency,
//			String ipAddress) throws ConnectException, Exception {
//		PayInfo payInfo = null;
//		if (StringUtils.isEmpty(orderId)) {
//			orderId = getOrderId();
//			feeAmount = DataEnum.countFee(Constant.PAY_WAY_WXPAY, orderAmount);
//			payInfo = new PayInfo();
//			payInfo.setCost(orderAmount);
//			payInfo.setCostTrue(orderAmount);
//			payInfo.setCostFee(feeAmount);
//			payInfo.setCreateTime(new Date());
//			payInfo.setCurrency(currency);
//			payInfo.setCustId(custId);
//			payInfo.setOrderChannel(Constant.PAY_WAY_WXPAY);
//			payInfo.setOrderDesc("用户充值");
//			payInfo.setOrderId(orderId);
//			payInfo.setOrderState(0);
//			payInfo.setOrderType(1);
//			payInfo.setProductId(ProductEnum.RECHARGE_TYPE.getType() + "");
//			payInfo.setProductType(ProductEnum.RECHARGE_TYPE.getType() + "");
//			payInfo.setProductDesc("微信扫码支付");
//		} else {
//			payInfo = new PayInfo();
//			payInfo.setOrderId(orderId);
//			payInfo = orderAPI.executePay(payInfo, null, null, null);
//			if (payInfo == null || payInfo.getCost() == null) {
//				return null;
//			}
//			orderAmount = payInfo.getCost();
//			custId = payInfo.getCustId();
//		}
//
//		com.rongzhong.component.pay.entity.OrderInfo orderInfo = new com.rongzhong.component.pay.entity.OrderInfo();
//		orderInfo.setOrderAmount(orderAmount);
//		orderInfo.setOrderCurrency(currency);
//		orderInfo.setOrderDatetime(DateUtils.getDate("yyyyMMddHHmmss"));
//		orderInfo.setSn(orderId);
//		orderInfo.setProductName("充值支付");
////		WxQRpay wxQRpay = Wxpay.getWxQRpay(orderInfo, ipAddress);
////		if (wxQRpay != null) {
////			orderAPI.executePay(payInfo, custId, null, null);
////		}
////		return wxQRpay.getCodeUrl();
//		String url = YryzPaySDK.buildWebWxPayUrl(orderInfo, ipAddress);
//		if(url != null) {
//			orderAPI.executePay(payInfo, custId, null, null);
//		}
//		return url;
//	}

    public PageList<OrderListDTO> getOrderListWeb(String custId, String orderDesc, String startDate, String endDate,
                                                  Integer productType, int type, int pageNo, int pageSize) {

        PageList<OrderListDTO> page = new PageList<>();
        page.setCurrentPage(pageNo);
        page.setPageSize(pageSize);
        try {
            PageList<OrderListVo> listPage = orderAPI.getOrderListWeb(custId, orderDesc, startDate, endDate,
                    productType, type, pageNo, pageSize).getData();
            page.setCount(listPage.getCount());
            if (listPage.getEntities() != null) {
                List<OrderListDTO> list = (List<OrderListDTO>) GsonUtils.parseList(listPage.getEntities(), OrderListDTO.class);
                page.setEntities(list);
            }
            return page;
        } catch (Exception e) {
            logger.warn("资金RPC调用异常，查询用户账单信息失败", e);
            return null;
        }
    }

    public PageList<PayInfoDTO> getPayInfoListWeb(String custId, String date, String orderId, Integer productType,
                                                  int pageNo, int pageSize) {
        if (logger.isDebugEnabled()) {
            logger.info("getPayInfoListWeb === custId:" + custId + "..orderId:" + orderId + "..pageNo:" + pageNo + "-"
                    + pageSize);
        }
        PageList<PayInfoDTO> page = new PageList<>();
        page.setCurrentPage(pageNo);
        page.setPageSize(pageSize);
        try {
            PageList<PayInfo> listPage = orderAPI.getPayInfoWeb(custId, date, orderId, pageNo, pageSize).getData();
            page.setCount(listPage.getCount());
            if (listPage.getEntities() != null) {
                List<PayInfoDTO> list = (List<PayInfoDTO>) GsonUtils.parseList(listPage.getEntities(), PayInfoDTO.class);
                page.setEntities(list);
            }
            return page;
        } catch (Exception e) {
            logger.warn("资金RPC调用异常，查询用户充值信息失败", e);
            return null;
        }
    }

    /**
     * 更新系统账户缓存
     *
     * @see com.yryz
     */
    public void updateSysIDCache() {
        try {
            orderAPI.updateUserCache(AccountEnum.SYSID);
            orderAPI.updateUserCache(AccountEnum.OPTID);
        } catch (Exception e) {
            logger.warn("资金RPC调用异常，更新用户缓存信息失败..updateUserCache");
        }
    }

    /**
     * 执行订单
     *
     * @param orderId
     * @param custId
     * @param password
     * @return
     */
    public Response<?> executeOrder(String orderId, String custId, String password) {
        try {
            Response<?> return1 = orderAsynAPI.executeOrder(orderId, custId, password);
            if (return1 != null && return1.success()) {
                return ResponseUtils.returnSuccess();
            } else {
                String msg = return1.getMsg();
                if (OrderMsgEnum.NOT_ENOUGH.getMsg().equals(msg)) {
                    Map<String, Integer> map = new HashMap<>(1);
                    map.put("code", OrderMsgEnum.NOT_ENOUGH.getCode());
                    return ResponseUtils.returnObjectSuccess(map);
                }
                return ResponseUtils.returnCommonException(return1.getMsg());
            }
        } catch (Exception e) {
            logger.warn("executeOrder exception", e);
            return ResponseUtils.returnCommonException(ExceptionEnum.Exception.getShowMsg());
        }
    }

    /**
     * 获取订单详情
     *
     * @param orderId
     * @return
     */
    public OrderInfo getOrderInfo(String orderId) {
        try {
            OrderInfo orderInfo = orderAsynAPI.getOrderInfo(orderId).getData();
            return orderInfo;
        } catch (Exception e) {
            logger.warn("资金RPC调用异常，查询订单信息失败..getOrderInfo", e);
            return null;
        }
    }

}
