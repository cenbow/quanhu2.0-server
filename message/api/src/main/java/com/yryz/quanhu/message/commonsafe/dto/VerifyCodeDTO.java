/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年12月6日
 * Id: CommonSafeDTO.java, 2017年12月6日 下午3:10:04 Administrator
 */
package com.yryz.quanhu.message.commonsafe.dto;

import java.io.Serializable;

/**
 * 验证码接口dto
 * @author danshiyu
 * @version 1.0
 * @date 2017年12月6日 下午3:10:04
 */
@SuppressWarnings("serial")
public class VerifyCodeDTO implements Serializable {
	/**
	 * 普通验证码功能码 接入方自定义,例如:1.注册、2 .登录、3.找回密码等(图形验证码接口不用传）
	 */
	private Integer serviceCode;
	/**
	 * 业务类型 接入方自定义，例如phone(手机号)、email(邮箱)等
	 */
	private String commonServiceType;
	/**
	 * 验证码载体 例如:手机号、邮箱
	 */
	private String verifyKey;
	/**
	 * 接入方系统分配的id
	 */
	private String appId;
	
	/**
	 * 验证码（包含图像验证码和普通验证码，校验时使用）
	 */
	private String verifyCode;
	/**
	 * 是否需要删除验证码（校验时使用）
	 */
	private boolean needDelete;
	public Integer getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(Integer serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getVerifyKey() {
		return verifyKey;
	}
	public void setVerifyKey(String verifyKey) {
		this.verifyKey = verifyKey;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	
	public String getCommonServiceType() {
		return commonServiceType;
	}
	public void setCommonServiceType(String commonServiceType) {
		this.commonServiceType = commonServiceType;
	}
	
	public boolean isNeedDelete() {
		return needDelete;
	}
	public void setNeedDelete(boolean needDelete) {
		this.needDelete = needDelete;
	}
	public VerifyCodeDTO() {
		super();
	}
	/**
	 * @param serviceCode
	 * @param commonServiceType
	 * @param verifyKey
	 * @param appId
	 * @param verifyCode
	 * @exception 
	 */
	public VerifyCodeDTO(Integer serviceCode, String commonServiceType, String verifyKey, String appId,
			String verifyCode) {
		super();
		this.serviceCode = serviceCode;
		this.commonServiceType = commonServiceType;
		this.verifyKey = verifyKey;
		this.appId = appId;
		this.verifyCode = verifyCode;
	}
	
	
	
	/**
	 * @param serviceCode
	 * @param commonServiceType
	 * @param verifyKey
	 * @param appId
	 * @param verifyCode
	 * @param needDelete
	 * @exception 
	 */
	public VerifyCodeDTO(Integer serviceCode, String commonServiceType, String verifyKey, String appId,
			String verifyCode, boolean needDelete) {
		super();
		this.serviceCode = serviceCode;
		this.commonServiceType = commonServiceType;
		this.verifyKey = verifyKey;
		this.appId = appId;
		this.verifyCode = verifyCode;
		this.needDelete = needDelete;
	}
	/**
	 * @param serviceCode
	 * @param commonServiceType
	 * @param verifyKey
	 * @param appId
	 * @exception 
	 */
	public VerifyCodeDTO(Integer serviceCode, String commonServiceType, String verifyKey, String appId) {
		super();
		this.serviceCode = serviceCode;
		this.commonServiceType = commonServiceType;
		this.verifyKey = verifyKey;
		this.appId = appId;
	}
	@Override
	public String toString() {
		return "VerifyCodeDTO [serviceCode=" + serviceCode + ", commonServiceType=" + commonServiceType + ", verifyKey="
				+ verifyKey + ", appId=" + appId + ", verifyCode=" + verifyCode + ", needDelete=" + needDelete + "]";
	}
	
	
}
