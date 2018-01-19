package com.yryz.quanhu.dymaic.canal.rabbitmq.handler;

import java.util.List;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.yryz.quanhu.dymaic.canal.entity.CanalMsgContent;

/**
 * canal消息处理
 *
 * @author jk
 */
public interface SyncHandler {
	/**
	 * 判断是否是自己关注的消息
	 * @param msg
	 * @return
	 */
	Boolean watch(CanalMsgContent msg);
	
	/**
	 * 处理消息 需要保证幂等性
	 * @param msg
	 */
	void handler(CanalMsgContent msg);
}
