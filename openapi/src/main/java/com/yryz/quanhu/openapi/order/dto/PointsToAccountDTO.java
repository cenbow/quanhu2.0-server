/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月22日
 * Id: PointsToAccountDTO.java, 2018年1月22日 下午3:19:50 yehao
 */
package com.yryz.quanhu.openapi.order.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月22日 下午3:19:50
 * @Description 积分兑换DTO
 */
public class PointsToAccountDTO {
	
	/**
	 * 用户ID
	 */
	@ApiModelProperty(value="用户ID")
	private String userId;
	/**
	 * 兑换积分
	 */
	@ApiModelProperty(value="兑换积分")
	private Long cost;
	/**
	 * 支付密码
	 */
	@ApiModelProperty(value="支付密码")
	private String payPassword;
	/**
	 * 
	 * @exception 
	 */
	public PointsToAccountDTO() {
		super();
	}
	/**
	 * @param userId
	 * @param cost
	 * @param payPassword
	 * @exception 
	 */
	public PointsToAccountDTO(String userId, Long cost, String payPassword) {
		super();
		this.userId = userId;
		this.cost = cost;
		this.payPassword = payPassword;
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
