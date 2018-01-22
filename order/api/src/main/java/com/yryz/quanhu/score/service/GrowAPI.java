package com.yryz.quanhu.score.service;

import java.util.List;

import com.yryz.quanhu.score.entity.GrowEventInfo;
import com.yryz.quanhu.score.entity.GrowFlow;
import com.yryz.quanhu.score.entity.GrowFlowQuery;

/**
 * @author lsn 
 * on 2017/8/28 0028.
 */
public interface GrowAPI {
	/**
	 * 
	 * @param sei
	 * @return
	 * @Description 提交成长事件
	 */
	public Long saveGrowEvent(GrowEventInfo sei);
	/**
	 * 
	 * @param sei
	 * @return
	 * @Description 更新成长事件
	 */
	public int updateGrowEvent(GrowEventInfo sei);
	/**
	 * 
	 * @param id
	 * @return
	 * @Description 删除成长事件
	 */
	public int delGrowEvent(Long id);
	/**
	 * 
	 * @author Administrator
	 * @date 2017年10月18日
	 * @param start
	 * @param limit
	 * @return
	 * @Description 分页查询成长明细
	 */
	public List<GrowEventInfo> getGrowEventPage(int start, int limit);
	/**
	 * 
	 * @return
	 * @Description 查询全部成长明细
	 */
	public List<GrowEventInfo> getGrowEvent();

	/**
	 * @param gfq
	 * @param start
	 * @param limit
	 * @return
	 * @Description 获取成长流水记录
	 */
	public List<GrowFlow> getGrowFlowPage(GrowFlowQuery gfq, int start, int limit);
	
	/**
	 * 手动提升用户成长等级
	 * @param custId
	 * @param growLevel
	 * @param eventCode 
	 * @return
	 */
	public int promoteGrowLevel(String custId , String growLevel,String eventCode);
}