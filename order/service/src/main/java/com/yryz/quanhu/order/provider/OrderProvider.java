/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月18日
 * Id: OrderProvider.java, 2018年1月18日 上午9:36:29 yehao
 */
package com.yryz.quanhu.order.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.exception.MysqlOptException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.order.api.OrderApi;
import com.yryz.quanhu.order.entity.RrzOrderAccountHistory;
import com.yryz.quanhu.order.entity.RrzOrderCust2bank;
import com.yryz.quanhu.order.entity.RrzOrderInfo;
import com.yryz.quanhu.order.entity.RrzOrderIntegralHistory;
import com.yryz.quanhu.order.entity.RrzOrderPayInfo;
import com.yryz.quanhu.order.entity.RrzOrderUserAccount;
import com.yryz.quanhu.order.entity.RrzOrderUserPhy;
import com.yryz.quanhu.order.exception.CommonException;
import com.yryz.quanhu.order.exception.SourceNotEnoughException;
import com.yryz.quanhu.order.service.OrderAccountHistoryService;
import com.yryz.quanhu.order.service.OrderIntegralHistoryService;
import com.yryz.quanhu.order.service.OrderService;
import com.yryz.quanhu.order.service.UserAccountService;
import com.yryz.quanhu.order.service.UserPhyService;
import com.yryz.quanhu.order.utils.Page;
import com.yryz.quanhu.order.vo.AccountOrder;
import com.yryz.quanhu.order.vo.CustBank;
import com.yryz.quanhu.order.vo.IntegralOrder;
import com.yryz.quanhu.order.vo.OrderInfo;
import com.yryz.quanhu.order.vo.OrderListVo;
import com.yryz.quanhu.order.vo.PayInfo;
import com.yryz.quanhu.order.vo.UserAccount;
import com.yryz.quanhu.order.vo.UserPhy;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月18日 上午9:36:29
 * @Description 订单API接口实现
 */
@Service(interfaceClass=OrderApi.class)
public class OrderProvider implements OrderApi {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private UserPhyService userPhyService;
	
	@Autowired
	private OrderIntegralHistoryService orderIntegralHistoryService;
	
	@Autowired
	private OrderAccountHistoryService orderAccountHistoryService;

//	@Autowired
//	private PushManager pushService;

