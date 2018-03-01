/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018年1月19日
 * Id: OrderSDK.java, 2018年1月19日 下午6:27:28 yehao
 */
package com.yryz.quanhu.order.sdk;

import com.yryz.quanhu.order.sdk.constant.OrderEnum;
import com.yryz.quanhu.order.sdk.dto.InputOrder;
import com.yryz.quanhu.order.vo.UserAccount;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月19日 下午6:27:28
 * @Description 订单创建SDK
 */
public interface OrderSDK {

    /**
     * 用户创建待支付订单接口(用于客户端创建订单)
     *
     * @param inputOrder
     * @return
     */
    Long createOrder(InputOrder inputOrder);

    /**
     * 系统触发执行订单接口(用于业务端调用，如圈主回答问题后需要将钱支付给圈主，到期未回答退款给用户)
     *
     * @param orderEnum 订单枚举
     * @param fromId    借款人ID
     * @param toId      收款人ID
     * @param cost      金额
     * @return 成功返回订单ID，否则抛出QuanhuException
     */
    Long executeOrder(OrderEnum orderEnum, Long fromId, Long toId, Long cost);

    /**
     * 查询用户是否已经购买成功
     *
     * @param moduleEnum
     * @param userId
     * @param resourceId
     * @return
     */
    boolean isBuyOrderSuccess(String moduleEnum, long userId, long resourceId);

    /**
     * 获取用户账户信息
     *
     * @param userId 用户ID
     * @return
     */
    UserAccount getUserAccount(Long userId);

}
