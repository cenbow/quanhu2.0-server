package com.yryz.quanhu.order.exception;

import com.yryz.common.exception.NestedRuntimeException;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseConstant;

/**
 * @author yehao
 * @version 1.0
 * @date 2017年10月16日 下午3:25:06
 * @Description 通用异常
 */
public class CommonException extends QuanhuException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2468366252134385917L;
	
	public CommonException(String msg, Throwable cause) {
		super(ResponseConstant.EXCEPTION.getCode(), msg, msg, cause);
	}

	public CommonException(String msg) {
		super(ResponseConstant.EXCEPTION.getCode(), msg, msg);
	}

}
