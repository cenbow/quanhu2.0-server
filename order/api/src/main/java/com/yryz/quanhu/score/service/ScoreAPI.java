package com.yryz.quanhu.score.service;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.score.entity.ScoreEventInfo;
import com.yryz.quanhu.score.entity.ScoreFlow;
import com.yryz.quanhu.score.entity.ScoreFlowQuery;

/**
 * @author lsn on 2017/8/28 0028.
 */
public interface ScoreAPI {
	
	//积分事件管理API
	/**
	 * 保存积分事件
	 * @param sei
	 * @return
	 */
	public Long saveScoreEvent(ScoreEventInfo sei);
	/**
	 * 
	 * @param sei
	 * @return
	 * @Description 更新积分事件
	 */
	public int updateScoreEvent(ScoreEventInfo sei);
	/**
	 * 
	 * @param id
	 * @return
	 * @Description 删除积分事件
	 */
	public int delScoreEvent(Long id);
	/**
	 * 
	 * @param start
	 * @param limit
	 * @return
	 * @Description 分页查询积分明细
	 */
	public PageList<ScoreEventInfo> getScoreEventPage( );
	/**
	 * 
	 * @return
	 * @Description 查询全部积分明细
	 */
	public PageList<ScoreEventInfo> getScoreEvent();
	
	//用户积分操作API
	/**
	 * 
	 * @param sfq
	 * @param 获取积分流水记录
	 * flowType: 0 :获取新增积分流水 1：获取消费积分流水 ,-1: 获取全部积分流水
	 * @param start
	 * @param limit
	 * @return
	 * @Description 获取积分流水记录
	 */
	public PageList<ScoreFlow> getScoreFlowPage(ScoreFlowQuery sfq );
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
	
}
