/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年8月22日
 * Id: EventAPIImpl.java, 2017年8月22日 下午5:11:59 yehao
 */
package com.yryz.quanhu.order.score.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
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
	public List<EventReportVo> getScoreFlowList(EventInfo log) {
		return eventService.getScoreFlowList(log);
	}
}
