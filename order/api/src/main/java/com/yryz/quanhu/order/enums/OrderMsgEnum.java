/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年9月19日
 * Id: OrderMsgEnum.java, 2017年9月19日 下午4:06:40 yehao
 */
package com.yryz.quanhu.order.enums;

/**
 * @author yehao
 * @version 1.0
 * @date 2017年9月19日 下午4:06:40
 * @Description 订单类型枚举
 */
public enum OrderMsgEnum {
	
	NOT_ENOUGH("用户余额不足",1000);
	
	
	/**
	 * @param msg
	 * @param code
	 * @exception 
	 */
	private OrderMsgEnum(String msg, int code) {
		this.msg = msg;
		this.code = code;
	}
	private String msg;
	private int code;
	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}
	
}
