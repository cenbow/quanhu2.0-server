/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月18日
 * Id: RrzOrderUserAccount.java, 2018年1月18日 上午10:09:43 yehao
 */
package com.yryz.quanhu.order.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月18日 上午10:09:43
 * @Description 用户支付账户信息
 */
public class RrzOrderUserAccount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4174568150771596708L;
	
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
	 * 账户状态    1，正常状态，0，非正常状态
	 */
	private Integer accountState;

	/**
	 * @param custId
	 * @param accountSum
	 * @param integralSum
	 * @param costSum
	 * @param createTime
	 * @param updateTime
	 * @param smallNopass
	 * @param accountState
	 * @exception 
	 */
	public RrzOrderUserAccount(String custId, Long accountSum, Long integralSum, Long costSum, Date createTime,
			Date updateTime, Integer smallNopass, Integer accountState) {
		super();
		this.custId = custId;
		this.accountSum = accountSum;
		this.integralSum = integralSum;
		this.costSum = costSum;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.smallNopass = smallNopass;
		this.accountState = accountState;
	}

	/**
	 * 
	 * @exception 
	 */
	public RrzOrderUserAccount() {
		super();
		// TODO Auto-generated constructor stub
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
	 * @return the accountSum
	 */
	public Long getAccountSum() {
		return accountSum;
	}

	/**
	 * @param accountSum the accountSum to set
	 */
	public void setAccountSum(Long accountSum) {
		this.accountSum = accountSum;
	}

	/**
	 * @return the integralSum
	 */
	public Long getIntegralSum() {
		return integralSum;
	}

	/**
	 * @param integralSum the integralSum to set
	 */
	public void setIntegralSum(Long integralSum) {
		this.integralSum = integralSum;
	}

	/**
	 * @return the costSum
	 */
	public Long getCostSum() {
		return costSum;
	}

	/**
	 * @param costSum the costSum to set
	 */
	public void setCostSum(Long costSum) {
		this.costSum = costSum;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the smallNopass
	 */
	public Integer getSmallNopass() {
		return smallNopass;
	}

	/**
	 * @param smallNopass the smallNopass to set
	 */
	public void setSmallNopass(Integer smallNopass) {
		this.smallNopass = smallNopass;
	}

	/**
	 * @return the accountState
	 */
	public Integer getAccountState() {
		return accountState;
	}

	/**
	 * @param accountState the accountState to set
	 */
	public void setAccountState(Integer accountState) {
		this.accountState = accountState;
	}	
	
}
