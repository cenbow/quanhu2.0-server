/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年10月21日
 * Id: ThreadPoolUtils.java, 2017年10月21日 上午11:28:21 Administrator
 */
package com.yryz.quanhu.message.notice.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年10月21日 上午11:28:21
 * @Description 线程池工具
 */
public class ThreadPoolUtils {
	private static final ExecutorService syncExe = new ThreadPoolExecutor(8, 8, 0, TimeUnit.MICROSECONDS, new LinkedBlockingQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());
	
	public static void newThread(Runnable command) {
		syncExe.execute(command);
	}
}
