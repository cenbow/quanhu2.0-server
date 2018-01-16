package com.yryz.common.exception;

/**
 * parse操作
 * @author Administrator
 *
 */
public class SearchServerException extends NestedRuntimeException {

	private static final long serialVersionUID = 8214904932910177470L;

	public SearchServerException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public SearchServerException(String msg) {
		super(msg);
	}

	public SearchServerException(Throwable cause) {
		super(cause);
	}
	
}
