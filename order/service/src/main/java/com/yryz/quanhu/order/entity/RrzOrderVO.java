/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月18日
 * Id: RrzOrderVO.java, 2018年1月18日 上午10:16:44 yehao
 */
package com.yryz.quanhu.order.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月18日 上午10:16:44
 * @Description 订单详情
 */
public class RrzOrderVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3242075884744872181L;

	private RrzOrderInfo orderInfo;
	
	private List<RrzOrderAccountHistory> accounts;
	
	private List<RrzOrderIntegralHistory> integrals;
	
	/**
	 * 扩展参数
	 */
	private String extend;

	/**
	 * 
	 * @exception 
	 */
	public RrzOrderVO() {
		super();
	}

	/**
	 * @param orderInfo
	 * @param accounts
	 * @param integrals
	 * @param extend
	 * @exception 
	 */
	public RrzOrderVO(RrzOrderInfo orderInfo, List<RrzOrderAccountHistory> accounts,
			List<RrzOrderIntegralHistory> integrals, String extend) {
		super();
		this.orderInfo = orderInfo;
		this.accounts = accounts;
		this.integrals = integrals;
		this.extend = extend;
	}

	/**
	 * @return the orderInfo
	 */
	public RrzOrderInfo getOrderInfo() {
		return orderInfo;
	}

	/**
	 * @param orderInfo the orderInfo to set
	 */
	public void setOrderInfo(RrzOrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	/**
	 * @return the accounts
	 */
	public List<RrzOrderAccountHistory> getAccounts() {
		return accounts;
	}

	/**
	 * @param accounts the accounts to set
	 */
	public void setAccounts(List<RrzOrderAccountHistory> accounts) {
		this.accounts = accounts;
	}

	/**
	 * @return the integrals
	 */
	public List<RrzOrderIntegralHistory> getIntegrals() {
		return integrals;
	}

	/**
	 * @param integrals the integrals to set
	 */
	public void setIntegrals(List<RrzOrderIntegralHistory> integrals) {
		this.integrals = integrals;
	}

	/**
	 * @return the extend
	 */
	public String getExtend() {
		return extend;
	}

	/**
	 * @param extend the extend to set
	 */
	public void setExtend(String extend) {
		this.extend = extend;
	}
	
}
