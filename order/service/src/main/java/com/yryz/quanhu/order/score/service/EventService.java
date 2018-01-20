/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年8月22日
 * Id: EventService.java, 2017年8月22日 下午5:11:02 yehao
 */
package com.yryz.quanhu.order.score.service;

import java.util.List;

import com.yryz.quanhu.score.vo.EventInfo;

/**
 * @author xiepeng
 * @version 1.0
 * @date 2017年8月22日 下午5:11:02
 * @Description 事件日志
 */
public interface EventService {

    /**
     * 保存日志
     */
    void saveEvent(EventInfo log);
    
    void processEvent(EventInfo ei);

    /**
     * 查询日志
     * @return List
     */
    List<EventInfo> getEvent(EventInfo log);
}
