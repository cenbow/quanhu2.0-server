/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月23日
 * Id: FreePayDTO.java, 2018年1月23日 上午11:40:58 yehao
 */
package com.yryz.quanhu.openapi.order.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月23日 上午11:40:58
 * @Description 免密支付接口
 */
public class FreePayDTO {
	
	/**
	 * 设置类型0：不设置，1：设置
	 */
	@ApiModelProperty("设置类型0：不设置，1：设置")
	private Integer type;
	
	/**
	 * 支付密码
	 */
	@ApiModelProperty("支付密码")
	private String payPassword;

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
