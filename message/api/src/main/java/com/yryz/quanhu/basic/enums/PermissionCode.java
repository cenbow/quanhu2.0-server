/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年9月9日
 * Id: PermissionEnum.java, 2017年9月9日 下午2:59:42 Administrator
 */
package com.yryz.quanhu.basic.enums;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年9月9日 下午2:59:42
 * @Description 权限类型枚举
 */
public enum PermissionCode {
	DIS_TAIK("disTalk","禁言"),
	CREATE_COTERIE("createCoterie","创建私圈");
	
	private String code;
	
	private String name;
	
	PermissionCode(String code,String name) {
		this.name = name;
		this.code = code;
	}
	
	public String getCode(){
		return code;
	}
	
	public String getName(){
		return name;
	}
}
