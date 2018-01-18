/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月18日
 * Id: NotifyRecordDao.java, 2018年1月18日 上午11:14:03 yehao
 */
package com.yryz.quanhu.order.dao.persistence;

import com.yryz.quanhu.order.entity.RrzNotifyRecord;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月18日 上午11:14:03
 * @Description 回调管理
 */
public interface NotifyRecordDao {
	
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
	 * @param recordId 记录ID
	 * @return
	 */
	public RrzNotifyRecord get(String recordId);


}
