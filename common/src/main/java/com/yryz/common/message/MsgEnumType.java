/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年9月21日
 * Id: MsgEnumType.java, 2017年9月21日 下午2:31:34 Administrator
 */
package com.yryz.common.message;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年9月21日 下午2:31:34
 * @Description 消息枚举类型，唯一
 */
public enum MsgEnumType {
	/** 头像审核失败 */
	USER_IMG_AUDIT_FAIL("20040001");
	
	private String type;
	
	MsgEnumType(String type) {
		this.type = type;
	}
	public String getType(){
		return this.type;
	}
}
