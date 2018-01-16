package com.yryz.common.exception;


/**
 * mysql操作
 * @author Administrator
 *
 */
public class RedisOptException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 256477053359224237L;

	public RedisOptException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public RedisOptException(String msg) {
		super(msg);
	}

	public RedisOptException(Throwable cause) {
		super(cause);
	}
	
}
