/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月17日
 * Id: OrderApi.java, 2018年1月17日 下午8:04:54 yehao
 */
package com.yryz.quanhu.order.api;

import java.util.List;
import java.util.Map;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
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
 * @date 2018年1月17日 下午8:04:54
 * @Description 订单接口服务
 */
public interface OrderApi {
	
	/**
	 * 执行消费资金订单数据(非现金订单)  系统平台ID：1000（固定）
	 * @param orderInfo 订单描述信息
	 * @param accounts 资金数据交割列表
	 * @param integrals 积分数据交割列表
	 * @param custId 用户ID(需验证密码的客户ID)
	 * @param payPassword 支付密码
	 * @param remark 备注信息
	 * @return
	 */
	public Response<?> executeOrder(OrderInfo orderInfo ,List<AccountOrder> accounts ,List<IntegralOrder> integrals ,String custId ,String payPassword, String remark);
	
	/**
	 * 执行充值、提现、手续费订单。涉及资金的业务(现金订单)
	 * @param payInfo 执行详情
	 * @param custId 用户ID(需验证密码的客户ID)
	 * @param payPassword 支付密码
	 * @param remark 备注信息
	 * @return
	 */
	public Response<PayInfo> executePay(PayInfo payInfo ,String custId ,String payPassword ,String remark);
	
	/**
	 * 订单流水查询
	 * @param custId
	 * @param date
	 * @param productTypeId
	 * @param type 1,消费流水；2，积分流水
	 * @param start
	 * @param limit
	 * @return
	 */
	public Response<List<OrderListVo>> getOrderList(String custId ,String date ,Integer productTypeId ,int type , long start , long limit);
	
	/**
	 * 获取用户账户信息
	 * @param custId 用户ID
	 * @return
	 */
	public Response<UserAccount> getUserAccount(String custId);
	
	/**
	 * 更新用户账户状态
	 * @param account 用户账户信息
	 * @return
	 */
	public Response<?> dealUserAccount(UserAccount account);
	
	/**
	 * 验证安全信息
	 * @param userPhy
	 * @return
	 */
	public Response<?> checkSecurityProblem(UserPhy userPhy);
	
	/**
	 * 冻结支付
	 * @param custId
	 * @return
	 */
	public Response<?> lockUserAccount(String custId);
	
	/**
	 * 解冻支付
	 * @param custId
	 * @return
	 */
	public Response<?> unlockUserAccount(String custId);
	
	/**
	 * 检查用户支付密码是否正确
	 * 
	 * @param custId
	 * @param payPassword
	 * @return
	 */
	public Response<?> checkUserPayPassword(String custId ,String payPassword); 

	
	/**
	 * 更新用户实名认证信息
	 * @param userPhy 用户实名认证信息
	 */
	public Response<?> dealUserPhy(UserPhy userPhy);
	
	/**
	 * 获取用户实名认证信息
	 * @param custId 用户ID
	 * @return
	 */
	public Response<UserPhy> getUserPhy(String custId);
	
	/**
	 * 获取用户银行卡数据ID
	 * @param custBankId
	 * @return
	 */
	public Response<CustBank> getCustBankById(String custBankId);
	
	/**
	 * 获取用户绑定银行列表
	 * @param userId
	 * @return
	 */
	public Response<List<CustBank>> getCustBanks(String custId);
	
	/**
	 * 处理用户绑定银行信息
	 * @param custBank 用户银行卡信息主体
	 * @param type 1，保存/更新；0，删除
	 */
	public Response<?> dealCustBank(CustBank custBank , int type);
	
	/**
	 * pageSize
	 * 
	 * @param custId
	 * @param orderDesc
	 * @param startDate
	 * @param endDate
	 * @param productType
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Response<PageList<OrderListVo>> getOrderListWeb(String custId, String orderDesc,String startDate,String endDate, Integer productType, int type ,int pageNo,
			int pageSize);
	
	/**
	 * 获取充值订单
	 * 
	 * @param custId
	 * @param date
	 * @param orderId
	 * @param type
	 * @param start
	 * @param limit
	 * @return
	 */
	public Response<List<PayInfo>> getPayInfo(String custId ,String date ,String orderId ,int type , long start, long limit);
	
	
	/**
	 * 获取充值订单(WEB)
	 * 
	 * @param custId
	 * @param date
	 * @param orderId
	 * @param start
	 * @param limit
	 * @return
	 */
	public Response<PageList<PayInfo>> getPayInfoWeb(String custId ,String date ,String orderId  , int pageNo, int pageSize);
	
	/**
	 * 积分统计定时器
	 * 
	 */
	public Response<?> integralSum();
	
	/**
	 * 获取用户积分统计信息
	 * 
	 * @param custId
	 * @return
	 */
	public Response<Map<String,String>> getUserIntegralSum(String custId);
	
	/**
	 * 单独对某用户的ID做统一统计
	 * 
	 * @param custId
	 */
	public Response<?> integralSumByCustId(String custId); 
	
	/**
	 * 更新用户缓存信息
	 * @param custId
	 */
	public Response<?> updateUserCache(String custId);
	
	/**
	 * 获取订单详情
	 * @param orderId
	 * @return
	 */
	public Response<OrderInfo> getOrderInfo(String orderId);

}
