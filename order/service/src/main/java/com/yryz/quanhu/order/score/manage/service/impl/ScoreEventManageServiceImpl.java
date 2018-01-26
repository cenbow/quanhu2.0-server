package com.yryz.quanhu.order.score.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.order.score.manage.dao.persistence.ScoreEventManageDao;
import com.yryz.quanhu.order.score.manage.service.ScoreEventManageService;
import com.yryz.quanhu.score.entity.ScoreEventInfo;

/**
 * Created by lsn on 2017/8/28.
 */
@Transactional
@Service
public class ScoreEventManageServiceImpl implements ScoreEventManageService{
	
	@Autowired
	ScoreEventManageDao scoreEventDao;

	@Override
	public Long save(ScoreEventInfo se) {
		scoreEventDao.save(se);
		return se.getId();
	}

	@Override
	public int delById(Long id) {
		return scoreEventDao.delById(id);
	}

	@Override
	public int update(ScoreEventInfo se) {
		return scoreEventDao.update(se);
	}

	@Override
	public ScoreEventInfo getByCode(String code) {
		return scoreEventDao.getByCode(code);
	}

	@Override
	public PageList<ScoreEventInfo> getAll() {
		return scoreEventDao.getAll();
	}

	@Override
	public PageList<ScoreEventInfo> getPage() {
		return scoreEventDao.getPage();
	}
		
}
