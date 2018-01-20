/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年9月9日
 * Id: UserPermissionStatusVo.java, 2017年9月9日 下午5:02:59 Administrator
 */
package com.yryz.quanhu.message.push.entity;


import com.yryz.quanhu.message.push.enums.PermissionCode;

import java.io.Serializable;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年9月9日 下午5:02:59
 * @Description 权限请求
 */

@SuppressWarnings("serial")
public class PermissionReqDTO implements Serializable {
	/**
	 * 用户id
	 */
	private String custId;
	
	/**
	 * 权限类型
	 */
	private PermissionCode typeEnum;
	/**
	 * @param custId
	 * @param typeEnum
	 * @exception 
	 */
	public PermissionReqDTO(String custId, PermissionCode typeEnum) {
		super();
		this.custId = custId;
		this.typeEnum = typeEnum;
	}
	/**
	 * 
	 * @exception 
	 */
	public PermissionReqDTO() {
		super();
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public PermissionCode getTypeEnum() {
		return typeEnum;
	}
	public void setTypeEnum(PermissionCode typeEnum) {
		this.typeEnum = typeEnum;
	}
	@Override
	public String toString() {
		return "UserPermissionReqDTO [custId=" + custId + ", typeEnum=" + typeEnum + "]";
	}
	
}
