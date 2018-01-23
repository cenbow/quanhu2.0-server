/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月23日
 * Id: ExecuteOrderDTO.java, 2018年1月23日 上午11:34:26 yehao
 */
package com.yryz.quanhu.openapi.order.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月23日 上午11:34:26
 * @Description 支付订单的DTO
 */
public class ExecuteOrderDTO implements Serializable {
	
	/**
	 * 订单ID
	 */
	@ApiModelProperty("订单ID")
	private String orderId;
	
	/**
	 * 用户ID
	 */
	@ApiModelProperty("用户ID")
	private String custId; 
	
	/**
	 * 支付密码
	 */
	@ApiModelProperty("支付密码")
	private String payPassord;

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the custId
	 */
	public String getCustId() {
		return custId;
	}

	/**
	 * @param custId the custId to set
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}

	/**
	 * @return the payPassord
	 */
	public String getPayPassord() {
		return payPassord;
	}

	/**
	 * @param payPassord the payPassord to set
	 */
	public void setPayPassord(String payPassord) {
		this.payPassord = payPassord;
	}
	
}
