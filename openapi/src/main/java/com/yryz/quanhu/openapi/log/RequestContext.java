/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年10月12日
 * Id: RequestContext.java, 2017年10月12日 下午1:43:26 yehao
 */
package com.yryz.quanhu.openapi.log;


import com.yryz.common.utils.IdGen;
import com.yryz.quanhu.support.requestlog.entity.RequestLog;

/**
 * @author yehao
 * @version 1.0
 * @date 2017年10月12日 下午1:43:26
 * @Description 用户请求日志的Context
 */
public class RequestContext {
	
	private static ThreadLocal<RequestLog> threadLocal = new ThreadLocal<RequestLog>(){
		@Override
		protected RequestLog initialValue() {
			RequestLog requestLog = new RequestLog();
			requestLog.setRequestId(IdGen.uuid());
			return requestLog;
	    }
	};
	
	public static RequestLog getRequestLog(){
		return threadLocal.get();
	}
	
}
