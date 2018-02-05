/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月20日
 * Id: OrderController.java, 2018年1月20日 上午11:04:02 yehao
 */
package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.rongzhong.component.pay.api.YryzPaySDK;
import com.rongzhong.component.pay.entity.PayResponse;
import com.rongzhong.component.pay.iospay.IosVerify;
import com.rongzhong.component.pay.wxpay.Wxpay;
import com.yryz.common.annotation.UserBehaviorValidation;
import com.yryz.common.exception.RpcOptException;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.DateUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.common.utils.WebUtil;
import com.yryz.quanhu.message.commonsafe.api.CommonSafeApi;
import com.yryz.quanhu.message.commonsafe.constants.CheckVerifyCodeReturnCode;
import com.yryz.quanhu.message.commonsafe.constants.CommonServiceType;
import com.yryz.quanhu.message.commonsafe.dto.VerifyCodeDTO;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.openapi.order.dto.*;
import com.yryz.quanhu.openapi.order.utils.BankUtil;
import com.yryz.quanhu.openapi.order.utils.DataEnum;
import com.yryz.quanhu.openapi.service.PayService;
import com.yryz.quanhu.order.api.OrderApi;
import com.yryz.quanhu.order.enums.OrderConstant;
import com.yryz.quanhu.order.enums.OrderDescEnum;
import com.yryz.quanhu.order.enums.OrderPayConstants;
import com.yryz.quanhu.order.enums.ProductEnum;
import com.yryz.quanhu.order.vo.*;
import com.yryz.quanhu.support.config.api.BasicConfigApi;
import com.yryz.quanhu.user.contants.SmsType;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserLoginSimpleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月20日 上午11:04:02
 * @Description 资金管理
 */
