/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月18日
 * Id: RrzOrderCust2bank.java, 2018年1月18日 上午9:58:34 yehao
 */
package com.yryz.quanhu.order.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月18日 上午9:58:34
 * @Description 用户绑定的银行卡信息
 */
public class RrzOrderCust2bank implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 555715892398311026L;
	
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
	 * 是否默认卡
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
	private	String updateBy;			
	
	/**
	 * 更新时间
	 */
	private Date updateDate;			
	
	/**
	 * 删除标记       0,未删除；1，已删除
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

	/**
	 * 
	 * @exception 
	 */
	public RrzOrderCust2bank() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cust2BankId
	 * @param custId
	 * @param bankCardNo
	 * @param bankId
	 * @param fastPay
	 * @param phone
	 * @param defaultCard
	 * @param createBy
	 * @param createDate
	 * @param updateBy
	 * @param updateDate
	 * @param delFlag
	 * @param remarks
	 * @param bankCardType
	 * @param name
	 * @param bankCode
	 * @param noAgree
	 * @exception 
	 */
	public RrzOrderCust2bank(String cust2BankId, String custId, String bankCardNo, String bankId, Integer fastPay,
			String phone, Integer defaultCard, String createBy, Date createDate, String updateBy, Date updateDate,
			String delFlag, String remarks, Integer bankCardType, String name, String bankCode, String noAgree) {
		super();
		this.cust2BankId = cust2BankId;
		this.custId = custId;
		this.bankCardNo = bankCardNo;
		this.bankId = bankId;
		this.fastPay = fastPay;
		this.phone = phone;
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
	 * @return the bankId
	 */
	public String getBankId() {
		return bankId;
	}

	/**
	 * @param bankId the bankId to set
	 */
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	/**
	 * @return the fastPay
	 */
	public Integer getFastPay() {
		return fastPay;
	}

	/**
	 * @param fastPay the fastPay to set
	 */
	public void setFastPay(Integer fastPay) {
		this.fastPay = fastPay;
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
	 * @return the defaultCard
	 */
	public Integer getDefaultCard() {
		return defaultCard;
	}

	/**
	 * @param defaultCard the defaultCard to set
	 */
	public void setDefaultCard(Integer defaultCard) {
		this.defaultCard = defaultCard;
	}

	/**
	 * @return the createBy
	 */
	public String getCreateBy() {
		return createBy;
	}

	/**
	 * @param createBy the createBy to set
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the updateBy
	 */
	public String getUpdateBy() {
		return updateBy;
	}

	/**
	 * @param updateBy the updateBy to set
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the delFlag
	 */
	public String getDelFlag() {
		return delFlag;
	}

	/**
	 * @param delFlag the delFlag to set
	 */
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the bankCardType
	 */
	public Integer getBankCardType() {
		return bankCardType;
	}

	/**
	 * @param bankCardType the bankCardType to set
	 */
	public void setBankCardType(Integer bankCardType) {
		this.bankCardType = bankCardType;
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

	/**
	 * @return the noAgree
	 */
	public String getNoAgree() {
		return noAgree;
	}

	/**
	 * @param noAgree the noAgree to set
	 */
	public void setNoAgree(String noAgree) {
		this.noAgree = noAgree;
	}		
	
}
