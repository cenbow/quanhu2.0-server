package com.yryz.quanhu.order.grow.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.PageList;
import com.yryz.common.utils.PageUtils;
import com.yryz.quanhu.grow.entity.GrowEventInfo;
import com.yryz.quanhu.grow.entity.GrowFlow;
import com.yryz.quanhu.grow.entity.GrowFlowQuery;
import com.yryz.quanhu.grow.service.GrowAPI;
import com.yryz.quanhu.order.grow.entity.GrowLevel;
import com.yryz.quanhu.order.grow.manage.service.GrowEventManageService;
import com.yryz.quanhu.order.grow.manage.service.GrowLevelManageService;
import com.yryz.quanhu.order.grow.service.GrowFlowService;
import com.yryz.quanhu.order.score.service.EventAcountService;
import com.yryz.quanhu.order.utils.Page;
import com.yryz.quanhu.score.vo.EventAcount;
import com.yryz.quanhu.score.vo.GrowFlowReportVo;
import com.yryz.quanhu.score.vo.GrowLevelVo;

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
	public PageList<GrowFlowReportVo> getGrowFlowPage(GrowFlowQuery gfq) {
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		   PageList<GrowFlowReportVo> pageList = new PageList<>();
	        pageList.setCurrentPage(gfq.getCurrentPage());
	        pageList.setPageSize(gfq.getPageSize());

			Page<GrowFlow> page = new Page<GrowFlow>();
			page.setPageNo(gfq.getCurrentPage());
			page.setPageSize(gfq.getPageSize());
			//PageHelper.startPage(gfq.getCurrentPage(), gfq.getPageSize());
			 @SuppressWarnings("unchecked")
com.github.pagehelper.Page<GrowFlow> pageHelpGrow = PageUtils.startPage(gfq.getCurrentPage(), gfq.getPageSize(),true);
pageHelpGrow = (com.github.pagehelper.Page<GrowFlow>) growFlowService.getPage(gfq);
			List<GrowFlow> list =  new ArrayList<GrowFlow>(pageHelpGrow.getResult());
	
			List<GrowFlowReportVo> listVO =  new ArrayList<GrowFlowReportVo>();
			
			for (GrowFlow s :list){
				GrowFlowReportVo vo = new GrowFlowReportVo();
				vo.setAllGrow(s.getAllGrow()); 
				vo.setEventCode(s.getEventCode());
				vo.setEventName(s.getEventName());
				vo.setNewGrow(s.getNewGrow());
				vo.setUpdateTime(sdf.format(s.getUpdateTime()));
				vo.setCreateTime(sdf.format(s.getCreateTime()));
				vo.setUserId(s.getUserId());
				listVO.add(vo);
				
			}
			
			
			
//			page.setResult(list);
//			page.setTotalCount(pageHelpGrow.getTotal());
			
			//page.setTotalCount(((com.github.pagehelper.Page<GrowFlow>) list).getTotal());


			pageList.setEntities(listVO);
			pageList.setCount(pageHelpGrow.getTotal());
			
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
			String existsLevel = ea.getGrowLevel().toString();
			if (existsLevel.compareTo(growLevel) >= 0) {
				return 0;
			}
			int growLevelStart  = gl.getLevelStart();
			
//			if ( followers.compareTo("20")<0){
//				growLevel = "4";
//				growLevelStart = 500;
//			}
//			if ( followers.compareTo("20")>=0){
//				growLevel = "5";
//				growLevelStart = 500;
//			}
			// 之前的累计成长值
			long lastGrow = ea.getGrow();
//			//判断当前成长值是否小于等级开始区间
//			if (lastGrow > growLevelStart) {
//				return 0;
//			}
				int newGrow = (int) Math.abs(growLevelStart  - lastGrow);
				GrowFlow gf = new GrowFlow(userId, eventCode, newGrow);
				gf.setAllGrow(growLevelStart  + 0L);
				gf.setCreateTime(now);
				gf.setUpdateTime(now);
				growFlowService.save(gf);
	
				ea.setGrow(growLevelStart  + 0L);
				ea.setGrowLevel(Integer.valueOf(growLevel));
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
		ea.setGrowLevel(Integer.valueOf(growLevel));
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


	@Override
	public List<GrowFlowReportVo> getGrowFlowAll(GrowFlowQuery gfq) {
//		return growFlowService.getAll(gfq);
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		List<GrowFlow> list = growFlowService.getAll(gfq);
		List<GrowFlowReportVo> listVO =  new ArrayList<GrowFlowReportVo>();
		
		for (GrowFlow s :list){
			GrowFlowReportVo vo = new GrowFlowReportVo();
			vo.setAllGrow(s.getAllGrow()); 
			vo.setEventCode(s.getEventCode());
			vo.setEventName(s.getEventName());
			vo.setNewGrow(s.getNewGrow());
			vo.setUpdateTime(sdf.format(s.getUpdateTime()));
			vo.setCreateTime(sdf.format(s.getCreateTime()));
			vo.setUserId(s.getUserId());
			listVO.add(vo);
			
		}
		return listVO;
		
	 
	}
	
	@Override
	public List<GrowLevelVo> getGrowLevelAll(){
		
		List<GrowLevel> list  = 	 growLevelManageService.getAll();
		List<GrowLevelVo> voList = new ArrayList<GrowLevelVo>();
		for(GrowLevel growLevel:list){
			GrowLevelVo VO = new GrowLevelVo();
			VO.setCreateTime(growLevel.getCreateTime());
			VO.setGrade(growLevel.getGrade());
			VO.setId(growLevel.getId());
			VO.setLevel(growLevel.getLevel().toString());
			VO.setLevelEnd(growLevel.getLevelEnd());
			VO.setLevelStart(growLevel.getLevelStart());
			VO.setUpdateTime(growLevel.getUpdateTime());
			voList.add(VO);
		}
		
		return voList;
	}

}
