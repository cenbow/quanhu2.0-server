package com.yryz.quanhu.coterie.exception;

/**
 * mysql操作
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class MysqlOptException extends DatasOptException {

	public MysqlOptException(String msg, Throwable cause) {
		super("[mysql]" + msg, cause);
	}

	public MysqlOptException(String msg) {
		super("[mysql]" + msg);
	}

	public MysqlOptException(Throwable cause) {
		super(cause);
	}
	
}
