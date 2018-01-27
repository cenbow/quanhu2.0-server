package com.yryz.quanhu.openapi.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.quanhu.openapi.log.LogThreadPool;
import com.yryz.quanhu.support.requestlog.api.RequestLogAPI;
import com.yryz.quanhu.support.requestlog.entity.RequestLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/1/27
 * @description
 */
@Service
public class RequestLogService {
    private static final Logger logger = LoggerFactory.getLogger(RequestLogService.class);

    @Reference(check = false)
    private RequestLogAPI requestLogAPI;

    public void executeLog(RequestLog requestLog) {
        logRequest(requestLog);
    }


    private void logRequest(RequestLog requestLog) {
        LogThreadPool.doExecute(new Runnable() {
            @Override
            public void run() {
                try {
                    requestLogAPI.execute(requestLog);
                } catch (Exception e) {
                    logger.error("requestLogAPI execute error", e);
                }
            }
        });
    }
}
