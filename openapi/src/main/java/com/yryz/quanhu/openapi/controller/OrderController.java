/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月20日
 * Id: OrderController.java, 2018年1月20日 上午11:04:02 yehao
 */
package com.yryz.quanhu.openapi.controller;

import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.quanhu.order.api.OrderApi;

import io.swagger.annotations.Api;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月20日 上午11:04:02
 * @Description TODO (这里用一句话描述这个方法的作用)
 */
@Api("资金管理 ")
@RestController
public class OrderController {
	
	@Reference
	private OrderApi orderApi;
	
	
}
