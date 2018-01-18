package com.yryz.quanhu.support.id.exception;

public class UndeclaredPrimaryKeyException extends BaseGenKeyException {

	private static final long serialVersionUID = 1L;

	public UndeclaredPrimaryKeyException() {
		super();
	}

	public UndeclaredPrimaryKeyException(String message) {
		super(message);
	}

	public UndeclaredPrimaryKeyException(String message, Throwable cause) {
		super(message, cause);
	}
}
