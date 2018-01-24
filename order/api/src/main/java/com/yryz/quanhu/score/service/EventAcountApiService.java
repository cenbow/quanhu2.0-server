package com.yryz.quanhu.score.service;

import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.quanhu.grow.entity.GrowFlow;
import com.yryz.quanhu.grow.entity.GrowFlowQuery;
import com.yryz.quanhu.score.entity.ScoreFlow;
import com.yryz.quanhu.score.entity.ScoreFlowQuery;
import com.yryz.quanhu.score.vo.EventAcount;
import com.yryz.quanhu.score.vo.EventSign;

/**
 * 事件
 * @author lsn
 *
 */

public interface EventAcountApiService {
	/**
	 * 用户积分、成长统计
	 * @param custId
	 * @return
	 */
	EventAcount getEventAcount(String userId);
	/**
	 * 获取签到
	 * @param custId
	 * @param eventCode
	 * @return
	 */
	EventSign getEventSign(String userId , String eventCode);
	/**
	 * 获取积分明细
	 * @param sfq
	 * @param flowType
	 * @param start
	 * @param limit
	 * @return
	 */
	List<ScoreFlow> getScoreFlow(ScoreFlowQuery sfq , int flowType , int start , int limit);
	/**
	 * 获取成长明细
	 * @param gfq
	 * @param start
	 * @param limit
	 * @return
	 */
	List<GrowFlow> getGrowFlow(GrowFlowQuery gfq , int start , int limit);
}
