package com.yryz.quanhu.order.vo;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户绑定银行卡信息
 * 
 * @author yehao
 *
 */
@SuppressWarnings("serial")
public class CustBank implements Serializable {

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
	 * 银行ID
	 */
	private String bankId;
	/**
	 * 是否开通快捷支付：0，不开通；1，开通
	 */
	private Integer fastPay;
	/**
	 * 银行预留手机号
	 */
	private String phone;
	/**
	 * 是否默认卡，1，表示是默认卡，0，表示非默认卡
	 */
	private Integer defaultCard;
	/**
	 * 创建人，(更新数据的时候，只需要传入此参数即可，系统会自动根据实际情况填入创建人还是更新人)
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
	 * 备注信息
	 */
	private String ramarks;
	/**
	 * 银行卡类型
	 */
	private Integer bankCardType;
	/**
	 * 用户姓名
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

	public CustBank() {
		super();
	}

	public CustBank(String custId, String bankCardNo, String bankId, Integer fastPay, String phone, Integer defaultCard,
			String createBy, Date createDate, String updateBy, Date updateDate, String ramarks, Integer bankCardType,
			String name, String bankCode, String noAgree) {
		super();
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
		this.ramarks = ramarks;
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
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
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
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getRamarks() {
		return ramarks;
	}

	public void setRamarks(String ramarks) {
		this.ramarks = ramarks;
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
		return "CustBank [cust2BankId=" + cust2BankId + ", custId=" + custId + ", bankCardNo=" + bankCardNo
				+ ", bankId=" + bankId + ", fastPay=" + fastPay + ", phone=" + phone + ", defaultCard=" + defaultCard
				+ ", createBy=" + createBy + ", createDate=" + createDate + ", updateBy=" + updateBy + ", updateDate="
				+ updateDate + ", ramarks=" + ramarks + ", bankCardType=" + bankCardType + ", name=" + name
				+ ", bankCode=" + bankCode + ", noAgree=" + noAgree + "]";
	}

}
