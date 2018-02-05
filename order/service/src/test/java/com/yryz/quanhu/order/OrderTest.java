/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月19日
 * Id: OrderTest.java, 2018年1月19日 下午2:10:59 yehao
 */
package com.yryz.quanhu.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.yryz.common.response.PageList;
import com.yryz.quanhu.order.enums.OrderPayConstants;
import com.yryz.quanhu.order.vo.*;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.Response;
import com.yryz.common.utils.GsonUtils;
import com.yryz.common.utils.IdGen;
import com.yryz.quanhu.order.api.OrderApi;
import com.yryz.quanhu.order.api.OrderAsynApi;
import com.yryz.quanhu.order.enums.OrderDescEnum;
import com.yryz.quanhu.order.enums.ProductEnum;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月19日 下午2:10:59
 * @Description 订单管理单元测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class OrderTest {
	
//	private static IdAPI idAPI;
	
	@Reference
	private static OrderApi orderAPI;
	
	private static OrderAsynApi orderAsynAPI;
	
	@Test
	public void createOrder(){
		String orderId = IdGen.uuid();
		System.out.println(orderId);
		int num = 100;
		OrderInfo orderInfo = getOrderInfo(orderId, num);
		AccountOrder accountOrder = getAccountOrder(orderId, num);
		List<AccountOrder> accountOrders = new ArrayList<>();
		accountOrders.add(accountOrder);
		IntegralOrder integralOrder = getIntegralOrder(orderId, num);
		List<IntegralOrder> integralOrders = new ArrayList<>();
		integralOrders.add(integralOrder);
		PreOrderVo orderVO = new PreOrderVo();
		orderVO.setOrderInfo(orderInfo);
		orderVO.setAccounts(accountOrders);
//		orderVO.setIntegrals(integralOrders);
		Response<?> orderVO2 = orderAPI.executeOrder(orderInfo, accountOrders, integralOrders, null, null, null);
//		OrderVO orderVO2 = orderAsynAPI.createOrder(orderVO);
		System.out.println(GsonUtils.parseJson(orderVO2));
	}
	
	@Test
	public void executeOrder(){
		Response<?> return1 = orderAsynAPI.executeOrder("2017092711304025", "4qqcxnbnht", "abb");
		System.out.println(GsonUtils.parseJson(return1));
	}
	
	@Test
	public void getOrderInfo(){
		OrderInfo orderInfo = orderAPI.getOrderInfo("2017102411894024").getData();
		System.out.println(GsonUtils.parseJson(orderInfo));
	}
	
	public OrderInfo getOrderInfo(String orderId ,int num){
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setCost(num + 0L);
		orderInfo.setCustId("4qqcxnbnht");
		orderInfo.setOrderDesc("测试订单" + num);
		orderInfo.setOrderId(orderId);
		orderInfo.setOrderType(1);
		orderInfo.setProductDesc("测试产品描述");
		orderInfo.setProductId("1000");
		orderInfo.setProductType(1000);
		orderInfo.setRemark("测试备注" + num);
		orderInfo.setCallback("http://yehao-callback");
		orderInfo.setType(3);
		orderInfo.setBizContent("bizContent");
		return orderInfo;
	}
	
	public AccountOrder getAccountOrder(String orderId ,int num){
		AccountOrder accountOrder = new AccountOrder();
		accountOrder.setCost(num + 0L);
		accountOrder.setCustId("4qqcxnbnht");
		accountOrder.setOrderDesc("测试订单" + num);
		accountOrder.setOrderId(orderId);
		accountOrder.setOrderType(1);
		accountOrder.setProductDesc("测试产品描述");
		accountOrder.setProductId("1000");
		accountOrder.setProductType(1000);
		accountOrder.setRemarks("测试备注" + num);
		return accountOrder;
	}
	
	public IntegralOrder getIntegralOrder(String orderId ,int num){
		IntegralOrder integralOrder = new IntegralOrder();
		integralOrder.setCost(num + 0L);
		integralOrder.setCustId("yehao-order");
		integralOrder.setOrderDesc("测试订单" + num);
		integralOrder.setOrderId(orderId);
		integralOrder.setOrderType(1);
		integralOrder.setProductDesc("测试产品描述");
		integralOrder.setProductId("1000");
		integralOrder.setProductType(1000);
		integralOrder.setRemarks("测试备注" + num);
		return integralOrder;
	}
	
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
		orderInfo.setOrderType(1);
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
	
	@Test
	public void getPayinfoWeb(){
		System.out.println(GsonUtils.parseJson(orderAPI.getPayInfoWeb("yehao-test", null, null, 0, 0))); 
	}

	@Test
	public void testUserTotalIntegral(){
		Response<Map<Long, Long>> response = orderAPI.getUserTotalIntegral(Lists.newArrayList(1000L));
		if(response.success()){
			System.out.println(JSON.toJSONString(response.getData()));
		}
	}

	@Test
	public void testGetPayInfo(){
		Response<PayInfo> response = orderAPI.getPayInfo("12017102611917028");
		if(response.success()){
			System.out.println(JSON.toJSONString(response.getData()));
		}
	}

	@Test
	public void testGetWithdrawCashPage(){
		WithdrawCashDto withdrawCashDto = new WithdrawCashDto();
		withdrawCashDto.setOrderState(OrderPayConstants.OrderState.CREATE);
		Response<PageList<PayInfo>> response = orderAPI.getWithdrawCashPage(withdrawCashDto);
		if(response.success()){
			System.out.println(JSON.toJSONString(response.getData()));
		}
	}

}
