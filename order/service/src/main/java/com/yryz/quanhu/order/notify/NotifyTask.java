/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年9月14日
 * Id: NotifyTask.java, 2017年9月14日 下午3:18:58 yehao
 */
package com.yryz.quanhu.order.notify;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yryz.common.utils.HTTPWeb;
import com.yryz.common.utils.IdGen;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.order.entity.RrzNotifyRecord;
import com.yryz.quanhu.order.service.NotifyRecordService;

/**
 * @author yehao
 * @version 1.0
 * @date 2017年9月14日 下午3:18:58
 * @Description 通知任务
 */
public class NotifyTask implements Runnable , Delayed {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private long executeTime;
	
	private NotifyQueue notifyQueue;
	
	private RrzNotifyRecord rrzNotifyRecord;
	
	private NotifyRecordService notifyRecordService;
	
	/**
	 * @param notifyQueue
	 * @param rrzNotifyRecord
	 * @exception 
	 */
	public NotifyTask(NotifyQueue notifyQueue, NotifyRecordService notifyRecordService,RrzNotifyRecord rrzNotifyRecord) {
		super();
		this.notifyQueue = notifyQueue;
		this.rrzNotifyRecord = rrzNotifyRecord;
		this.notifyRecordService = notifyRecordService;
		this.executeTime = getExecuteTime(rrzNotifyRecord);
	}

	/**
	 * @param o
	 * @return
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Delayed o) {
		if(o instanceof NotifyTask){
			NotifyTask task = (NotifyTask) o;
			return executeTime > task.executeTime ? 1 : (executeTime < task.executeTime ? -1 : 0);
		} else {
			return 0;
		}
	}

	/**
	 * @param unit
	 * @return
	 * @see java.util.concurrent.Delayed#getDelay(java.util.concurrent.TimeUnit)
	 */
	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(executeTime - System.currentTimeMillis(), TimeUnit.SECONDS);
	}
	
	/**
	 * 获取执行时间
	 * @param record
	 * @return
	 */
    private long getExecuteTime(RrzNotifyRecord record) {
        long lastTime = record.getLastNotifyTime().getTime();
        Integer nextNotifyTime = NotifyParam.getNotifyTime(record.getNotifyTimes());
        return (nextNotifyTime == null ? 0 : nextNotifyTime * 1000) + lastTime;
    }

	/**
	 * 执行主体
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		String url = rrzNotifyRecord.getUrl();
		String msg = rrzNotifyRecord.getMsg();
		Map<String, String> map = new HashMap<>(1);
		map.put("msg", msg);
		String result = null;
		try {
			result = HTTPWeb.post(url, map);
		} catch (Exception e) {
			logger.info("order callback exception",e);
		}
		//设置当前更新时间
		rrzNotifyRecord.setLastNotifyTime(new Date());
		//设置更新次数
		rrzNotifyRecord.setNotifyTimes(rrzNotifyRecord.getNotifyTimes().intValue() + 1);
		if(StringUtils.isNotEmpty(result) && StringUtils.equalsIgnoreCase(NotifyParam.getSuccessValue(), result)){
			logger.info("order callback success ... url :" + url + "...msg：" + msg + "result:" + result);
		} else {
			logger.info("order callback faild ... url :" + url + "...msg：" + msg + "result:" + result);
			notifyQueue.addTask(rrzNotifyRecord);
		}
		try {
			if(StringUtils.isEmpty(rrzNotifyRecord.getRecordId())){
				rrzNotifyRecord.setRecordId(IdGen.uuid());
				notifyRecordService.insert(rrzNotifyRecord);
			} else {
				notifyRecordService.update(rrzNotifyRecord);
			}
		} catch (Exception e) {
			logger.warn("更新数据库异常",e);
		}
		
	}

}
