package com.yryz.quanhu.score.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.grow.entity.GrowFlowQuery;
import com.yryz.quanhu.score.entity.ScoreEventInfo;
import com.yryz.quanhu.score.entity.ScoreFlowQuery;
import com.yryz.quanhu.score.vo.EventAcount;
import com.yryz.quanhu.score.vo.EventSign;
import com.yryz.quanhu.score.vo.GrowFlowReportVo;
import com.yryz.quanhu.score.vo.ScoreFlowReportVo;

/**
 * 事件
 * @author syc
 *
 */

public interface EventAcountApiService {
	
	/**
	 * 用户积分、成长统计
	 * @param userId
	 * @return EventAcount
	 */
	EventAcount getEventAcount(String userId);
	
	
	/**
	 * 批量用户积分、成长统计
	 * @param userIds
	 * @return Map<Long, EventAcount>
	 */
	public Response<Map<Long, EventAcount>>  getEventAcountBatch(Set<Long> userIds);
	
	/**
	 * 获取签到
	 * @param custId
	 * @param eventCode
	 * @return
	 */
	EventSign getEventSign(String userId , String eventCode);
	
	
	


	
	/**
	 * 
	 * @param userId
	 * @param score
	 * @param eventCode
	 * @return
	 * @Description 消费积分
	 */
	public int consumeScore(String userId , int score , String eventCode);
	/**
	 * 
	 * @param userId
	 * @param score
	 * @param eventCode
	 * @return
	 * @Description 增加积分
	 */
	public int addScore(String userId , int score , String eventCode);
	
	/**
	 * 获取积分明细
	 * @param sfq
	 * @param flowType
	 * @param currentPage  页码
	 * @param pageSize 条数
	 * @return
	 */
	
	Response<PageList<ScoreFlowReportVo>> getScoreFlow(ScoreFlowQuery sfq);
	
	/**
	 * 获取全部积分明细
	 * @param sfq
	 * @return List<ScoreFlow>
	 */
	public Response<List<ScoreFlowReportVo>> getScoreFlowAll( ScoreFlowQuery sfq );
	
	/**
	 * 获取成长明细
	 * @param gfq
	 * @param currentPage  页码
	 * @param pageSize  条数
	 * @return
	 */
	Response<PageList<GrowFlowReportVo>> getGrowFlow(GrowFlowQuery gfq );
	/**
	 * 获取全部成长明细
	 * @param gfq
	 * @return List<GrowFlow>
	 */
	public Response<List<GrowFlowReportVo>> getGrowFlowAll(GrowFlowQuery gfq );
	
	
	/**
	 * 获取积分配置
	 * @return
	 */
	Response<PageList<ScoreFlowReportVo>> geteventScore(ScoreFlowQuery sfq);
	
	
	/**
	 * 获取积分配置
	 * @return
	 */
	Response<ScoreFlowReportVo> geteventScoreOne(ScoreFlowQuery sfq);
	
	
	
	/**
	 * 获取积分配置
	 * @return
	 */
	Response<String> updateEventScoreOne(ScoreEventInfo scoreEventInfo);
	
	
	/**
	 * 根据各种条件查询用户事件账户表信息
	 * @return List<ScoreFlowReportVo>
	 */
	Response<List<ScoreFlowReportVo>> getEventAcountAll(ScoreFlowQuery sfq);


}
