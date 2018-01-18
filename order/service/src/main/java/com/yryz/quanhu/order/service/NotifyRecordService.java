/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月18日
 * Id: NotifyRecordService.java, 2018年1月18日 上午11:02:35 yehao
 */
package com.yryz.quanhu.order.service;

import com.yryz.quanhu.order.entity.RrzNotifyRecord;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月18日 上午11:02:35
 * @Description 回调管理服务
 */
public interface NotifyRecordService {
	
	/**
	 * 添加回调通知记录
	 * @param record
	 */
	public void insert(RrzNotifyRecord record);
	
	/**
	 * 更新回调通知记录
	 * @param record
	 */
	public void update(RrzNotifyRecord record);
	
	/**
	 * 获取回调通知记录
	 * @param recordId
	 * @return
	 */
	public RrzNotifyRecord get(String recordId);

}
