/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月22日
 * Id: UserPhyDTO.java, 2018年1月22日 下午4:52:42 yehao
 */
package com.yryz.quanhu.openapi.order.dto;

import java.util.Date;

import com.yryz.quanhu.openapi.utils.CommonUtils;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月22日 下午4:52:42
 * @Description 用户安全信息DTO
 */
public class UserPhyDTO {
	
	/**
	 * 用户ID
	 */
	private String custId;
	/**
	 * 用户真实姓名
	 */
	private String phyName;
	/**
	 * 支付密码
	 */
	private String payPassword;
	/**
	 * 证件号码
	 */
	private String custIdcardNo;
	/**
	 * 证件类型
	 */
	private Integer custIdcardType;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 是否开通小额免密，0，不开通；1，开通
	 */
	private Integer smallNopass;

	public UserPhyDTO() {
		super();
	}

	public UserPhyDTO(String custId, String phyName, String payPassword, String custIdcardNo,
			Integer custIdcardType, Date createTime, Date updateTime, Integer smallNopass) {
		super();
		this.custId = custId;
		this.phyName = phyName;
		this.payPassword = payPassword;
		this.custIdcardNo = CommonUtils.getIdCardNo(custIdcardNo);
		this.custIdcardType = custIdcardType;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.smallNopass = smallNopass;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getPhyName() {
		return phyName;
	}

	public void setPhyName(String phyName) {
		this.phyName = phyName;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	public String getCustIdcardNo() {
		return CommonUtils.getIdCardNo(this.custIdcardNo);
	}

	public void setCustIdcardNo(String custIdcardNo) {
		this.custIdcardNo = CommonUtils.getIdCardNo(custIdcardNo);
	}

	public Integer getCustIdcardType() {
		return custIdcardType;
	}

	public void setCustIdcardType(Integer custIdcardType) {
		this.custIdcardType = custIdcardType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getSmallNopass() {
		return smallNopass;
	}

	public void setSmallNopass(Integer smallNopass) {
		this.smallNopass = smallNopass;
	}

	@Override
	public String toString() {
		return "RrzOrderUserPhy [custId=" + custId + ", phyName=" + phyName + ", payPassword=" + payPassword
				+ ", custIdcardNo=" + custIdcardNo + ", custIdcardType=" + custIdcardType + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", smallNopass=" + smallNopass + "]";
	}

}
