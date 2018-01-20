package com.yryz.quanhu.order.score.rule.service;

import com.yryz.quanhu.order.score.type.ScoreTypeEnum;

/**
 * 每次触发事件积分或一次永久积分服务接口
 * @author lsn
 *
 */
public interface PertimeRuleScoreService extends RuleScoreService{
	
	default ScoreTypeEnum getScoreType(){
		return ScoreTypeEnum.Pertime;
	};
	
}
