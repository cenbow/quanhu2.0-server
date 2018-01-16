/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月8日
 * Id: ReturnRstCode.java, 2018年1月8日 上午11:18:39 Administrator
 */
package com.yryz.common.response;

/**
 * 返回码枚举<br/>
 * 100-200为网关系统专用，201-xxxxx可以开放给业务用
 * @author danshiyu
 * @version 1.0
 * @date 2018年1月8日 上午11:18:39
 */
public enum ResponseConstant {
	/**
	 * 访问成功，为全局预知内的有效反馈（捕获了预知的异常可返回100，传参数少传不能返回)
	 */
	SUCCESS("100","success","success"),
    SYS_EXCEPTION("102",  "系统异常", "网络开小差了，请稍候再试！"),
    NEED_TOKEN("105", "用户未登录或者被挤掉了", "请重新登录"),
    TOKEN_EXPIRE("106","短期token过期","请重新登录"),
    PARAM_MISSING("110","参数缺失","%s不存在"),
    EXCEPTION("111","未知错误","网络开小差了，请稍后再试"),
    TOKEN_INVALID("122","无效token","请重新登录"),
    NO_TOKEN("123","token被后清掉了","请重新登录"),
    VALIDATE_EXCEPTION("2000","数据验证失败！","网络开小差了，请稍候再试！"),
    LOCK_EXCEPTION("3000", "分布式锁异常", "网络开小差了，请稍候再试！"),
    BUSY_EXCEPTION("4000", "业务逻辑异常", "网络开小差了，请稍候再试！");
	
	private String code;
	private String showMsg;
	private String errorMsg;
	/**
	 * 
	 * @exception 
	 */
	ResponseConstant(String code, String showMsg, String errorMsg) {
		this.code = code;
		this.showMsg = showMsg;
		this.errorMsg = errorMsg;
	}
	
	/**
	 * @return code
	 */
	public String getCode(){
		return this.code;
	}
	
	/**
	 * @return errorMsg
	 */
	public String getErrorMsg(){
		return this.errorMsg;
	}

	/**
	 * @return the showmsg
	 */
	public String getShowMsg() {
		return this.showMsg;
	}
	
	
}
