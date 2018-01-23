/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月20日
 * Id: OrderController.java, 2018年1月20日 上午11:04:02 yehao
 */
package com.yryz.quanhu.openapi.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.entity.RequestHeader;
import com.yryz.common.exception.RpcOptException;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.DateUtils;
import com.yryz.common.utils.IdGen;
import com.yryz.common.utils.StringUtils;
import com.yryz.common.utils.WebUtil;
import com.yryz.quanhu.message.commonsafe.api.CommonSafeApi;
import com.yryz.quanhu.message.commonsafe.constants.CommonServiceType;
import com.yryz.quanhu.message.commonsafe.dto.VerifyCodeDTO;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.openapi.order.dto.FindPayPasswordDTO;
import com.yryz.quanhu.openapi.order.dto.GetCashDTO;
import com.yryz.quanhu.openapi.order.dto.PointsToAccountDTO;
import com.yryz.quanhu.openapi.order.dto.UnbindBankCardDTO;
import com.yryz.quanhu.openapi.order.dto.UserBankDTO;
import com.yryz.quanhu.openapi.order.utils.BankUtil;
import com.yryz.quanhu.openapi.order.utils.DataEnum;
import com.yryz.quanhu.openapi.service.PayService;
import com.yryz.quanhu.order.api.OrderApi;
import com.yryz.quanhu.order.enums.OrderConstant;
import com.yryz.quanhu.order.enums.OrderDescEnum;
import com.yryz.quanhu.order.enums.ProductEnum;
import com.yryz.quanhu.order.vo.AccountOrder;
import com.yryz.quanhu.order.vo.IntegralOrder;
import com.yryz.quanhu.order.vo.OrderInfo;
import com.yryz.quanhu.order.vo.UserAccount;
import com.yryz.quanhu.order.vo.UserPhy;
import com.yryz.quanhu.user.contants.SmsType;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserLoginSimpleVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月20日 上午11:04:02
 * @Description 资金管理
 */
