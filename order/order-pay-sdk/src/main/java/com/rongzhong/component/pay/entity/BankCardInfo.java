package com.rongzhong.component.pay.entity;



/**
 * 银行卡相关信息
 * @author Administrator
 *
 */
public class BankCardInfo {
	private String name; // 姓名
	private String idCardNo; // 身份证号
	private String bankCardNo; // 银行卡号
	private String bankCode; // 银行代码
	private String bankName; // 银行名称
	private String phoneNo; // 银行预留手机号
	private String noAgree; // 银行卡签约的唯一编号(连连快捷支付)
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String cardNo) {
		this.bankCardNo = cardNo;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getNoAgree() {
		return noAgree;
	}
	public void setNoAgree(String noAgree) {
		this.noAgree = noAgree;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
}
