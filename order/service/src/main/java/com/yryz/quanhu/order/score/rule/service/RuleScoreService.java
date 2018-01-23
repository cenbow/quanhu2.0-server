package com.yryz.quanhu.order.score.rule.service;

import com.yryz.quanhu.order.score.type.ScoreTypeEnum;
import com.yryz.quanhu.score.entity.ScoreEventInfo;

/**
 * 积分服务接口
 * 所有积分类型的上级接口
 * 包含积分状态机更新操作
 * @author lsn
 *
 */
public interface RuleScoreService {
	
	ScoreTypeEnum getScoreType();
	
	boolean updateStatus(String statusKey , boolean needScore , boolean status);

	boolean processStatus(String userId , String eventCode , ScoreEventInfo sei ,  double amount);
	
	Long saveScoreFlow(String userId , String eventCode , ScoreEventInfo sei , int newScore , double amout);
}
