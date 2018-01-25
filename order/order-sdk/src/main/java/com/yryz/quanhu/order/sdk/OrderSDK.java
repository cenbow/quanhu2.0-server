/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018年1月19日
 * Id: OrderSDK.java, 2018年1月19日 下午6:27:28 yehao
 */
package com.yryz.quanhu.order.sdk;

import com.yryz.quanhu.order.sdk.dto.InputOrder;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月19日 下午6:27:28
 * @Description 订单创建SDK
 */
public interface OrderSDK {

    /**
     * 创建订单接口
     *
     * @param inputOrder
     * @return
     */
    Long createOrder(InputOrder inputOrder);

    /**
     * 查询用户是否已经购买成功
     *
     * @param moduleEnum
     * @param userId
     * @param resourceId
     * @return
     */
    boolean isBuyOrderSuccess(String moduleEnum, long userId, long resourceId);

}
