/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月18日
 * Id: RrzOrderInfo.java, 2018年1月18日 上午10:04:52 yehao
 */
package com.yryz.quanhu.order.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月18日 上午10:04:52
 * @Description 订单信息
 */
public class RrzOrderInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4532617816402447236L;

	public static final int ORDER_STATE_OFF = 0;
	
	public static final int ORDER_STATE_ON = 1;

	/**
	 * 订单号
	 */
	private String orderId;				
	
	/**
	 * 用户ID
	 */
	private String custId;				
	
	/**
	 * 涉及金额
	 */
	private Long cost;					
	
	/**
	 * 支付类型，1，加币；0，减币
	 */
	private Integer orderType;			
	
	/**
	 * 订单类型，1，余额；2，积分；3，混合
	 */
	private Integer type;				
	
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
	 * 订单说明
	 */
	private String orderDesc;			
	
	/**
	 * 备注
	 */
	private String remarks;				
	
	/**
	 * 创建时间
	 */
	private Date createTime;			
	
	/**
	 * 消费类型 0:平台资金 1:消费券
	 */
	private Byte consumeType; 			
	
	/**
	 * 订单状态 0：创建，1：成功
	 */
	private Integer orderState;			
	
	/**
	 * 回调地址 
	 */
	private String callback;			
	
	/**
	 * 扩展参数
	 */
	private String bizContent;
	
	

	/**
	 * @param orderId
	 * @param custId
	 * @param cost
	 * @param orderType
	 * @param type
	 * @param productId
	 * @param productType
	 * @param productDesc
	 * @param orderDesc
	 * @param remarks
	 * @param createTime
	 * @param consumeType
	 * @param orderState
	 * @param callback
	 * @param bizContent
	 * @exception 
	 */
	public RrzOrderInfo(String orderId, String custId, Long cost, Integer orderType, Integer type, String productId,
			Integer productType, String productDesc, String orderDesc, String remarks, Date createTime,
			Byte consumeType, Integer orderState, String callback, String bizContent) {
		super();
		this.orderId = orderId;
		this.custId = custId;
		this.cost = cost;
		this.orderType = orderType;
		this.type = type;
		this.productId = productId;
		this.productType = productType;
		this.productDesc = productDesc;
		this.orderDesc = orderDesc;
		this.remarks = remarks;
		this.createTime = createTime;
		this.consumeType = consumeType;
		this.orderState = orderState;
		this.callback = callback;
		this.bizContent = bizContent;
	}

	/**
	 * 
	 * @exception 
	 */
	public RrzOrderInfo() {
		super();
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
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
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

	/**
	 * @return the consumeType
	 */
	public Byte getConsumeType() {
		return consumeType;
	}

	/**
	 * @param consumeType the consumeType to set
	 */
	public void setConsumeType(Byte consumeType) {
		this.consumeType = consumeType;
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
	 * @return the callback
	 */
	public String getCallback() {
		return callback;
	}

	/**
	 * @param callback the callback to set
	 */
	public void setCallback(String callback) {
		this.callback = callback;
	}

	/**
	 * @return the bizContent
	 */
	public String getBizContent() {
		return bizContent;
	}

	/**
	 * @param bizContent the bizContent to set
	 */
	public void setBizContent(String bizContent) {
		this.bizContent = bizContent;
	}			
	
	
	

}
