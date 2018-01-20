package com.yryz.quanhu.configuration;

import java.io.Serializable;

import org.springframework.context.annotation.Configuration;
/**
 * 短信配置
 * @author danshiyu
 *
 */
@SuppressWarnings("serial")
@Configuration
public class SmsConfigVO implements Serializable {
	/**
     * 短信签名
     */
    private Long smsSignId = 1l;
    /**
     * 验证码模板id
     */
    private Long verifyTemplateId = 1l;
    /**
     * 通知类模板id，json字符串
     */
    private String msgTemplateIds = "[\"3\",\"4\"]";
	public Long getSmsSignId() {
		return smsSignId;
	}
	public void setSmsSignId(Long smsSignId) {
		this.smsSignId = smsSignId;
	}
	public Long getVerifyTemplateId() {
		return verifyTemplateId;
	}
	public void setVerifyTemplateId(Long verifyTemplateId) {
		this.verifyTemplateId = verifyTemplateId;
	}
	public String getMsgTemplateIds() {
		return msgTemplateIds;
	}
	public void setMsgTemplateIds(String msgTemplateIds) {
		this.msgTemplateIds = msgTemplateIds;
	}
}
