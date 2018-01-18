/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年12月7日
 * Id: VerifyCodeVO.java, 2017年12月7日 上午10:25:47 Administrator
 */
package com.yryz.quanhu.basic.entity.commonsafe;

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
	 * 验证码获取状态枚举
	 */
	private VerifyStatus status;
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
		SUCCESS(0),
		/** 失败 */
		FAIL(1),
		/** 超过限制 */
		MORETHAN_LIMIT(2),
		/** 频率过快 */
		TOO_FAST(3);
		private int status;
		VerifyStatus(int status) {
			this.status = status;
		}
		public int getStatus(){
			return this.status;
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
	public VerifyCodeVO(Integer serviceCode, String commonServiceType, String verifyCode, VerifyStatus status) {
		super();
		this.serviceCode = serviceCode;
		this.commonServiceType = commonServiceType;
		this.verifyCode = verifyCode;
		this.status = status;
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