@Api("资金管理 ")
@RestController
public class OrderController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Reference
	private OrderApi orderApi;
	
	@Reference
	private UserApi userApi;
	
	@Reference
	private CommonSafeApi commonSafeApi;
	
	@Autowired
	private PayService payService;
	
	/**
	 * 设置支付密码
	 * @param userPhy
	 * @return
	 */
    @ApiOperation("设置支付密码")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @PostMapping(value = "/{version}/setPayPassword")
	public Response<?> setPayPassword(@RequestBody UserPhy userPhy) {
		if (StringUtils.isEmpty(userPhy.getCustId())) {
			return ResponseUtils.returnCommonException("用户ID为必填");
		}
		if (StringUtils.isEmpty(userPhy.getPayPassword())) {
			return ResponseUtils.returnCommonException("新密码必填");
		}
		try {
			orderApi.checkUserPayPassword(userPhy.getCustId(), userPhy.getPayPassword());
			Response<?> response = orderApi.dealUserPhy(userPhy);
			if(response.success()){
				//发送消息
//				pushService.setSecurityProblem(custId, cost,appId);
			}
			return response;
		} catch (Exception e) {
			logger.error("设置支付密码失败", e);
			return ResponseUtils.returnCommonException("密码验证失败");
		}
	}
    
    /**
     * 设置密保问题
     * @param userPhy
     * @return
     */
    @ApiOperation("设置密保问题")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @PostMapping(value = "/{version}/setSecurityProblem")
	public Response<?> setSecurityProblem(@RequestBody UserPhy userPhy) {
		if (StringUtils.isEmpty(userPhy.getCustId())) {
			return ResponseUtils.returnCommonException("用户ID为必填");
		}
		if (StringUtils.isEmpty(userPhy.getPhyName()) || StringUtils.isEmpty(userPhy.getCustIdcardNo())) {
			return ResponseUtils.returnCommonException("信息必填");
		}
		try {
			return orderApi.dealUserPhy(userPhy);
		} catch (Exception e) {
			logger.error("设置密保问题失败", e);
			return ResponseUtils.returnCommonException("验证失败");
		}
	}
    
    /**
     * 找回支付密码
     * @param request
     * @param findPayPasswordDTO
     * @return
     */
    @ApiOperation("找回支付密码")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @PostMapping(value = "/{version}/findPayPassword")
	public Response<?> findPayPassword(HttpServletRequest request , @RequestBody FindPayPasswordDTO findPayPasswordDTO) {
		
		RequestHeader header = WebUtil.getHeader(request);

		if (StringUtils.isBlank(findPayPasswordDTO.getUserId()) || StringUtils.isBlank(findPayPasswordDTO.getVeriCode())) {
			return ResponseUtils.returnCommonException("please check paramter: custId | veriCode");
		}
		if (StringUtils.isBlank(findPayPasswordDTO.getPhyName()) || StringUtils.isBlank(findPayPasswordDTO.getCustIdcardNo())) {
			return ResponseUtils.returnCommonException("please check paramter: phyName | phyCardNo");
		}

		try {
			String phone = findPayPasswordDTO.getPhone();
			if(StringUtils.isEmpty(phone)){
				Response<UserLoginSimpleVO> response = userApi.getUserLoginSimpleVO(findPayPasswordDTO.getUserId());
				UserLoginSimpleVO userBase = response.success() ? response.getData() : null;
				if(userBase == null || StringUtils.isEmpty(userBase.getUserPhone())){
					return ResponseUtils.returnCommonException("当前用户不存在或者没有绑定手机号码");
				}
				phone = userBase.getUserPhone();
			}
			

			Response<Integer> checkCode = this.commonSafeApi.checkVerifyCode(new VerifyCodeDTO(SmsType.CODE_CHANGE_PAYPWD.getType(),
					CommonServiceType.PHONE_VERIFYCODE_SEND.getName(), phone, header.getAppId(),
					findPayPasswordDTO.getVeriCode(), false));
			if (!checkCode.success() || checkCode.getData() != 0 ) {
				return ResponseUtils.returnCommonException("短信码错误");
			}
			//重置支付密码
			UserPhy userPhy = new UserPhy();
			userPhy.setCustId(findPayPasswordDTO.getUserId());
			userPhy.setPayPassword("");
			orderApi.dealUserPhy(userPhy);
			//设置新密码
			userPhy = new UserPhy();
			userPhy.setPayPassword(findPayPasswordDTO.getPayPassword());
			orderApi.dealUserPhy(userPhy);
		} catch (Exception e) {
			logger.error("验证安全信息失败", e);
			return ResponseUtils.returnCommonException("验证失败");
		}

		return ResponseUtils.returnSuccess();
	}
    
    /**
     * 积分兑换
     * @param request
     * @param custId
     * @param cost
     * @param password
     * @return
     */
    @ApiOperation("积分兑换")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @PostMapping(value = "/{version}/pointsToAccount")
    public Response<?> pointsToAccount(HttpServletRequest request ,@RequestBody PointsToAccountDTO pointsToAccountDTO ) {
    	
    	RequestHeader header = WebUtil.getHeader(request);
    	
    	String userId = pointsToAccountDTO.getUserId();
    	Long cost = pointsToAccountDTO.getCost();
    	String payPassword = pointsToAccountDTO.getPayPassword();
    	
    	if (StringUtils.isEmpty(userId)) {
			return ResponseUtils.returnCommonException("用户ID为必填");
		}
		if (cost == null || cost.intValue() < 100 || cost % 100 != 0) {
			return ResponseUtils.returnCommonException("请输入正常的兑换金额");
		}
		String appId = header.getAppId();
		try {
			String orderId = payService.getOrderId();
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setCustId(userId);
			orderInfo.setCost(cost);
			orderInfo.setOrderDesc(OrderDescEnum.ACCOUNT_EXCHANGE);
			orderInfo.setOrderId(orderId);
			orderInfo.setOrderType(3);
			orderInfo.setProductDesc(ProductEnum.INTEGRAL_2_ACCOUNT.getDesc());
			orderInfo.setProductId(ProductEnum.INTEGRAL_2_ACCOUNT.getType() + "");
			orderInfo.setProductType(ProductEnum.INTEGRAL_2_ACCOUNT.getType());
			orderInfo.setType(3);

			AccountOrder accountOrder = new AccountOrder();
			accountOrder.setCost(cost);
			accountOrder.setCustId(userId);
			accountOrder.setOrderDesc(OrderDescEnum.ACCOUNT_EXCHANGE);
			accountOrder.setOrderId(orderId);
			accountOrder.setOrderType(1);
			accountOrder.setProductDesc(ProductEnum.INTEGRAL_2_ACCOUNT.getDesc());
			accountOrder.setProductId(ProductEnum.INTEGRAL_2_ACCOUNT.getType() + "");
			accountOrder.setProductType(ProductEnum.INTEGRAL_2_ACCOUNT.getType());
			List<AccountOrder> accounts = new ArrayList<>();
			accounts.add(accountOrder);

			IntegralOrder integralOrder = new IntegralOrder();
			integralOrder.setCost(cost);
			integralOrder.setCustId(userId);
			integralOrder.setOrderDesc(OrderDescEnum.INTEGRAL_EXCHANGE);
			integralOrder.setOrderId(orderId);
			integralOrder.setOrderType(0);
			integralOrder.setProductDesc(ProductEnum.INTEGRAL_2_ACCOUNT.getDesc());
			integralOrder.setProductId(ProductEnum.INTEGRAL_2_ACCOUNT.getType() + "");
			integralOrder.setProductType(ProductEnum.INTEGRAL_2_ACCOUNT.getType());
			List<IntegralOrder> integrals = new ArrayList<>();
			integrals.add(integralOrder);
			try {
				Response<?> return1 = orderApi.executeOrder(orderInfo, accounts, integrals, userId, payPassword, null);
				if (return1.success()) {
//					pushService.pointToAccount(custId, cost,appId);
					return ResponseUtils.returnSuccess();
				} else {
					return ResponseUtils.returnCommonException("兑换失败，请检查余额后重试");
				}
			} catch (Exception e) {
				return ResponseUtils.returnCommonException("资金RPC，executeOrder调用异常");
			}
		} catch (Exception e) {
			logger.error("积分兑换失败", e);
			return ResponseUtils.returnCommonException("密码输入错误或者余额不足");
		}
	}
    
    /**
     * 获取账户信息
     * @param custId
     * @return
     */
    @ApiOperation("获取账户信息")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @PostMapping(value = "/{version}/getUserAccount")
	public Response<?> getUserAccount(String custId) {
		if (StringUtils.isEmpty(custId)) {
			return ResponseUtils.returnCommonException("用户ID为必填");
		}
		try {
			Response<UserAccount> response = orderApi.getUserAccount(custId);
			if (!response.success() || response.getData() == null) {
				return ResponseUtils.returnCommonException("账户不存在");
			} else {
				return response;
			}
		} catch (Exception e) {
			logger.error("查询用户账户未知异常", e);
			return ResponseUtils.returnCommonException("账户不存在");
		}
	}
    
    /**
     * 用户提现
     * @param request
     * @param getCashDTO
     * @return
     */
    @ApiOperation("用户提现")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @PostMapping(value = "/{version}/getCash")
	public Response<?> getCash(HttpServletRequest request, @RequestBody GetCashDTO getCashDTO) {
		// return ReturnCodeUtils.getWarnResult(ActEnums.GET_CASH, "资金系统正在维护中");
    	String custId = getCashDTO.getCustId();
    	String cost = getCashDTO.getCost();
    	String cust2BankId = getCashDTO.getCust2BankId();
    	String payPassword = getCashDTO.getPayPassword();
		RequestHeader header = WebUtil.getHeader(request);
		
		if (StringUtils.isEmpty(custId)) {
			return ResponseUtils.returnCommonException("用户ID为必填");
		}
		if (StringUtils.isEmpty(cost)) {
			return ResponseUtils.returnCommonException("提现金额cost必填");
		} else {
			long lcost = Long.parseLong(cost);
			if (lcost < 100) {
				return ResponseUtils.returnCommonException("请输入正常的提现金额");
			}
		}
		if (StringUtils.isEmpty(cust2BankId)) {
			return ResponseUtils.returnCommonException("cust2BankId必填");
		}
		if (StringUtils.isEmpty(payPassword)) {
			return ResponseUtils.returnCommonException("payPassword必填");
		}

		if (DateUtils.checkBetween(new Date(), "23:00", "9:00")) {
			return ResponseUtils.returnCommonException("提现系统维护中，23:00-9:00 是系统维护时间");
		}

		try {
			return payService.getCash(header.getAppId() , custId, cost, cust2BankId, payPassword);
		} catch (RpcOptException e) {
			return ResponseUtils.returnCommonException(e.getMessage());
		} catch (Exception e) {
			logger.error("getCash未知异常", e);
			return ResponseUtils.returnCommonException("账户不存在");
		}
	}
	
    /**
     * 计算充值手续费
     * @return
     */
    @ApiOperation("计算充值手续费")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @PostMapping(value = "/{version}/getServiceCharge")
	public Response<?> getServiceCharge() {
		List<Map<String, Object>> list = DataEnum.getPayCharge();
		return ResponseUtils.returnListSuccess(list);
	}
    
    /**
     * 绑定银行卡
     * @param custId
     * @param bankCardNo
     * @param fastPay
     * @param phone
     * @param bankCardType
     * @param name
     * @param bankCode
     * @param no_agree
     * @param password
     * @return
     */
    @ApiOperation("绑定银行卡")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @PostMapping(value = "/{version}/bindBankCard")
	public Response<?> bindBankCard(String custId, String bankCardNo, String fastPay, String phone, String bankCardType,
			String name, String bankCode, String no_agree, String password) {
		if (StringUtils.isEmpty(custId)) {
			return ResponseUtils.returnCommonException("用户ID为必填");
		}
		if (StringUtils.isEmpty(bankCardNo)) {
			return ResponseUtils.returnCommonException("银行卡号bankCardNo必填");
		}
		if (StringUtils.isEmpty(name)) {
			return ResponseUtils.returnCommonException("用户真实姓名name必填");
		}
		
		bankCode = BankUtil.getSimpleNameOfBank(bankCardNo.replace(" ", ""));

		UserBankDTO userBankDTO = new UserBankDTO();
		userBankDTO.setBankCardNo(bankCardNo.trim());
		// orderCust2bank.setBankCardType(Integer.parseInt(bankCardType));
		userBankDTO.setBankCode(bankCode.trim());
		userBankDTO.setCreateBy(custId);
		userBankDTO.setCustId(custId);
		userBankDTO.setName(name.trim());
		// orderCust2bank.setPhone(phone);
		// orderCust2bank.setNoAgree(no_agree);
		try {
			UserBankDTO cust2bank = payService.bindbankcard(userBankDTO);
			if (cust2bank != null) {
				return ResponseUtils.returnObjectSuccess(cust2bank);
			} else {
				return ResponseUtils.returnCommonException("绑定失败,可能是卡号重复。请检查后重试");
			}
		} catch (Exception e) {
			logger.error("bindBankCard失败", e);
			return ResponseUtils.returnCommonException("绑定失败，请重试");
		}
	}

    /**
     * 我的银行卡列表
     * @param custId
     * @return
     */
    @ApiOperation("我的银行卡列表")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @PostMapping(value = "/{version}/bankCardList")
	public Response<?> bankCardList(String custId) {
		if (StringUtils.isEmpty(custId)) {
			return ResponseUtils.returnCommonException("用户ID为必填");
		}
		try {
			List<UserBankDTO> list = payService.bankcardlist(custId);
			if (list == null) {
				return ResponseUtils.returnListSuccess(list);
			} else {
				return ResponseUtils.returnListSuccess(list);
			}
		} catch (Exception e) {
			return ResponseUtils.returnCommonException("服务器正在打盹...");
		}
	}
    
    
    /**
     * 设置默认卡
     * @param userBankDTO
     * @return
     */
    @ApiOperation("设置默认卡")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @PostMapping(value = "/{version}/setDefaultBankCard")
	public Response<?> setDefaultBankCard(@RequestBody UserBankDTO userBankDTO) {
		if (StringUtils.isEmpty(userBankDTO.getCustId())) {
			return ResponseUtils.returnCommonException("用户ID为必填");
		}
		if (StringUtils.isEmpty(userBankDTO.getCust2BankId())) {
			return ResponseUtils.returnCommonException("关系ID：cust2BankId必填");
		}

		userBankDTO.setCreateBy(userBankDTO.getCustId());
		userBankDTO.setDefaultCard(1);
		try {
			UserBankDTO userbank = payService.bindbankcard(userBankDTO);
			if (userbank != null) {
				return ResponseUtils.returnSuccess();
			} else {
				return ResponseUtils.returnCommonException("设置失败，请重试");
			}
		} catch (Exception e) {
			return ResponseUtils.returnCommonException("设置失败，请重试");
		}
	}
    
    
    /**
     * 解绑银行卡
     * @param unbindBankCardDTO
     * @return
     */
    @ApiOperation("解绑银行卡")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @PostMapping(value = "/{version}/unbindBankCard")
	public Response<?> unbindBankCard(@RequestBody UnbindBankCardDTO unbindBankCardDTO) {
		if (StringUtils.isEmpty(unbindBankCardDTO.getCustId())) {
			return ResponseUtils.returnCommonException("用户ID为必填");
		}
		if (StringUtils.isEmpty(unbindBankCardDTO.getCust2BankId())) {
			return ResponseUtils.returnCommonException("关系ID：cust2BankId必填");
		}
		if (StringUtils.isEmpty(unbindBankCardDTO.getPayPassword())) {
			return ResponseUtils.returnCommonException("验证密码：payPassword必填");
		}

		Response<?> return1 = payService.checkPassword(unbindBankCardDTO.getCustId(), unbindBankCardDTO.getPayPassword());
		if (return1 == null || !return1.success()) {
			return ResponseUtils.returnCommonException(return1.getMsg());
		}

		UserBankDTO userBankDTO = new UserBankDTO();
		userBankDTO.setCust2BankId(unbindBankCardDTO.getCust2BankId());
		userBankDTO.setCustId(unbindBankCardDTO.getCustId());
		userBankDTO.setCreateBy(unbindBankCardDTO.getCustId());
		userBankDTO.setDelFlag("1");
		try {
			if (payService.unbindbankcard(userBankDTO)) {
				return ResponseUtils.returnSuccess();
			} else {
				return ResponseUtils.returnCommonException("解绑失败，请重试");
			}
		} catch (Exception e) {
			logger.error("解绑失败", e);
			return ResponseUtils.returnCommonException("解绑失败，请重试");
		}
	}
    
    /**
     * 设置小额免密
     * @param custId
     * @param type
     * @param password
     * @return
     */
    @ApiOperation("设置小额免密")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @PostMapping(value = "/{version}/setFreePay")
	public Response<?> setFreePay(String custId, Integer type, String password) {
		if (StringUtils.isEmpty(custId)) {
			return ResponseUtils.returnCommonException("用户ID为必填");
		}
		if (type == null) {
			return ResponseUtils.returnCommonException("type必填");
		}
		if (StringUtils.isEmpty(password)) {
			return ResponseUtils.returnCommonException("密码必填");
		}
		try {
			if (!payService.checkPassword(custId, password).success()) {
				return ResponseUtils.returnCommonException("支付密码验证失败");
			}
			if (payService.setFreePay(custId, type)) {
				return ResponseUtils.returnSuccess();
			} else {
				return ResponseUtils.returnCommonException("设置失败，请重试");
			}
		} catch (Exception e) {
			return ResponseUtils.returnCommonException("设置失败，请重试");
		}
	}
    
    /**
     * 获取最低充值金额
     * @return
     */
    @ApiOperation("获取最低充值金额")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @PostMapping(value = "/{version}/getMinChargeAmount")
	public Response<?> getMinChargeAmount() {
		Map<String, Object> map = DataEnum.getMinChargeAmount();
		return ResponseUtils.returnObjectSuccess(map);
	}
    
    /**
     * 创建充值订单
     * @param custId
     * @param payWay
     * @param orderSrc
     * @param orderAmount
     * @param currency
     * @param request
     * @return
     */
    @ApiOperation("创建充值订单")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @PostMapping(value = "/{version}/getNewPayFlowId")
	public Response<?> getNewPayFlowId(String custId, String payWay, String orderSrc, long orderAmount, String currency,
			HttpServletRequest request) {
		if (StringUtils.isEmpty(custId)) {
			return ResponseUtils.returnCommonException("用户ID为必填");
		}
		if (StringUtils.isEmpty(payWay)) {
			return ResponseUtils.returnCommonException("支付方式：payWay必填");
		}
		if (StringUtils.isEmpty(orderSrc)) {
			return ResponseUtils.returnCommonException("支付来源：orderSrc");
		}
		if (StringUtils.isEmpty(currency)) {
			return ResponseUtils.returnCommonException("币种：currency必填");
		}
		if ( orderAmount < 100 || orderAmount % 100 != 0) {
			return ResponseUtils.returnCommonException("请输入正常充值金额");
		}
		

		String ipAddress = WebUtil.getClientIP(request);
		long fee = DataEnum.countFee(payWay, orderAmount);
//		OrderVO orderVO = payServcie.getNewPayFlowId(custId, payWay, orderSrc, orderAmount, fee, currency, ipAddress);
//		return ReturnModel.beanToString(orderVO);
		return ResponseUtils.returnSuccess();
	}
	
    /**
     * 阿里支付回调
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/alipayNotify")
	public void alipayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		logger.info("receive alipayNotify");
//		PayResponse payResp = null;
//		try {
//			// payResp = Alipay.parsePayResult(request);
//			payResp = YryzPaySDK.parseAliPayResult(request);
//		} catch (Exception e) {
//			logger.error("alipayNotify faild ", e);
//			response.getWriter().write("alipayNotify faild ");
//			response.getWriter().flush();
//			return;
//		}
//		logger.info("收到支付宝回调并解析成功，结果为：" + payResp);
//		if (payResp.getResult() == Response.SUCCESS || payResp.getResult() == Response.FAILURE) {
//			System.out.println("支付宝回调成功");
//
//			int orderState = 2;
//			if (payResp.getResult() == Response.SUCCESS) {
//				orderState = 1;
//			}
//			payService.completePayInfo(payResp, OrderConstant.PAY_WAY_ALIPAY, orderState);
//
//			response.getWriter().write("success");
//		}
		// logger.info("支付宝回调结束");
	}
    
    /**
     * 微信支付回调
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/wxpayNotify")
	public void wxpayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("receive wxpayNotify...");
//		PayResponse payResp = null;
//		try {
//			// payResp = Wxpay.parsePayResult(request);
//			payResp = YryzPaySDK.parseWxPayResult(request);
//		} catch (Exception e) {
//			logger.error("wxpayNotify faild ", e);
//			response.getWriter().write("wxpayNotify faild ");
//			response.getWriter().flush();
//			return;
//		}
//		logger.info("收到微信回调并解析成功，结果为：" + payResp);
//		if (payResp.getResult() == Response.SUCCESS || payResp.getResult() == Response.FAILURE) {
//			int orderState = 2;
//			if (payResp.getResult() == Response.SUCCESS) {
//				orderState = 1;
//			}
//			payServcie.completePayInfo(payResp, Constant.PAY_WAY_WXPAY, orderState);
//
//			response.getWriter().write(Wxpay.buildReturnXML("SUCCESS", "OK"));
//			response.getWriter().flush();
//		}
    }
    
    /**
	 * 获取请求参数中所有的信息
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
		Map<String, String> res = new HashMap<String, String>();
		Enumeration<?> temp = request.getParameterNames();
		if (null != temp) {
			while (temp.hasMoreElements()) {
				String en = (String) temp.nextElement();
				String value = request.getParameter(en);
				res.put(en, value);
				// 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
				// System.out.println("ServletUtil类247行 temp数据的键=="+en+"
				// 值==="+value);
				if (null == res.get(en) || "".equals(res.get(en))) {
					res.remove(en);
				}
			}
		}
		return res;
	}
    
	
}
