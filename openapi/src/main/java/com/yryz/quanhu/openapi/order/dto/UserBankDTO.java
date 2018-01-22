/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月22日
 * Id: Cust2bankDTO.java, 2018年1月22日 下午4:10:01 yehao
 */
package com.yryz.quanhu.openapi.order.dto;

import java.io.Serializable;
import java.util.Date;

import com.yryz.quanhu.openapi.utils.CommonUtils;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月22日 下午4:10:01
 * @Description 用户
 */
public class UserBankDTO implements Serializable {
	
	/**
	 * 主键ID
	 */
	private String cust2BankId;
	/**
	 * 用户ID
	 */
	private String custId;
	/**
	 * 银行卡号
	 */
	private String bankCardNo;
	/**
	 * 所属银行ID
	 */
	private String bankId;
	/**
	 * 是否开通快捷支付
	 */
	private Integer fastPay;
	/**
	 * 银行预留手机号
	 */
	private String phone;
	/**
	 * 是否默认卡 0:否 1:是
	 */
	private Integer defaultCard;
	/**
	 * 创建人
	 */
	private String createBy;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 更新人
	 */
	private String updateBy;
	/**
	 * 更新时间
	 */
	private Date updateDate;
	/**
	 * 删除标记 0,未删除；1，已删除
	 */
	private String delFlag;
	/**
	 * 备注信息
	 */
	private String remarks;
	/**
	 * 卡类型
	 */
	private Integer bankCardType;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 银行代码
	 */
	private String bankCode;
	/**
	 * 快捷支付协议编号
	 */
	private String noAgree;

	public UserBankDTO() {
		super();
	}

	public UserBankDTO(String cust2BankId, String custId, String bankCardNo, String bankId, Integer fastPay,
			String phone, Integer defaultCard, String createBy, Date createDate, String updateBy, Date updateDate,
			String delFlag, String remarks, Integer bankCardType, String name, String bankCode, String noAgree) {
		super();
		this.cust2BankId = cust2BankId;
		this.custId = custId;
		this.bankCardNo = CommonUtils.getBankCard(bankCardNo);
		this.bankId = bankId;
		this.fastPay = fastPay;
		this.phone = CommonUtils.getPhone(phone);
		this.defaultCard = defaultCard;
		this.createBy = createBy;
		this.createDate = createDate;
		this.updateBy = updateBy;
		this.updateDate = updateDate;
		this.delFlag = delFlag;
		this.remarks = remarks;
		this.bankCardType = bankCardType;
		this.name = name;
		this.bankCode = bankCode;
		this.noAgree = noAgree;
	}

	public String getCust2BankId() {
		return cust2BankId;
	}

	public void setCust2BankId(String cust2BankId) {
		this.cust2BankId = cust2BankId;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getBankCardNo() {
		return CommonUtils.getBankCard(bankCardNo);
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = CommonUtils.getBankCard(bankCardNo);
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public Integer getFastPay() {
		return fastPay;
	}

	public void setFastPay(Integer fastPay) {
		this.fastPay = fastPay;
	}

	public String getPhone() {
		return CommonUtils.getPhone(phone);
	}

	public void setPhone(String phone) {
		this.phone = CommonUtils.getPhone(phone);
	}

	public Integer getDefaultCard() {
		return defaultCard;
	}

	public void setDefaultCard(Integer defaultCard) {
		this.defaultCard = defaultCard;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getBankCardType() {
		return bankCardType;
	}

	public void setBankCardType(Integer bankCardType) {
		this.bankCardType = bankCardType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getNoAgree() {
		return noAgree;
	}

	public void setNoAgree(String noAgree) {
		this.noAgree = noAgree;
	}

	@Override
	public String toString() {
		return "RrzOrderCust2bank [cust2BankId=" + cust2BankId + ", custId=" + custId + ", bankCardNo=" + bankCardNo
				+ ", bankId=" + bankId + ", fastPay=" + fastPay + ", phone=" + phone + ", defaultCard=" + defaultCard
				+ ", createBy=" + createBy + ", createDate=" + createDate + ", updateBy=" + updateBy + ", updateDate="
				+ updateDate + ", delFlag=" + delFlag + ", remarks=" + remarks + ", bankCardType=" + bankCardType
				+ ", name=" + name + ", bankCode=" + bankCode + ", noAgree=" + noAgree + "]";
	}

}