	/**
	 * 执行订单
	 * @param orderInfo
	 * @param accounts
	 * @param integrals
	 * @param custId
	 * @param payPassword
	 * @param remark
	 * @return
	 * @see com.yryz.quanhu.order.api.OrderApi#executeOrder(com.yryz.quanhu.order.vo.OrderInfo, java.util.List, java.util.List, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Response<?> executeOrder(OrderInfo orderInfo, List<AccountOrder> accounts, List<IntegralOrder> integrals,
			String custId, String payPassword, String remark) {
		logger.info("executeOrder start orderId:" + orderInfo.getOrderId());
		logger.info("executeOrder...orderInfo:" + GsonUtils.parseJson(orderInfo) + "...accounts:"
				+ GsonUtils.parseJson(accounts) + "...integrals:" + GsonUtils.parseJson(integrals));
		logger.info("executeOrder end orderId:" + orderInfo.getOrderId());

		RrzOrderInfo rrzOrderInfo = (RrzOrderInfo) GsonUtils.parseObj(orderInfo, RrzOrderInfo.class);
		List<RrzOrderAccountHistory> rrzOrderAccountHistories = (List<RrzOrderAccountHistory>) GsonUtils
				.parseList(accounts, RrzOrderAccountHistory.class);
		List<RrzOrderIntegralHistory> rrzOrderIntegralHistories = (List<RrzOrderIntegralHistory>) GsonUtils
				.parseList(integrals, RrzOrderIntegralHistory.class);
		try {
			return orderService.executeOrder(rrzOrderInfo, rrzOrderAccountHistories, rrzOrderIntegralHistories, custId,
					payPassword, remark);
		} catch (MysqlOptException e) {
			// 出现任何异常，将重新刷新缓存，以防出现数据不一致的问题
			orderService.refreshOrder(rrzOrderInfo, rrzOrderAccountHistories, rrzOrderIntegralHistories);
			logger.warn("executeOrder error ...orderInfo:" + GsonUtils.parseJson(orderInfo) + "...accounts:"
					+ GsonUtils.parseJson(accounts) + "...integrals:" + GsonUtils.parseJson(integrals));
			return ResponseUtils.returnException(new CommonException("数据库异常"));
		} catch (SourceNotEnoughException e) {
			// 出现任何异常，将重新刷新缓存，以防出现数据不一致的问题
			orderService.refreshOrder(rrzOrderInfo, rrzOrderAccountHistories, rrzOrderIntegralHistories);
			return ResponseUtils.returnException(new CommonException("余额不足"));
		} catch (Exception e) {
			// 出现任何异常，将重新刷新缓存，以防出现数据不一致的问题
			orderService.refreshOrder(rrzOrderInfo, rrzOrderAccountHistories, rrzOrderIntegralHistories);
			e.printStackTrace();
			logger.warn("unknown Exception", e);
			logger.warn("executeOrder error ...orderInfo:" + GsonUtils.parseJson(orderInfo) + "...accounts:"
					+ GsonUtils.parseJson(accounts) + "...integrals:" + GsonUtils.parseJson(integrals));
			return ResponseUtils.returnException(new CommonException("未知异常"));
		}
	}

	/**
	 * 执行资金订单
	 * @param payInfo
	 * @param custId
	 * @param payPassword
	 * @param remark
	 * @return
	 * @see com.yryz.quanhu.order.api.OrderApi#executePay(com.yryz.quanhu.order.vo.PayInfo, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Response<PayInfo> executePay(PayInfo payInfo, String custId, String payPassword, String remark) {
		logger.info("executePay...payInfo:" + GsonUtils.parseJson(payInfo) + "...custId:" + custId + "...payPassword:"
				+ payPassword + "...remark:" + remark);
		try {
			RrzOrderPayInfo orderPayInfo = (RrzOrderPayInfo) GsonUtils.parseObj(payInfo, RrzOrderPayInfo.class);
			orderPayInfo = orderService.executePayInfo(orderPayInfo, custId, payPassword, remark);
//			// 生效的充值订单推送消息
//			if (orderPayInfo != null && orderPayInfo.getProductType() != null
//					&& orderPayInfo.getProductType().intValue() == ProductTypeEnum.RECHARGE_TYPE
//					&& StringUtils.isNotEmpty(orderPayInfo.getCustId()) && orderPayInfo.getOrderState().intValue() == 1){
//				pushService.charge(orderPayInfo.getCustId(), orderPayInfo.getCost());
//			}
			return ResponseUtils.returnObjectSuccess(GsonUtils.parseObj(orderPayInfo, PayInfo.class));
		} catch (MysqlOptException e) {
			// 出现任何异常，将重新刷新缓存缓存数据，以防出现数据不一致的问题
			orderService.refreshPay(payInfo);
			return ResponseUtils.returnException(new CommonException("数据库异常"));
		} catch (Exception e) {
			// 出现任何异常，将重新刷新缓存缓存数据，以防出现数据不一致的问题
			orderService.refreshPay(payInfo);
			e.printStackTrace();
			logger.warn("unknown Exception", e);
			logger.warn("executePay error...payInfo:" + GsonUtils.parseJson(payInfo) + "...custId:" + custId
					+ "...payPassword:" + payPassword + "...remark:" + remark);
			return ResponseUtils.returnException(new CommonException("未知异常"));
		}
	}

	/**
	 * 获取订单列表
	 * @param custId
	 * @param date
	 * @param productTypeId
	 * @param type
	 * @param start
	 * @param limit
	 * @return
	 * @see com.yryz.quanhu.order.api.OrderApi#getOrderList(java.lang.String, java.lang.String, java.lang.Integer, int, long, long)
	 */
	@Override
	public Response<List<OrderListVo>> getOrderList(String custId, String date, Integer productTypeId, int type,
			long start, long limit) {
		try {
			if (type == 1) {
				return ResponseUtils.returnListSuccess(GsonUtils.parseList(
						orderAccountHistoryService.getOrderAccountList(custId, date, productTypeId, start, limit),
						OrderListVo.class));
			} else if (type == 2) {
				return ResponseUtils.returnListSuccess(GsonUtils.parseList(
						orderIntegralHistoryService.getOrderIntegralList(custId, date, productTypeId, start, limit),
						OrderListVo.class));
			}
			return null;
		} catch (MysqlOptException e) {
			return null;
		} catch (Exception e) {
			logger.warn("unknown Exception", e);
			return null;
		}
	}

	/**
	 * 查询账户信息
	 * @param custId
	 * @return
	 * @see com.yryz.quanhu.order.api.OrderApi#getUserAccount(java.lang.String)
	 */
	@Override
	public Response<UserAccount> getUserAccount(String custId) {
		try {
			RrzOrderUserAccount account = new RrzOrderUserAccount();
			account.setCustId(custId);
			account = userAccountService.getUserAccount(account);
			UserAccount userAccount = (UserAccount) GsonUtils.json2Obj(GsonUtils.parseJson(account), UserAccount.class);
			return ResponseUtils.returnObjectSuccess(userAccount);
		} catch (MysqlOptException e) {
			return null;
		} catch (Exception e) {
			logger.warn("unknown Exception", e);
			return null;
		}
	}

