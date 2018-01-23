package com.yryz.quanhu.order.grow.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yryz.quanhu.order.grow.entity.GrowLevel;
import com.yryz.quanhu.order.grow.manage.dao.persistence.GrowLevelManageDao;
import com.yryz.quanhu.order.grow.manage.service.GrowLevelManageService;

@Transactional
@Service
public class GrowLevelManageServiceImpl implements GrowLevelManageService {

	@Autowired
	GrowLevelManageDao growLevelManageDao;
	
	@Override
	public Long save(GrowLevel level) {
		growLevelManageDao.save(level);
		return level.getId();
	}

	@Override
	public int update(GrowLevel level) {
		return growLevelManageDao.update(level);
	}

	@Override
	public int delById(Long id) {
		return growLevelManageDao.delById(id);
	}

	@Override
	public List<GrowLevel> getAll() {
		return growLevelManageDao.getAll();
	}

	@Override
	public List<GrowLevel> getPage(int start, int limit) {
		return growLevelManageDao.getPage(start, limit);
	}

	@Override
	public GrowLevel getByLevelValue(int levelValue) {
		return growLevelManageDao.getByLevelValue(levelValue);
	}

	@Override
	public GrowLevel getByLevel(String level) {
		return growLevelManageDao.getByLevel(level);
	}

}
