/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月18日
 * Id: OrderServiceImpl.java, 2018年1月18日 上午11:12:11 yehao
 */
package com.yryz.quanhu.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.DateUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.common.utils.IdGen;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.order.dao.persistence.RrzOrderCust2bankDao;
import com.yryz.quanhu.order.dao.persistence.RrzOrderInfoDao;
import com.yryz.quanhu.order.dao.persistence.RrzOrderPayHistoryDao;
import com.yryz.quanhu.order.dao.persistence.RrzOrderPayInfoDao;
import com.yryz.quanhu.order.dao.redis.RrzOrderInfoRedis;
import com.yryz.quanhu.order.entity.RrzOrderAccountHistory;
import com.yryz.quanhu.order.entity.RrzOrderCust2bank;
import com.yryz.quanhu.order.entity.RrzOrderInfo;
import com.yryz.quanhu.order.entity.RrzOrderIntegralHistory;
import com.yryz.quanhu.order.entity.RrzOrderPayHistory;
import com.yryz.quanhu.order.entity.RrzOrderPayInfo;
import com.yryz.quanhu.order.entity.RrzOrderUserAccount;
import com.yryz.quanhu.order.entity.RrzOrderVO;
import com.yryz.quanhu.order.enums.AccountEnum;
import com.yryz.quanhu.order.enums.OrderConstant;
import com.yryz.quanhu.order.enums.OrderDescEnum;
import com.yryz.quanhu.order.enums.OrderMsgEnum;
import com.yryz.quanhu.order.enums.ProductEnum;
import com.yryz.quanhu.order.exception.CommonException;
import com.yryz.quanhu.order.exception.SourceNotEnoughException;
import com.yryz.quanhu.order.notify.NotifyQueue;
import com.yryz.quanhu.order.service.OrderAccountHistoryService;
import com.yryz.quanhu.order.service.OrderIntegralHistoryService;
import com.yryz.quanhu.order.service.OrderService;
import com.yryz.quanhu.order.service.UserAccountService;
import com.yryz.quanhu.order.service.UserPhyService;
import com.yryz.quanhu.order.utils.CollectionUtils;
import com.yryz.quanhu.order.utils.Page;
import com.yryz.quanhu.order.vo.PayInfo;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月18日 上午11:12:11
 * @Description 订单管理接口服务实现
 */
@Transactional
@Service
public class OrderServiceImpl implements OrderService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RrzOrderCust2bankDao rrzOrderCust2bankDao;

	@Autowired
	private RrzOrderPayHistoryDao rrzOrderPayHistoryDao;

	@Autowired
	private RrzOrderInfoDao rrzOrderInfoDao;

	@Autowired
	private RrzOrderPayInfoDao rrzOrderPayInfoDao;
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private UserPhyService userPhyService;
	
	@Autowired
	private OrderIntegralHistoryService orderIntegralHistoryService;
	
	@Autowired
	private OrderAccountHistoryService orderAccountHistoryService;
	
