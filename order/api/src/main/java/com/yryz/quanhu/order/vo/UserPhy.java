package com.yryz.quanhu.order.vo;

import java.io.Serializable;

/**
 * 用户实名认证信息详情
 * 
 * @author yehao
 *
 */
@SuppressWarnings("serial")
public class UserPhy implements Serializable {
	/**
	 * 用户ID
	 */
	private String custId;
	/**
	 * 真实姓名
	 */
	private String phyName;
	/**
	 * 支付密码
	 */
	private String payPassword;
	/**
	 * 证件号
	 * 
	 */
	private String custIdcardNo;
	/**
	 * 证件类型
	 */
	private Integer custIdcardType;
	/**
	 * 如果是修改支付密码，需要填入原支付密码
	 */
	private String oldPassword;

	public UserPhy() {
		super();
	}

	public UserPhy(String custId, String phyName, String payPassword, String custIdcardNo, int custIdcardType,
			String oldPassword) {
		super();
		this.custId = custId;
		this.phyName = phyName;
		this.payPassword = payPassword;
		this.custIdcardNo = custIdcardNo;
		this.custIdcardType = custIdcardType;
		this.oldPassword = oldPassword;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getPhyName() {
		return phyName;
	}

	public void setPhyName(String phyName) {
		this.phyName = phyName;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	public String getCustIdcardNo() {
		return custIdcardNo;
	}

	public void setCustIdcardNo(String custIdcardNo) {
		this.custIdcardNo = custIdcardNo;
	}

	public int getCustIdcardType() {
		return custIdcardType;
	}

	public void setCustIdcardType(int custIdcardType) {
		this.custIdcardType = custIdcardType;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	@Override
	public String toString() {
		return "UserPhy [custId=" + custId + ", phyName=" + phyName + ", payPassword=" + payPassword + ", custIdcardNo="
				+ custIdcardNo + ", custIdcardType=" + custIdcardType + ", oldPassword=" + oldPassword + "]";
	}

}
