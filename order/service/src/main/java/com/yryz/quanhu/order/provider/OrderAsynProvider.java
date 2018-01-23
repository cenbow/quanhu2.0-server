/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月18日
 * Id: OrderAsynProvider.java, 2018年1月18日 上午9:36:41 yehao
 */
package com.yryz.quanhu.order.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.order.api.OrderAsynApi;
import com.yryz.quanhu.order.entity.RrzOrderInfo;
import com.yryz.quanhu.order.entity.RrzOrderVO;
import com.yryz.quanhu.order.service.OrderService;
import com.yryz.quanhu.order.vo.OrderInfo;
import com.yryz.quanhu.order.vo.PreOrderVo;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月18日 上午9:36:41
 * @Description 异步订单接口实现
 */
@Service(interfaceClass=OrderAsynApi.class)
public class OrderAsynProvider implements OrderAsynApi {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private OrderService orderService;

	/**
	 * 创建异步订单
	 * @param preOrderVo
	 * @return
	 * @see com.yryz.quanhu.order.api.OrderAsynApi#createOrder(com.yryz.quanhu.order.vo.PreOrderVo)
	 */
	@Override
	public Response<PreOrderVo> createOrder(PreOrderVo preOrderVo) {
		RrzOrderVO rrzOrderVO = orderService.createOrder(GsonUtils.parseObj(preOrderVo, RrzOrderVO.class));
		return ResponseUtils.returnObjectSuccess(GsonUtils.parseObj(rrzOrderVO, PreOrderVo.class));
	}

	/**
	 * 执行异步订单
	 * @param orderId
	 * @param custId
	 * @param password
	 * @return
	 * @see com.yryz.quanhu.order.api.OrderAsynApi#executeOrder(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Response<?> executeOrder(String orderId, String custId, String password) {
		Response return1 = orderService.executeOrder(orderId, custId, password);
		return return1;
	}

	/**
	 * 获取订单详情
	 * @param orderId
	 * @return
	 * @see com.yryz.quanhu.order.api.OrderAsynApi#getOrderInfo(java.lang.String)
	 */
	@Override
	public Response<OrderInfo> getOrderInfo(String orderId) {
		RrzOrderInfo rrzOrderInfo = orderService.getOrderInfo(orderId);
		return ResponseUtils.returnObjectSuccess(GsonUtils.parseObj(rrzOrderInfo, OrderInfo.class));
	}
	
	
}
