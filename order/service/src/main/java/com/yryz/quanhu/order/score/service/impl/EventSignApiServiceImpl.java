package com.yryz.quanhu.order.score.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.quanhu.score.service.EventAPI;
import com.yryz.quanhu.score.service.EventSignApiService;
import com.yryz.quanhu.score.vo.EventInfo;
/**
 * 签到
 * @author lsn
 *
 */
@Service(interfaceClass=EventSignApiService.class)
public class EventSignApiServiceImpl implements EventSignApiService {

	@Reference(lazy=true)
	EventAPI eventAPI;

	@Override
	public void commitSignEvent(EventInfo ei) {
		eventAPI.commit(ei);
	}

}
