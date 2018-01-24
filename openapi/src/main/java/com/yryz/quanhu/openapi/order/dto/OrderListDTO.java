/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月22日
 * Id: OrderListDTO.java, 2018年1月22日 下午4:51:23 yehao
 */
package com.yryz.quanhu.openapi.order.dto;

import java.util.Date;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月22日 下午4:51:23
 * @Description 订单列表的DTO
 */
public class OrderListDTO {
	
	/**
	 * 客户ID
	 */
	private String custId;
	/**
	 * 订单ID
	 */
	private String orderId;
	/**
	 * 订单类型：1，加币；0，减币
	 */
	private Integer orderType;
	/**
	 * 交易金额
	 */
	private Long cost;
	/**
	 * 交易余额
	 */
	private Long accountSum;
	/**
	 * 产品ID
	 */
	private String productId;
	/**
	 * 产品类型
	 */
	private Integer productType;
	/**
	 * 产品说明
	 */
	private String productDesc;
	/**
	 * 订单描述
	 */
	private String orderDesc;
	/**
	 * 备注信息
	 */
	private String remarks;
	/**
	 * 创建时间
	 */
	private Date createTime;

	public OrderListDTO() {
		super();
	}

	public OrderListDTO(String custId, String orderId, Integer orderType, Long cost, Long accountSum, String productId,
			Integer productType, String productDesc, String orderDesc, String remarks, Date createTime) {
		super();
		this.custId = custId;
		this.orderId = orderId;
		this.orderType = orderType;
		this.cost = cost;
		this.accountSum = accountSum;
		this.productId = productId;
		this.productType = productType;
		this.productDesc = productDesc;
		this.orderDesc = orderDesc;
		this.remarks = remarks;
		this.createTime = createTime;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Long getCost() {
		return cost;
	}

	public void setCost(Long cost) {
		this.cost = cost;
	}

	public Long getAccountSum() {
		return accountSum;
	}

	public void setAccountSum(Long accountSum) {
		this.accountSum = accountSum;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "OrderList [custId=" + custId + ", orderId=" + orderId + ", orderType=" + orderType + ", cost=" + cost
				+ ", accountSum=" + accountSum + ", productId=" + productId + ", productType=" + productType
				+ ", productDesc=" + productDesc + ", orderDesc=" + orderDesc + ", remarks=" + remarks + ", createTime="
				+ createTime + "]";
	}

}
