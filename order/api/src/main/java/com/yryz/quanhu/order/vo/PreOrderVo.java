/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年9月12日
 * Id: OrderVO.java, 2017年9月12日 上午9:21:54 yehao
 */
package com.yryz.quanhu.order.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author yehao
 * @version 1.0
 * @date 2017年9月12日 上午9:21:54
 * @Description 预提交订单类
 */
public class PreOrderVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1122160945095399257L;
	
	/**
	 * 订单描述
	 */
	private OrderInfo orderInfo;
	
	/**
	 * 消费处理详情
	 */
	private List<AccountOrder> accounts;
	
	/**
	 * 积分处理详情
	 */
	private List<IntegralOrder> integrals;
	
	/**
	 * 
	 * @exception 
	 */
	public PreOrderVo() {
		super();
	}
	
	/**
	 * @return the orderInfo
	 */
	public OrderInfo getOrderInfo() {
		return orderInfo;
	}

	/**
	 * @param orderInfo the orderInfo to set
	 */
	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	/**
	 * @return the accounts
	 */
	public List<AccountOrder> getAccounts() {
		return accounts;
	}



	/**
	 * @param accounts the accounts to set
	 */
	public void setAccounts(List<AccountOrder> accounts) {
		this.accounts = accounts;
	}

	/**
	 * @return the integrals
	 */
	public List<IntegralOrder> getIntegrals() {
		return integrals;
	}

	/**
	 * @param integrals the integrals to set
	 */
	public void setIntegrals(List<IntegralOrder> integrals) {
		this.integrals = integrals;
	}
	
	

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
