package com.yryz.quanhu.behavior.common.manager;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.utils.DateUtils;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.behavior.comment.entity.Comment;
import com.yryz.quanhu.behavior.common.util.ThreadPoolUtil;
import com.yryz.quanhu.behavior.like.entity.Like;
import com.yryz.quanhu.grow.entity.GrowFlowQuery;
import com.yryz.quanhu.score.enums.EventEnum;
import com.yryz.quanhu.score.service.EventAPI;
import com.yryz.quanhu.score.service.EventAcountApiService;
import com.yryz.quanhu.score.vo.EventInfo;
import com.yryz.quanhu.score.vo.GrowFlowReportVo;

/**
 * 事件代理
 * @author danshiyu
 *
 */
@Service
public class EventManager {
	private static final Logger logger = LoggerFactory.getLogger(EventManager.class);
	
    @Reference(check = false)
    private EventAPI eventAPI;
    @Reference(check = false)
    private EventAcountApiService eventAcountApiService;
    
    /**
     * 评论事件提交
     * @param comment
     */
    public void commentCommitEvent(Comment comment){
    	ThreadPoolUtil.execue(new Runnable() {
			
			@Override
			public void run() {
				commentEvent(comment);
			}
		});
    }
    
    /**
     * 点赞事件提交
     * @param like
     */
    public void likeCommitEvent(Like like){
    	ThreadPoolUtil.execue(new Runnable() {
			
			@Override
			public void run() {
				likeEvent(like);
			}
		});
    }
    
    /**
     * 评论事件提交
     * @param comment
     */
    private void commentEvent(Comment comment){
    	try {
            GrowFlowQuery gfq = new GrowFlowQuery();
            gfq.setStartTime(DateUtils.getDate("yyyyy-MM-dd")+" 00:00:00");
            gfq.setEndTime(DateUtils.getDate("yyyyy-MM-dd")+" 23:59:59");

            List<EventInfo> eventInfos = new ArrayList<EventInfo>();
            //被评论者得成长值
            EventInfo eventInfo = new EventInfo();
            eventInfo.setOwnerId(comment.getCreateUserId().toString());
            eventInfo.setUserId(String.valueOf(comment.getTargetUserId()));
            eventInfo.setEventGrow(String.valueOf(2));
            eventInfo.setCreateTime(DateUtils.currentDatetime());
            eventInfo.setResourceId(String.valueOf(comment.getResourceId()));
            eventInfo.setEventCode(EventEnum.REPLY_POST.getCode());
            eventInfo.setEventNum(1);
            eventInfo.setAmount(0.0);
            eventInfo.setCoterieId(String.valueOf(comment.getCoterieId()));
            eventInfo.setCircleId("");
            gfq.setEventCode(EventEnum.REPLY_POST.getCode());
            gfq.setUserId(String.valueOf(comment.getTargetUserId()));
            List<GrowFlowReportVo> growFlowQueries = eventAcountApiService.getGrowFlowAll(gfq).getData();
            int currentCount = 0;
            for (GrowFlowReportVo growFlowReportVo : growFlowQueries) {
                currentCount += growFlowReportVo.getNewGrow();
            }
            if (currentCount < 10) {
                eventInfos.add(eventInfo);
            }
            //评论者
            EventInfo eventInfo_ = new EventInfo();
            eventInfo_.setOwnerId(String.valueOf(comment.getTargetUserId()));
            eventInfo_.setUserId(comment.getCreateUserId().toString());
            eventInfo_.setEventGrow(String.valueOf(5));
            eventInfo_.setCreateTime(DateUtils.currentDatetime());
            eventInfo_.setResourceId(String.valueOf(comment.getResourceId()));
            eventInfo_.setEventCode(EventEnum.COMMENT.getCode());
            eventInfo_.setEventNum(1);
            eventInfo_.setAmount(0.0);
            eventInfo_.setCoterieId(String.valueOf(comment.getCoterieId()));
            eventInfo_.setCircleId("");

            gfq.setEventCode(EventEnum.COMMENT.getCode());
            gfq.setUserId(String.valueOf(comment.getCreateUserId()));
            List<GrowFlowReportVo> growFlowQueries_ = eventAcountApiService.getGrowFlowAll(gfq).getData();
            int currentCount_ = 0;
            for (GrowFlowReportVo growFlowReportVo : growFlowQueries_) {
                currentCount_ += growFlowReportVo.getNewGrow();
            }
            if (currentCount_ < 10) {
                eventInfos.add(eventInfo_);
            }
            eventAPI.commit(eventInfos);
            logger.info("[comment_eventInfo]:eventInfo:{}",JsonUtils.toFastJson(eventInfos));
        } catch (Exception e) {
            logger.error("评论接入积分系统出现异常:" + e);
        }
    }
    
    
    
