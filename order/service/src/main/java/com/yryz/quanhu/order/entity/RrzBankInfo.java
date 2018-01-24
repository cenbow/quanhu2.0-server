/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月18日
 * Id: RrzBankInfo.java, 2018年1月18日 上午9:55:17 yehao
 */
package com.yryz.quanhu.order.entity;

import java.io.Serializable;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月18日 上午9:55:17
 * @Description 银行信息
 */
public class RrzBankInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6792959171419352641L;
	
	/**
	 * 银行ID
	 */
	private String bankId;			
	
	/**
	 * 银行名称
	 */
	private String bankName;		
	
	/**
	 * 实名认证代码
	 */
	private String identCode;		
	
	/**
	 * 支付所需代码
	 */
	private String payCode;			
	
	/**
	 * 银行图标路径
	 */
	private String iconUrl;			
	
	/**
	 * 备注信息
	 */
	private String remark;			
	
	/**
	 * 银行代码
	 */
	private String bankCode;	
	
	/**
	 * 显示排序
	 */
	private Integer seq;			
	
	/**
	 * 是否支持借记卡网银支付
	 */
	private Integer webPayDebit;		
	
	/**
	 * 是否支持信用卡网银支付
	 */
	private Integer webPayCredit;		
	
	/**
	 * 是否支持借记卡快捷支付
	 */
	private Integer fastPayDebit;		
	
	/**
	 * 是否支持信用卡快捷支付
	 */
	private Integer fastPayCredit;

	/**
	 * 
	 * @exception 
	 */
	public RrzBankInfo() {
		super();
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
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the identCode
	 */
	public String getIdentCode() {
		return identCode;
	}

	/**
	 * @param identCode the identCode to set
	 */
	public void setIdentCode(String identCode) {
		this.identCode = identCode;
	}

	/**
	 * @return the payCode
	 */
	public String getPayCode() {
		return payCode;
	}

	/**
	 * @param payCode the payCode to set
	 */
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	/**
	 * @return the iconUrl
	 */
	public String getIconUrl() {
		return iconUrl;
	}

	/**
	 * @param iconUrl the iconUrl to set
	 */
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
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
	 * @return the seq
	 */
	public Integer getSeq() {
		return seq;
	}

	/**
	 * @param seq the seq to set
	 */
	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	/**
	 * @return the webPayDebit
	 */
	public Integer getWebPayDebit() {
		return webPayDebit;
	}

	/**
	 * @param webPayDebit the webPayDebit to set
	 */
	public void setWebPayDebit(Integer webPayDebit) {
		this.webPayDebit = webPayDebit;
	}

	/**
	 * @return the webPayCredit
	 */
	public Integer getWebPayCredit() {
		return webPayCredit;
	}

	/**
	 * @param webPayCredit the webPayCredit to set
	 */
	public void setWebPayCredit(Integer webPayCredit) {
		this.webPayCredit = webPayCredit;
	}

	/**
	 * @return the fastPayDebit
	 */
	public Integer getFastPayDebit() {
		return fastPayDebit;
	}

	/**
	 * @param fastPayDebit the fastPayDebit to set
	 */
	public void setFastPayDebit(Integer fastPayDebit) {
		this.fastPayDebit = fastPayDebit;
	}

	/**
	 * @return the fastPayCredit
	 */
	public Integer getFastPayCredit() {
		return fastPayCredit;
	}

	/**
	 * @param fastPayCredit the fastPayCredit to set
	 */
	public void setFastPayCredit(Integer fastPayCredit) {
		this.fastPayCredit = fastPayCredit;
	}
	
}
