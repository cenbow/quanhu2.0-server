package com.yryz.quanhu.order.score.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yryz.quanhu.order.score.dao.persistence.SysEventManageDao;
import com.yryz.quanhu.order.score.service.SysEventManageService;
import com.yryz.quanhu.score.entity.SysEventInfo;

@Service
public class SysEventManageServiceImpl implements SysEventManageService {
	
	@Autowired
	SysEventManageDao sysEventManageDao;

	@Override
	public Long saveSysEvent(SysEventInfo sei) {
		sysEventManageDao.saveSysEvent(sei);
		return sei.getId();
	}

	@Override
	public int updateSysEvent(SysEventInfo sei) {
		return sysEventManageDao.updateSysEvent(sei);
	}

	@Override
	public int delById(Long id) {
		return sysEventManageDao.delById(id);
	}

	@Override
	public SysEventInfo getByCode(String eventCode) {
		return sysEventManageDao.getByCode(eventCode);
	}

	@Override
	public SysEventInfo getById(Long id) {
		return sysEventManageDao.getById(id);
	}

	@Override
	public List<SysEventInfo> getAll() {
		return sysEventManageDao.getAll();
	}

}
