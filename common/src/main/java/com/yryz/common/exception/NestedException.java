/*
 * NestedException.java
 * Copyright (c) 2011,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2011-3-8 Created
 */
package com.yryz.common.exception;

/**
 * <p>
 * 自定义异常基类
 * </p>
 * 
 * @author kinjoYang
 * @version 1.0 2011-3-8
 * @since 1.0
 * @see com.rongzhong.core.exception.NestedRuntimeException
 */
public class NestedException extends NestedRuntimeException {

	private static final long serialVersionUID = -4775190692869227607L;

	int type = ExceptionDescriptor.EXCEPTION_DEF;

	public NestedException(String msg) {
		super(msg);
	}

	public NestedException(Throwable cause) {
		super(cause);
	}

	public NestedException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public NestedException(int t, Throwable cause) {
		super(cause);
		this.type = t;
	}

	public NestedException(String msg, int t, Throwable cause) {
		super(msg, cause);
		this.type = t;
	}

	/**
	 * Return the detail message, including the message from the g3check
	 * exception if there is one.
	 */
	@Override
    public String getMessage() {
		return super.getMessage(type);
	}
}
