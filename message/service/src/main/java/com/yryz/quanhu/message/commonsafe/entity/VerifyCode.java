/*
 * VerifyCodeModel.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2017-12-10 Created
 */
package com.yryz.quanhu.message.commonsafe.entity;

import java.util.Date;

import com.yryz.common.entity.GenericEntity;

/**
 * 验证码表
 * 
 * @author xxx
 * @version 1.0 2017-12-10
 * @since 1.0
 */
@SuppressWarnings("serial")
public class VerifyCode extends GenericEntity{

    /**
     * 验证码载体例如，手机号、邮箱地址
     */
    private String verifyKey;

    /**
     * 业务类型，比如phone、email
     */
    private String serviceType;

    /**
     * 验证码
     */
    private String verifyCode;

    private Byte serviceCode;

    public String getVerifyKey() {
        return verifyKey;
    }

    public void setVerifyKey(String verifyKey) {
        this.verifyKey = verifyKey == null ? null : verifyKey.trim();
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType == null ? null : serviceType.trim();
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode == null ? null : verifyCode.trim();
    }

    public Byte getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(Byte serviceCode) {
        this.serviceCode = serviceCode;
    }


	/**
	 * 
	 * @exception 
	 */
	public VerifyCode() {
		super();
	}

	/**
	 * @param key
	 * @param serviceType
	 * @param verifyCode
	 * @param serviceCode
	 * @param createDate
	 * @exception 
	 */
	public VerifyCode(String verifyKey, String serviceType, String verifyCode, Byte serviceCode, Date createDate) {
		super();
		this.verifyKey = verifyKey;
		this.serviceType = serviceType;
		this.verifyCode = verifyCode;
		this.serviceCode = serviceCode;
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "VerifyCode [verifyKey=" + verifyKey + ", serviceType=" + serviceType + ", verifyCode=" + verifyCode
				+ ", serviceCode=" + serviceCode + "]";
	}
}