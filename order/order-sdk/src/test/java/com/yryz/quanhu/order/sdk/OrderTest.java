/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018年1月19日
 * Id: OrderTest.java, 2018年1月19日 下午6:39:42 yehao
 */
package com.yryz.quanhu.order.sdk;

import com.yryz.quanhu.order.sdk.constant.OrderEnum;
import com.yryz.quanhu.order.sdk.dto.InputOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月19日 下午6:39:42
 * @Description SDK测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class OrderTest {

    @Autowired
    private OrderSDK orderSDK;

    @Test
    public void testCreateOrder() {
        InputOrder inputOrder = new InputOrder();
        inputOrder.setFromId(8888L);
        inputOrder.setToId(9999L);
        inputOrder.setCost(100L);
        inputOrder.setOrderEnum(OrderEnum.READ_ORDER);
        inputOrder.setModuleEnum("1004");
        inputOrder.setCoterieId(1L);
        inputOrder.setCreateUserId(1L);
        inputOrder.setResourceId(1L);
        inputOrder.setBizContent("{\"kes\":\"123456\"}");
        Long orderId = orderSDK.createOrder(inputOrder);
        System.out.println("orderId=" + orderId);
    }

}
