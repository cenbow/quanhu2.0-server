package com.yryz.quanhu.order.score.rule.service;

import com.yryz.quanhu.order.score.type.ScoreTypeEnum;

public interface OnceRuleScoreService extends RuleScoreService{

	default ScoreTypeEnum getScoreType(){
		return ScoreTypeEnum.Once;
	};
	
}
