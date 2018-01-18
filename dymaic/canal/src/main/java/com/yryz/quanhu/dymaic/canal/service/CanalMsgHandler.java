package com.yryz.quanhu.dymaic.canal.service;

import java.util.List;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.yryz.quanhu.dymaic.canal.entity.CanalMsgContent;

/**
 * canal消息处理
 *
 * @author jk
 */
public interface CanalMsgHandler {
	/**
	 * 信息 转换
	 * @param entries
	 * @return
	 */
	List<CanalMsgContent> convert(List<CanalEntry.Entry> entries);
	
	/**
	 * 消息发送到rabbitMq
	 * @param canalMsg
	 * @return
	 */
	Boolean sendMq(CanalMsgContent canalMsg);
}
