/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年10月12日
 * Id: RequestLogService.java, 2017年10月12日 下午4:49:43 yehao
 */
package com.yryz.quanhu.support.requestlog.service;


import com.yryz.quanhu.support.requestlog.entity.RequestLog;

/**
 * @author yehao
 * @version 1.0
 * @date 2017年10月12日 下午4:49:43
 * @Description 请求日志服务
 */
public interface RequestLogService {
	
	/**
	 * 请求日志服务
	 * @param requestLog
	 */
	public void execute(RequestLog requestLog);

}
