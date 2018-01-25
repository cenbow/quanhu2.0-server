/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月23日
 * Id: BindBankCardDTO.java, 2018年1月23日 下午2:23:37 yehao
 */
package com.yryz.quanhu.openapi.order.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月23日 下午2:23:37
 * @Description 绑定银行卡号
 */
public class BindBankCardDTO implements Serializable {
	
	
	/**
	 * 用户ID
	 */
	@ApiModelProperty("用户ID")
	private String custId;
	
	/**
	 * 银行卡号
	 */
	@ApiModelProperty("银行卡号")
	private String bankCardNo;
	
	/**
	 * 真实姓名
	 */
	@ApiModelProperty("真实姓名")
	private String name;
	
	/**
	 * 银行名
	 */
	@ApiModelProperty("银行名称，可选")
	private String bankCode;
	
	/**
	 * @param custId
	 * @param bankCardNo
	 * @param name
	 * @param bankCode
	 * @exception 
	 */
	public BindBankCardDTO(String custId, String bankCardNo, String name, String bankCode) {
		super();
		this.custId = custId;
		this.bankCardNo = bankCardNo;
		this.name = name;
		this.bankCode = bankCode;
	}

	/**
	 * 
	 * @exception 
	 */
	public BindBankCardDTO() {
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
	 * @return the bankCardNo
	 */
	public String getBankCardNo() {
		return bankCardNo;
	}

	/**
	 * @param bankCardNo the bankCardNo to set
	 */
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the bankCode
	 */
	public String getBankCode() {
		return bankCode;
	}

	/**
	 * @param bankCode the bankCode to set
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	

}
