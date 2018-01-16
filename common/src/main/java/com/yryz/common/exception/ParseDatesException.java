package com.yryz.common.exception;

/**
 * parse操作
 * @author Administrator
 *
 */
public class ParseDatesException extends NestedRuntimeException {

	private static final long serialVersionUID = 8214904932910177470L;

	public ParseDatesException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ParseDatesException(String msg) {
		super(msg);
	}

	public ParseDatesException(Throwable cause) {
		super(cause);
	}
	
}
