/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月22日
 * Id: GetCashDTO.java, 2018年1月22日 下午3:45:54 yehao
 */
package com.yryz.quanhu.openapi.order.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月22日 下午3:45:54
 * @Description 用户提现
 */
public class GetCashDTO implements Serializable {
	
	/**
	 * 用户ID
	 */
	@ApiModelProperty(value="用户ID")
	private String custId; 
	
	/**
	 * 提现金额
	 */
	@ApiModelProperty(value="提现金额")
	private String cost; 
	
	/**
	 * 提现绑定卡ID
	 */
	@ApiModelProperty(value="提现绑定卡ID")
	private String cust2BankId; 
	
	/**
	 * 支付密码
	 */
	@ApiModelProperty(value="支付密码")
	private String payPassword;

	/**
	 * 
	 * @exception 
	 */
	public GetCashDTO() {
		super();
	}

	/**
	 * @param custId
	 * @param cost
	 * @param cust2BankId
	 * @param payPassword
	 * @exception 
	 */
	public GetCashDTO(String custId, String cost, String cust2BankId, String payPassword) {
		super();
		this.custId = custId;
		this.cost = cost;
		this.cust2BankId = cust2BankId;
		this.payPassword = payPassword;
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
	public String getCost() {
		return cost;
	}

	/**
	 * @param cost the cost to set
	 */
	public void setCost(String cost) {
		this.cost = cost;
	}

	/**
	 * @return the cust2BankId
	 */
	public String getCust2BankId() {
		return cust2BankId;
	}

	/**
	 * @param cust2BankId the cust2BankId to set
	 */
	public void setCust2BankId(String cust2BankId) {
		this.cust2BankId = cust2BankId;
	}

	/**
	 * @return the payPassword
	 */
	public String getPayPassword() {
		return payPassword;
	}

	/**
	 * @param payPassword the payPassword to set
	 */
	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}
	
}
