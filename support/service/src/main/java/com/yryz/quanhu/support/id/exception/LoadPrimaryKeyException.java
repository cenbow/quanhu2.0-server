package com.yryz.quanhu.support.id.exception;

public class LoadPrimaryKeyException extends BaseGenKeyException {

	private static final long serialVersionUID = 1L;

	public LoadPrimaryKeyException() {
		super();
	}

	public LoadPrimaryKeyException(String message) {
		super(message);
	}

	public LoadPrimaryKeyException(String message, Throwable cause) {
		super(message, cause);
	}
}
