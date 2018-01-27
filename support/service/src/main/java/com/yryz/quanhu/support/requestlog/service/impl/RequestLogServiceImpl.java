/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年10月12日
 * Id: RequestLogServiceImpl.java, 2017年10月12日 下午4:50:46 yehao
 */
package com.yryz.quanhu.support.requestlog.service.impl;


import com.yryz.quanhu.support.requestlog.entity.RequestLog;
import com.yryz.quanhu.support.requestlog.mongo.RequestLogMongo;
import com.yryz.quanhu.support.requestlog.service.RequestLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yehao
 * @version 1.0
 * @date 2017年10月12日 下午4:50:46
 * @Description 请求日志服务实现
 */
@Service
public class RequestLogServiceImpl implements RequestLogService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private RequestLogMongo requestLogMongo;

	/**
	 * 执行请求日志
	 * @param requestLog
	 */
	@Override
	public void execute(RequestLog requestLog) {
		try {
//			String id = requestLog.getRequestId();
//			if(StringUtils.isNotEmpty(id)){
//				RequestLog log = requestLogMongo.get(id);
//				if(log == null){
//					requestLogMongo.save(requestLog);
//				} else {
//					requestLogMongo.update(requestLog);
//				}
//			}
			if(requestLog != null){
				requestLogMongo.saveLog(requestLog);
			}
		} catch (Exception e) {
			logger.info("RequestLog save mongo faild ..." , e);
		}
	}

}
