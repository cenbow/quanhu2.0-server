/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年9月14日
 * Id: NotifyQueue.java, 2017年9月14日 下午7:20:32 yehao
 */
package com.yryz.quanhu.order.notify;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.order.entity.RrzNotifyRecord;
import com.yryz.quanhu.order.entity.RrzOrderInfo;
import com.yryz.quanhu.order.service.NotifyRecordService;

/**
 * @author yehao
 * @version 1.0
 * @date 2017年9月14日 下午7:20:32
 * @Description 回调队列
 */
@Service
public class NotifyQueue {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	NotifyManager notifyManager = null;
	
	@Autowired
	private NotifyRecordService notifyRecordService;
	
	public void addOrderInfoNotify(RrzOrderInfo orderInfo){
		logger.info("addOrderInfoNotify... orderinfo: " + GsonUtils.parseJson(orderInfo));
		if(notifyManager == null){
			notifyManager = NotifyManager.getInstance();
		}
		RrzNotifyRecord notifyRecord = new RrzNotifyRecord();
		notifyRecord.setCreateTime(new Date());
		notifyRecord.setMsg(GsonUtils.parseJson(orderInfo));
		notifyRecord.setUrl(orderInfo.getCallback());
		notifyRecord.setNotifyTimes(0);
		notifyRecord.setLimitNotifyTimes(10);
//		notifyRecord.setRecordId(IdGen.uuid());
		notifyRecord.setLastNotifyTime(new Date());
		addTask(notifyRecord);
	}
	
	public void addTask(RrzNotifyRecord notifyRecord){
		if(notifyRecord == null){
			return ;
		}
		int notifyTimes = notifyRecord.getNotifyTimes() == null ? 0 : notifyRecord.getNotifyTimes();
		int limitNotifyTimes = notifyRecord.getLimitNotifyTimes() == null ? 0 : notifyRecord.getLimitNotifyTimes();
		if(notifyTimes < limitNotifyTimes){
            long times = notifyRecord.getLastNotifyTime().getTime() + 1;
            notifyRecord.setLastNotifyTime(new Date(times));
            notifyManager.putTask(new NotifyTask(this , notifyRecordService, notifyRecord));
		} else {
			logger.info("nofity order hava reach the upper limit , recorder:" + GsonUtils.parseJson(notifyRecord));
			try {
				notifyRecordService.update(notifyRecord);
			} catch (Exception e) {
				logger.warn("update nofityRecord faild",e);
			}
		}
	}

}