//	@Autowired
//	private QuanhuMessage quanhuMessage;
//	
//	@Autowired
//	private EventManager eventManager;
	
	@Autowired
	private RrzOrderInfoRedis rrzOrderInfoRedis;
	
	@Autowired
	private NotifyQueue notifyQueue;
	
	private static final int SAVE_LIMIT = 5;

	/**
	 * 执行订单。
	 * @param orderInfo 订单记录
	 * @param accounts 消费流水
	 * @param integrals 积分流水
	 * @param custId 用户ID
	 * @param payPassword 支付密码,选填，不填则不校验
	 * @param remark
	 * @return
	 * @see com.yryz.service.order.modules.order.service.IOrderService#executeOrder(com.yryz.service.order.modules.order.entity.RrzOrderInfo, java.util.List, java.util.List, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Response<?> executeOrder(RrzOrderInfo orderInfo, List<RrzOrderAccountHistory> accounts,
			List<RrzOrderIntegralHistory> integrals, String custId, String payPassword, String remark) {
		if(StringUtils.isNotEmpty(custId)){
			RrzOrderUserAccount account = userAccountService.getUserAccount(custId);
			if (account == null){
				return ResponseUtils.returnException(new CommonException("账户不存在或者系统异常"));
			}
			if (account.getAccountState().intValue() == 0){
				return ResponseUtils.returnException(new CommonException("账户被冻结"));
			}
			if ((account.getSmallNopass().intValue() == 0
					|| account.getSmallNopass().intValue() == 1 && orderInfo.getCost().longValue() > 5000)
					&& StringUtils.isEmpty(payPassword)){
				return ResponseUtils.returnException(new CommonException("支付密码为空"));
			}
			Response<?> return1 = userPhyService.checkPayPassword(custId, payPassword);
			if (!return1.success()) {
				return return1;
			}
		}
		// 先检查是否存在余额不足的问题
		if (checkAccountOrder(accounts) && checkIntegralOrder(integrals)) { 
			 // 处理消费账户流水
			if (CollectionUtils.isNotEmpty(accounts)) {
				for (RrzOrderAccountHistory rrzOrderAccountHistory : accounts) {
					String orderCustId = rrzOrderAccountHistory.getCustId();
					RrzOrderUserAccount rrzOrderUserAccount = userAccountService.getUserAccount(orderCustId);
					if(rrzOrderAccountHistory.getOrderType().intValue() == 1){
						rrzOrderAccountHistory.setAccountSum(rrzOrderUserAccount.getAccountSum() + rrzOrderAccountHistory.getCost());
					} else {
						rrzOrderAccountHistory.setAccountSum(rrzOrderUserAccount.getAccountSum() - rrzOrderAccountHistory.getCost());
					}
					rrzOrderAccountHistory.setCreateTime(new Date());
					orderAccountHistoryService.insert(rrzOrderAccountHistory);
				}
			}
			// 处理积分账户流水
			if (CollectionUtils.isNotEmpty(integrals)) { 
				for (RrzOrderIntegralHistory rrzOrderIntegralHistory : integrals) {
					String orderCustId = rrzOrderIntegralHistory.getCustId();
					RrzOrderUserAccount rrzOrderUserAccount = userAccountService.getUserAccount(orderCustId);
					if(rrzOrderIntegralHistory.getOrderType().intValue() == 1){
						rrzOrderIntegralHistory.setAccountSum(rrzOrderUserAccount.getIntegralSum() + rrzOrderIntegralHistory.getCost());
					} else {
						rrzOrderIntegralHistory.setAccountSum(rrzOrderUserAccount.getIntegralSum() - rrzOrderIntegralHistory.getCost());
					}
					rrzOrderIntegralHistory.setCreateTime(new Date());
					orderIntegralHistoryService.insert(rrzOrderIntegralHistory);
				}
			}
			if (orderInfo != null) {
				RrzOrderInfo orderInfo2 = rrzOrderInfoDao.getForUpdate(orderInfo.getOrderId());
				try {
					// 如果订单已存在，则不用再次更新。
					if (orderInfo2 == null) { 
						// --
						// create
						// by
						// yehao
						// 2017-4-11
						orderInfo.setCreateTime(new Date());
						// 默认设置消费类型为 用户消费账户金额
						if (orderInfo.getConsumeType() == null){
							orderInfo.setConsumeType((byte) 0);
						}
						orderInfo.setOrderState(RrzOrderInfo.ORDER_STATE_ON);
						rrzOrderInfoDao.insert(orderInfo);
					} else {
						int orderState = orderInfo2.getOrderState() == null ? 0 : orderInfo2.getOrderState().intValue();
						if(orderState == RrzOrderInfo.ORDER_STATE_ON){
							return ResponseUtils.returnException(new CommonException("该订单已经处理"));
						}
						if(!StringUtils.equals(orderInfo2.getCustId(), orderInfo.getCustId()) ||  
								!StringUtils.equals(orderInfo2.getOrderId(), orderInfo.getOrderId())){
							return ResponseUtils.returnException(new CommonException("新旧订单信息不一致"));
						}
						orderInfo2.setOrderState(RrzOrderInfo.ORDER_STATE_ON);
					}
				} finally {
					if(orderInfo2 != null){
						rrzOrderInfoDao.update(orderInfo2);
					}
				}
				
			}
			
			
			//处理账户余额
			// 处理消费账户余额
			if (CollectionUtils.isNotEmpty(accounts)) { 
				for (RrzOrderAccountHistory rrzOrderAccountHistory : accounts) {
					String orderCustId = rrzOrderAccountHistory.getCustId();
					userAccountService.optAccountSource(orderCustId,
							rrzOrderAccountHistory.getCost().longValue(), rrzOrderAccountHistory.getOrderType());
				}
			}
			// 处理积分账户余额
			if (CollectionUtils.isNotEmpty(integrals)) {
				for (RrzOrderIntegralHistory rrzOrderIntegralHistory : integrals) {
					String orderCustId = rrzOrderIntegralHistory.getCustId();
					userAccountService.optIntegralSource(orderCustId,
							rrzOrderIntegralHistory.getCost().longValue(), rrzOrderIntegralHistory.getOrderType());
				}
			}
			
		} else {// 已经检查过余额不足的话，就直接返回结果
			throw new SourceNotEnoughException(OrderMsgEnum.NOT_ENOUGH.getMsg());
		}
		//如果是退款订单，则发送推销消息
		if(orderInfo.getProductType().intValue() == ProductEnum.CASH_REFUND.getType()){ 
//			quanhuMessage.sendMessage(MessageConstant.CASH_REFUND, orderInfo.getCustId(), orderInfo.getCost().toString());
		}
		return ResponseUtils.returnSuccess();
	}

	/**
	 * 检查消费账户订单信息是否可以执行
	 * @param orders
	 * @return
	 */
	public boolean checkAccountOrder(List<RrzOrderAccountHistory> orders) {
		if (CollectionUtils.isEmpty(orders)){
			return true;
		}
		Map<String, Long> map = new HashMap<String, Long>(1);
		for (RrzOrderAccountHistory rrzOrderAccountHistory : orders) {
			long cost = rrzOrderAccountHistory.getCost().longValue();
			if(cost < 0) {
				return false;
			}
			String custId = rrzOrderAccountHistory.getCustId();
			long count = map.get(custId) == null ? 0 : map.get(custId);
			// 如果是消费，则累计消费需要增加
			if (rrzOrderAccountHistory.getOrderType().intValue() == 0) { 
				count = count - rrzOrderAccountHistory.getCost().longValue();
			} else {
				count = count + rrzOrderAccountHistory.getCost().longValue();
			}
			map.put(custId, count);
		}
		Set<String> custIds = map.keySet();
		for (String custId : custIds) {
			// 系统账户无视规则
			if (AccountEnum.SYSID.equals(custId) || AccountEnum.OPTID.equals(custId)){
				continue;
			}
			RrzOrderUserAccount rrzOrderUserAccount = userAccountService.getUserAccount(custId);
			if (rrzOrderUserAccount == null) {
				return false;
			}
			// 如果是扣费，且账户异常状态，也直接返回失败
			if (map.get(custId) < 0 && rrzOrderUserAccount.getAccountState().intValue() == 0) { 
				return false;
			}
			if ((rrzOrderUserAccount.getAccountSum().longValue() + map.get(custId)) < 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查积分账户订单信息是否可以执行
	 * 
	 * @param orders
	 * @return
	 */
	public boolean checkIntegralOrder(List<RrzOrderIntegralHistory> orders) {
		if (CollectionUtils.isEmpty(orders)){
			return true;
		}
		Map<String, Long> map = new HashMap<String, Long>(1);
		for (RrzOrderIntegralHistory rrzOrderIntegralHistory : orders) {
			long cost = rrzOrderIntegralHistory.getCost().longValue();
			if(cost < 0) {
				return false;
			}
			String custId = rrzOrderIntegralHistory.getCustId();
			long count = map.get(custId) == null ? 0 : map.get(custId);
			if (rrzOrderIntegralHistory.getOrderType().intValue() == 0) {
				count = count - rrzOrderIntegralHistory.getCost().longValue();
			} else {
				count = count + rrzOrderIntegralHistory.getCost().longValue();
			}
			// 计算好的余额存入缓存
			map.put(custId, count); 
		}
		Set<String> custIds = map.keySet();
		for (String custId : custIds) {
			// 系统账户无视规则
			if (AccountEnum.SYSID.equals(custId) || AccountEnum.OPTID.equals(custId)){
				continue;
			}

			RrzOrderUserAccount rrzOrderUserAccount = userAccountService.getUserAccount(custId);
			if (rrzOrderUserAccount == null) {
				return false;
			}
			// 如果是扣费，且账户异常状态，也直接返回失败
			if (map.get(custId) < 0 && rrzOrderUserAccount.getAccountState().intValue() == 0) { 
				return false;
			}
			if ((rrzOrderUserAccount.getIntegralSum().longValue() + map.get(custId)) < 0) {
				return false;
			}
		}
		return true;
	}

	@Override
	public RrzOrderPayInfo executePayInfo(RrzOrderPayInfo rrzOrderPayInfo, String custId, String payPassword,
			String remark) {
		if (!userPhyService.checkPayPassword(custId, payPassword).success()){
			return null;
		}
		boolean flag = true;
		RrzOrderPayInfo orderPayInfo = rrzOrderPayInfoDao.get(rrzOrderPayInfo.getOrderId());
		// 检查此订单是否已经被执行过。
		if (orderPayInfo != null && orderPayInfo.getOrderState() != null
				&& orderPayInfo.getOrderState().intValue() == 1) {
			logger.info("this order has been excuted ... payInfo : " + rrzOrderPayInfo );
			return null;
		}
		// 执行充值订单信息保存操作
		if (orderPayInfo == null) { 
			orderPayInfo = rrzOrderPayInfo;
			rrzOrderPayInfoDao.insert(orderPayInfo);
		} else {
			if (rrzOrderPayInfo.getCompleteTime() != null) {
				orderPayInfo.setCompleteTime(rrzOrderPayInfo.getCompleteTime());
			}
			if (rrzOrderPayInfo.getEndDesc() != null) {
				orderPayInfo.setEndDesc(rrzOrderPayInfo.getEndDesc());
			}
			if (rrzOrderPayInfo.getOrderState() != null) {
				orderPayInfo.setOrderState(rrzOrderPayInfo.getOrderState());
			}
			rrzOrderPayInfoDao.update(orderPayInfo);
		}

		if (orderPayInfo.getOrderState() != null && orderPayInfo.getOrderState() != null
				&& orderPayInfo.getOrderState().intValue() == 1) {// 生效订单则须执行订单
			// 如果是苹果支付，则需要验证金额,由于苹果的订单无校验
			if (OrderConstant.PAY_WAY_IOS_IAP.equals(orderPayInfo.getOrderChannel())) { 
				// 屏蔽苹果安全校验
				// if(rrzOrderPayInfo.getCost().longValue() !=
				// orderPayInfo.getCost().longValue()){
				// System.out.println("苹果支付数据不对：苹果金额：" +
				// rrzOrderPayInfo.getCost() + "实际金额：" +
				// orderPayInfo.getCost());
				// return null;
				// }
			}
			// 如果是生效订单，则执行订单的相关资金交割
			int productType = orderPayInfo.getProductType();
			if (productType == ProductEnum.RECHARGE_TYPE.getType()) {
				flag = executeRechargeOrder(orderPayInfo);
			} else if (productType == ProductEnum.CASH_TYPE.getType()) {
				flag = executeCashOrder(orderPayInfo);
			}
		}
		if (!flag) {
			throw new CommonException("订单执行失败，请检查账户余额是否充足");
		}
		return orderPayInfo;
	}

	/**
	 * 执行充值现金订单， 我充值100元获得消费资金100元，扣去手续费2块。实际平台进账98元 ，操作结果如下 记录2条
	 * 记录1，进账记录：充值金额100，实际进账100，充的消费账户100 记录2，出账记录：充值金额100，实际出账2，记录类型是手续费
	 * 消费账户增加100元的现金及流水
	 * 
	 * @param rrzOrderPayInfo
	 */
	public boolean executeRechargeOrder(RrzOrderPayInfo rrzOrderPayInfo) {
		// 执行资金变动
		RrzOrderInfo orderInfo = new RrzOrderInfo();
		orderInfo.setCost(rrzOrderPayInfo.getCostTrue());
		if (StringUtils.isNotEmpty(rrzOrderPayInfo.getCustId())){
			orderInfo.setCustId(rrzOrderPayInfo.getCustId());
		}
		orderInfo.setOrderDesc(OrderDescEnum.ACCOUNT_RECHARGE);
		orderInfo.setOrderId(rrzOrderPayInfo.getOrderId());
		orderInfo.setOrderType(1);
		orderInfo.setProductDesc(ProductEnum.RECHARGE_TYPE.getDesc());
		orderInfo.setProductId(ProductEnum.RECHARGE_TYPE.getType() + "");
		orderInfo.setProductType(ProductEnum.RECHARGE_TYPE.getType());
		orderInfo.setType(1);
		orderInfo.setCreateTime(new Date());
		RrzOrderAccountHistory orderAccountHistory = new RrzOrderAccountHistory();
		orderAccountHistory.setCost(rrzOrderPayInfo.getCostTrue());
		orderAccountHistory.setCreateTime(new Date());
		orderAccountHistory.setCustId(rrzOrderPayInfo.getCustId());
		orderAccountHistory.setOrderDesc(OrderDescEnum.ACCOUNT_RECHARGE);
		orderAccountHistory.setOrderId(rrzOrderPayInfo.getOrderId());
		orderAccountHistory.setOrderType(1);
		orderAccountHistory.setProductDesc(ProductEnum.RECHARGE_TYPE.getDesc());
		orderAccountHistory.setProductId(ProductEnum.RECHARGE_TYPE.getType() + "");
		orderAccountHistory.setProductType(ProductEnum.RECHARGE_TYPE.getType());
		List<RrzOrderAccountHistory> list = new ArrayList<>();
		list.add(orderAccountHistory);
		Response<?> return1 = executeOrder(orderInfo, list, null, null, null, null);
		if (return1.success()) {
			// 增加进账记录
			RrzOrderPayHistory orderPayHistory = (RrzOrderPayHistory) GsonUtils.parseObj(rrzOrderPayInfo,
					RrzOrderPayHistory.class);
			orderPayHistory.setHistoryId(IdGen.uuid());
			orderPayHistory.setCreateTime(new Date());
			orderPayHistory.setOrderType(1);
			rrzOrderPayHistoryDao.insert(orderPayHistory);

			// 增加手续费出账记录
			RrzOrderPayHistory feeOrderPayHistory = (RrzOrderPayHistory) GsonUtils.parseObj(rrzOrderPayInfo,
					RrzOrderPayHistory.class);
			feeOrderPayHistory.setCostTrue(rrzOrderPayInfo.getCostFee());
			feeOrderPayHistory.setOrderType(0);
			feeOrderPayHistory.setCreateTime(new Date());
			feeOrderPayHistory.setOrderDesc(OrderDescEnum.ACCOUNT_FEE);
			feeOrderPayHistory.setProductDesc(ProductEnum.FEE_TYPE.getDesc());
			feeOrderPayHistory.setProductId(ProductEnum.FEE_TYPE.getType() + "");
			feeOrderPayHistory.setProductType(ProductEnum.FEE_TYPE.getType());
			feeOrderPayHistory.setHistoryId(IdGen.uuid());
			rrzOrderPayHistoryDao.insert(feeOrderPayHistory);
			//提交事件
//			eventManager.commitRecharge(rrzOrderPayInfo.getCustId(),rrzOrderPayInfo.getCostTrue());
//			quanhuMessage.sendMessage(MessageConstant.RECHARGE, rrzOrderPayInfo.getCustId(), "" + rrzOrderPayInfo.getCost());
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 执行提现现金订单， 我提现100元，扣去积分102点，扣去手续费2块(用户承担手续费)。实际转账100元 ，操作结果如下
	 * 记录1，出账记录：提现金额100，手续费，实际出账102，扣的积分账户102，记录积分流水2笔支出，提现金额、提现手续费，记录资金流水，提现金额，
	 * 提现手续费 扣去积分账户102的余额
	 * 
	 * @param rrzOrderPayInfo
	 */
	public boolean executeCashOrder(RrzOrderPayInfo rrzOrderPayInfo) {

		// 执行资金变动
		RrzOrderInfo orderInfo = new RrzOrderInfo();
		orderInfo.setCost(rrzOrderPayInfo.getCostTrue());
		orderInfo.setOrderDesc(OrderDescEnum.INTEGRAL_CASH);
		if (StringUtils.isNotEmpty(rrzOrderPayInfo.getCustId())){
			orderInfo.setCustId(rrzOrderPayInfo.getCustId());
		}
		orderInfo.setOrderId(rrzOrderPayInfo.getOrderId());
		orderInfo.setOrderType(0);
		orderInfo.setProductDesc(ProductEnum.CASH_TYPE.getDesc());
		orderInfo.setProductId(ProductEnum.CASH_TYPE.getType() + "");
		orderInfo.setProductType(ProductEnum.CASH_TYPE.getType());
		orderInfo.setType(2);
		orderInfo.setCreateTime(new Date());
		List<RrzOrderIntegralHistory> list = new ArrayList<>();
		RrzOrderIntegralHistory orderIntegralHistory = new RrzOrderIntegralHistory();
		orderIntegralHistory.setCost(rrzOrderPayInfo.getCostTrue());
		orderIntegralHistory.setCreateTime(new Date());
		orderIntegralHistory.setCustId(rrzOrderPayInfo.getCustId());
		orderIntegralHistory.setOrderDesc(OrderDescEnum.INTEGRAL_CASH);
		orderIntegralHistory.setOrderId(rrzOrderPayInfo.getOrderId());
		orderIntegralHistory.setOrderType(0);
		orderIntegralHistory.setProductDesc(ProductEnum.CASH_TYPE.getDesc());
		orderIntegralHistory.setProductId(ProductEnum.CASH_TYPE.getType() + "");
		orderIntegralHistory.setProductType(ProductEnum.CASH_TYPE.getType());
		list.add(orderIntegralHistory);
		RrzOrderIntegralHistory feeFrderIntegralHistory = new RrzOrderIntegralHistory();
		feeFrderIntegralHistory.setCost(rrzOrderPayInfo.getCostFee());
		feeFrderIntegralHistory.setCreateTime(new Date());
		feeFrderIntegralHistory.setCustId(rrzOrderPayInfo.getCustId());
		feeFrderIntegralHistory.setOrderDesc(OrderDescEnum.INTEGRAL_CASH_FEE);
		feeFrderIntegralHistory.setOrderId(rrzOrderPayInfo.getOrderId());
		feeFrderIntegralHistory.setOrderType(0);
		feeFrderIntegralHistory.setProductDesc(ProductEnum.FEE_CASH_TYPE.getDesc());
		feeFrderIntegralHistory.setProductId(ProductEnum.FEE_CASH_TYPE.getType() + "");
		feeFrderIntegralHistory.setProductType(ProductEnum.FEE_CASH_TYPE.getType());
		list.add(feeFrderIntegralHistory);
		Response<?> return1 = executeOrder(orderInfo, null, list, null, null, null);

		if (return1.success()) {
			// 增加出账记录
			RrzOrderPayHistory orderPayHistory = (RrzOrderPayHistory) GsonUtils.parseObj(rrzOrderPayInfo,
					RrzOrderPayHistory.class);
			orderPayHistory.setCreateTime(new Date());
			orderPayHistory.setOrderType(0);
			orderPayHistory.setHistoryId(IdGen.uuid());
			rrzOrderPayHistoryDao.insert(orderPayHistory);

			// 增加出账记录
			RrzOrderPayHistory feeOrderPayHistory = (RrzOrderPayHistory) GsonUtils.parseObj(rrzOrderPayInfo,
					RrzOrderPayHistory.class);
			feeOrderPayHistory.setCostTrue(rrzOrderPayInfo.getCostFee());
			feeOrderPayHistory.setOrderType(0);
			feeOrderPayHistory.setCreateTime(new Date());
			feeOrderPayHistory.setOrderDesc(OrderDescEnum.INTEGRAL_CASH_FEE);
			feeOrderPayHistory.setProductDesc(ProductEnum.FEE_CASH_TYPE.getDesc());
			feeOrderPayHistory.setProductId(ProductEnum.FEE_CASH_TYPE.getType() + "");
			feeOrderPayHistory.setProductType(ProductEnum.FEE_CASH_TYPE.getType());
			feeOrderPayHistory.setHistoryId(IdGen.uuid());
			rrzOrderPayHistoryDao.insert(feeOrderPayHistory);
//			quanhuMessage.sendMessage(MessageConstant.CASH, rrzOrderPayInfo.getCustId(), rrzOrderPayInfo.getCost() + "");
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<RrzOrderCust2bank> getCustBanks(String custId) {
		RrzOrderCust2bank rrzOrderCust2bank = new RrzOrderCust2bank();
		rrzOrderCust2bank.setCustId(custId);
		return rrzOrderCust2bankDao.getList(rrzOrderCust2bank);
	}

	@Override
	public RrzOrderCust2bank dealCustBank(RrzOrderCust2bank rrzOrderCust2bank, int type) {
		String cust2BankId = rrzOrderCust2bank.getCust2BankId();
		// 表示数据删除
		if (type == 0) { 
			// 如果不为空，则表示是数据更新
			if (StringUtils.isNotEmpty(cust2BankId)) { 
				RrzOrderCust2bank orderCust2bank = rrzOrderCust2bankDao.get(cust2BankId);
				orderCust2bank.setDelFlag("1");
				int defaultCard = orderCust2bank.getDefaultCard();
				if (defaultCard == 1) {
					List<RrzOrderCust2bank> list = rrzOrderCust2bankDao.getList(orderCust2bank);
					if (CollectionUtils.isNotEmpty(list)) {
						for (RrzOrderCust2bank rrzOrderCust2bank2 : list) {
							// 取顺序不是非当前卡号的另外一个卡，设为默认卡
							if (!rrzOrderCust2bank2.getCust2BankId().equals(cust2BankId)) {
								rrzOrderCust2bank2.setDefaultCard(1);
								rrzOrderCust2bankDao.update(rrzOrderCust2bank2);
								break;
							}
						}
					}
					orderCust2bank.setDefaultCard(0);
				}
				rrzOrderCust2bankDao.update(orderCust2bank);
				return orderCust2bank;
			}
			return rrzOrderCust2bank;
		} else {
			// 如果不为空，则表示是数据更新,主要是更新默认卡信息
			if (StringUtils.isNotEmpty(cust2BankId)) { 
				// 如果卡号重复，则提示错误
				if (CollectionUtils.isNotEmpty(rrzOrderCust2bankDao.getBybankCardNo(rrzOrderCust2bank))) { 
					throw new CommonException("银行卡号重复");
				}
				RrzOrderCust2bank orderCust2bank = rrzOrderCust2bankDao.get(cust2BankId);
				if (rrzOrderCust2bank.getDefaultCard() != null) {
					int defaultCard = rrzOrderCust2bank.getDefaultCard();
					// 如果是设置默认卡，则将其他默认卡置为0
					if (defaultCard == 1) { 
						List<RrzOrderCust2bank> list = rrzOrderCust2bankDao.getList(orderCust2bank);
						for (RrzOrderCust2bank rrzOrderCust2bank2 : list) {
							if (rrzOrderCust2bank2.getDefaultCard() == 1) {
								rrzOrderCust2bank2.setDefaultCard(0);
								rrzOrderCust2bankDao.update(rrzOrderCust2bank2);
							}
						}
					} else { // 取消默认卡。默认另外找一个卡设为默认卡。
						if (orderCust2bank.getDefaultCard() == null
								|| orderCust2bank.getDefaultCard().intValue() != 1) {
							throw new CommonException("当前卡不是默认卡，不能设为非默认卡");
						}
						List<RrzOrderCust2bank> list = rrzOrderCust2bankDao.getList(orderCust2bank);
						for (RrzOrderCust2bank rrzOrderCust2bank2 : list) {
							// 取顺序不是非当前卡号的另外一个卡，设为默认卡
							if (!rrzOrderCust2bank2.getCust2BankId().equals(cust2BankId)) {
								rrzOrderCust2bank2.setDefaultCard(1);
								rrzOrderCust2bankDao.update(rrzOrderCust2bank2);
								break;
							}
						}
					}
					orderCust2bank.setDefaultCard(rrzOrderCust2bank.getDefaultCard());
				}
				if (rrzOrderCust2bank.getCreateBy() != null) {
					orderCust2bank.setUpdateBy(rrzOrderCust2bank.getCreateBy());
				}
				orderCust2bank.setUpdateDate(new Date());
				if (rrzOrderCust2bank.getBankCardNo() != null) {
					orderCust2bank.setBankCardNo(rrzOrderCust2bank.getBankCardNo());
				}
				if (rrzOrderCust2bank.getBankCardType() != null) {
					orderCust2bank.setBankCardType(rrzOrderCust2bank.getBankCardType());
				}
				if (rrzOrderCust2bank.getName() != null) {
					orderCust2bank.setName(rrzOrderCust2bank.getName());
				}
				rrzOrderCust2bankDao.update(orderCust2bank);
				return orderCust2bank;

			} else {
				RrzOrderCust2bank orderCust2bank = rrzOrderCust2bank;
				// 如果卡号重复，则提示错误
				if (CollectionUtils.isNotEmpty(rrzOrderCust2bankDao.getBybankCardNo(orderCust2bank))) { 
					throw new CommonException("银行卡号重复");
				}
				orderCust2bank.setDelFlag("0");
				orderCust2bank.setCreateDate(new Date());
				// 如果没有银行卡，设为非默认卡
				if (CollectionUtils.isEmpty(rrzOrderCust2bankDao.getList(orderCust2bank))) {
					orderCust2bank.setDefaultCard(1);
				} else {
					orderCust2bank.setDefaultCard(0);
				}
				rrzOrderCust2bank.setCust2BankId(IdGen.uuid());
				rrzOrderCust2bankDao.insert(orderCust2bank);
				return orderCust2bank;
			}
		}
	}
	
	@Override
	public RrzOrderCust2bank getCustBank(String custBankId) {
		return rrzOrderCust2bankDao.get(custBankId);
	}

	/**
	 * 更新订单用户缓存，回滚数据库与redis关系
	 * @param orderInfo
	 * @param accounts
	 * @param integrals
	 * @see com.yryz.service.order.modules.order.service.IOrderService#refreshOrder(com.yryz.service.order.modules.order.entity.RrzOrderInfo, java.util.List, java.util.List)
	 */
	@Override
	public void refreshOrder(RrzOrderInfo orderInfo, List<RrzOrderAccountHistory> accounts,
			List<RrzOrderIntegralHistory> integrals) {
		Set<String> custIds = new HashSet<>();
		// 处理消费账户流水
		if (CollectionUtils.isNotEmpty(accounts)) { 
			for (RrzOrderAccountHistory rrzOrderAccountHistory : accounts) {
				String orderCustId = rrzOrderAccountHistory.getCustId();
				custIds.add(orderCustId);
			}
		}
		// 处理积分账户流水
		if (CollectionUtils.isNotEmpty(integrals)) { 
			for (RrzOrderIntegralHistory rrzOrderIntegralHistory : integrals) {
				String orderCustId = rrzOrderIntegralHistory.getCustId();
				custIds.add(orderCustId);
			}
		}
		
		//更新数据到缓存
		for (String custId : custIds) {
			userAccountService.updateUserAccountCache(custId);
		}
	}

	/**
	 * 更新资金用户缓存，回滚数据库与redis关系
	 * @param payInfo
	 * @see com.yryz.service.order.modules.order.service.IOrderService#refreshPay(com.yryz.service.api.order.entity.PayInfo)
	 */
	@Override
	public void refreshPay(PayInfo payInfo) {
		Set<String> custIds = new HashSet<>();
		custIds.add(payInfo.getCustId());
		for (String custId : custIds) {
			userAccountService.updateUserAccountCache(custId);
		}
	}

	/**
	 * 获取现金流水列表
	 * @param custId
	 * @param date
	 * @param orderId
	 * @param start
	 * @param limit
	 * @return
	 * @see com.yryz.service.order.modules.order.service.IOrderService#getPayInfo(java.lang.String, java.lang.String, java.lang.String, long, long)
	 */
	@Override
	public List<RrzOrderPayInfo> getPayInfo(String custId, String date, String orderId, long start, long limit) {
		if (logger.isDebugEnabled()) {
			logger.info("getPayInfo,custId:" + custId + "-date:" + date + "-start:" + start + "-limit:" + limit);
		}
		RrzOrderPayInfo rrzOrderPayInfo = new RrzOrderPayInfo();
		rrzOrderPayInfo.setCustId(custId);
		rrzOrderPayInfo.setCreateTime(DateUtils.parseDate(date));
		rrzOrderPayInfo.setOrderId(orderId);
		rrzOrderPayInfo.setStart(start);
		rrzOrderPayInfo.setLimit(limit);
		return rrzOrderPayInfoDao.getList(rrzOrderPayInfo);
	}

	/**
	 * 获取现金流水列表(web端分页)
	 * @param custId
	 * @param date
	 * @param orderId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @see com.yryz.service.order.modules.order.service.IOrderService#getPayInfoWeb(java.lang.String, java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public Page<RrzOrderPayInfo> getPayInfoWeb(String custId, String date, String orderId, int pageNo, int pageSize) {
		if (logger.isDebugEnabled()) {
			logger.info("getPayInfoWeb,custId:" + custId + "-date:" + date + "-pageNo:" + pageNo + "-pageSize:" + pageSize);
		}
		Page<RrzOrderPayInfo> page = new Page<RrzOrderPayInfo>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setOrderBy("create_time");
		page.setOrder(Page.DESC);
		
		RrzOrderPayInfo rrzOrderPayInfo = new RrzOrderPayInfo();
		rrzOrderPayInfo.setCustId(custId);
		rrzOrderPayInfo.setCreateTime(DateUtils.parseDate(date));
		rrzOrderPayInfo.setOrderId(orderId);
		
		com.github.pagehelper.Page<RrzOrderIntegralHistory> pageHelp = PageHelper.startPage(page.getPageNo(), page.getPageSize());
		List<RrzOrderPayInfo> list =  rrzOrderPayInfoDao.getListWeb(rrzOrderPayInfo);
		page.setResult(list);
		page.setTotalCount(pageHelp.getTotal());
		return page;
	}

	/**
	 * 创建预处理订单
	 * @param orderVO
	 * @return
	 * @see com.yryz.service.order.modules.order.service.IOrderService#createOrder(com.yryz.service.order.modules.order.entity.RrzOrderVO)
	 */
	@Override
	public RrzOrderVO createOrder(RrzOrderVO orderVO) {
		if(orderVO == null){
			throw new CommonException("orderVO is required");
		}
		RrzOrderInfo orderInfo = orderVO.getOrderInfo();
		if(orderInfo == null || StringUtils.isEmpty(orderInfo.getOrderId()) || StringUtils.isEmpty(orderInfo.getCustId()) || StringUtils.isEmpty(orderInfo.getCallback())){
			throw new CommonException("orderInfo, custId , orderId ,callback  is required");
		}
		orderInfo.setOrderState(RrzOrderInfo.ORDER_STATE_OFF);
		orderInfo.setCreateTime(new Date());
		orderVO.setOrderInfo(orderInfo);
		boolean flag = false;
//		if (!(checkAccountOrder(orderVO.getAccounts()) && checkIntegralOrder(orderVO.getIntegrals()))) { // 先检查是否存在余额不足的问题
//			throw new ServiceException(ServiceException.CODE_SYS_ERROR,"用户余额不足");
//		}
		for (int i = 0; i < SAVE_LIMIT; i++) { 
			if(rrzOrderInfoRedis.saveOrderVO(orderVO)){
				flag = true;
				break;
			} else {
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		if(flag){
			rrzOrderInfoDao.insert(orderInfo);
			return orderVO;
		} else {
			return null;
		}
	}

	/**
	 * 执行订单
	 * @param orderId
	 * @param custId
	 * @param password
	 * @return
	 * @see com.yryz.service.order.modules.order.service.IOrderService#executeOrder(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Response<?> executeOrder(String orderId, String custId, String password) {
		RrzOrderVO orderVO = rrzOrderInfoRedis.getOrderVO(orderId);
		if(orderVO == null){
			return ResponseUtils.returnException(new CommonException("无此订单信息或者订单已经执行"));
		}
		Response<?> return1 = executeOrder(orderVO.getOrderInfo(), orderVO.getAccounts(), orderVO.getIntegrals(), custId, password, null);
		if(return1 != null && return1.success()){
			notifyQueue.addOrderInfoNotify(orderVO.getOrderInfo());
		} else {
			logger.warn("订单处理失败...orderId: " + orderId + "-custId:" + custId + "-password" + password);
		}
		return return1;
	}

	/**
	 * 获取订单详情
	 * @param orderId
	 * @return
	 * @see com.yryz.service.order.modules.order.service.IOrderService#getOrderInfo(java.lang.String)
	 */
	@Override
	public RrzOrderInfo getOrderInfo(String orderId) {
		return rrzOrderInfoDao.get(orderId);
	}

}