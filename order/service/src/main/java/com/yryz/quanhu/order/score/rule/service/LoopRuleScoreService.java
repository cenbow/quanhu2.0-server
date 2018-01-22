package com.yryz.quanhu.order.score.rule.service;

import com.yryz.quanhu.order.score.type.ScoreTypeEnum;

/**
 * 事件触发次数限制型循环积分规则接口
 * @author lsn
 *
 */
public interface LoopRuleScoreService extends RuleScoreService{
	
	default ScoreTypeEnum getScoreType(){
		return ScoreTypeEnum.Loop;
	};
	
}