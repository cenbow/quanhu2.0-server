package com.rongzhong.component.pay.chinapay;
/**
 * @author huang.xuting
 *
 */

public class TransactionBean {
	private String merId;       //商户号，数字，定长15位
	private String merDate;     //商户日期yyyyMMdd,数字，定长8位
	private String merSeqId;    //流水号，数字，变长16位
	private String cardNo;      //收款账号，数字，变长25位
	private String usrName;    //收款人姓名，字符，变长20位
	private String openBank;    //开户银行名称，字符，变长50位
	private String prov;        //省份，字符，变长20位
	private String city;        //城市，字符，变长40位
	private String transAmt;    //金额（单位分），数字，变长12位
	private String purpose;     //用途，字符，变长25位
	private String subBank;     //支行名称，字符，变长80位
	private String flag;        //付款标志，字符，定长2位
	private String version;     //版本号，字符，变长8位
	private String chkValue;    //交易签名，字符，定长256位
	
	//应答数据
	private String responseCode; //应答码
	private String cpDate;       //Cp接收到交易的日期，定长8位
	private String cpSeqId;      //Cp流水，定长6位
	private String stat;         //交易状态码，定长1位
	private String feeAmt;       //手续费（单位分），变长12位
	private String backDate;     //退单日期，定长8位
	
	//批量退单查询数据
	private String fromDate;     //开始退单日期，定长8位
	private String toDate;       //结束退单日期，变长8位
	
	//备付金余额查询数据                         
	private String merAmt;       //备付金余额（单位分），定长15位
	
	//备付金明细查询数据
	private String type;         //查询类别，定长2位
	
	//返回报文数据
	private String data;         //控台返回报文
	
	private String termType;
	
	private String signFlag = "1";	
	
	public String toString(){
		return new StringBuffer(merId).append(merDate)
		.append(merSeqId).append(cardNo)
		.append(usrName).append(openBank)
		.append(prov).append(city)
		.append(transAmt).append(purpose)
		.append(subBank)
		.append(flag)
		.append(version).append(termType).toString();
	}
	
	public String transString(){
		return new StringBuffer("merId=").append(merId).append(" & merDate=").append(merDate)
		.append(" & merSeqId=").append(merSeqId).append(" & cardNo=").append(cardNo)
		.append(" & usrName=").append(usrName).append(" & openBank=").append(openBank)
		.append(" & prov=").append(prov).append(" & city=").append(city)
		.append(" & transAmt=").append(transAmt).append(" & purpose=").append(purpose)
		.append(" & subBank=").append(subBank)
		.append(" & flag=").append(flag)
		.append(" & version=").append(version).append(termType).toString();
	}
	

	public String getTermType() {
		return termType;
	}

	public void setTermType(String termType) {
		this.termType = termType;
	}

	public String getChkValue() {
		return chkValue;
	}
	public void setChkValue(String chkValue) {
		this.chkValue = chkValue;
	}

	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getUsrName() {
		return usrName;
	}
	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}

	public String getMerDate() {
		return merDate;
	}

	public void setMerDate(String merDate) {
		this.merDate = merDate;
	}

	public String getMerSeqId() {
		return merSeqId;
	}

	public void setMerSeqId(String merSeqId) {
		this.merSeqId = merSeqId;
	}

	public String getOpenBank() {
		return openBank;
	}

	public void setOpenBank(String openBank) {
		this.openBank = openBank;
	}

	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTransAmt() {
		return transAmt;
	}

	public void setTransAmt(String transAmt) {
		this.transAmt = transAmt;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getSubBank() {
		return subBank;
	}

	public void setSubBank(String subBank) {
		this.subBank = subBank;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getCpDate() {
		return cpDate;
	}

	public void setCpDate(String cpDate) {
		this.cpDate = cpDate;
	}

	public String getCpSeqId() {
		return cpSeqId;
	}

	public void setCpSeqId(String cpSeqId) {
		this.cpSeqId = cpSeqId;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getFeeAmt() {
		return feeAmt;
	}

	public void setFeeAmt(String feeAmt) {
		this.feeAmt = feeAmt;
	}

	public String getBackDate() {
		return backDate;
	}

	public void setBackDate(String backDate) {
		this.backDate = backDate;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getMerAmt() {
		return merAmt;
	}

	public void setMerAmt(String merAmt) {
		this.merAmt = merAmt;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getSignFlag() {
		return signFlag;
	}

	public void setSignFlag(String signFlag) {
		this.signFlag = signFlag;
	}
	
}
