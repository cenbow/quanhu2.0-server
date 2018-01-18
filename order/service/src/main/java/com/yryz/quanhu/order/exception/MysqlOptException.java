package com.yryz.quanhu.order.exception;

import com.yryz.common.exception.NestedRuntimeException;

/**
 * mysql操作
 * @author Administrator
 *
 */
public class MysqlOptException extends NestedRuntimeException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 256477053359224237L;

	public MysqlOptException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public MysqlOptException(String msg) {
		super(msg);
	}

	public MysqlOptException(Throwable cause) {
		super(cause);
	}
	
}
