package com.yryz.quanhu.order.vo;

import java.io.Serializable;

/**
 * 用户账户信息详情
 * 
 * @author yehao
 *
 */
@SuppressWarnings("serial")
public class UserAccount implements Serializable {
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
	 * 是否开通小额免密，0，不开通；1，开通
	 */
	private Integer smallNopass;
	/**
	 * 账户状态
	 */
	private Integer accountState;

	public UserAccount() {
		super();
	}

	public UserAccount(String custId, Long accountSum, Long integralSum, Long costSum, Integer accountState) {
		super();
		this.custId = custId;
		this.accountSum = accountSum;
		this.integralSum = integralSum;
		this.costSum = costSum;
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
		return "UserAccount [custId=" + custId + ", accountSum=" + accountSum + ", integralSum=" + integralSum
				+ ", costSum=" + costSum + ", accountState=" + accountState + "]";
	}

}
