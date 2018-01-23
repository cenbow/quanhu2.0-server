package com.yryz.quanhu.order.grow.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yryz.quanhu.grow.entity.GrowEventInfo;
import com.yryz.quanhu.order.grow.manage.dao.persistence.GrowEventManageDao;
import com.yryz.quanhu.order.grow.manage.service.GrowEventManageService;

/**
 * Created by lsn on 2017/8/28.
 */
@Transactional
@Service
public class GrowEventManageServiceImpl implements GrowEventManageService{
	
	@Autowired
	GrowEventManageDao growEventDao;

	@Override
	public Long save(GrowEventInfo se) {
		growEventDao.save(se);
		return se.getId();
	}

	@Override
	public int delById(Long id) {
		return growEventDao.delById(id);
	}

	@Override
	public int update(GrowEventInfo se) {
		return growEventDao.update(se);
	}

	@Override
	public GrowEventInfo getByCode(String code) {
		return growEventDao.getByCode(code);
	}

	@Override
	public List<GrowEventInfo> getAll() {
		return growEventDao.getAll();
	}

	@Override
	public List<GrowEventInfo> getPage(int start, int limit) {
		return growEventDao.getPage(start, limit);
	}
		
}
