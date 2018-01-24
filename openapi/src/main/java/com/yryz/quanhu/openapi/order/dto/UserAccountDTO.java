/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月23日
 * Id: UserAccountDTO.java, 2018年1月23日 上午11:13:16 yehao
 */
package com.yryz.quanhu.openapi.order.dto;

import java.util.Date;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月23日 上午11:13:16
 * @Description TODO (这里用一句话描述这个方法的作用)
 */
public class UserAccountDTO {
	
	/**
	 * 客户ID
	 */
	private String custId;
	/**
	 * 账户余额
	 */
	private Long accountSum;
	/**
	 * 积分余额
	 */
	private Long integralSum;
	/**
	 * 累计余额
	 */
	private Long costSum;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 是否开通小额免密，0，不开通；1，开通
	 */
	private Integer smallNopass;
	/**
	 * 账户状态 1，正常状态，2，非正常状态
	 */
	private Integer accountState;

	public UserAccountDTO() {
		super();
	}

	public UserAccountDTO(String custId, Long accountSum, Long integralSum, Long costSum, Date createTime,
			Date updateTime, Integer accountState) {
		super();
		this.custId = custId;
		this.accountSum = accountSum;
		this.integralSum = integralSum;
		this.costSum = costSum;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.accountState = accountState;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public Long getAccountSum() {
		return accountSum;
	}

	public void setAccountSum(Long accountSum) {
		this.accountSum = accountSum;
	}

	public Long getIntegralSum() {
		return integralSum;
	}

	public void setIntegralSum(Long integralSum) {
		this.integralSum = integralSum;
	}

	public Long getCostSum() {
		return costSum;
	}

	public void setCostSum(Long costSum) {
		this.costSum = costSum;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getSmallNopass() {
		return smallNopass;
	}

	public void setSmallNopass(Integer smallNopass) {
		this.smallNopass = smallNopass;
	}

	public Integer getAccountState() {
		return accountState;
	}

	public void setAccountState(Integer accountState) {
		this.accountState = accountState;
	}

	@Override
	public String toString() {
		return "RrzOrderUserAccount [custId=" + custId + ", accountSum=" + accountSum + ", integralSum=" + integralSum
				+ ", costSum=" + costSum + ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", smallNopass=" + smallNopass + ", accountState=" + accountState + "]";
	}

}
