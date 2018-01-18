/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月18日
 * Id: NotifyRecordServiceImpl.java, 2018年1月18日 下午2:17:25 yehao
 */
package com.yryz.quanhu.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.yryz.quanhu.order.dao.persistence.NotifyRecordDao;
import com.yryz.quanhu.order.entity.RrzNotifyRecord;
import com.yryz.quanhu.order.service.NotifyRecordService;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月18日 下午2:17:25
 * @Description 回调记录管理
 */
public class NotifyRecordServiceImpl implements NotifyRecordService {
	
	@Autowired
	private NotifyRecordDao notifyRecordDao;

	/**
	 * 添加记录
	 * @param record
	 * @see com.yryz.quanhu.order.service.NotifyRecordService#insert(com.yryz.quanhu.order.entity.RrzNotifyRecord)
	 */
	@Override
	public void insert(RrzNotifyRecord record) {
		notifyRecordDao.insert(record);
	}

	/**
	 * 更新记录
	 * @param record
	 * @see com.yryz.quanhu.order.service.NotifyRecordService#update(com.yryz.quanhu.order.entity.RrzNotifyRecord)
	 */
	@Override
	public void update(RrzNotifyRecord record) {
		notifyRecordDao.update(record);
	}

	/**
	 * 获取记录详情
	 * @param recordId
	 * @return
	 * @see com.yryz.quanhu.order.service.NotifyRecordService#get(java.lang.String)
	 */
	@Override
	public RrzNotifyRecord get(String recordId) {
		return notifyRecordDao.get(recordId);
	}

}
