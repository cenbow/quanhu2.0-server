package com.rongzhong.component.pay.entity;



/**
 * 支付结果
 * @author Administrator
 *
 */
public class PayResponse extends Response {
	private String bankSn; // 银行流水号
	private String mchId;  // 商户号
	private String payAmount; // 支付金额(单位: 分)
	private String payDatetime; // 支付完成时间
	private String payWay;      // 支付方式
	private String bankCardNo;  // 支付的银行卡号
	private String accountName; // 银行卡账户名
	private String noAgree;     // 开通快捷支付银行卡的协议编号
	private String endDesc;		//返回参数
	
	public String toString() {
		StringBuffer result = new StringBuffer();
		
		result.append("{result = " + super.getResult());
		result.append(", message = " + super.getMessage());
		result.append(", sn = " + super.getSn());
		result.append(", bankSn = " + bankSn);
		result.append(", mchId = " + mchId);
		result.append(", payAmount = " + payAmount);
		result.append(", payDatetime = " + payDatetime + "}");
		return result.toString();
	}
	
	public String getBankSn() {
		return bankSn;
	}
	public void setBankSn(String bankSn) {
		this.bankSn = bankSn;
	}
	public String getPayDatetime() {
		return payDatetime;
	}
	public void setPayDatetime(String payDatetime) {
		this.payDatetime = payDatetime;
	}
	public String getMchId() {
		return mchId;
	}
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}
	public String getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getNoAgree() {
		return noAgree;
	}

	public void setNoAgree(String noAgree) {
		this.noAgree = noAgree;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public String getEndDesc() {
		return endDesc;
	}

	public void setEndDesc(String endDesc) {
		this.endDesc = endDesc;
	}
	
}
