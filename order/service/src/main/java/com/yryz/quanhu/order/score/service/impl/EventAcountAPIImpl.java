package com.yryz.quanhu.order.score.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.PageList;
import com.yryz.common.utils.PageUtils;
import com.yryz.quanhu.order.score.service.EventAcountService;
import com.yryz.quanhu.order.score.service.ScoreStatusSignService;
import com.yryz.quanhu.order.utils.Page;
import com.yryz.quanhu.score.entity.ScoreEventInfo;
import com.yryz.quanhu.score.entity.ScoreFlowQuery;
import com.yryz.quanhu.score.service.EventAcountAPI;
import com.yryz.quanhu.score.vo.CircleStatsVo;
import com.yryz.quanhu.score.vo.CoterieStatsVo;
import com.yryz.quanhu.score.vo.EventAcount;
import com.yryz.quanhu.score.vo.EventSign;
import com.yryz.quanhu.score.vo.ScoreFlowReportVo;
import com.yryz.quanhu.score.vo.UserStatsVo;


@Service(interfaceClass=EventAcountAPI.class)
public class EventAcountAPIImpl implements EventAcountAPI {
	
	private static final Logger logger = LoggerFactory.getLogger(EventAcountAPIImpl.class);
	@Autowired
	EventAcountService eventAcountService;

	@Autowired
	ScoreStatusSignService scoreStatusSignService;
	
	//注解使用dubbo服务端服务
//	@Reference(lazy=true)
//	UserStatsService userStatsService;
	
	
	//注解使用dubbo服务端服务
//	@Reference(lazy=true)
//	private CircleStatisticsService circleStatisticsService;
	
	//注解使用dubbo服务端服务
//	@Reference(lazy=true)
//	private CoterieStatisticsService coterieStatisticsService;

	@Override
	public EventAcount getEventAcount(String userId) {
		return eventAcountService.getLastAcount(userId);
	}

	@Override
	public EventSign getEventSign(String userId, String eventCode) {
		return scoreStatusSignService.getByCode(userId, eventCode);
	}

	@Override
	public UserStatsVo getUserStats(String custId) {
//		if (StringUtils.isEmpty(custId)) {
//			throw ServiceException.paramsError("custId");
//		}
//		try {
//			// 真实数据查询待实现
//			return userStatsService.getUserStats(custId);
//		} catch (DatasOptException e) {
//			logger.error(e.getMessage(), e);
//			throw ServiceException.sysError();
//		} catch (Exception e) {
//			logger.error("getUserStats error", e);
//			throw ServiceException.sysError();
//		}
		return  null;
	}

	@Override
	public Map<String, UserStatsVo> getUserStats(Set<String> custIds) {
//		if (CollectionUtils.isEmpty(custIds)) {
//			throw ServiceException.paramsError("custIds");
//		}
//		try {
//			// 真实数据查询待实现
//			Map<String, UserStatsVo> map = new HashMap<>(custIds.size());
//
//			// 返回的假数据
//			for (Iterator<String> iterator = custIds.iterator(); iterator.hasNext();) {
//				String custId = iterator.next();
//				map.put(custId, userStatsService.getUserStats(custId));
//			}
//			return map;
//		} catch (DatasOptException e) {
//			logger.error(e.getMessage(), e);
//			throw ServiceException.sysError();
//		} catch (Exception e) {
//			logger.error("getUserStats error", e);
//			throw ServiceException.sysError();
//		}
		return  null;
	}

	@Override
	public PageList<CircleStatsVo> listCircleStats(String circleId, int pageNum, int pageSize) {
//		if (pageNum < 1 || pageSize < 1 || pageSize > 100) {
//			throw ServiceException.paramsError("pageNum", "pageSize");
//		}
//		ListPage<CircleStatsVo> listPage = new ListPage<>(pageNum, pageSize);
//		try {
//			int start=(pageNum-1)*pageSize;
//			List<CircleStatistics> result = circleStatisticsService.find(circleId,start, pageSize);
//			
//			List<CircleStatsVo> rstList=Lists.newArrayList();
//			for (int i = 0; i < result.size(); i++) {
//				CircleStatistics info=result.get(i);
//				CircleStatsVo vo=new CircleStatsVo();
//				vo.setAccountTotal(info.getPayNum());
//				vo.setCircleId(info.getCircleId());
//				vo.setCollections(info.getCollectionNum());
//				vo.setComment(info.getCommentNum());
//				vo.setCoterieNum(info.getCoterieNum());
//				vo.setGood(info.getLikeNum());
//				vo.setMemberNum(info.getMemberNum());
//				vo.setResourceTotal(info.getContentNum());
//				vo.setShared(info.getShareNum());
//				rstList.add(vo);
//			}
//			// 真实数据查询待实现
//			listPage.setList(rstList);
//			
//			Long count=circleStatisticsService.findCount(circleId);
//			listPage.setTotalCount(count);
//			return listPage;
//		} catch (DatasOptException e) {
//			logger.error(e.getMessage(), e);
//			throw ServiceException.sysError();
//		} catch (Exception e) {
//			logger.error("listCircleStats error", e);
//			throw ServiceException.sysError();
//		}
		return  null;
	}

