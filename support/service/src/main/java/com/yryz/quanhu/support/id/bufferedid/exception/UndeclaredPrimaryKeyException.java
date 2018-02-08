package com.yryz.quanhu.support.id.bufferedid.exception;

import com.yryz.quanhu.support.id.bufferedid.exception.BaseGenKeyException;

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
