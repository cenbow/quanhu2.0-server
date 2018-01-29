package com.yryz.quanhu.openapi.log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/1/27
 * @description
 */
public class LogThreadPool {

    private static final ExecutorService API_LOG_EXE = new ThreadPoolExecutor(8, 8, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(10000), new ThreadPoolExecutor.CallerRunsPolicy());

    public static void doExecute(Runnable command) {
        API_LOG_EXE.execute(command);
    }
}
