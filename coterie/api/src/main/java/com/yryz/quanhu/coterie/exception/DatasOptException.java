package com.yryz.quanhu.coterie.exception;


/**
 * 自定义数据库操作异常
 * @author xiepeng
 */
@SuppressWarnings("serial")
public class DatasOptException extends RuntimeException {

	public DatasOptException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public DatasOptException(String msg) {
		super(msg);
	}

	public DatasOptException(Throwable cause) {
		super(cause);
	}
	
}
