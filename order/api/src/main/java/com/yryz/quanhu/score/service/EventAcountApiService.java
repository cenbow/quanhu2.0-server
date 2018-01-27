package com.yryz.quanhu.score.service;

import com.yryz.common.response.PageList;
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
	 * @param currentPage  页码
	 * @param pageSize 条数
	 * @return
	 */
	
	PageList<ScoreFlow> getScoreFlow(ScoreFlowQuery sfq);
	/**
	 * 获取成长明细
	 * @param gfq
	 * @param currentPage  页码
	 * @param pageSize  条数
	 * @return
	 */
	PageList<GrowFlow> getGrowFlow(GrowFlowQuery gfq );
}
