package com.yryz.quanhu.order.score.dao.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yryz.quanhu.score.entity.SysEventInfo;

@Mapper
public interface SysEventManageDao {

	void saveSysEvent(SysEventInfo sei);

	int updateSysEvent(SysEventInfo sei);
	 
	int delById(Long id);
	
	SysEventInfo getByCode(String eventCode);
	
	SysEventInfo getById(Long id);
	
	List<SysEventInfo> getAll();
}
