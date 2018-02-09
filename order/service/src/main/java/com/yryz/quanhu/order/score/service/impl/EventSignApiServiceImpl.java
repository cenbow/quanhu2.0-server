package com.yryz.quanhu.order.score.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.quanhu.score.service.EventAPI;
import com.yryz.quanhu.score.service.EventSignApiService;
import com.yryz.quanhu.score.vo.EventInfo;

import net.sf.json.JSONObject;
/**
 * 签到
 * @author lsn
 *
 */
@Service(interfaceClass=EventSignApiService.class)
public class EventSignApiServiceImpl implements EventSignApiService {
	
    private static final Logger logger = LoggerFactory.getLogger(EventSignApiServiceImpl.class);

	@Reference(lazy=true)
	EventAPI eventAPI;

	@Override
	public void commitSignEvent(EventInfo ei) {
		JSONObject obj = JSONObject.fromObject(ei);
		logger.info("签到={}", obj );
		eventAPI.commit(ei);
	}

}
