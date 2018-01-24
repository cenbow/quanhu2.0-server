/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月18日
 * Id: RrzOrderUserPhy.java, 2018年1月18日 上午10:10:25 yehao
 */
package com.yryz.quanhu.order.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月18日 上午10:10:25
 * @Description 用户安全相关
 */
public class RrzOrderUserPhy implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7862363096714344581L;

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

	/**
	 * 
	 * @exception 
	 */
	public RrzOrderUserPhy() {
		super();
	}

	/**
	 * @param custId
	 * @param phyName
	 * @param payPassword
	 * @param custIdcardNo
	 * @param custIdcardType
	 * @param createTime
	 * @param updateTime
	 * @param smallNopass
	 * @exception 
	 */
	public RrzOrderUserPhy(String custId, String phyName, String payPassword, String custIdcardNo,
			Integer custIdcardType, Date createTime, Date updateTime, Integer smallNopass) {
		super();
		this.custId = custId;
		this.phyName = phyName;
		this.payPassword = payPassword;
		this.custIdcardNo = custIdcardNo;
		this.custIdcardType = custIdcardType;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.smallNopass = smallNopass;
	}

	/**
	 * @return the custId
	 */
	public String getCustId() {
		return custId;
	}

	/**
	 * @param custId the custId to set
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}

	/**
	 * @return the phyName
	 */
	public String getPhyName() {
		return phyName;
	}

	/**
	 * @param phyName the phyName to set
	 */
	public void setPhyName(String phyName) {
		this.phyName = phyName;
	}

	/**
	 * @return the payPassword
	 */
	public String getPayPassword() {
		return payPassword;
	}

	/**
	 * @param payPassword the payPassword to set
	 */
	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	/**
	 * @return the custIdcardNo
	 */
	public String getCustIdcardNo() {
		return custIdcardNo;
	}

	/**
	 * @param custIdcardNo the custIdcardNo to set
	 */
	public void setCustIdcardNo(String custIdcardNo) {
		this.custIdcardNo = custIdcardNo;
	}

	/**
	 * @return the custIdcardType
	 */
	public Integer getCustIdcardType() {
		return custIdcardType;
	}

	/**
	 * @param custIdcardType the custIdcardType to set
	 */
	public void setCustIdcardType(Integer custIdcardType) {
		this.custIdcardType = custIdcardType;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the smallNopass
	 */
	public Integer getSmallNopass() {
		return smallNopass;
	}

	/**
	 * @param smallNopass the smallNopass to set
	 */
	public void setSmallNopass(Integer smallNopass) {
		this.smallNopass = smallNopass;
	}
	
}
