package com.yryz.quanhu.dymaic.canal.job;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component
public class DiffExecutor {
	@Resource
	private ThreadPoolTaskExecutor poolTaskExecutor;
	private List<DiffHandler> handlerList=Lists.newArrayList();
	/**
	 * 执行同步
	 * @param msg
	 */
	public void process(){
		for (int i = 0; i < handlerList.size(); i++) {
			DiffHandler handler=handlerList.get(i);
			if(handler!=null){
				poolTaskExecutor.execute(new Runnable() {
					@Override
					public void run() {
						handler.handler();					
					}
				});
			}
		}
	}
	
	protected void register(DiffHandler handler){
		if(!handlerList.contains(handler)){
			handlerList.add(handler);
		}
	}
}
