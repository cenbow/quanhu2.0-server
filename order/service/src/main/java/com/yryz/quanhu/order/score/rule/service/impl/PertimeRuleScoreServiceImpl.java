package com.yryz.quanhu.order.score.rule.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yryz.quanhu.order.score.rule.service.PertimeRuleScoreService;
import com.yryz.quanhu.order.score.service.EventAcountService;
import com.yryz.quanhu.order.score.service.ScoreFlowService;
import com.yryz.quanhu.score.entity.ScoreEventInfo;

@Service
@Transactional
public class PertimeRuleScoreServiceImpl extends BaseRuleScoreServiceImpl
		implements PertimeRuleScoreService {

	@Autowired
	ScoreFlowService scoreFlowService;
	
	@Autowired
	EventAcountService eventAcountService;
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public boolean processStatus(String custId, String eventCode , ScoreEventInfo sei , double amount) {
		saveScoreFlow(custId, eventCode, sei , 0 , amount);
		return true;
	}

}