@Api(tags="资金管理 ")
@RestController
@RequestMapping(value = "/services/app")
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

	@Reference
	private BasicConfigApi basicConfigApi;

	/**
	 * 设置支付密码
	 * @param setPayPasswordDTO
	 * @return
	 */
    @ApiOperation("设置支付密码")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true),
			@ApiImplicitParam(name = "token", paramType = "header", required = true)
	})
	@UserBehaviorValidation(login = true)
	@PostMapping(value = "/{version}/pay/setPayPassword")
	public Response<?> setPayPassword(@RequestHeader String userId, @RequestBody SetPayPasswordDTO setPayPasswordDTO) {
		if (StringUtils.isEmpty(userId)) {
			return ResponseUtils.returnCommonException("用户ID不能为空");
		}
		if (null == setPayPasswordDTO || StringUtils.isEmpty(setPayPasswordDTO.getPayPassword())) {
			return ResponseUtils.returnCommonException("新密码不能为空");
		}
		UserPhy userPhy = new UserPhy();
		//设置custId为Header里面获取的userId
		userPhy.setCustId(userId);
		userPhy.setOldPassword(setPayPasswordDTO.getOldPayPassword());
		userPhy.setPayPassword(setPayPasswordDTO.getPayPassword());
		return orderApi.dealUserPhy(userPhy);
	}

    /**
     * 设置密保问题
     * @param userId
     * @param userPhy
     * @return
     */
    @ApiOperation("设置密保问题")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true),
			@ApiImplicitParam(name = "token", paramType = "header", required = true)
	})
	@UserBehaviorValidation(login = true)
    @PostMapping(value = "/{version}/pay/setSecurityProblem")
	public Response<?> setSecurityProblem(@RequestHeader String userId, @RequestBody UserPhy userPhy) {
		if (StringUtils.isEmpty(userId)) {
			return ResponseUtils.returnCommonException("用户ID为必填");
		}
		if (StringUtils.isEmpty(userPhy.getPhyName()) || StringUtils.isEmpty(userPhy.getCustIdcardNo())) {
			return ResponseUtils.returnCommonException("信息必填");
		}
		userPhy.setCustId(userId);
//		return orderApi.dealUserPhy(userPhy);
		return ResponseUtils.returnCommonException("接口已下线");
	}
    
    /**
     * 找回支付密码
     * @param appId
     * @param findPayPasswordDTO
     * @return
     */
    @ApiOperation("找回支付密码")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true),
			@ApiImplicitParam(name = "token", paramType = "header", required = true)
	})
	@UserBehaviorValidation(login = true)
    @PostMapping(value = "/{version}/pay/findPayPassword")
	public Response<?> findPayPassword(@RequestHeader String appId, @RequestHeader String userId,
									   @RequestBody FindPayPasswordDTO findPayPasswordDTO) {
		if(StringUtils.isBlank(userId)){
			return ResponseUtils.returnCommonException("用户ID不能为空！");
		}
    	if(null == findPayPasswordDTO){
			return ResponseUtils.returnCommonException("参数对象不能为空！");
		}
		if(StringUtils.isBlank(findPayPasswordDTO.getVeriCode())){
			return ResponseUtils.returnCommonException("验证码不能为空！");
		}
		UserLoginSimpleVO userLoginSimpleVO = ResponseUtils.getResponseData(userApi.getUserLoginSimpleVO(Long.valueOf(userId)));
		if(null == userLoginSimpleVO || StringUtils.isBlank(userLoginSimpleVO.getUserPhone())){
			return ResponseUtils.returnCommonException("用户未绑定手机号！");
		}
		Response<Integer> checkVerifyCodeResponse = commonSafeApi.checkVerifyCode(
				new VerifyCodeDTO(SmsType.CODE_CHANGE_PAYPWD.getType(), CommonServiceType.PHONE_VERIFYCODE_SEND.getName(),
						userLoginSimpleVO.getUserPhone(), appId, findPayPasswordDTO.getVeriCode(), true));
		if(!checkVerifyCodeResponse.success()
				|| checkVerifyCodeResponse.getData() != CheckVerifyCodeReturnCode.SUCCESS.getCode()){
			return ResponseUtils.returnCommonException("验证码验证失败！");
		}
		//重置支付密码
		UserPhy userPhy = new UserPhy();
		userPhy.setCustId(userId);
		userPhy.setPayPassword("");
		return orderApi.dealUserPhy(userPhy);
	}
    
    /**
     * 积分兑换
     * @param appId
     * @param pointsToAccountDTO
     * @return
     */
    @ApiOperation("积分兑换")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true),
			@ApiImplicitParam(name = "token", paramType = "header", required = true)
	})
	@UserBehaviorValidation(login = true)
    @PostMapping(value = "/{version}/pay/pointsToAccount")
    public Response<?> pointsToAccount(@RequestHeader String appId, @RequestHeader String userId,
									   @RequestBody PointsToAccountDTO pointsToAccountDTO ) {
    	Long cost = pointsToAccountDTO.getCost();
    	String payPassword = pointsToAccountDTO.getPayPassword();
    	
    	if (StringUtils.isEmpty(userId)) {
			return ResponseUtils.returnCommonException("用户ID为必填");
		}
		if (StringUtils.isEmpty(payPassword)) {
			return ResponseUtils.returnCommonException("支付密码为必填");
		}
		if (cost == null || cost.intValue() < 100 || cost % 100 != 0) {
			return ResponseUtils.returnCommonException("请输入正常的兑换金额");
		}
		//验证密码
		Response<?> checkResponse = payService.checkPassword(userId, payPassword);
		if (!checkResponse.success()) {
			return checkResponse;
		}
		String orderId = payService.getOrderId();
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setCustId(userId);
		orderInfo.setCost(cost);
		orderInfo.setOrderDesc(OrderDescEnum.ACCOUNT_EXCHANGE);
		orderInfo.setOrderId(orderId);
		orderInfo.setOrderType(0);
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
		Response<?> response =  orderApi.executeOrder(orderInfo, accounts, integrals, userId, payPassword, null);
//		if(response.success()){
//			pushService.pointToAccount(userId, cost,appId);
//		}
		return response;
	}
    
    /**
     * 获取账户信息
     * @param userId
     * @return
     */
    @ApiOperation("获取账户信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true),
			@ApiImplicitParam(name = "token", paramType = "header", required = true)
	})
	@UserBehaviorValidation(login = true)
    @GetMapping(value = "/{version}/pay/getUserAccount")
	public Response<UserAccountVO> getUserAccount(@RequestHeader String userId) {
		if (StringUtils.isEmpty(userId)) {
			return ResponseUtils.returnCommonException("用户ID为必填");
		}
		try {
			Response<UserAccount> response = orderApi.getUserAccount(userId);
			if (!response.success() || response.getData() == null) {
				return ResponseUtils.returnCommonException("账户不存在");
			} else {
				//转换custId为userId
				UserAccount account = response.getData();
				UserAccountVO userAccountVO = new UserAccountVO();
				userAccountVO.setAccountState(account.getAccountState());
				userAccountVO.setAccountSum(account.getAccountSum());
				userAccountVO.setCostSum(account.getCostSum());
				userAccountVO.setIntegralSum(account.getIntegralSum());
				userAccountVO.setSmallNopass(account.getSmallNopass());
				userAccountVO.setUserId(account.getCustId());
				return ResponseUtils.returnObjectSuccess(userAccountVO);
			}
		} catch (Exception e) {
			logger.error("查询用户账户未知异常", e);
			return ResponseUtils.returnCommonException("账户不存在");
		}
	}

	private WithdrawCashConfig getWithdrawCashConfig(){
		WithdrawCashConfig withdrawCashConfig = new WithdrawCashConfig();
		try {
			String value = ResponseUtils.getResponseData(basicConfigApi.getValue("account.nopass.pay"));
			withdrawCashConfig = JSON.parseObject(value,WithdrawCashConfig.class);
		}catch (Exception e){
			logger.error("获取资金-提现配置失败", e);
		}
		return withdrawCashConfig;
	}
    
    /**
     * 用户提现
     * @param appId
     * @param getCashDTO
     * @return
     */
    @ApiOperation("用户提现")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true),
			@ApiImplicitParam(name = "token", paramType = "header", required = true)
	})
	@UserBehaviorValidation(login = true)
    @PostMapping(value = "/{version}/pay/getCash")
	public Response<?> getCash(@RequestHeader String appId, @RequestHeader String userId, @RequestBody GetCashDTO getCashDTO) {
    	String cost = getCashDTO.getCost();
    	String cust2BankId = getCashDTO.getCust2BankId();
    	String payPassword = getCashDTO.getPayPassword();

		if (StringUtils.isEmpty(userId)) {
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
		//添加提现控制开关
		WithdrawCashConfig withdrawCashConfig = getWithdrawCashConfig();
		if(WithdrawCashConfig.NOT_ALLOWED == withdrawCashConfig.getAllowFlag()){
			return ResponseUtils.returnCommonException(withdrawCashConfig.getMsg());
		}
		return payService.getCash(appId , userId, cost, cust2BankId, payPassword);
	}
	
    /**
     * 计算充值手续费
     * @return
     */
    @ApiOperation("计算充值手续费")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true),
			@ApiImplicitParam(name = "token", paramType = "header", required = true)
	})
	@UserBehaviorValidation(login = true)
    @GetMapping(value = "/{version}/pay/getServiceCharge")
	public Response<?> getServiceCharge() {
		List<Map<String, Object>> list = DataEnum.getPayCharge();
		return ResponseUtils.returnListSuccess(list);
	}
    
    /**
     * 绑定银行卡
     * @param userId
     * @param bindBankCardDTO
     * @return
     */
    @ApiOperation("绑定银行卡")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true),
			@ApiImplicitParam(name = "token", paramType = "header", required = true)
	})
	@UserBehaviorValidation(login = true)
    @PostMapping(value = "/{version}/pay/bindBankCard")
	public Response<UserBankDTO> bindBankCard(@RequestHeader String userId, @RequestBody BindBankCardDTO bindBankCardDTO) {
    	String bankCardNo = bindBankCardDTO.getBankCardNo();
    	String name = bindBankCardDTO.getName();
    	String bankCode = bindBankCardDTO.getBankCode();
    	
		if (StringUtils.isEmpty(userId)) {
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
		userBankDTO.setBankCode(bankCode.trim());
		userBankDTO.setCreateBy(userId);
		userBankDTO.setCustId(userId);
		userBankDTO.setName(name.trim());
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
     * @param userId
     * @return
     */
    @ApiOperation("我的银行卡列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true),
			@ApiImplicitParam(name = "token", paramType = "header", required = true)
	})
	@UserBehaviorValidation(login = true)
    @GetMapping(value = "/{version}/pay/bankCardList")
	public Response<?> bankCardList(@RequestHeader String userId) {
		if (StringUtils.isEmpty(userId)) {
			return ResponseUtils.returnCommonException("用户ID为必填");
		}
		try {
			List<UserBankDTO> list = payService.bankcardlist(userId);
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
     * @param userId
     * @param userBankDTO
     * @return
     */
    @ApiOperation("设置默认卡")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true),
			@ApiImplicitParam(name = "token", paramType = "header", required = true)
	})
	@UserBehaviorValidation(login = true)
    @PostMapping(value = "/{version}/pay/setDefaultBankCard")
	public Response<?> setDefaultBankCard(@RequestHeader String userId, @RequestBody UserBankDTO userBankDTO) {
		if (StringUtils.isEmpty(userId)) {
			return ResponseUtils.returnCommonException("用户ID为必填");
		}
		if (StringUtils.isEmpty(userBankDTO.getCust2BankId())) {
			return ResponseUtils.returnCommonException("关系ID：cust2BankId必填");
		}
		userBankDTO.setCustId(userId);
		userBankDTO.setCreateBy(userId);
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
	@ApiImplicitParams({
			@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true),
			@ApiImplicitParam(name = "token", paramType = "header", required = true)
	})
	@UserBehaviorValidation(login = true)
    @PostMapping(value = "/{version}/pay/unbindBankCard")
	public Response<?> unbindBankCard(@RequestHeader String userId, @RequestBody UnbindBankCardDTO unbindBankCardDTO) {
		if (StringUtils.isEmpty(userId)) {
			return ResponseUtils.returnCommonException("用户ID为必填");
		}
		if (StringUtils.isEmpty(unbindBankCardDTO.getCust2BankId())) {
			return ResponseUtils.returnCommonException("关系ID：cust2BankId必填");
		}
		if (StringUtils.isEmpty(unbindBankCardDTO.getPayPassword())) {
			return ResponseUtils.returnCommonException("验证密码：payPassword必填");
		}

		//验证密码
		Response<?> checkResponse = payService.checkPassword(userId, unbindBankCardDTO.getPayPassword());
		if (!checkResponse.success()) {
			logger.warn("支付密码验证失败");
			return checkResponse;
		}

		UserBankDTO userBankDTO = new UserBankDTO();
		userBankDTO.setCust2BankId(unbindBankCardDTO.getCust2BankId());
		userBankDTO.setCustId(userId);
		userBankDTO.setCreateBy(userId);
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
     * @param userId
     * @param freePayDTO
     * @return
     */
    @ApiOperation("设置小额免密")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true),
			@ApiImplicitParam(name = "token", paramType = "header", required = true)
	})
	@UserBehaviorValidation(login = true)
    @PostMapping(value = "/{version}/pay/setFreePay")
	public Response<?> setFreePay(@RequestHeader String userId, @RequestBody FreePayDTO freePayDTO) {
    	Integer type = freePayDTO.getType();
    	String password = freePayDTO.getPayPassword();
		if (StringUtils.isEmpty(userId)) {
			return ResponseUtils.returnCommonException("用户ID为必填");
		}
		if (type == null) {
			return ResponseUtils.returnCommonException("type必填");
		}
		if (StringUtils.isEmpty(password)) {
			return ResponseUtils.returnCommonException("密码必填");
		}
		try {
			//验证密码
			Response<?> checkResponse = payService.checkPassword(userId, password);
			if (!checkResponse.success()) {
				logger.warn("支付密码验证失败");
				return checkResponse;
			}
			if (payService.setFreePay(userId, type)) {
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
	@ApiImplicitParams({
			@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true),
			@ApiImplicitParam(name = "token", paramType = "header", required = true)
	})
	@UserBehaviorValidation(login = true)
    @GetMapping(value = "/{version}/pay/getMinChargeAmount")
	public Response<?> getMinChargeAmount() {
		Map<String, Object> map = DataEnum.getMinChargeAmount();
		return ResponseUtils.returnObjectSuccess(map);
	}
    
    /**
     * 创建充值订单
     * @param userId
     * @param payOrderDTO
     * @param request
     * @return
     */
    @ApiOperation("创建充值订单")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true),
			@ApiImplicitParam(name = "token", paramType = "header", required = true)
	})
	@UserBehaviorValidation(login = true)
    @PostMapping(value = "/{version}/pay/getNewPayFlowId")
	public Response<PayVO> getNewPayFlowId(@RequestHeader String userId, @RequestBody PayOrderDTO payOrderDTO, HttpServletRequest request) {
		String payWay = payOrderDTO.getPayWay();
		String orderSrc = payOrderDTO.getOrderSrc();
		Long orderAmount = payOrderDTO.getOrderAmount();
		String currency = payOrderDTO.getCurrency();
		if (StringUtils.isEmpty(userId)) {
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
		if (null == orderAmount || orderAmount < 100 || orderAmount % 100 != 0) {
			return ResponseUtils.returnCommonException("请输入正常充值金额");
		}

		String ipAddress = WebUtil.getClientIP(request);
		long fee = DataEnum.countFee(payWay, orderAmount);
		return payService.getNewPayFlowId(userId, payWay, orderSrc, orderAmount, fee, currency, ipAddress);
	}
	
    /**
     * 阿里支付回调
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation("支付宝支付回调")
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @RequestMapping(value = "/{version}/pay/alipayNotify" ,method = {RequestMethod.GET,RequestMethod.POST})
	public void alipayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	logger.info("receive alipayNotify");
		PayResponse payResp = null;
		try {
			payResp = YryzPaySDK.parseAliPayResult(request);
		} catch (Exception e) {
			logger.error("alipayNotify faild ", e);
			response.getWriter().write("alipayNotify faild ");
			response.getWriter().flush();
			return;
		}
		logger.info("收到支付宝回调并解析成功，结果为：" + payResp);
		if (payResp.getResult() == PayResponse.SUCCESS || payResp.getResult() == PayResponse.FAILURE) {
			System.out.println("支付宝回调成功");

			int orderState = OrderPayConstants.OrderState.FAILURE;
			if (payResp.getResult() == PayResponse.SUCCESS) {
				orderState = OrderPayConstants.OrderState.SUCCESS;
			}
			Response<?> payResponse = payService.completePayInfo(payResp, OrderConstant.PAY_WAY_ALIPAY, orderState);
			if(payResponse.success()){
				response.getWriter().write("success");
			}else {
				response.getWriter().write("failure");
			}
		}
		logger.info("支付宝回调结束");
	}
    
    /**
     * 微信支付回调
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation("微信支付回调")
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @RequestMapping(value = "/{version}/pay/wxpayNotify",method = {RequestMethod.GET,RequestMethod.POST})
	public void wxpayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("receive wxpayNotify...");
		PayResponse payResp = null;
		try {
			payResp = YryzPaySDK.parseWxPayResult(request);
		} catch (Exception e) {
			logger.error("wxpayNotify faild ", e);
			response.getWriter().write("wxpayNotify faild ");
			response.getWriter().flush();
			return;
		}
		logger.info("收到微信回调并解析成功，结果为：" + payResp);
		if (payResp.getResult() == PayResponse.SUCCESS || payResp.getResult() == PayResponse.FAILURE) {
			int orderState = OrderPayConstants.OrderState.FAILURE;
			if (payResp.getResult() == PayResponse.SUCCESS) {
				orderState = OrderPayConstants.OrderState.SUCCESS;
			}
			Response<?> payResponse = payService.completePayInfo(payResp, OrderConstant.PAY_WAY_WXPAY, orderState);
			if(payResponse.success()){
				response.getWriter().write(Wxpay.buildReturnXML("SUCCESS", "OK"));
				response.getWriter().flush();
			}else{
				response.getWriter().write("wxpayNotify faild ");
				response.getWriter().flush();
			}
		}
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
	
	/**
	 * 获取安全信息
	 * @param userId
	 * @return
	 */
    @ApiOperation("获取安全信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true),
			@ApiImplicitParam(name = "token", paramType = "header", required = true)
	})
	@UserBehaviorValidation(login = true)
    @GetMapping(value = "/{version}/pay/getUserPhy")
	public Response<?> getUserPhy(@RequestHeader String userId) {
		if (StringUtils.isEmpty(userId)) {
			return ResponseUtils.returnCommonException("用户ID为必填");
		}
		try {
			UserPhyDTO userPhy = payService.getUserPhy(userId);
			UserAccount account = payService.getUserAccount(userId);
			if (userPhy == null || account == null) {
				return ResponseUtils.returnCommonException("当前用户数据不存在，请重试");
			}
			Map<String, Object> json = new HashMap<>(5);
			json.put("userId", userPhy.getCustId());
			json.put("phyName", replaceStr(userPhy.getPhyName(), 1));
			json.put("phyCardNo", replaceStr(userPhy.getCustIdcardNo(), 4));
			if (StringUtils.isEmpty(userPhy.getPayPassword())) {
				json.put("isPayPassword", 0);
			} else {
				json.put("isPayPassword", 1);
			}
			json.put("smallNopass", account.getSmallNopass());
			return ResponseUtils.returnObjectSuccess(json);
		} catch (Exception e) {
			logger.error("查询用户安全信息", e);
			return ResponseUtils.returnCommonException("用户不存在");
		}
	}

	private static String replaceStr(String str, int num) {
		if (StringUtils.isEmpty(str)) {
			return null;
		}
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (i >= num) {
				char chat = chars[i];
				chat = '*';
				chars[i] = chat;
			}
		}
		return new String(chars);
	}
	
	/**
	 * 获取订单列表
	 * @param userId
	 * @param date
	 * @param productType
	 * @param type
	 * @param start
	 * @param limit
	 * @return
	 */
    @ApiOperation("获取订单列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true),
			@ApiImplicitParam(name = "token", paramType = "header", required = true)
	})
	@UserBehaviorValidation(login = true)
    @GetMapping(value = "/{version}/pay/getOrderList")
	public Response<OrderListDTO> getOrderList(@RequestHeader String userId, String date, Integer productType, Integer type,
									Long start, Long limit) {
		if (StringUtils.isEmpty(userId)) {
			return ResponseUtils.returnCommonException("用户ID为必填");
		}
		if (type == null) {
			return ResponseUtils.returnCommonException("请输入查询类型：1，消费流水；2，积分流水");
		}
		if (start == null) {
			start = 0L;
		}
		if (limit == null) {
			limit = 20L;
		}
		try {
			List<OrderListDTO> list = payService.getOrderList(userId, date, productType, type, start, limit);
			return ResponseUtils.returnListSuccess(list);
		} catch (Exception e) {
			logger.error("查询用户账单信息失败", e);
			return ResponseUtils.returnCommonException("无数据");
		}
	}
    
    /**
     * 苹果内购
     * @param userId
	 * @param checkIOSPayDTO
	 * @return
     */
    @ApiOperation("苹果内购支付")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true),
			@ApiImplicitParam(name = "token", paramType = "header", required = true)
	})
	@UserBehaviorValidation(login = true)
    @PostMapping(value = "/{version}/pay/checkIOSPay")
    public Response<?> checkIOSPay(@RequestHeader String userId, @RequestBody CheckIOSPayDTO checkIOSPayDTO) {
		String orderId = checkIOSPayDTO.getOrderId();
		Long orderAmount = checkIOSPayDTO.getOrderAmount();
		String receipt = checkIOSPayDTO.getReceipt();
		if (orderAmount == null || orderAmount.intValue() < 100 || orderAmount.intValue() % 100 != 0) {
			return ResponseUtils.returnCommonException("请输入正常的兑换金额");
		}
		
		boolean isSandbox = false;
		if ("575155838165196903".equals(userId)) {
			isSandbox = true;
		}

		String result = IosVerify.verifyReceipt(receipt, isSandbox);
		logger.info("ios check receipt : " + result);
		logger.info("ios check receive result : " + result);
		try {
			JSONObject json = new JSONObject(result);
			if (json.getInt("status") != 0) {
				if (json.getInt("status") == 21005) { // 苹果服务不可用，需要再次测试
					return ResponseUtils.returnCommonException("验证苹果服务超时");
				} else {
					return ResponseUtils.returnCommonException("验证苹果服务失败");
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
			return payService.completePayInfo(payResp, OrderConstant.PAY_WAY_IOS_IAP, orderState);
		} catch (Exception e) {
			logger.error("苹果内购验证失败", e);
			return ResponseUtils.returnException(e);
		}
	}
    
    /**
     * 获取统计信息
     * @param userId
     * @return
     */
	public Response<?> getStatistics(@RequestHeader String userId) {
		if (StringUtils.isEmpty(userId)) {
			return ResponseUtils.returnCommonException("userId为空");
		}
		return ResponseUtils.returnSuccess();
//		try {
//			UserStatistics statistics = payServcie.getIntegralStatistics(userId);
//			return ReturnModel.beanToString(statistics);
//		} catch (Exception e) {
//			logger.error("查询积分统计失败", e);
//			return ReturnModel.returnException(ReturnCode.WARN, "无数据");
//		}
	}

	/**
	 * 支付宝网页支付
	 * @param request
	 * @param response
	 * @throws Exception
	 */
    @ApiOperation("支付宝网页支付")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true),
			@ApiImplicitParam(name = "token", paramType = "header", required = true)
	})
	@UserBehaviorValidation(login = true)
	@GetMapping(value = "{version}/pay/toAlipay")
	public void toAlipay(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String orderId = request.getParameter("orderId");
		if (StringUtils.isEmpty(orderId)) {
			return;
		}
//		String ipAddress = WebUtil.getClientIP(request);
//		String requestHtml = payService.buildAlipayRequest(orderId, null, 0L, 0L, "156", ipAddress, null, false);
//		logger.warn("requestHtml:" + requestHtml);
//		response.setContentType("text/html; charset=" + AlipayConfig.input_charset);
//		response.getWriter().println(requestHtml);
//		response.getWriter().flush();
	}
	
    
    /**
     * 执行异步订单
     * @param userId
     * @param executeOrderDTO
     * @return
     */
    @ApiOperation("执行异步订单")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true),
			@ApiImplicitParam(name = "token", paramType = "header", required = true)
	})
	@UserBehaviorValidation(login = true)
    @PostMapping(value = "/{version}/pay/executeOrder")
    public Response<?> executeOrder(@RequestHeader String userId, @RequestBody ExecuteOrderDTO executeOrderDTO) {
		String orderId = executeOrderDTO.getOrderId();
		String password = executeOrderDTO.getPayPassord();
		if (StringUtils.isEmpty(orderId)) {
			return ResponseUtils.returnCommonException("orderId必填");
		}
		if (StringUtils.isEmpty(userId)) {
			return ResponseUtils.returnCommonException("custId必填");
		}
		try {
			return payService.executeOrder(orderId, userId, password);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseUtils.returnCommonException("未知异常");
		}
	}
    
    /**
     * 查询订单状态
     * @param orderId
     * @return
     */
    @ApiOperation("查询订单状态")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true),
			@ApiImplicitParam(name = "token", paramType = "header", required = true)
	})
	@UserBehaviorValidation(login = true)
    @GetMapping(value = "/{version}/pay/getOrderInfo")
	public Response<OrderInfo> getOrderInfo(String orderId) {
		if (StringUtils.isEmpty(orderId)) {
			return ResponseUtils.returnCommonException("orderId必填");
		}
		try {
			OrderInfo orderInfo = payService.getOrderInfo(orderId);
			if (orderInfo != null) {
				orderInfo.setBizContent(null);
				orderInfo.setCallback(null);
				return ResponseUtils.returnObjectSuccess(orderInfo);
			} else {
				return ResponseUtils.returnCommonException("无订单数据");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseUtils.returnCommonException("未知异常");
		}
	}
	
    
	
}
