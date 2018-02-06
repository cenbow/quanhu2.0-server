/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月18日
 * Id: RrzOrderAccountHistory.java, 2018年1月18日 上午9:57:44 yehao
 */
package com.yryz.quanhu.order.entity;

import java.io.Serializable;
import java.util.Date;

import com.yryz.quanhu.order.utils.PageEntity;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月18日 上午9:57:44
 * @Description 用户余额流水
 */
public class RrzOrderAccountHistory extends PageEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6996854488972362952L;
	
	/**
	 * 主键ID
	 */
	private String historyId;		
	
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

	/**
	 * @param historyId
	 * @param custId
	 * @param orderId
	 * @param orderType
	 * @param cost
	 * @param accountSum
	 * @param productId
	 * @param productType
	 * @param productDesc
	 * @param orderDesc
	 * @param remarks
	 * @param createTime
	 * @exception 
	 */
	public RrzOrderAccountHistory(String historyId, String custId, String orderId, Integer orderType, Long cost,
			Long accountSum, String productId, Integer productType, String productDesc, String orderDesc,
			String remarks, Date createTime) {
		super();
		this.historyId = historyId;
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

	/**
	 * 
	 * @exception 
	 */
	public RrzOrderAccountHistory() {
		super();
	}

	/**
	 * @return the historyId
	 */
	public String getHistoryId() {
		return historyId;
	}

	/**
	 * @param historyId the historyId to set
	 */
	public void setHistoryId(String historyId) {
		this.historyId = historyId;
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
	 * @return the orderType
	 */
	public Integer getOrderType() {
		return orderType;
	}

	/**
	 * @param orderType the orderType to set
	 */
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	/**
	 * @return the cost
	 */
	public Long getCost() {
		return cost;
	}

	/**
	 * @param cost the cost to set
	 */
	public void setCost(Long cost) {
		this.cost = cost;
	}

	/**
	 * @return the accountSum
	 */
	public Long getAccountSum() {
		return accountSum;
	}

	/**
	 * @param accountSum the accountSum to set
	 */
	public void setAccountSum(Long accountSum) {
		this.accountSum = accountSum;
	}

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the productType
	 */
	public Integer getProductType() {
		return productType;
	}

	/**
	 * @param productType the productType to set
	 */
	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	/**
	 * @return the productDesc
	 */
	public String getProductDesc() {
		return productDesc;
	}

	/**
	 * @param productDesc the productDesc to set
	 */
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	/**
	 * @return the orderDesc
	 */
	public String getOrderDesc() {
		return orderDesc;
	}

	/**
	 * @param orderDesc the orderDesc to set
	 */
	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}		
	
}
