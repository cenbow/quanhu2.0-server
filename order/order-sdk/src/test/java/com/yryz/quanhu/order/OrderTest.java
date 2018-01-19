/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月19日
 * Id: OrderTest.java, 2018年1月19日 下午6:39:42 yehao
 */
package com.yryz.quanhu.order;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.Response;
import com.yryz.common.utils.GsonUtils;
import com.yryz.common.utils.IdGen;
import com.yryz.quanhu.order.api.OrderApi;
import com.yryz.quanhu.order.enums.OrderDescEnum;
import com.yryz.quanhu.order.enums.ProductEnum;
import com.yryz.quanhu.order.vo.AccountOrder;
import com.yryz.quanhu.order.vo.IntegralOrder;
import com.yryz.quanhu.order.vo.OrderInfo;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月19日 下午6:39:42
 * @Description TODO (这里用一句话描述这个方法的作用)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class OrderTest {
	
	@Reference
	private static OrderApi orderAPI;
	
	@Test
	public void testexcuteOrder(){
		System.out.println(excuteOrder("yehao-test", 100000));
	}
	
	private boolean excuteOrder(String custId ,long number){
		String orderId = IdGen.uuid();
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setCost(number);
		orderInfo.setOrderDesc(OrderDescEnum.ACTIVITY_USER_LOTTERY);
		orderInfo.setOrderId(orderId);
		orderInfo.setOrderType(3);
		orderInfo.setProductDesc(ProductEnum.LOTTERY_TYPE.getDesc());
		orderInfo.setProductId(ProductEnum.LOTTERY_TYPE.getType() + "");
		orderInfo.setProductType(ProductEnum.LOTTERY_TYPE.getType());
		orderInfo.setType(3);
		
		//用户加币
		AccountOrder accountOrder = new AccountOrder();
		accountOrder.setCost(number);
		accountOrder.setCustId(custId);
		accountOrder.setOrderDesc(OrderDescEnum.ACTIVITY_USER_LOTTERY);
		accountOrder.setOrderId(orderId);
		accountOrder.setOrderType(1);
		accountOrder.setProductDesc(ProductEnum.LOTTERY_TYPE.getDesc());
		accountOrder.setProductId(ProductEnum.LOTTERY_TYPE.getType() + "");
		accountOrder.setProductType(ProductEnum.LOTTERY_TYPE.getType());
		List<AccountOrder> accounts = new ArrayList<>();
		accounts.add(accountOrder);
		
		//扣除积分
		IntegralOrder integralOrder = new IntegralOrder();
		integralOrder.setCost(number);
		integralOrder.setCustId(custId);
		integralOrder.setOrderDesc(OrderDescEnum.ACTIVITY_USER_LOTTERY);
		integralOrder.setOrderId(orderId);
		integralOrder.setOrderType(1);
		integralOrder.setProductDesc(ProductEnum.LOTTERY_TYPE.getDesc());
		integralOrder.setProductId(ProductEnum.LOTTERY_TYPE.getType() + "");
		integralOrder.setProductType(ProductEnum.LOTTERY_TYPE.getType());
		List<IntegralOrder> integrals = new ArrayList<>();
		integrals.add(integralOrder);
		
		Response<?> return1 = orderAPI.executeOrder(orderInfo, accounts, integrals, null, null, null);
		if(return1.success()){
			return true;
		} else {
			System.out.println(GsonUtils.parseJson(return1));
			return false;
		}
	}

}
