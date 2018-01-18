/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月18日
 * Id: RrzOrderPayInfo.java, 2018年1月18日 上午10:08:57 yehao
 */
package com.yryz.quanhu.order.entity;

import java.io.Serializable;
import java.util.Date;

import com.yryz.quanhu.order.utils.PageEntity;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月18日 上午10:08:57
 * @Description 用户资金支付信息表
 */
public class RrzOrderPayInfo extends PageEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1874407214603309222L;
	
	/**
	 * 订单ID
	 */
	private String orderId;			
	
	/**
	 * 客户ID
	 */
	private String custId;			
	
	/**
	 * 1，加币；0，减币
	 */
	private Integer orderType;		
	
	/**
	 * 支付通道：银行就填银行ID，支付宝填1000，微信支付填1001
	 */
	private String orderChannel;	
	
	/**
	 * 充值金额(现金数量)
	 */
	private Long cost;				
	
	/**
	 * 实际金额(实际执行悠然币数量)
	 */
	private Long costTrue;			
	
	/**
	 * 手续费
	 */
	private Long costFee;			
	
	/**
	 * 币种：156（人民币）
	 */
	private String currency;		
	
	/**
	 * 产品ID
	 * @see ProductEnum
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
	 * 传入支付机构的参数(请填写原始数据)
	 */
	private String startDesc;		
	
	/**
	 * 支付机构回调参数(请填写原始数据)
	 */
	private String endDesc;			
	
	/**
	 * 订单说明
	 */
	private String orderDesc;		
	
	/**
	 * 订单状态    0,未完成；1，已完成
	 */
	private Integer orderState;		
	
	/**
	 * 订单创建时间
	 */
	private Date createTime;		
	
	/**
	 * 订单完成时间
	 */
	private Date completeTime;		
	
	/**
	 * 备注信息
	 */
	private String remarks;

	/**
	 * 
	 * @exception 
	 */
	public RrzOrderPayInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param orderId
	 * @param custId
	 * @param orderType
	 * @param orderChannel
	 * @param cost
	 * @param costTrue
	 * @param costFee
	 * @param currency
	 * @param productId
	 * @param productType
	 * @param productDesc
	 * @param startDesc
	 * @param endDesc
	 * @param orderDesc
	 * @param orderState
	 * @param createTime
	 * @param completeTime
	 * @param remarks
	 * @exception 
	 */
	public RrzOrderPayInfo(String orderId, String custId, Integer orderType, String orderChannel, Long cost,
			Long costTrue, Long costFee, String currency, String productId, Integer productType, String productDesc,
			String startDesc, String endDesc, String orderDesc, Integer orderState, Date createTime, Date completeTime,
			String remarks) {
		super();
		this.orderId = orderId;
		this.custId = custId;
		this.orderType = orderType;
		this.orderChannel = orderChannel;
		this.cost = cost;
		this.costTrue = costTrue;
		this.costFee = costFee;
		this.currency = currency;
		this.productId = productId;
		this.productType = productType;
		this.productDesc = productDesc;
		this.startDesc = startDesc;
		this.endDesc = endDesc;
		this.orderDesc = orderDesc;
		this.orderState = orderState;
		this.createTime = createTime;
		this.completeTime = completeTime;
		this.remarks = remarks;
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
	 * @return the costFee
	 */
	public Long getCostFee() {
		return costFee;
	}

	/**
	 * @param costFee the costFee to set
	 */
	public void setCostFee(Long costFee) {
		this.costFee = costFee;
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
	 * @return the startDesc
	 */
	public String getStartDesc() {
		return startDesc;
	}

	/**
	 * @param startDesc the startDesc to set
	 */
	public void setStartDesc(String startDesc) {
		this.startDesc = startDesc;
	}

	/**
	 * @return the endDesc
	 */
	public String getEndDesc() {
		return endDesc;
	}

	/**
	 * @param endDesc the endDesc to set
	 */
	public void setEndDesc(String endDesc) {
		this.endDesc = endDesc;
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
	 * @return the orderState
	 */
	public Integer getOrderState() {
		return orderState;
	}

	/**
	 * @param orderState the orderState to set
	 */
	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
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
	 * @return the completeTime
	 */
	public Date getCompleteTime() {
		return completeTime;
	}

	/**
	 * @param completeTime the completeTime to set
	 */
	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
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
