package com.yryz.quanhu.order.grow.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.yryz.common.response.PageList;
import com.yryz.quanhu.grow.entity.GrowEventInfo;
import com.yryz.quanhu.grow.entity.GrowFlow;
import com.yryz.quanhu.grow.entity.GrowFlowQuery;
import com.yryz.quanhu.grow.service.GrowAPI;
import com.yryz.quanhu.order.entity.RrzOrderIntegralHistory;
import com.yryz.quanhu.order.entity.RrzOrderPayInfo;
import com.yryz.quanhu.order.grow.entity.GrowLevel;
import com.yryz.quanhu.order.grow.manage.service.GrowEventManageService;
import com.yryz.quanhu.order.grow.manage.service.GrowLevelManageService;
import com.yryz.quanhu.order.grow.service.GrowFlowService;
import com.yryz.quanhu.order.score.service.EventAcountService;
import com.yryz.quanhu.order.utils.Page;
import com.yryz.quanhu.score.entity.ScoreFlow;
import com.yryz.quanhu.score.entity.ScoreFlowQuery;
import com.yryz.quanhu.score.vo.EventAcount;

@Service(interfaceClass=GrowAPI.class)
public class GrowAPIImpl implements GrowAPI {

	@Autowired
	GrowEventManageService growEventManageService;

	@Autowired
	GrowFlowService growFlowService;

	@Autowired
	EventAcountService eventAcountService;

	@Autowired
	GrowLevelManageService growLevelManageService;

	@Override
	public Long saveGrowEvent(GrowEventInfo sei) {
		return growEventManageService.save(sei);
	}

	@Override
	public int updateGrowEvent(GrowEventInfo sei) {
		return growEventManageService.update(sei);
	}

	@Override
	public int delGrowEvent(Long id) {
		return growEventManageService.delById(id);
	}

	@Override
	public List<GrowEventInfo> getGrowEventPage(int start, int limit) {
		return growEventManageService.getPage(start, limit);
	}

	@Override
	public PageList<GrowFlow> getGrowFlowPage(GrowFlowQuery gfq) {

		   PageList<GrowFlow> pageList = new PageList<>();
	        pageList.setCurrentPage(gfq.getCurrentPage());
	        pageList.setPageSize(gfq.getPageSize());

			Page<GrowFlow> page = new Page<GrowFlow>();
			page.setPageNo(gfq.getCurrentPage());
			page.setPageSize(gfq.getPageSize());
com.github.pagehelper.Page<GrowFlowQuery> pageHelpGrow = PageHelper.startPage(gfq.getCurrentPage(), gfq.getPageSize());
			List<GrowFlow> list =  growFlowService.getPage(gfq);
			page.setResult(list);
			page.setTotalCount(pageHelpGrow.getTotal());
			
			//page.setTotalCount(((com.github.pagehelper.Page<GrowFlow>) list).getTotal());


			pageList.setEntities(list);
			pageList.setCount(((com.github.pagehelper.Page<GrowFlow>) list).getTotal());
			
			

	        
			 return pageList;

	}

	@Override
	public List<GrowEventInfo> getGrowEvent() {
		return growEventManageService.getAll();
	}

	@Override
	public int promoteGrowLevel(String userId, String growLevel,String eventCode) {
		EventAcount ea = eventAcountService.getLastAcount(userId);
		GrowLevel gl = growLevelManageService.getByLevel(growLevel);
		Date now = new Date();
		if(StringUtils.isEmpty(eventCode)){
			eventCode = "-1";
		}
		if (ea != null && ea.getId() != null) {
			String existsLevel = ea.getGrowLevel();
			if (existsLevel.compareTo(growLevel) >= 0) {
				return 0;
			}
			int grow = gl.getLevelStart();
			// 之前的累计成长值
			long lastGrow = ea.getGrow();
			int newGrow = (int) Math.abs(grow - lastGrow);
			GrowFlow gf = new GrowFlow(userId, eventCode, newGrow);
			gf.setAllGrow(grow + 0L);
			gf.setCreateTime(now);
			gf.setUpdateTime(now);
			growFlowService.save(gf);

			ea.setGrow(newGrow + 0L);
			ea.setGrowLevel(growLevel);
			ea.setUpdateTime(now);
			ea.setScore(null);
			eventAcountService.update(ea);
			return 1;
		}
		//事件账户中还没有个人记录
		GrowFlow gf = new GrowFlow(userId, eventCode, gl.getLevelStart());
		gf.setAllGrow(gl.getLevelStart() + 0L);
		gf.setCreateTime(now);
		gf.setUpdateTime(now);
		growFlowService.save(gf);
		ea = new EventAcount(userId);
		ea.setGrow(gl.getLevelStart() + 0L);
		ea.setGrowLevel(growLevel);
		ea.setCreateTime(now);
		ea.setUpdateTime(now);
		eventAcountService.save(ea);
		return 0;
	}
	
	public static void main(String args[]){
		String a = "L1"; 
		String b = "L2";
		System.out.println(a.compareTo(b));
	}

}
