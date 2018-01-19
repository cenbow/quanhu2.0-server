package com.yryz.quanhu.message.entity.config;

import java.io.Serializable;
import java.util.List;

/**
 * 短信模板配置
 * @author danshiyu
 *
 */
@SuppressWarnings("serial")
public class SmsTemplateCofigDTO implements Serializable {
	/**
	 * 短信验证码模板id
	 */
	private Integer verifyCodeTemplateId;
	
	/**
	 * 全部短信通知模板id
	 */
	private List<Integer> msgTemplateIds;
	
	/**
	 * ip限制开关
	 */
	private Boolean ipLimitFlag;
	/**
	 * ip每天限制数量
	 */
	private Integer ipLimitNum;
	/**
	 * 不校验图形验证码的次数
	 */
	private Integer imgNoCheckNum;
	public Integer getVerifyCodeTemplateId() {
		return verifyCodeTemplateId;
	}

	public void setVerifyCodeTemplateId(Integer verifyCodeTemplateId) {
		this.verifyCodeTemplateId = verifyCodeTemplateId;
	}

	public List<Integer> getMsgTemplateIds() {
		return msgTemplateIds;
	}

	public void setMsgTemplateIds(List<Integer> msgTemplateIds) {
		this.msgTemplateIds = msgTemplateIds;
	}

	public Boolean getIpLimitFlag() {
		return ipLimitFlag;
	}

	public void setIpLimitFlag(Boolean ipLimitFlag) {
		this.ipLimitFlag = ipLimitFlag;
	}

	public Integer getIpLimitNum() {
		return ipLimitNum;
	}

	public void setIpLimitNum(Integer ipLimitNum) {
		this.ipLimitNum = ipLimitNum;
	}

	public Integer getImgNoCheckNum() {
		return imgNoCheckNum;
	}

	public void setImgNoCheckNum(Integer imgNoCheckNum) {
		this.imgNoCheckNum = imgNoCheckNum;
	}

	@Override
	public String toString() {
		return "SmsTemplateCofigDTO [verifyCodeTemplateId=" + verifyCodeTemplateId + ", msgTemplateIds="
				+ msgTemplateIds + ", ipLimitFlag=" + ipLimitFlag + ", ipLimitNum=" + ipLimitNum + ", imgNoCheckNum="
				+ imgNoCheckNum + "]";
	}
	
}
