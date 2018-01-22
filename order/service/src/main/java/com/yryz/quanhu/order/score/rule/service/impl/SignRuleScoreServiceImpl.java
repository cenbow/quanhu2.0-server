package com.yryz.quanhu.order.score.rule.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yryz.quanhu.order.score.rule.service.SignRuleScoreService;
import com.yryz.quanhu.order.score.service.EventAcountService;
import com.yryz.quanhu.order.score.service.ScoreFlowService;
import com.yryz.quanhu.order.score.service.ScoreStatusSignService;
import com.yryz.quanhu.order.score.type.ScoreTypeEnum;
import com.yryz.quanhu.order.score.utils.EventUtil;
import com.yryz.quanhu.score.entity.ScoreEventInfo;
import com.yryz.quanhu.score.vo.EventSign;

@Transactional
@Service
public class SignRuleScoreServiceImpl extends BaseRuleScoreServiceImpl implements SignRuleScoreService {

	@Autowired
	ScoreStatusSignService scoreStatusSignService;

	@Autowired
	ScoreFlowService scoreFlowService;

	@Autowired
	EventAcountService eventAcountService;

	@Override
	public boolean processStatus(String userId, String eventCode, ScoreEventInfo sei, 
			double amount) {
		// 进来的签到事件都是拦截器根据Redis状态判定后放行的，数据有可能不准确
		Date now = new Date();
		String scoreSignKey = EventUtil.getScoreStatusKey(userId, eventCode, ScoreTypeEnum.Sign);
		// 1.查询数据库中当前用户的签到信息
		EventSign sss = scoreStatusSignService.getByCode(userId, eventCode);
		if (sss != null && sss.getId() != null) {
			// 数据库有签到信息
			int eventScore = sei.getEventScore();
			Date lastSign = sss.getLastSignTime();
			int daysInterval = EventUtil.daysInterval(lastSign, now);
			switch (daysInterval) {
			case 0:
				// 当日的签到已在数据库中记录，则只需更新redis当日签到状态
				return updateStatus(scoreSignKey, false, true);
			case 1:
				// 当日与上一日是连续的日期，则按连续区间规则计算积分
				int signCount = sss.getSignCount();
				int newCount = signCount + 1; 
				// 变更状态记录
				sss.setLastSignTime(now);
				sss.setSignCount(newCount);
				sss.setUpdateTime(now);
				scoreStatusSignService.update(sss);
				int eventLoopUnit = sei.getEventLoopUnit();
				int newScore = newCount >= eventLoopUnit ? eventScore * eventLoopUnit : eventScore * newCount;
				saveScoreFlow(userId, eventCode, sei, newScore, amount);
				return updateStatus(scoreSignKey, true, true);
			default:
				// 其他情况都属于签到中断的情况，则重置连续区间起始时间与连续天数
				sss.setLastSignTime(now);
				sss.setSignCount(1);
				sss.setUpdateTime(now);
				scoreStatusSignService.update(sss);
				// 记录本次的新增积分流水及总值
				saveScoreFlow(userId, eventCode, sei, sei.getEventScore(), amount);
				return updateStatus(scoreSignKey, true, true);
			}
		}

		// 2.若数据库中不存在记录，则需要新增初始化用户签到数据
		sss = new EventSign(userId, eventCode);
		sss.setCreateTime(now);
		sss.setUpdateTime(now);
		sss.setLastSignTime(now);
		sss.setSignCount(1);
		scoreStatusSignService.save(sss);
		saveScoreFlow(userId, eventCode, sei, sei.getEventScore(), amount);
		return updateStatus(scoreSignKey, true, true);
	}

}
