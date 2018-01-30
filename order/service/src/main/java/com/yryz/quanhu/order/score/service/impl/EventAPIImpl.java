/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年8月22日
 * Id: EventAPIImpl.java, 2017年8月22日 下午5:11:59 yehao
 */
package com.yryz.quanhu.order.score.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.aliyun.oss.ServiceException;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.order.score.service.EventService;
import com.yryz.quanhu.score.service.EventAPI;
import com.yryz.quanhu.score.vo.EventInfo;
import com.yryz.quanhu.score.vo.EventReportVo;

/**
 * @author xiepeng
 * @version 1.0
 * @date 2017年8月22日 下午5:11:59
 * @Description 事件处理
 */
@Service(interfaceClass=EventAPI.class)
public class EventAPIImpl implements EventAPI {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    EventService eventService;

    @Override
    public void commit(EventInfo event) {
        eventService.processEvent(event);
    }

    @Override
    public void commit(List<EventInfo> list) {
    	list.forEach(action->{
    		eventService.processEvent(action);
    	});
    }

	    
	@Override
	public   Response<EventReportVo> getScoreFlowList(EventInfo log) {
		logger.info("Response<List<EventReportVo>> getScoreFlowList: " + log );
	try {
		EventReportVo list = eventService.getScoreFlowList(log);
		return ResponseUtils.returnObjectSuccess(list);
	    }  catch (ServiceException e) {
		 return ResponseUtils.returnException(e);
	    } catch (Exception e) {
		logger.error("unKown Exception", e);
		 return ResponseUtils.returnException(e);
	   }
	}
	
	
}