    /**
     * 点赞事件提交
     * @param like
     */
    private void likeEvent(Like like){
    	try {
            GrowFlowQuery gfq = new GrowFlowQuery();
            gfq.setStartTime(DateUtils.getDate("yyyyy-MM-dd")+" 00:00:00");
            gfq.setEndTime(DateUtils.getDate("yyyyy-MM-dd")+" 23:59:59");
            //对接积分系统
            List<EventInfo> eventInfos = new ArrayList<EventInfo>();
            //点赞
            EventInfo eventInfo = new EventInfo();
            eventInfo.setOwnerId(String.valueOf(like.getResourceUserId()));
            eventInfo.setUserId(String.valueOf(like.getUserId()));
            eventInfo.setEventGrow(String.valueOf(1));
            eventInfo.setCreateTime(DateUtils.currentDatetime());
            eventInfo.setResourceId(String.valueOf(like.getResourceId()));
            eventInfo.setEventCode(String.valueOf(13));
            eventInfo.setEventNum(1);
            eventInfo.setAmount(0.0);
            eventInfo.setCoterieId(String.valueOf(""));
            eventInfo.setCircleId("");
            gfq.setEventCode("13");
            gfq.setUserId(String.valueOf(like.getUserId()));
            List<GrowFlowReportVo> growFlowQueries = eventAcountApiService.getGrowFlowAll(gfq).getData();
            int currentCount = 0;
            for (GrowFlowReportVo growFlowReportVo : growFlowQueries) {
                currentCount += growFlowReportVo.getNewGrow();
            }
            if (currentCount < 10) {
                eventInfos.add(eventInfo);
            }
            //被点赞
            EventInfo eventInfo_ = new EventInfo();
            eventInfo_.setOwnerId(String.valueOf(like.getResourceUserId()));
            eventInfo_.setUserId(String.valueOf(like.getResourceUserId()));
            eventInfo_.setEventGrow(String.valueOf(1));
            eventInfo_.setCreateTime(DateUtils.currentDatetime());
            eventInfo_.setResourceId(String.valueOf(like.getResourceId()));
            eventInfo_.setEventCode(String.valueOf(10));
            eventInfo_.setEventNum(1);
            eventInfo_.setAmount(0.0);
            eventInfo_.setCoterieId(String.valueOf(""));
            eventInfo_.setCircleId("");
            gfq.setEventCode("10");
            gfq.setUserId(String.valueOf(like.getResourceUserId()));
            List<GrowFlowReportVo> growFlowQueries_ = eventAcountApiService.getGrowFlowAll(gfq).getData();
            int currentCount_ = 0;
            for (GrowFlowReportVo growFlowReportVo : growFlowQueries_) {
                currentCount_ += growFlowReportVo.getNewGrow();
            }
            if (currentCount_ < 10) {
                eventInfos.add(eventInfo_);
            }
            eventAPI.commit(eventInfos);
        } catch (Exception e) {
            logger.info("对接积分系统出现异常:" + e);
        }
    }
}