	/**
	 * 修改账户信息
	 * @param account
	 * @return
	 * @see com.yryz.quanhu.order.api.OrderApi#dealUserAccount(com.yryz.quanhu.order.vo.UserAccount)
	 */
	@Override
	public Response<?> dealUserAccount(UserAccount account) {
		try {
			RrzOrderUserAccount rrzOrderUserAccount = (RrzOrderUserAccount) GsonUtils.parseObj(account,
					RrzOrderUserAccount.class);
			userAccountService.executeRrzOrderUserAccount(rrzOrderUserAccount);
			return ResponseUtils.returnSuccess();
		} catch (Exception e) {
			logger.warn("unknown Exception", e);
		}
		return ResponseUtils.returnException(new CommonException("执行失败"));
	}

	/**
	 * 验证密码
	 * @param userPhy
	 * @return
	 * @see com.yryz.quanhu.order.api.OrderApi#checkSecurityProblem(com.yryz.quanhu.order.vo.UserPhy)
	 */
	@Override
	public Response<?> checkSecurityProblem(UserPhy userPhy) {
		logger.info("收到安全信息密码验证需求，custId：" + userPhy.getCustId());
		try {
			RrzOrderUserPhy rrzOrderUserPhy = (RrzOrderUserPhy) GsonUtils.parseObj(userPhy, RrzOrderUserPhy.class);

			return userPhyService.checkSecurityProblem(rrzOrderUserPhy);
		} catch (MysqlOptException e) {
			return ResponseUtils.returnException(new CommonException("数据提交失败"));
		} catch (Exception e) {
			logger.warn("unknown Exception", e);
			return ResponseUtils.returnException(new CommonException("未知异常"));
		}
	}

	/**
	 * 锁定账户
	 * @param custId
	 * @return
	 * @see com.yryz.quanhu.order.api.OrderApi#lockUserAccount(java.lang.String)
	 */
	@Override
	public Response<?> lockUserAccount(String custId) {
		try {
			userAccountService.lockUserAccount(custId);
			return ResponseUtils.returnSuccess();
		} catch (MysqlOptException e) {
			return ResponseUtils.returnException(new CommonException("数据提交失败"));
		} catch (Exception e) {
			logger.warn("unknown Exception", e);
			return ResponseUtils.returnException(new CommonException("未知异常"));
		}
	}

	/**
	 * 解锁系统账户
	 * @param custId
	 * @return
	 * @see com.yryz.quanhu.order.api.OrderApi#unlockUserAccount(java.lang.String)
	 */
	@Override
	public Response<?> unlockUserAccount(String custId) {
		try {
			userAccountService.unlockUserAccount(custId);
			return ResponseUtils.returnSuccess();
		} catch (MysqlOptException e) {
			return ResponseUtils.returnException(new CommonException("数据提交失败"));
		} catch (Exception e) {
			logger.warn("unknown Exception", e);
			return ResponseUtils.returnException(new CommonException("未知异常"));
		}
	}

	/**
	 * 检查支付密码
	 * @param custId
	 * @param payPassword
	 * @return
	 * @see com.yryz.quanhu.order.api.OrderApi#checkUserPayPassword(java.lang.String, java.lang.String)
	 */
	@Override
	public Response<?> checkUserPayPassword(String custId, String payPassword) {
		logger.info("收到支付密码验证需求，custId：" + custId + ",payPassword:" + payPassword);
		try {
			return userPhyService.checkPayPassword(custId, payPassword);
		} catch (MysqlOptException e) {
			return ResponseUtils.returnCommonException("数据提交失败");
		} catch (Exception e) {
			logger.warn("unknown Exception", e);
			return ResponseUtils.returnCommonException("未知异常");
		}
	}

	/**
	 * 处理安全信息
	 * @param userPhy
	 * @return
	 * @see com.yryz.quanhu.order.api.OrderApi#dealUserPhy(com.yryz.quanhu.order.vo.UserPhy)
	 */
	@Override
	public Response<?> dealUserPhy(UserPhy userPhy) {
		try {
			if (StringUtils.isEmpty(userPhy.getCustId())){
				return ResponseUtils.returnCommonException("客户ID不能为空");
			}
			RrzOrderUserPhy rrzOrderUserPhy = (RrzOrderUserPhy) GsonUtils.parseObj(userPhy, RrzOrderUserPhy.class);
			return userPhyService.dealUserPhy(rrzOrderUserPhy, userPhy.getOldPassword());
		} catch (Exception e) {
			logger.warn("unknown Exception", e);
			return ResponseUtils.returnCommonException("未知错误");
		}
	}

