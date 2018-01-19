package com.yryz.quanhu.sms.vo;

import java.io.Serializable;
/**
 * 短信配置
 * @author danshiyu
 *
 */
@SuppressWarnings("serial")
public class SmsConfigVO implements Serializable {
	/**
     * 短信通道，对应签名
     */
    private String smsChannel;
    /**
     * 验证码模板id
     */
    private String verifyTemplateId;
    /**
     * 通知类模板id，json字符串
     */
    private String msgTemplateIds;
	public String getSmsChannel() {
		return smsChannel;
	}
	public void setSmsChannel(String smsChannel) {
		this.smsChannel = smsChannel;
	}
	public String getVerifyTemplateId() {
		return verifyTemplateId;
	}
	public void setVerifyTemplateId(String verifyTemplateId) {
		this.verifyTemplateId = verifyTemplateId;
	}
	public String getMsgTemplateIds() {
		return msgTemplateIds;
	}
	public void setMsgTemplateIds(String msgTemplateIds) {
		this.msgTemplateIds = msgTemplateIds;
	}
}
