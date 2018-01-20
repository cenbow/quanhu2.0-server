package com.yryz.quanhu.order.score.service;

import java.util.List;

import com.yryz.quanhu.score.entity.SysEventInfo;

public interface SysEventManageService {
	
	Long saveSysEvent(SysEventInfo sei);

	int updateSysEvent(SysEventInfo sei);
	 
	int delById(Long id);
	
	SysEventInfo getByCode(String eventCode);
	
	SysEventInfo getById(Long id);
	
	List<SysEventInfo> getAll();

}
