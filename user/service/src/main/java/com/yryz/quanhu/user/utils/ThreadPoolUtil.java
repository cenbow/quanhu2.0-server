package com.yryz.quanhu.user.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具
 * @author yehao
 *
 */
public class ThreadPoolUtil {
	
	private static final ExecutorService API_LOG_EXE = new ThreadPoolExecutor(8, 8, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(10000), new ThreadPoolExecutor.CallerRunsPolicy());

	public static void insertApiLog(Runnable command) {
		API_LOG_EXE.execute(command);
	}

}
