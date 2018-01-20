package com.yryz.quanhu.order.score.rule.service;

import com.yryz.quanhu.order.score.type.ScoreTypeEnum;

public interface SignRuleScoreService extends RuleScoreService{

	default ScoreTypeEnum getScoreType(){
		return ScoreTypeEnum.Sign;
	}
}
