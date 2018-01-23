package com.rongzhong.component.pay.entity;




/**
 * 代收付交易查询实体
 * @author liucx
 *
 */
public class FindCashTrans {
	private String trxCode;//交易码
	private String reqSn; //交易批次号   弱开始日期和结束日期无 则必填，
	private String merchantid; // 商户号
	private String querySn ;  //交易流水号
	private int status ;//状态  0成功,1失败, 2全部,3退票
	private int type;// 查询类型 C(2) 0.按完成日期1.按提交日期，默认为1  查询类型 C(2) 0.按完成日期1.按提交日期，默认为1 
	private String startDay ;//开始日期  YYYYMMDDHHmmss
	private String endDay;//结束日期  YYYYMMDDHHmmss
	public String getTrxCode() {
		return trxCode;
	}
	public void setTrxCode(String trxCode) {
		this.trxCode = trxCode;
	}
	public String getReqSn() {
		return reqSn;
	}
	public void setReqSn(String reqSn) {
		this.reqSn = reqSn;
	}
	public String getMerchantid() {
		return merchantid;
	}
	public void setMerchantid(String merchantid) {
		this.merchantid = merchantid;
	}
	public String getQuerySn() {
		return querySn;
	}
	public void setQuerySn(String querySn) {
		this.querySn = querySn;
	}
	public String getStartDay() {
		return startDay;
	}
	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}
	public String getEndDay() {
		return endDay;
	}
	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	

}
