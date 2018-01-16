package com.yryz.common.exception;

import com.yryz.common.web.ReturnCode;

public class BaseException extends RuntimeException {
	/**
	 */
	private static final long serialVersionUID = 1L;
	private int code;
	private String msg;
	private Object[] param=null;

	public Object[] getParam() {
		return param;
	}

	public void setParam(Object[] param) {
		this.param = param;
	}

	public BaseException(int code) {
		this.code = code;
	}
	
	public BaseException(int code, String msg) {
		super(msg);
		this.code = code;
		this.msg = msg;
	}

	public BaseException(int code, Object ...agrs) {
		param = agrs;
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	

}
