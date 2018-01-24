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

	public UserBankDTO() {
		super();
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
	
}
