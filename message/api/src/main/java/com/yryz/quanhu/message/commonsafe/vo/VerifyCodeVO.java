/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年12月7日
 * Id: VerifyCodeVO.java, 2017年12月7日 上午10:25:47 Administrator
 */
package com.yryz.quanhu.message.commonsafe.vo;

import java.io.Serializable;

/**
 * 验证码vo
 * @author danshiyu
 * @version 1.0
 * @date 2017年12月7日 上午10:25:47
 */
@SuppressWarnings("serial")
public class VerifyCodeVO implements Serializable {
	/**
	 * 功能码（接入方自定义）
	 */
	private Integer serviceCode;
	/**
	 * 业务类型
	 */
	private String commonServiceType;
	/**
	 * 验证码
	 */
	private String verifyCode;
	/**
	 * 过期时间
	 */
	private long expireAt;
	/**
	 * 验证码获取状态枚举
	 */
	private VerifyStatus status;

	/**
	 * 是否需要    图像验证码/滑动验证码
	 * @return
	 */
	String isSendViewCode;

	public String getIsSendViewCode() {
		return isSendViewCode;
	}

	public void setIsSendViewCode(String isSendViewCode) {
		this.isSendViewCode = isSendViewCode;
	}

	public Integer getServiceCode() {
		return serviceCode;
	}


	public void setServiceCode(Integer serviceCode) {
		this.serviceCode = serviceCode;
	}


	public String getCommonServiceType() {
		return commonServiceType;
	}


	public void setCommonServiceType(String commonServiceType) {
		this.commonServiceType = commonServiceType;
	}


	public long getExpireAt() {
		return expireAt;
	}


	public void setExpireAt(long expireAt) {
		this.expireAt = expireAt;
	}


	public String getVerifyCode() {
		return verifyCode;
	}


	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}



	public VerifyStatus getStatus() {
		return status;
	}


	public void setStatus(VerifyStatus status) {
		this.status = status;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VerifyCodeVO other = (VerifyCodeVO) obj;
		if (commonServiceType != other.commonServiceType)
			return false;
		if (serviceCode != other.serviceCode)
			return false;
		if (verifyCode == null) {
			if (other.verifyCode != null)
				return false;
		} else if (!verifyCode.equals(other.verifyCode))
			return false;
		return true;
	}

	/**
	 * 验证码获取状态
	 */
	public enum VerifyStatus{
		/** 成功 */
		SUCCESS(0,"success"),
		/** 失败 */
		FAIL(1,"发送失败"),
		/** 超过限制 */
		MORETHAN_LIMIT(2,"日发送总量超过了"),
		/** 频率过快 */
		TOO_FAST(3,"超过了短信发送频率");
		private int status;
		private String msg;
		VerifyStatus(int status,String msg) {
			this.status = status;
			this.msg = msg;
		}
		public int getStatus(){
			return this.status;
		}
		public String getMsg(){
			return this.msg;
		}
	}

	/**
	 * @param serviceCode
	 * @param commonServiceType
	 * @param verifyCode
	 * @exception 
	 */
	public VerifyCodeVO(Integer serviceCode, String commonServiceType, String verifyCode) {
		super();
		this.serviceCode = serviceCode;
		this.commonServiceType = commonServiceType;
		this.verifyCode = verifyCode;
	}


	/**
	 * @param serviceCode
	 * @param commonServiceType
	 * @param verifyCode
	 * @param status
	 * @exception 
	 */
	public VerifyCodeVO(Integer serviceCode, String commonServiceType, String verifyCode, VerifyStatus status,long expireAt) {
		super();
		this.serviceCode = serviceCode;
		this.commonServiceType = commonServiceType;
		this.verifyCode = verifyCode;
		this.status = status;
		this.expireAt = expireAt;
	}


	public VerifyCodeVO() {
		super();
	}


	@Override
	public String toString() {
		return "VerifyCodeVO [serviceCode=" + serviceCode + ", commonServiceType=" + commonServiceType + ", verifyCode="
				+ verifyCode + ", status=" + status + "]";
	}
	
	
}
