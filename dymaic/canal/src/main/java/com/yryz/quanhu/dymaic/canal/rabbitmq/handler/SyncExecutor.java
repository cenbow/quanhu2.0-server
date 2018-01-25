package com.yryz.quanhu.dymaic.canal.rabbitmq.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.yryz.common.entity.CanalMsgContent;

@Component
public class SyncExecutor {
	private List<SyncHandler> handlerList=Lists.newArrayList();
	/**
	 * 执行同步
	 * @param msg
	 */
	public void process(CanalMsgContent msg){
		for (int i = 0; i < handlerList.size(); i++) {
			SyncHandler syncHandler=handlerList.get(i);
			if(syncHandler.watch(msg)){
				syncHandler.handler(msg);
			}
		}
	}
	
	protected void register(SyncHandler handler){
		if(!handlerList.contains(handler)){
			handlerList.add(handler);
		}
	}
	
}
