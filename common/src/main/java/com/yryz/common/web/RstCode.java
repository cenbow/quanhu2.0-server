/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月8日
 * Id: ReturnRstCode.java, 2018年1月8日 上午11:18:39 Administrator
 */
package com.yryz.common.web;

/**
 * 返回码枚举<br/>
 * 100-200为网关系统专用，201-xxxxx可以开放给业务用
 * @author danshiyu
 * @version 1.0
 * @date 2018年1月8日 上午11:18:39
 */
public enum RstCode {
	/**
	 * 访问成功，为全局预知内的有效反馈（捕获了预知的异常可返回100，传参数少传不能返回)
	 */
	SUCCESS(100,"success"),
	/**
	 * 服务异常
	 */
	ERROR(102,"系统异常"),
	/**
	 * 需要token
	 */
	NEEDTOKEN(105,"请重新登录"),
	/**
	 * token失效
	 */
	TOKEN_EXPIRE(106,"token过期"),
	/**
	 * 开发者丢失参数
	 */
	PARAM_MISSING(110,"参数缺失"),
	/**
	 * 未知错误
	 */
	UNKOWN_ERROR(111,"未知错误"),
	
	/**
	 * 无效token
	 */
	TOKEN_INVALID(122,"无效token"),
	/**
	 * 没有token,后台被踢
	 */
	NO_TOKEN(123,"请重新登录");
	
	private int code;
	private String errorMsg;
	/**
	 * 
	 * @exception 
	 */
	RstCode(int code,String errorMsg) {
		this.code = code;
		this.errorMsg = errorMsg;
	}
	
	public int getCode(){
		return this.code;
	}
	public String getMsg(){
		return this.errorMsg;
	}
}
