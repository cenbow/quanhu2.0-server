package com.yryz.quanhu.order.score.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.utils.PageUtils;
import com.yryz.quanhu.order.score.manage.service.ScoreEventManageService;
import com.yryz.quanhu.order.score.service.EventAcountService;
import com.yryz.quanhu.order.score.service.ScoreFlowService;
import com.yryz.quanhu.order.utils.Page;
import com.yryz.quanhu.score.entity.ScoreEventInfo;
import com.yryz.quanhu.score.entity.ScoreFlow;
import com.yryz.quanhu.score.entity.ScoreFlowQuery;
import com.yryz.quanhu.score.service.ScoreAPI;
import com.yryz.quanhu.score.vo.EventAcount;

@Service(interfaceClass=ScoreAPI.class)
public class ScoreAPIImpl implements ScoreAPI {

	@Autowired
	ScoreEventManageService scoreEventManageService;

	@Autowired
	ScoreFlowService scoreFlowService;

	@Autowired
	EventAcountService eventAcountService;

	@Override
	public Long saveScoreEvent(ScoreEventInfo sei) {
		return scoreEventManageService.save(sei);
	}

	@Override
	public int updateScoreEvent(ScoreEventInfo sei) {
		return scoreEventManageService.update(sei);
	}

	@Override
	public int delScoreEvent(Long id) {
		return scoreEventManageService.delById(id);
	}

	@Override
	public PageList<ScoreEventInfo> getScoreEventPage() {
		return scoreEventManageService.getPage();
	}
	


	@Override
	public  PageList<ScoreFlow> getScoreFlowPage(ScoreFlowQuery sfq) {
		
		   PageList<ScoreFlow> pageList = new PageList<>();
	        pageList.setCurrentPage(sfq.getCurrentPage());
	        pageList.setPageSize(sfq.getPageSize());

			Page<ScoreFlow> page = new Page<ScoreFlow>();
			page.setPageNo(sfq.getCurrentPage());
			page.setPageSize(sfq.getPageSize());
			//PageHelper.startPage(sfq.getCurrentPage(), sfq.getPageSize());
	        @SuppressWarnings("unchecked")
			com.github.pagehelper.Page<ScoreFlow> pageHelp = PageUtils.startPage(sfq.getCurrentPage(), sfq.getPageSize(), true);
			//com.github.pagehelper.Page<ScoreFlow> pageHelp =   PageUtils.startPage(sfq.getCurrentPage(), sfq.getPageSize(), true);
	        pageHelp = (com.github.pagehelper.Page<ScoreFlow>) scoreFlowService.getPage(sfq);
			List<ScoreFlow> list =  new ArrayList<ScoreFlow>(pageHelp.getResult());
//			List<ScoreFlow> list =   scoreFlowService.getPage(sfq);
//			page.setResult(pageHelp.getResult());
//			page.setTotalCount(pageHelp.getTotal());

//	        if ( CollectionUtils.isEmpty(list)) {
//	            pageList.setCount(0L);
//	        } else {
//	            pageList.setCount(scoreFlowService.countgetPage(sfq));
//	        }
			pageList.setEntities(list);
			pageList.setCount(pageHelp.getTotal());
 
		
	        return pageList;
	        
	}

	@Override
	public int consumeScore(String custId, int score, String eventCode) {
		score = Math.abs(score);
		// 消费记流水
		EventAcount ea = eventAcountService.getLastAcount(custId);
		// 更新总积分值
		if (ea != null && ea.getId() != null) {
			Long allScore = ea.getScore();
			if (allScore >= score) {
				Date now = new Date();
				ScoreFlow sf = new ScoreFlow(custId, eventCode, score);
				sf.setConsumeFlag(1);
				sf.setAllScore(allScore - score);
				sf.setCreateTime(now);
				sf.setUpdateTime(now);
				scoreFlowService.save(sf);
				ea.setScore(-Math.abs(score + 0L));
				ea.setUpdateTime(now);
				ea.setGrow(null);
				ea.setGrowLevel(null);
				return eventAcountService.update(ea);
			}
			return 0;
		}
		return 0;
	}

	@Override
	public int addScore(String custId, int score, String eventCode) {
		EventAcount ea = eventAcountService.getLastAcount(custId);
		Date now = new Date();
		ScoreFlow sf = new ScoreFlow(custId, eventCode, score);
		sf.setConsumeFlag(0);
		sf.setNewScore(score);
		sf.setCreateTime(now);
		sf.setUpdateTime(now);
		if (ea != null && ea.getId() != null) {
			Long allScore = ea.getScore();
			sf.setAllScore(allScore + score);
			scoreFlowService.save(sf);
			ea.setScore(Math.abs(score + 0L));
			ea.setUpdateTime(now);
			ea.setGrow(null);
			ea.setGrowLevel(null);
			return eventAcountService.update(ea);
		}

		sf.setAllScore(score + 0L);
		scoreFlowService.save(sf);
		ea = new EventAcount(custId);
		ea.setCreateTime(now);
		ea.setUpdateTime(now);
		ea.setScore(score + 0L);
		eventAcountService.save(ea);
		return 0;
	}

	@Override
	public PageList<ScoreEventInfo> getScoreEvent() {
		return scoreEventManageService.getAll();
	}

}