	/**
	 * 获取物理信息
	 * @param custId
	 * @return
	 * @see com.yryz.quanhu.order.api.OrderApi#getUserPhy(java.lang.String)
	 */
	@Override
	public Response<UserPhy> getUserPhy(String custId) {
		try {
			RrzOrderUserPhy orderUserPhy = userPhyService.getUserPhy(custId);
			return ResponseUtils.returnObjectSuccess(GsonUtils.parseObj(orderUserPhy, UserPhy.class));
		} catch (MysqlOptException e) {
			return ResponseUtils.returnCommonException("数据库操作异常");
		} catch (Exception e) {
			logger.warn("unknown Exception", e);
			return ResponseUtils.returnCommonException("未知错误");
		}
	}

	/**
	 * 获取用户银行卡信息
	 * @param custBankId
	 * @return
	 * @see com.yryz.quanhu.order.api.OrderApi#getCustBankById(java.lang.String)
	 */
	@Override
	public Response<CustBank> getCustBankById(String custBankId) {
		try {
			RrzOrderCust2bank cust2bank = orderService.getCustBank(custBankId);
			return ResponseUtils.returnObjectSuccess(GsonUtils.parseObj(cust2bank, CustBank.class));
		} catch (MysqlOptException e) {
			return ResponseUtils.returnCommonException("数据库操作异常");
		} catch (Exception e) {
			logger.warn("unknown Exception", e);
			return ResponseUtils.returnCommonException("未知异常");
		}
	}

	/**
	 * 返回绑定的银行卡
	 * @param custId
	 * @return
	 * @see com.yryz.quanhu.order.api.OrderApi#getCustBanks(java.lang.String)
	 */
	@Override
	public Response<List<CustBank>> getCustBanks(String custId) {
		try {
			List<RrzOrderCust2bank> list = orderService.getCustBanks(custId);
			return ResponseUtils.returnListSuccess(GsonUtils.parseList(list, CustBank.class));
		} catch (MysqlOptException e) {
			return ResponseUtils.returnCommonException("数据库操作异常");
		} catch (Exception e) {
			logger.warn("unknown Exception", e);
			return ResponseUtils.returnCommonException("未知异常");
		}
	}

	/**
	 * 编辑银行卡信息
	 * @param custBank
	 * @param type
	 * @return
	 * @see com.yryz.quanhu.order.api.OrderApi#dealCustBank(com.yryz.quanhu.order.vo.CustBank, int)
	 */
	@Override
	public Response<?> dealCustBank(CustBank custBank, int type) {
		try {
			RrzOrderCust2bank rrzOrderCust2bank = (RrzOrderCust2bank) GsonUtils.parseObj(custBank,
					RrzOrderCust2bank.class);
			RrzOrderCust2bank cust2bank = orderService.dealCustBank(rrzOrderCust2bank, type);
			return ResponseUtils.returnObjectSuccess(GsonUtils.parseObj(cust2bank, CustBank.class));
		} catch (CommonException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.warn("unknown Exception", e);
			return ResponseUtils.returnCommonException("未知异常");
		}
	}

	/**
	 * 获取订单列表
	 * @param custId
	 * @param orderDesc
	 * @param startDate
	 * @param endDate
	 * @param productType
	 * @param type
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @see com.yryz.quanhu.order.api.OrderApi#getOrderListWeb(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, int, int, int)
	 */
	@Override
	public Response<PageList<OrderListVo>> getOrderListWeb(String custId, String orderDesc, String startDate,
			String endDate, Integer productType, int type, int pageNo, int pageSize) {
		try {
			PageList<OrderListVo> listPage = new PageList<>();
			listPage.setCurrentPage(pageNo);
			listPage.setPageSize(pageSize);
			Map<String, Object> params = new HashMap<>(5);
			params.put("custId", custId);
			params.put("orderDesc", orderDesc);
			params.put("startDate", startDate);
			params.put("endDate", endDate);
			params.put("productType", productType);
			if (type == 1) {
				Page<RrzOrderAccountHistory> page = orderAccountHistoryService.getOrderAccountListWeb(params, pageNo, pageSize);
				listPage.setCount(page.getTotalCount());
				if (page.getResult() != null) {
					List<RrzOrderAccountHistory> histories = page.getResult();
					List<OrderListVo> list = GsonUtils.parseList(histories,
							OrderListVo.class);
					listPage.setEntities(list);
				}
			} else if (type == 2) {
				Page<RrzOrderIntegralHistory> page = orderIntegralHistoryService.getOrderIntegralListWeb(params, pageNo, pageSize);
				listPage.setCount(page.getTotalCount());
				if (page.getResult() != null) {
					List<RrzOrderIntegralHistory> histories = page.getResult();
					List<OrderListVo> list = GsonUtils.parseList(histories,
							OrderListVo.class);
					listPage.setEntities(list);
				}
			}
			return ResponseUtils.returnObjectSuccess(listPage);
		} catch (MysqlOptException e) {
			return ResponseUtils.returnCommonException("数据库操作异常");
		} catch (Exception e) {
			logger.warn("unknown Exception", e);
			return ResponseUtils.returnCommonException("未知异常");
		}
	}

