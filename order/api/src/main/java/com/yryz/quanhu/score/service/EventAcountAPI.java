package com.yryz.quanhu.score.service;

import java.util.Map;
import java.util.Set;

import com.aliyun.oss.ServiceException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.score.entity.ScoreFlowQuery;
import com.yryz.quanhu.score.vo.CircleStatsVo;
import com.yryz.quanhu.score.vo.CoterieStatsVo;
import com.yryz.quanhu.score.vo.EventAcount;
import com.yryz.quanhu.score.vo.EventSign;
import com.yryz.quanhu.score.vo.ScoreFlowReportVo;
import com.yryz.quanhu.score.vo.UserStatsVo;

/**
 * 
 * @author lsn
 * @version 1.0
 * @date 2017年9月27日 下午3:35:23
 * @Description 事件统计服务
 */
public interface EventAcountAPI {
	/**
	 * 事件统计获取
	 * @param userId
	 * @return
	 */
	EventAcount getEventAcount(String userId);
	/**
	 * 获取签到数据
	 * @param userId
	 * @param eventCode
	 * @return
	 */
	EventSign getEventSign(String userId, String eventCode);
	
	/**
	 * 用户维度数据统计
	 * @param userId
	 * @return UserStatsVo
	 * @throws ServiceException
	 */
	UserStatsVo getUserStats(String userId);
	/**
	 *  用户维度数据统计
	 * @param userIds
	 * @return Map<String,UserStatsVo>
	 * @throws ServiceException
	 */
	Map<String,UserStatsVo> getUserStats(Set<String> userIds);
	
	/**
	 * 圈子维度数据统计查询
	 * @param circleId 圈子id
	 * @param pageNum
	 * @param pageSize
	 * @return ListPage<CircleStatsVo>
	 * @throws ServiceException
	 * @Description 圈子id作为预留的查询条件
	 */
	PageList<CircleStatsVo> listCircleStats(String circleId,int pageNum,int pageSize);
	
	/**
	 * 私圈维度数据统计查询
	 * @param coterieId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws ServiceException
	 * @Description 私圈id作为预留查询条件
	 */
	PageList<CoterieStatsVo> listCoterieStats(String coterieId,int pageNum,int pageSize);
	
	/**
	 * 用于用户创建时，初始化事件账户表，避免对账户表过多状态判定
	 * @param userId
	 * @return
	 */
	Long initAcount(String userId);
	
	/**
	 * 查询EventAcount信息
	 * @param ScoreFlowQuery sfq
	 * @return PageList<ScoreFlowReportVo>
	 */
	PageList<ScoreFlowReportVo> getEventAcount(ScoreFlowQuery sfq);
}
