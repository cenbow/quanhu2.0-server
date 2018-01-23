package com.rongzhong.component.pay.wxpay.util;



public class PayException extends RuntimeException {
	/**
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private Object[] param=null;

	public Object[] getParam() {
		return param;
	}

	public void setParam(Object[] param) {
		this.param = param;
	}

	public PayException(String code) {
		super(code);
		this.code = code;
	}

	public PayException(String code, Object ...agrs) {
		super(code);
		param = agrs;
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
