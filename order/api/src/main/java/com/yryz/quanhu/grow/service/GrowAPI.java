package com.yryz.quanhu.grow.service;

import java.util.List;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.grow.entity.GrowEventInfo;
import com.yryz.quanhu.grow.entity.GrowFlowQuery;
import com.yryz.quanhu.score.vo.GrowFlowReportVo;

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
	public PageList<GrowFlowReportVo> getGrowFlowPage(GrowFlowQuery gfq);
	
	/**
	 * 手动提升用户成长等级
	 * @param userId
	 * @param growLevel
	 * @param eventCode 
	 * @return
	 */
	public int promoteGrowLevel(String userId , String growLevel,String eventCode);
	
	
	/**
	 * 
	 * @param gfq
	 * @return List
	 * @Description 全部成长值明细
	 */
	public  List<GrowFlowReportVo> getGrowFlowAll(GrowFlowQuery gfq);
}
