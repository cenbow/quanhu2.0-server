/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月22日
 * Id: FindPayPasswordDTO.java, 2018年1月22日 下午2:57:40 yehao
 */
package com.yryz.quanhu.openapi.order.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月22日 下午2:57:40
 * @Description 找回支付密码
 */
public class FindPayPasswordDTO implements Serializable {
	
	/**
	 * 用户ID
	 */
	@ApiModelProperty(value="用户ID")
	private String userId;
	
	/**
	 * 手机号
	 */
	@ApiModelProperty(value="手机号，可选")
	private String phone;
	
	/**
	 * 验证码
	 */
	@ApiModelProperty(value="验证码")
	private String veriCode;
	
	/**
	 * 支付密码
	 */
	@ApiModelProperty(value="支付密码，新密码")
	private String payPassword;
	
	/**
	 * 真实姓名
	 */
	@ApiModelProperty(value="真实姓名，可选")
	private String phyName; 
	
	/**
	 * 身份证号
	 */
	@ApiModelProperty(value="身份证号，可选")
	private String custIdcardNo;

	/**
	 * 
	 * @exception 
	 */
	public FindPayPasswordDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param userId
	 * @param phone
	 * @param veriCode
	 * @param payPassword
	 * @param phyName
	 * @param custIdcardNo
	 * @exception 
	 */
	public FindPayPasswordDTO(String userId, String phone, String veriCode, String payPassword, String phyName,
			String custIdcardNo) {
		super();
		this.userId = userId;
		this.phone = phone;
		this.veriCode = veriCode;
		this.payPassword = payPassword;
		this.phyName = phyName;
		this.custIdcardNo = custIdcardNo;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the veriCode
	 */
	public String getVeriCode() {
		return veriCode;
	}

	/**
	 * @param veriCode the veriCode to set
	 */
	public void setVeriCode(String veriCode) {
		this.veriCode = veriCode;
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

	/**
	 * @return the phyName
	 */
	public String getPhyName() {
		return phyName;
	}

	/**
	 * @param phyName the phyName to set
	 */
	public void setPhyName(String phyName) {
		this.phyName = phyName;
	}

	/**
	 * @return the custIdcardNo
	 */
	public String getCustIdcardNo() {
		return custIdcardNo;
	}

	/**
	 * @param custIdcardNo the custIdcardNo to set
	 */
	public void setCustIdcardNo(String custIdcardNo) {
		this.custIdcardNo = custIdcardNo;
	}
	
}
