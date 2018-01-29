package com.yryz.quanhu.order.score.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.grow.entity.GrowFlow;
import com.yryz.quanhu.grow.entity.GrowFlowQuery;
import com.yryz.quanhu.grow.service.GrowAPI;
import com.yryz.quanhu.score.entity.ScoreFlow;
import com.yryz.quanhu.score.entity.ScoreFlowQuery;
import com.yryz.quanhu.score.service.EventAcountAPI;
import com.yryz.quanhu.score.service.EventAcountApiService;
import com.yryz.quanhu.score.service.ScoreAPI;
import com.yryz.quanhu.score.vo.EventAcount;
import com.yryz.quanhu.score.vo.EventSign;


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
	public EventSign getEventSign(String userId, String eventCode) {
		return eventAcountAPI.getEventSign(userId, eventCode);
	}

	@Override
	public Response<PageList<ScoreFlow>> getScoreFlow(ScoreFlowQuery sfq) {
		try {
			// return ResponseUtils.returnObjectSuccess(pageList);
			PageList<ScoreFlow> pageList = scoreAPI.getScoreFlowPage(sfq);
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
	public Response<PageList<GrowFlow>> getGrowFlow(GrowFlowQuery gfq ) {
		  try {
			  PageList<GrowFlow> pageList =  growAPI.getGrowFlowPage(gfq);
		      return ResponseUtils.returnObjectSuccess(pageList);
	        } catch (QuanhuException e) {
	            return ResponseUtils.returnException(e);
	        } catch (Exception e) {
	            logger.error("获取成长值明细列表异常！", e);
	            return ResponseUtils.returnException(e);
	        }
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
	    
}
