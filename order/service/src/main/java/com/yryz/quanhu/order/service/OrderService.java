/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月18日
 * Id: OrderService.java, 2018年1月18日 上午10:59:26 yehao
 */
package com.yryz.quanhu.order.service;

import java.util.List;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.order.entity.RrzOrderAccountHistory;
import com.yryz.quanhu.order.entity.RrzOrderCust2bank;
import com.yryz.quanhu.order.entity.RrzOrderInfo;
import com.yryz.quanhu.order.entity.RrzOrderIntegralHistory;
import com.yryz.quanhu.order.entity.RrzOrderPayInfo;
import com.yryz.quanhu.order.entity.RrzOrderVO;
import com.yryz.quanhu.order.utils.Page;
import com.yryz.quanhu.order.vo.PayInfo;
import com.yryz.quanhu.order.vo.WithdrawCashDto;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月18日 上午10:59:26
 * @Description 订单管理
 */
public interface OrderService {
	
	/**
	 * 同步执行订单业务
	 * @param orderInfo 订单信息
	 * @param accounts	消费资金处理详情列表
	 * @param integrals 积分资金处理详情列表
	 * @param custId	客户ID
	 * @param payPassword	支付密码
	 * @param remark	备注信息
	 * @return
	 */
	void executeOrder(RrzOrderInfo orderInfo, List<RrzOrderAccountHistory> accounts, List<RrzOrderIntegralHistory> integrals,
			String custId, String payPassword, String remark);
	
	/**
	 * 执行充值/提现等现金订单
	 * @param rrzOrderPayInfo
	 * @param custId
	 * @param payPassword
	 * @param remark
	 * @return
	 */
	RrzOrderPayInfo executePayInfo(RrzOrderPayInfo rrzOrderPayInfo ,String custId, String payPassword, String remark);
	
	/**
	 * 我的银行卡列表
	 * 
	 * @param custId
	 * @return
	 */
	List<RrzOrderCust2bank> getCustBanks(String custId);
	
	/**
	 * 处理我的银行卡信息
	 * 
	 * @param rrzOrderCust2bank
	 * @param type
	 */
	RrzOrderCust2bank dealCustBank(RrzOrderCust2bank rrzOrderCust2bank, int type);
	
	/**
	 * 获取银行信息
	 * 
	 * @param custBankId
	 * @return
	 */
	RrzOrderCust2bank getCustBank(String custBankId);
	
	/**
	 * 回滚相关用户的账务数据。重新一致化mysql和redis数据
	 * 
	 * @param orderInfo
	 * @param accounts
	 * @param integrals
	 */
	void refreshOrder(RrzOrderInfo orderInfo, List<RrzOrderAccountHistory> accounts, List<RrzOrderIntegralHistory> integrals);
	
	/**
	 * 刷新订单充值相关用户
	 * 
	 * @param payInfo
	 */
	void refreshPay(PayInfo payInfo);

	/**
	 * 获取支付订单详情
	 * @param orderId
	 * @return
	 */
	RrzOrderPayInfo getPayInfo(String orderId);

	/**
	 * 获取提现申请订单列表
	 * @param withdrawCashDto
	 * @return
	 */
	PageList<RrzOrderPayInfo> getWithdrawCashPage(WithdrawCashDto withdrawCashDto);
	
	/**
	 * 获取充值订单列表
	 * 
	 * @param custId
	 * @param date
	 * @param orderId
	 * @param start
	 * @param limit
	 * @return
	 */
	List<RrzOrderPayInfo> getPayInfoList(String custId, String date, String orderId, long start, long limit);
	
	/**
	 * 获取充值订单列表
	 * 
	 * @param custId
	 * @param date
	 * @param orderId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Page<RrzOrderPayInfo> getPayInfoWeb(String custId, String date, String orderId, int pageNo, int pageSize);
	
	/**
	 * 创建预处理订单
	 * @param orderVO
	 * @return
	 */
	RrzOrderVO createOrder(RrzOrderVO orderVO);
	
	/**
	 * 执行订单
	 * @param orderId
	 * @param custId
	 * @param password
	 * @return
	 */
	void executeOrder(String orderId ,String custId ,String password);
	
	/**
	 * 查询订单详情
	 * @param orderId
	 * @return
	 */
	RrzOrderInfo getOrderInfo(String orderId);

}
