/**
 * 
 */
package com.yryz.quanhu.message.sms.entity;

/**
 * 短信配置
 * @author pxie
 * @version 2016-08-18
 */
public class SmsConfigModel {
	
	private int id;
	private int expireTime; //过期时间 - 分钟
	private int dayLimit;   //单个用户每日获取短信上限
	private int sendPers;   //获取短信间隔  - 秒
	private int codeLength;
	private int status;     //短信开关  0 open， 1 close
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(int expireTime) {
		this.expireTime = expireTime;
	}
	public int getDayLimit() {
		return dayLimit;
	}
	public void setDayLimit(int dayLimit) {
		this.dayLimit = dayLimit;
	}
	public int getSendPers() {
		return sendPers;
	}
	public void setSendPers(int sendPers) {
		this.sendPers = sendPers;
	}
	public int getCodeLength() {
		return codeLength;
	}
	public void setCodeLength(int codeLength) {
		this.codeLength = codeLength;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}