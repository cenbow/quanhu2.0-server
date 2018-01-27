/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年10月12日
 * Id: RequestLogAPI.java, 2017年10月12日 下午4:44:56 yehao
 */
package com.yryz.quanhu.support.requestlog.api;


import com.yryz.quanhu.support.requestlog.entity.RequestLog;

/**
 * @author yehao
 * @version 1.0
 * @date 2017年10月12日 下午4:44:56
 * @Description 日志请求API
 */
public interface RequestLogAPI {
	/**
	 * 记录api日志
	 * @param requestLog
	 * @Description 记录api日志
	 */
	public void execute(RequestLog requestLog);

}
