package com.yryz.quanhu.order.score.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.PageList;
import com.yryz.quanhu.grow.entity.GrowFlow;
import com.yryz.quanhu.grow.entity.GrowFlowQuery;
import com.yryz.quanhu.grow.service.GrowAPI;
import com.yryz.quanhu.score.entity.ScoreFlow;
import com.yryz.quanhu.score.entity.ScoreFlowQuery;
import com.yryz.quanhu.score.service.EventAcountAPI;
import com.yryz.quanhu.score.service.EventAcountApiService;
import com.yryz.quanhu.score.service.ScoreAPI;
import com.yryz.quanhu.score.vo.EventAcount;
import com.yryz.quanhu.score.vo.EventSign;


/**
 * 事件
 * @author syc
 *
 */
@Service(interfaceClass=EventAcountApiService.class)
public class EventAcountApiServiceImpl implements EventAcountApiService {

	@Reference(lazy=true)
	EventAcountAPI eventAcountAPI;
	
	@Reference(lazy=true)
	ScoreAPI scoreAPI;
	
	@Reference(lazy=true)
	GrowAPI growAPI;
	
	@Override
	public EventAcount getEventAcount(String userId) {
		return eventAcountAPI.getEventAcount(userId);
	}

	@Override
	public EventSign getEventSign(String userId, String eventCode) {
		return eventAcountAPI.getEventSign(userId, eventCode);
	}

	@Override
	public PageList<ScoreFlow> getScoreFlow(ScoreFlowQuery sfq ) {
		return scoreAPI.getScoreFlowPage(sfq);
	}

	@Override
	public PageList<GrowFlow> getGrowFlow(GrowFlowQuery gfq ) {
		return growAPI.getGrowFlowPage(gfq);
	}
	

}
