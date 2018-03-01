package com.yryz.quanhu.behavior.common.util;

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
	
	private static final ExecutorService THREAD_POOL = new ThreadPoolExecutor(8, 40, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(10000), new ThreadPoolExecutor.CallerRunsPolicy());

	public static void execue(Runnable command) {
		THREAD_POOL.execute(command);
	}

}
