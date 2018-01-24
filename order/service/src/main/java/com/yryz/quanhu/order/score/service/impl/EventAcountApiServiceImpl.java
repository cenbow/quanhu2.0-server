package com.yryz.quanhu.order.score.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
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
	public EventAcount getEventAcount(String custId) {
		return eventAcountAPI.getEventAcount(custId);
	}

	@Override
	public EventSign getEventSign(String userId, String eventCode) {
		return eventAcountAPI.getEventSign(userId, eventCode);
	}

	@Override
	public List<ScoreFlow> getScoreFlow(ScoreFlowQuery sfq , int flowType , int start , int limit) {
		return scoreAPI.getScoreFlowPage(sfq, flowType, start, limit);
	}

	@Override
	public List<GrowFlow> getGrowFlow(GrowFlowQuery gfq , int start , int limit) {
		return growAPI.getGrowFlowPage(gfq, start, limit);
	}
	

}
