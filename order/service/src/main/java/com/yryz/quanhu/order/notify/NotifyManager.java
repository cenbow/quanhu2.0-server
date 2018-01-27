/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年9月14日
 * Id: NotifyManager.java, 2017年9月14日 下午4:15:55 yehao
 */
package com.yryz.quanhu.order.notify;

import java.util.concurrent.DelayQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.yryz.common.context.SpringContextHelper;

/**
 * @author yehao
 * @version 1.0
 * @date 2017年9月14日 下午4:15:55
 * @Description 通知管理
 */
public class NotifyManager {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private volatile static NotifyManager notifyManager = null;
	
	private DelayQueue<NotifyTask> tasks ;
	
	private ThreadPoolTaskExecutor threadPool ; 
	
	private final long EXECUTE_INTERVAL = 50;
	
	private NotifyManager(){
		threadPool = (ThreadPoolTaskExecutor) SpringContextHelper.getBean(ThreadPoolTaskExecutor.class);
		tasks = new DelayQueue<NotifyTask>();
		start();
	}
	
	/**
	 * 类初始化
	 * @return
	 */
	public static NotifyManager getInstance(){
		if(notifyManager == null){
			synchronized (NotifyManager.class) {
				if(notifyManager == null){
					notifyManager = new NotifyManager();
				}
			}
		}
		return notifyManager;
	}
	
	private void start(){
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						//50毫秒执行一次
						Thread.sleep(EXECUTE_INTERVAL);
						// 如果当前活动线程等于最大线程，那么不执行
						if (threadPool.getActiveCount() < threadPool.getMaxPoolSize()) {
							final NotifyTask task = tasks.poll();
							if (task != null) {
								threadPool.execute(new Runnable() {
									@Override
									public void run() {
										logger.info(threadPool.getActiveCount() + "---------");
										tasks.remove(task);
										task.run();
									}
								});
							}
						}
					}
				} catch (Exception e) {
					logger.warn("异步线程执行失败",e);
				}
			}
		});
	}
	
	public void putTask(NotifyTask notifyTask){
		tasks.put(notifyTask);
	}

}
