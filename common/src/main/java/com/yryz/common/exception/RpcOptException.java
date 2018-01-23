/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月22日
 * Id: RpcOptException.java, 2018年1月22日 下午4:01:44 yehao
 */
package com.yryz.common.exception;

import com.yryz.common.response.ResponseConstant;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月22日 下午4:01:44
 * @Description RPC操作异常
 */
public class RpcOptException extends QuanhuException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8687727848022180460L;
	
	public RpcOptException(String msg, Throwable cause) {
		super(ResponseConstant.SYS_EXCEPTION.getCode(), msg, msg, cause);
	}

	public RpcOptException(String msg) {
		super(ResponseConstant.SYS_EXCEPTION.getCode(), msg, msg);
	}


}
