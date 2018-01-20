package com.yryz.quanhu.message.sms.dto;

import java.io.Serializable;
import java.util.Map;
/**
 * 短信发送dto
 * @author danshiyu
 *
 */
@SuppressWarnings("serial")
public class SmsDTO implements Serializable {
	/**
	 * 应用id
	 */
	private String appId;
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 短信类型
	 */
	private SmsType smsType;
	/**
	 * 通知类短信模板id,短信验证码不用传
	 */
	private Long msgTemplateId;
	/**
	 * 短信模板传参，根据实际申请的模板内容来设置
	 */
	private Map<String,Object> smsParams;
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public SmsType getSmsType() {
		return smsType;
	}

	public void setSmsType(SmsType smsType) {
		this.smsType = smsType;
	}

	public Long getMsgTemplateId() {
		return msgTemplateId;
	}

	public void setMsgTemplateId(Long msgTemplateId) {
		this.msgTemplateId = msgTemplateId;
	}

	public Map<String, Object> getSmsParams() {
		return smsParams;
	}

	public void setSmsParams(Map<String, Object> smsParams) {
		this.smsParams = smsParams;
	}
	
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public SmsDTO() {
		super();
	}
	
	/**
	 * 验证码
	 * @param phone
	 * @param smsType
	 * @param smsParams
	 */
	public SmsDTO(String phone,String appId, SmsType smsType, Map<String, Object> smsParams) {
		super();
		this.phone = phone;
		this.appId = appId;
		this.smsType = smsType;
		this.smsParams = smsParams;
	}
	/**
	 * 短信通知
	 * @param phone
	 * @param smsType
	 * @param msgTemplateId
	 * @param smsParams
	 */
	public SmsDTO(String phone,String appId, SmsType smsType, Long msgTemplateId, Map<String, Object> smsParams) {
		super();
		this.phone = phone;
		this.appId = appId;
		this.smsType = smsType;
		this.msgTemplateId = msgTemplateId;
		this.smsParams = smsParams;
	}

	@Override
	public String toString() {
		return "SmsDTO [phone=" + phone + ", smsType=" + smsType + ", msgTemplateId=" + msgTemplateId + ", smsParams="
				+ smsParams + "]";
	}

	/**
	 * 短信类型枚举
	 * 
	 */
	public enum SmsType{
		/** 短信验证码 */
		VERIFY_CODE(10),
		/** 短信通知 */
		SMS_MSG(11);
		private int type;
		
		SmsType(int type) {
			this.type = type;
		}
		public int getType(){
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
	}
}
