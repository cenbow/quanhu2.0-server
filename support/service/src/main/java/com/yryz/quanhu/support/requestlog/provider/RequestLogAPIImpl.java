/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年10月12日
 * Id: RequestLogAPIImpl.java, 2017年10月12日 下午4:48:44 yehao
 */
package com.yryz.quanhu.support.requestlog.provider;


import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.quanhu.support.requestlog.api.RequestLogAPI;
import com.yryz.quanhu.support.requestlog.entity.RequestLog;
import com.yryz.quanhu.support.requestlog.service.RequestLogService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yehao
 * @version 1.0
 * @date 2017年10月12日 下午4:48:44
 * @Description 请求日志记录
 */

@Service
public class RequestLogAPIImpl implements RequestLogAPI {
	
	@Autowired
	private RequestLogService requestLogService;

	/**
	 * 执行并保存/更新请求日志
	 * @param requestLog
	 */
	@Override
	public void execute(RequestLog requestLog) {
		requestLogService.execute(requestLog);
	}

}
