/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * Created on 2018年1月18日
 * Id: OrderProvider.java, 2018年1月18日 上午9:36:29 yehao
 */
package com.yryz.quanhu.order.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.exception.MysqlOptException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.order.api.OrderApi;
import com.yryz.quanhu.order.entity.*;
import com.yryz.quanhu.order.exception.CommonException;
import com.yryz.quanhu.order.service.*;
import com.yryz.quanhu.order.utils.Page;
import com.yryz.quanhu.order.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	 */
	@Override
	public Response<?> executeOrder(OrderInfo orderInfo, List<AccountOrder> accounts, List<IntegralOrder> integrals,
			String custId, String payPassword, String remark) {
		logger.info("executeOrder start orderId:" + orderInfo.getOrderId());
		logger.info("executeOrder...orderInfo:" + GsonUtils.parseJson(orderInfo) + "...accounts:"
				+ GsonUtils.parseJson(accounts) + "...integrals:" + GsonUtils.parseJson(integrals));
		RrzOrderInfo rrzOrderInfo = GsonUtils.parseObj(orderInfo, RrzOrderInfo.class);
		List<RrzOrderAccountHistory> rrzOrderAccountHistories = GsonUtils.parseList(accounts, RrzOrderAccountHistory.class);
		List<RrzOrderIntegralHistory> rrzOrderIntegralHistories = GsonUtils.parseList(integrals, RrzOrderIntegralHistory.class);
		try {
			orderService.executeOrder(rrzOrderInfo, rrzOrderAccountHistories, rrzOrderIntegralHistories, custId,
					payPassword, remark);
			logger.info("executeOrder end orderId:" + orderInfo.getOrderId());
			return ResponseUtils.returnSuccess();
		} catch (Exception e) {
			// 出现任何异常，将重新刷新缓存，以防出现数据不一致的问题
			orderService.refreshOrder(rrzOrderInfo, rrzOrderAccountHistories, rrzOrderIntegralHistories);
			logger.error("executeOrder error", e);
			return ResponseUtils.returnException(e);
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
	 */
	@Override
	public Response<UserAccount> getUserAccount(String custId) {
		try {
			RrzOrderUserAccount account = new RrzOrderUserAccount();
			account.setCustId(custId);
			account = userAccountService.getUserAccount(account);
			UserAccount userAccount = GsonUtils.json2Obj(GsonUtils.parseJson(account), UserAccount.class);
			return ResponseUtils.returnObjectSuccess(userAccount);
		}catch (Exception e) {
			logger.warn("getUserAccount Exception", e);
			return ResponseUtils.returnException(e);
		}
	}

	/**
	 * 修改账户信息
	 * @param account
	 * @return
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
	 */
	@Override
	public Response<?> checkUserPayPassword(String custId, String payPassword) {
		logger.info("收到支付密码验证需求，custId：" + custId + ",payPassword:" + payPassword);
		try {
			return userPhyService.checkPayPassword(custId, payPassword);
		} catch (Exception e) {
			return ResponseUtils.returnException(e);
		}
	}

	/**
	 * 处理安全信息
	 * @param userPhy
	 * @return
	 */
	@Override
	public Response<?> dealUserPhy(UserPhy userPhy) {
		try {
			if (StringUtils.isEmpty(userPhy.getCustId())){
				return ResponseUtils.returnCommonException("客户ID不能为空");
			}
			RrzOrderUserPhy rrzOrderUserPhy = GsonUtils.parseObj(userPhy, RrzOrderUserPhy.class);
			return userPhyService.dealUserPhy(rrzOrderUserPhy, userPhy.getOldPassword());
		} catch (Exception e) {
			logger.warn("dealUserPhy Exception", e);
			return ResponseUtils.returnException(e);
		}
	}

	/**
	 * 获取物理信息
	 * @param custId
	 * @return
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

	@Override
	public Response<PayInfo> getPayInfo(String orderId) {
		try {
			return ResponseUtils.returnObjectSuccess(GsonUtils.parseObj(orderService.getPayInfo(orderId),PayInfo.class));
		}catch (Exception e){
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<PageList<PayInfo>> getWithdrawCashPage(WithdrawCashDto withdrawCashDto) {
		try {
			PageList<RrzOrderPayInfo> originPage = orderService.getWithdrawCashPage(withdrawCashDto);
			PageList<PayInfo> pageList = new PageList<>();
			pageList.setCurrentPage(withdrawCashDto.getCurrentPage());
			pageList.setPageSize(withdrawCashDto.getPageSize());
			pageList.setCount(originPage.getCount());
			pageList.setEntities(GsonUtils.parseList(originPage.getEntities(), PayInfo.class));
			return ResponseUtils.returnObjectSuccess(pageList);
		}catch (Exception e){
			return ResponseUtils.returnException(e);
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
	 */
	@Override
	public Response<List<PayInfo>> getPayInfoList(String custId, String date, String orderId, int type, long start,
			long limit) {
		List<RrzOrderPayInfo> list = orderService.getPayInfoList(custId, date, orderId, start, limit);
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
	 */
	@Override
	public Response<Map<String, String>> getUserIntegralSum(String custId) {
		return ResponseUtils.returnObjectSuccess(orderIntegralHistoryService.getUserIntegralSum(custId));
	}

	/**
	 * 初始化用户统计信息
	 * @param custId
	 * @return
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
	 */
	@Override
	public Response<OrderInfo> getOrderInfo(String orderId) {
		RrzOrderInfo rrzOrderInfo = orderService.getOrderInfo(orderId);
		return ResponseUtils.returnObjectSuccess(GsonUtils.parseObj(rrzOrderInfo, OrderInfo.class));
	}

	@Override
	public Response<Map<Long, Long>> getUserTotalIntegral(List<Long> userIdList) {
		try {
		    return ResponseUtils.returnObjectSuccess(orderIntegralHistoryService.getUserTotalIntegral(userIdList));
		}catch (Exception e){
			return ResponseUtils.returnException(e);
		}
	}

}