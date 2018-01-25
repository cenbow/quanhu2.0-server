/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年9月12日
 * Id: ThreadPoolUtil.java, 2017年9月12日 下午3:37:59 Administrator
 */
package com.yryz.quanhu.support.illegalwords.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolUtil {
	private static final ExecutorService sensitiveWord = Executors
			.newFixedThreadPool(3);

	public static void initSensitive(Runnable command) {
		sensitiveWord.execute(command);
	}  
}
