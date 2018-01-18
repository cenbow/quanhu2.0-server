package com.yryz.quanhu.order.exception;

import com.yryz.common.exception.NestedRuntimeException;

/**
 * @author yehao
 * @version 1.0
 * @date 2017年10月16日 下午3:25:19
 * @Description 资源未找到
 */
public class SourceNotEnoughException extends NestedRuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5778693266372279790L;

	public SourceNotEnoughException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public SourceNotEnoughException(String msg) {
		super(msg);
	}

	public SourceNotEnoughException(Throwable cause) {
		super(cause);
	}

}