	/**
	 * 获取资金信息
	 * @param custId
	 * @param date
	 * @param orderId
	 * @param type
	 * @param start
	 * @param limit
	 * @return
	 * @see com.yryz.quanhu.order.api.OrderApi#getPayInfo(java.lang.String, java.lang.String, java.lang.String, int, long, long)
	 */
	@Override
	public Response<List<PayInfo>> getPayInfo(String custId, String date, String orderId, int type, long start,
			long limit) {
		List<RrzOrderPayInfo> list = orderService.getPayInfo(custId, date, orderId, start, limit);
		return ResponseUtils.returnListSuccess(GsonUtils.parseList(list, PayInfo.class));
	}

	/**
	 * 获取资金信息(web端)
	 * @param custId
	 * @param date
	 * @param orderId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @see com.yryz.quanhu.order.api.OrderApi#getPayInfoWeb(java.lang.String, java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public Response<PageList<PayInfo>> getPayInfoWeb(String custId, String date, String orderId, int pageNo,
			int pageSize) {
		PageList<PayInfo> listpage = new PageList<>();
		listpage.setCurrentPage(pageNo);
		listpage.setPageSize(pageSize);
		try {
			Page<RrzOrderPayInfo> page = orderService.getPayInfoWeb(custId, date, orderId, pageNo, pageSize);
			listpage.setCount(page.getTotalCount());
			if (page.getResult() != null) {
				List<RrzOrderPayInfo> infos = page.getResult();
				List<PayInfo> list = (List<PayInfo>) GsonUtils.parseList(infos, PayInfo.class);
				listpage.setEntities(list);
			}
			return ResponseUtils.returnObjectSuccess(listpage);
		} catch (MysqlOptException e) {
			return ResponseUtils.returnCommonException("数据库操作异常");
		} catch (Exception e) {
			logger.warn("unknown Exception", e);
			return ResponseUtils.returnCommonException("未知异常");
		}
	}

	/**
	 * 执行统计信息
	 * @return
	 * @see com.yryz.quanhu.order.api.OrderApi#integralSum()
	 */
	@Override
	public Response<?> integralSum() {
		orderIntegralHistoryService.integralSum();
		return ResponseUtils.returnSuccess();
	}

	/**
	 * 获取用户统计信息
	 * @param custId
	 * @return
	 * @see com.yryz.quanhu.order.api.OrderApi#getUserIntegralSum(java.lang.String)
	 */
	@Override
	public Response<Map<String, String>> getUserIntegralSum(String custId) {
		return ResponseUtils.returnObjectSuccess(orderIntegralHistoryService.getUserIntegralSum(custId));
	}

	/**
	 * 初始化用户统计信息
	 * @param custId
	 * @return
	 * @see com.yryz.quanhu.order.api.OrderApi#integralSumByCustId(java.lang.String)
	 */
	@Override
	public Response<?> integralSumByCustId(String custId) {
		orderIntegralHistoryService.integralSum(custId);
		return ResponseUtils.returnSuccess();
	}

	/**
	 * 更新缓存
	 * @param custId
	 * @return
	 * @see com.yryz.quanhu.order.api.OrderApi#updateUserCache(java.lang.String)
	 */
	@Override
	public Response<?> updateUserCache(String custId) {
		userAccountService.updateUserAccountCache(custId);
		return ResponseUtils.returnSuccess();
	}

	/**
	 * 获取订单信息
	 * @param orderId
	 * @return
	 * @see com.yryz.quanhu.order.api.OrderApi#getOrderInfo(java.lang.String)
	 */
	@Override
	public Response<OrderInfo> getOrderInfo(String orderId) {
		RrzOrderInfo rrzOrderInfo = orderService.getOrderInfo(orderId);
		return ResponseUtils.returnObjectSuccess(GsonUtils.parseObj(rrzOrderInfo, OrderInfo.class));
	}

}