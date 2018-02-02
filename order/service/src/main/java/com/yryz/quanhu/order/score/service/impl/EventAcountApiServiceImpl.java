package com.yryz.quanhu.order.score.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.grow.entity.GrowFlowQuery;
import com.yryz.quanhu.grow.service.GrowAPI;
import com.yryz.quanhu.score.entity.ScoreEventInfo;
import com.yryz.quanhu.score.entity.ScoreFlowQuery;
import com.yryz.quanhu.score.service.EventAcountAPI;
import com.yryz.quanhu.score.service.EventAcountApiService;
import com.yryz.quanhu.score.service.ScoreAPI;
import com.yryz.quanhu.score.vo.EventAcount;
import com.yryz.quanhu.score.vo.EventSign;
import com.yryz.quanhu.score.vo.GrowFlowReportVo;
import com.yryz.quanhu.score.vo.ScoreFlowReportVo;


/**
 * 事件
 * @author syc
 *
 */
@Service(interfaceClass=EventAcountApiService.class)
public class EventAcountApiServiceImpl implements EventAcountApiService {
	
	
    private Logger logger = LoggerFactory.getLogger(EventAcountApiServiceImpl.class);

	@Reference(lazy=true)
	EventAcountAPI eventAcountAPI;
	
	@Reference(lazy=true)
	ScoreAPI scoreAPI;
	
	@Reference(lazy=true)
	GrowAPI growAPI;
	
	@Override
	public EventAcount getEventAcount(String userId) {
		return eventAcountAPI.getEventAcount(userId);
	}
	
	@Override
	public Response<Map<Long, EventAcount>>  getEventAcountBatch(Set<Long> userIds) {
		
		try {
			 Map<Long, EventAcount> result = new HashMap<>();
				for(Long userId : userIds){
					result.put(userId,eventAcountAPI.getEventAcount(String.valueOf(userId)));
				}
		      return ResponseUtils.returnApiObjectSuccess(result);
	        } catch (QuanhuException e) {
	            return ResponseUtils.returnException(e);
	        } catch (Exception e) {
	            logger.error("获取批量查询积分统计异常！", e);
	            return ResponseUtils.returnException(e);
	        }

	}

	@Override
	public EventSign getEventSign(String userId, String eventCode) {
		return eventAcountAPI.getEventSign(userId, eventCode);
	}

	
 


	/**
	 * 
	 * @param userId
	 * @param score
	 * @param eventCode
	 * @return
	 * @Description 消费积分
	 */
	@Override
	public int consumeScore(String userId , int score , String eventCode){
		int consumeScore = 0;
		try {
			consumeScore = scoreAPI.consumeScore(userId, score, eventCode);
		} catch (Exception e) {
			logger.error("消费积分异常！", e);
		}
		return consumeScore;

	}
	

	/**
	 * 
	 * @param userId
	 * @param score
	 * @param eventCode
	 * @return
	 * @Description 增加积分
	 */
	@Override
	public int addScore(String userId , int score , String eventCode){
		int flag = 0;
		try {
			flag = scoreAPI.addScore(userId, score, eventCode);
		} catch (Exception e) {
			logger.error("增加积分异常！", e);
		}
		return flag;
	}

	@Override
	public Response<List<ScoreFlowReportVo>> getScoreFlowAll(ScoreFlowQuery sfq) {
		 try {
			  List<ScoreFlowReportVo> list =  scoreAPI.getScoreFlowAll(sfq);
		      return ResponseUtils.returnListSuccess(list);
	        } catch (QuanhuException e) {
	            return ResponseUtils.returnException(e);
	        } catch (Exception e) {
	            logger.error("获取积分值明细列表异常！", e);
	            return ResponseUtils.returnException(e);
	        }
	}
	@Override
	public Response<PageList<ScoreFlowReportVo>> getScoreFlow(ScoreFlowQuery sfq) {
		try {
			
			PageList<ScoreFlowReportVo> pageList = scoreAPI.getScoreFlowPage(sfq);
			return ResponseUtils.returnObjectSuccess(pageList);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("获取积分明细列表异常！", e);
			return ResponseUtils.returnException(e);
		}
		// return scoreAPI.getScoreFlowPage(sfq);
	}
	
	
	@Override
	public Response<PageList<GrowFlowReportVo>> getGrowFlow(GrowFlowQuery gfq ) {
		  try {
			  PageList<GrowFlowReportVo> pageList =  growAPI.getGrowFlowPage(gfq);
		      return ResponseUtils.returnObjectSuccess(pageList);
	        } catch (QuanhuException e) {
	            return ResponseUtils.returnException(e);
	        } catch (Exception e) {
	            logger.error("获取成长值明细列表异常！", e);
	            return ResponseUtils.returnException(e);
	        }
	}
	
	
	@Override
	public Response<List<GrowFlowReportVo>> getGrowFlowAll(GrowFlowQuery gfq) {
		 try {
			  List<GrowFlowReportVo> list =  growAPI.getGrowFlowAll(gfq);
		      return ResponseUtils.returnListSuccess(list);
	        } catch (QuanhuException e) {
	            return ResponseUtils.returnException(e);
	        } catch (Exception e) {
	            logger.error("获取成长值明细列表异常！", e);
	            return ResponseUtils.returnException(e);
	        }
	}

	@Override
	public Response<PageList<ScoreFlowReportVo>> geteventScore(ScoreFlowQuery sfq) {

		 try {
			 PageList<ScoreFlowReportVo> list =  scoreAPI.getScoreEvent(sfq);
		      return ResponseUtils.returnObjectSuccess(list);
	        } catch (QuanhuException e) {
	            return ResponseUtils.returnException(e);
	        } catch (Exception e) {
	            logger.error("获取积分配置异常！", e);
	            return ResponseUtils.returnException(e);
	        }
	}

	@Override
	public Response<ScoreFlowReportVo> geteventScoreOne(ScoreFlowQuery sfq) {
		 try {
			 ScoreFlowReportVo vo =  scoreAPI.getScoreEventOne(sfq);
		      return ResponseUtils.returnObjectSuccess(vo);
	        } catch (QuanhuException e) {
	            return ResponseUtils.returnException(e);
	        } catch (Exception e) {
	            logger.error("获取积分配置异常！", e);
	            return ResponseUtils.returnException(e);
	        }
	}


	@Override
	public Response<String> updateEventScoreOne(ScoreEventInfo scoreEventInfo) {
		 try {
//			 ScoreEventInfo  sf =  new  ScoreEventInfo();
//				sf.setId(Long.valueOf(sfq.getId()));	 
			 String flag =  String.valueOf( scoreAPI.updateScoreEvent(scoreEventInfo));
		      return ResponseUtils.returnObjectSuccess(flag);
	        } catch (QuanhuException e) {
	            return ResponseUtils.returnException(e);
	        } catch (Exception e) {
	            logger.error("获取积分配置异常！", e);
	            return ResponseUtils.returnException(e);
	        }
	}

		 
	    
}
