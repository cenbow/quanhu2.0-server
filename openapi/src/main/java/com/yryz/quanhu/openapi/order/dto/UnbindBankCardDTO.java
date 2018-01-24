/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月23日
 * Id: UnbindBankCardDTO.java, 2018年1月23日 上午9:45:17 yehao
 */
package com.yryz.quanhu.openapi.order.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月23日 上午9:45:17
 * @Description 解绑银行卡DTO传输
 */
public class UnbindBankCardDTO {
	
	/**
	 * 用户ID
	 */
	@ApiModelProperty(value="用户ID")
	private String custId;
	
	/**
	 * 用户绑定银行卡ID
	 */
	@ApiModelProperty(value="用户绑定银行卡ID")
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
	public UnbindBankCardDTO() {
		super();
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