	@Override
	public PageList<CoterieStatsVo> listCoterieStats(String coterieId, int pageNum, int pageSize) {
//		if (pageNum < 1 || pageSize < 1 || pageSize > 100) {
//			throw ServiceException.paramsError("pageNum", "pageSize");
//		}
//		ListPage<CoterieStatsVo> listPage = new ListPage<>(pageNum, pageSize);
//		try {
//			int start=(pageNum-1)*pageSize;
//			List<CoterieStatistics>  result=coterieStatisticsService.find(coterieId,start, pageSize);
//			
//			List<CoterieStatsVo> rstList=Lists.newArrayList();
//			for (int i = 0; i < result.size(); i++) {
//				CoterieStatistics info=result.get(i);
//				CoterieStatsVo vo=new CoterieStatsVo();
//				vo.setAccountTotal(info.getPayNum());
//				vo.setCircleId(info.getCircleId());
//				vo.setCollections(info.getCollectionNum());
//				vo.setComment(info.getCommentNum());
//				vo.setCoterieId(info.getCoterieId());
//				vo.setGood(info.getLikeNum());
//				vo.setMemberNum(info.getMemberNum());
//				vo.setResourceTotal(info.getContentNum());
//				vo.setShared(info.getShareNum());
//				rstList.add(vo);
//			}
//			// 真实数据查询待实现
//			listPage.setList(rstList);
//
//			Long count=coterieStatisticsService.findCount(coterieId);
//			listPage.setTotalCount(count);
//			return listPage;
//		} catch (DatasOptException e) {
//			logger.error(e.getMessage(), e);
//			throw ServiceException.sysError();
//		} catch (Exception e) {
//			logger.error("listCoterieStats error", e);
//			throw ServiceException.sysError();
//		}
		return    null ;
	}

	@Override
	public Long initAcount(String custId) {
		return eventAcountService.initAcount(custId);
	}

	

	@Override
	public PageList<ScoreFlowReportVo> getEventAcount(ScoreFlowQuery sfq) {
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		   PageList<ScoreFlowReportVo> pageList = new PageList<>();
	        pageList.setCurrentPage(sfq.getCurrentPage());
	        pageList.setPageSize(sfq.getPageSize());

			Page<EventAcount> page = new Page<EventAcount>();
			page.setPageNo(sfq.getCurrentPage());
			page.setPageSize(sfq.getPageSize());
			//PageHelper.startPage(sfq.getCurrentPage(), sfq.getPageSize());
	        @SuppressWarnings("unchecked")
			com.github.pagehelper.Page<EventAcount> pageHelp = PageUtils.startPage(sfq.getCurrentPage(), sfq.getPageSize(), true);
			//com.github.pagehelper.Page<ScoreFlow> pageHelp =   PageUtils.startPage(sfq.getCurrentPage(), sfq.getPageSize(), true);
//	        EventAcount sfvo  = new EventAcount();  
//	        sfvo.setGrowLevel(sfq.getGrowLevel());
//	        sfvo.setGrow(sfq.getGrow());
	        pageHelp = (com.github.pagehelper.Page<EventAcount>) eventAcountService.getPage(sfq);
			List<EventAcount> list =  new ArrayList<EventAcount>(pageHelp.getResult());
 
			List<ScoreFlowReportVo> listVO =  new ArrayList<ScoreFlowReportVo>();
			for (EventAcount s :list){
				ScoreFlowReportVo vo = new ScoreFlowReportVo();
				vo.setId(s.getId());
				vo.setUserId(s.getUserId());
				vo.setScore(s.getScore());
				vo.setGrow(s.getGrow());
				vo.setGrowLevel(s.getGrowLevel());
				vo.setUpdateTime(sdf.format(s.getUpdateTime()));
				vo.setCreateTime(sdf.format(s.getCreateTime()));
				listVO.add(vo);
				
			}

			pageList.setEntities(listVO);
			pageList.setCount(pageHelp.getTotal());
		
	        return pageList;
//		
//		PageList<ScoreFlowReportVo> list =  eventAcountAPI.getEventAcount(sfq);
//		return null;
	}

}
