/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018年1月17日
 * Id: OrderApi.java, 2018年1月17日 下午8:04:54 yehao
 */
package com.yryz.quanhu.order.api;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.order.vo.*;

import java.util.List;
import java.util.Map;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月17日 下午8:04:54
 * @Description 订单接口服务
 */
public interface OrderApi {

    /**
     * 执行消费资金订单数据(非现金订单)  系统平台ID：1000（固定）
     *
     * @param orderInfo   订单描述信息
     * @param accounts    资金数据交割列表
     * @param integrals   积分数据交割列表
     * @param custId      用户ID(需验证密码的客户ID)
     * @param payPassword 支付密码
     * @param remark      备注信息
     * @return
     */
    Response<?> executeOrder(OrderInfo orderInfo, List<AccountOrder> accounts, List<IntegralOrder> integrals, String custId, String payPassword, String remark);

    /**
     * 执行充值、提现、手续费订单。涉及资金的业务(现金订单)
     *
     * @param payInfo     执行详情
     * @param custId      用户ID(需验证密码的客户ID)
     * @param payPassword 支付密码
     * @param remark      备注信息
     * @return
     */
    Response<PayInfo> executePay(PayInfo payInfo, String custId, String payPassword, String remark);

    /**
     * 订单流水查询
     *
     * @param custId
     * @param date
     * @param productTypeId
     * @param type          1,消费流水；2，积分流水
     * @param start
     * @param limit
     * @return
     */
    Response<List<OrderListVo>> getOrderList(String custId, String date, Integer productTypeId, int type, long start, long limit);

    /**
     * 获取用户账户信息
     *
     * @param custId 用户ID
     * @return
     */
    Response<UserAccount> getUserAccount(String custId);

    /**
     * 更新用户账户状态
     *
     * @param account 用户账户信息
     * @return
     */
    Response<?> dealUserAccount(UserAccount account);

    /**
     * 验证安全信息
     *
     * @param userPhy
     * @return
     */
    Response<?> checkSecurityProblem(UserPhy userPhy);

    /**
     * 冻结支付
     *
     * @param custId
     * @return
     */
    Response<?> lockUserAccount(String custId);

    /**
     * 解冻支付
     *
     * @param custId
     * @return
     */
    Response<?> unlockUserAccount(String custId);

    /**
     * 检查用户支付密码是否正确
     *
     * @param custId
     * @param payPassword
     * @return
     */
    Response<?> checkUserPayPassword(String custId, String payPassword);


    /**
     * 更新用户实名认证信息
     *
     * @param userPhy 用户实名认证信息
     */
    Response<?> dealUserPhy(UserPhy userPhy);

    /**
     * 获取用户实名认证信息
     *
     * @param custId 用户ID
     * @return
     */
    Response<UserPhy> getUserPhy(String custId);

    /**
     * 获取用户银行卡数据ID
     *
     * @param custBankId
     * @return
     */
    Response<CustBank> getCustBankById(String custBankId);

    /**
     * 获取用户绑定银行列表
     *
     * @param custId
     * @return
     */
    Response<List<CustBank>> getCustBanks(String custId);

    /**
     * 处理用户绑定银行信息
     *
     * @param custBank 用户银行卡信息主体
     * @param type     1，保存/更新；0，删除
     */
    Response<?> dealCustBank(CustBank custBank, int type);

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
    Response<PageList<OrderListVo>> getOrderListWeb(String custId, String orderDesc, String startDate, String endDate, Integer productType, int type, int pageNo,
                                                    int pageSize);

    /**
     * 获取支付订单详情
     * @param orderId
     * @return
     */
    Response<PayInfo> getPayInfo(String orderId);

    /**
     * 获取提现申请订单列表
     * @param withdrawCashDto
     * @return
     */
    Response<PageList<PayInfo>> getWithdrawCashPage(WithdrawCashDto withdrawCashDto);

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
    Response<List<PayInfo>> getPayInfoList(String custId, String date, String orderId, int type, long start, long limit);


    /**
     * 获取充值订单(WEB)
     *
     * @param custId
     * @param date
     * @param orderId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Response<PageList<PayInfo>> getPayInfoWeb(String custId, String date, String orderId, int pageNo, int pageSize);

    /**
     * 积分统计定时器(用户-产品类型维度)
     */
    Response<?> integralSum();

    /**
     * 获取用户积分统计信息(用户-产品类型维度)
     *
     * @param custId
     * @return
     */
    Response<Map<String, String>> getUserIntegralSum(String custId);

    /**
     * 单独对某用户的ID做统一统计(用户-产品类型维度)
     *
     * @param custId
     */
    Response<?> integralSumByCustId(String custId);

    /**
     * 更新用户缓存信息
     *
     * @param custId
     */
    Response<?> updateUserCache(String custId);

    /**
     * 获取订单详情
     *
     * @param orderId
     * @return
     */
    Response<OrderInfo> getOrderInfo(String orderId);

    /**
     * 批量查询用户总收益
     *
     * @param userIdList
     * @return
     */
    Response<Map<Long, Long>> getUserTotalIntegral(List<Long> userIdList);

}