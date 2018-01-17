/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月17日
 * Id: OrderAsynApi.java, 2018年1月17日 下午8:04:40 yehao
 */
package com.yryz.quanhu.order.api;

import com.yryz.common.response.Response;
import com.yryz.quanhu.order.vo.OrderInfo;
import com.yryz.quanhu.order.vo.PreOrderVo;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月17日 下午8:04:40
 * @Description 异步订单
 */
public interface OrderAsynApi {
	
	/**
	 * 创建预处理订单
	 * @param orderVO
	 * @return
	 */
	public Response<PreOrderVo> createOrder(PreOrderVo preOrderVo);
	
	/**
	 * 执行订单
	 * @param orderId
	 * @param custId
	 * @param password
	 * @return
	 */
	public Response<?> executeOrder(String orderId ,String custId ,String password);
	
	/**
	 * 获取订单详情
	 * @param orderId
	 * @return
	 */
	public Response<OrderInfo> getOrderInfo(String orderId);

}
