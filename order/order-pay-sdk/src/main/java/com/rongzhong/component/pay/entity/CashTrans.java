package com.rongzhong.component.pay.entity;



/**
 * 提现实体
 * @author liucx
 *
 */
public class CashTrans {
	private String businessCode; // 业务代码
	private String merchantId; // 商户号
	private String submitTime; // 提交时间
	private String termCode;
	private String validate; // 有效时间
	private String track2;
	private String track3;
	private String pincode;
	private String eUserCode; // 用户编号 C(1,20) 客户编号,开发人员可当作备注字段使用
	private String bankCode; // 银行代码 银行代码，存折必须填写。见附录3.3,强烈建议填写银行代码
	private String accountType; // 账户类型 00银行卡，01存折，02信用卡。不填默认为银行卡00。
	private String accountNo; // 账号 N(1,32) 银行卡或存折号码
	private String accountName; // 账号名 C(1,60) 银行卡或存折上的所有人姓名。
	private String accountProp; // 账号属性 C (1) 0私人，1公司。不填时，默认为私人0。
	private String amount; // 金额 整数，单位分
	private String currency; // 币种 人民币：CNY, 港元：HKD，美元：USD。不填时，默认为人民币。
	private String idType; // 证件类型 0：身份证,1: 户口簿，2：护照,3.军官证,4.士兵证，5.
							// 港澳居民来往内地通行证,6. 台湾同胞来往内地通行证,7. 临时身份证,8. 外国人居留证,9.
							// 警官证, X.其他证件
	private String id; // 证件号
	private String tel; // 小灵通带区号，不带括号，减号
	private String custUserid; // 商户自定义的用户号，开发人员可当作备注字段使用
	private String remark; // 备注
	private String recvchnl;
	private String cvv2; // 信用卡CVV2 C(3) 仅用于信用卡
	private String settacct; // 本交易结算户 结算到商户的账户，不需分别清算时不需填写。
	private String tltmerid; // posp专用
	private String instid; // posp专用
	private String bizinfo; // posp专用
	private String f46; // posp专用
	private String reserved; // posp专用 标志
	private String settgroupflag; // 分组清算标志
	private String summary; // 交易附言

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	public String getValidate() {
		return validate;
	}

	public void setValidate(String validate) {
		this.validate = validate;
	}

	public String getTrack2() {
		return track2;
	}

	public void setTrack2(String track2) {
		this.track2 = track2;
	}

	public String getTrack3() {
		return track3;
	}

	public void setTrack3(String track3) {
		this.track3 = track3;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String geteUserCode() {
		return eUserCode;
	}

	public void seteUserCode(String eUserCode) {
		this.eUserCode = eUserCode;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountProp() {
		return accountProp;
	}

	public void setAccountProp(String accountProp) {
		this.accountProp = accountProp;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getCustUserid() {
		return custUserid;
	}

	public void setCustUserid(String custUserid) {
		this.custUserid = custUserid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRecvchnl() {
		return recvchnl;
	}

	public void setRecvchnl(String recvchnl) {
		this.recvchnl = recvchnl;
	}

	public String getCvv2() {
		return cvv2;
	}

	public void setCvv2(String cvv2) {
		this.cvv2 = cvv2;
	}

	public String getSettacct() {
		return settacct;
	}

	public void setSettacct(String settacct) {
		this.settacct = settacct;
	}

	public String getTltmerid() {
		return tltmerid;
	}

	public void setTltmerid(String tltmerid) {
		this.tltmerid = tltmerid;
	}

	public String getInstid() {
		return instid;
	}

	public void setInstid(String instid) {
		this.instid = instid;
	}

	public String getBizinfo() {
		return bizinfo;
	}

	public void setBizinfo(String bizinfo) {
		this.bizinfo = bizinfo;
	}

	public String getF46() {
		return f46;
	}

	public void setF46(String f46) {
		this.f46 = f46;
	}

	public String getReserved() {
		return reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

	public String getSettgroupflag() {
		return settgroupflag;
	}

	public void setSettgroupflag(String settgroupflag) {
		this.settgroupflag = settgroupflag;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

}
