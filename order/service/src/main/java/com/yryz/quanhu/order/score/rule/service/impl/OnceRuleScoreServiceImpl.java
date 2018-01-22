package com.yryz.quanhu.order.score.rule.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yryz.quanhu.order.score.entity.ScoreStatusOnce;
import com.yryz.quanhu.order.score.rule.service.OnceRuleScoreService;
import com.yryz.quanhu.order.score.service.EventAcountService;
import com.yryz.quanhu.order.score.service.ScoreFlowService;
import com.yryz.quanhu.order.score.service.ScoreStatusOnceService;
import com.yryz.quanhu.order.score.type.ScoreTypeEnum;
import com.yryz.quanhu.order.score.utils.EventUtil;
import com.yryz.quanhu.score.entity.ScoreEventInfo;

@Service
@Transactional
public class OnceRuleScoreServiceImpl extends BaseRuleScoreServiceImpl implements OnceRuleScoreService{

	@Autowired
	ScoreStatusOnceService scoreStatusOnceService;
	
	@Autowired
	EventAcountService eventAcountService;
	
	@Autowired
	ScoreFlowService scoreFlowService;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean processStatus(String userId, String eventCode , ScoreEventInfo sei , double amount) {
		//一次性事件因为有持久层状态记录，因此也会对Redis中的状态缓存设置有效期
		String statusKey = EventUtil.getScoreStatusKey(userId, eventCode , ScoreTypeEnum.Once);
		//尽管状态机放行，这里仍先查询一次性积分事件状态表有没有记录过状态
		ScoreStatusOnce sso = scoreStatusOnceService.getByCode(userId, eventCode);
		if(sso != null && sso.getId() != null && sso.getScoreFlag() == 1){
			//判断数据库已记录过状态，则不再需要记录流水，仅更新状态机
			return updateStatus(statusKey, false, true);
		}
		//数据库无状态，则记录数据库积分流水，同时更新持久状态与redis状态
		//1. 一次性积分状态记录
		Date now = new Date();
		sso = new ScoreStatusOnce(userId , eventCode);
		sso.setScoreFlag(1);
		sso.setCreateTime(now);
		sso.setUpdateTime(now);
		scoreStatusOnceService.save(sso);
		//2. 记录积分流水并更新总值
		saveScoreFlow(userId, eventCode, sei , 0 , amount);
		//3. 更新一次性积分事件的redis状态记录
		return updateStatus(statusKey, true, true);
	}

}
