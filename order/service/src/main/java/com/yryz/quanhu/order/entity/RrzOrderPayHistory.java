/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月18日
 * Id: RrzOrderPayHistory.java, 2018年1月18日 上午10:07:56 yehao
 */
package com.yryz.quanhu.order.entity;

import java.io.Serializable;
import java.util.Date;

import com.yryz.quanhu.order.utils.PageEntity;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月18日 上午10:07:56
 * @Description 资金流水表
 */
public class RrzOrderPayHistory extends PageEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6058991368222276472L;
	
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
	 * 1，加币；0，减币
	 */
	private Integer orderType;		
	
	/**
	 * 支付通道：3,支付宝；4,微信；5，苹果内购
	 */
	private String orderChannel;	
	
	/**
	 * 充值金额
	 */
	private Long cost;				
	
	/**
	 * 实际金额
	 */
	private Long costTrue;			
	
	/**
	 * 币种
	 */
	private String currency;		
	
	/**
	 * 产品ID
	 */
	private String productId;		
	
	/**
	 * 产品类型
	 * @see ProductEnum
	 */
	private Integer productType;	
	
	/**
	 * 产品说明
	 * @see ProductEnum
	 */
	private String productDesc;		
	
	/**
	 * 订单说明
	 * @see OrderDescEnum
	 */
	private String orderDesc;		
	
	/**
	 * 订单创建时间
	 */
	private Date createTime;		
	
	/**
	 * 备注信息
	 */
	private String remarks;
	
	/**
	 * @param historyId
	 * @param custId
	 * @param orderId
	 * @param orderType
	 * @param orderChannel
	 * @param cost
	 * @param costTrue
	 * @param currency
	 * @param productId
	 * @param productType
	 * @param productDesc
	 * @param orderDesc
	 * @param createTime
	 * @param remarks
	 * @exception 
	 */
	public RrzOrderPayHistory(String historyId, String custId, String orderId, Integer orderType, String orderChannel,
			Long cost, Long costTrue, String currency, String productId, Integer productType, String productDesc,
			String orderDesc, Date createTime, String remarks) {
		super();
		this.historyId = historyId;
		this.custId = custId;
		this.orderId = orderId;
		this.orderType = orderType;
		this.orderChannel = orderChannel;
		this.cost = cost;
		this.costTrue = costTrue;
		this.currency = currency;
		this.productId = productId;
		this.productType = productType;
		this.productDesc = productDesc;
		this.orderDesc = orderDesc;
		this.createTime = createTime;
		this.remarks = remarks;
	}

	/**
	 * 
	 * @exception 
	 */
	public RrzOrderPayHistory() {
		super();
		// TODO Auto-generated constructor stub
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
	 * @return the orderChannel
	 */
	public String getOrderChannel() {
		return orderChannel;
	}

	/**
	 * @param orderChannel the orderChannel to set
	 */
	public void setOrderChannel(String orderChannel) {
		this.orderChannel = orderChannel;
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
	 * @return the costTrue
	 */
	public Long getCostTrue() {
		return costTrue;
	}

	/**
	 * @param costTrue the costTrue to set
	 */
	public void setCostTrue(Long costTrue) {
		this.costTrue = costTrue;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
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
	
	

}
