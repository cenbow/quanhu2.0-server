/**
 * 
 */
package com.yryz.quanhu.sms.entity;

/**
 * 短信发送记录
 * @author pxie
 * @version 2016-08-18
 */
public class SmsLogsModel {
	
	private String custId;
	private long lastSentTime; //最后发送时间
	private int dayLimit;     //今日已发送次数
	
	
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public long getLastSentTime() {
		return lastSentTime;
	}
	public void setLastSentTime(long lastSentTime) {
		this.lastSentTime = lastSentTime;
	}
	public int getDayLimit() {
		return dayLimit;
	}
	public void setDayLimit(int dayLimit) {
		this.dayLimit = dayLimit;
	}
	
}