package com.yryz.quanhu.order.score.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yryz.quanhu.order.score.dao.persistence.ScoreStatusDao;
import com.yryz.quanhu.order.score.entity.ScoreStatus;
import com.yryz.quanhu.order.score.service.ScoreStatusService;

/**
 * Created by lsn on 2017/8/28.
 */

@Transactional
@Service
public class ScoreStatusServiceImpl implements ScoreStatusService{
	
	@Autowired
	ScoreStatusDao scoreStatusDao;

	@Override
	public Long save(ScoreStatus ss) {
		scoreStatusDao.save(ss);
		return ss.getId();
	}

	@Override
	public int update(ScoreStatus ss) {
		return scoreStatusDao.update(ss);
	}

	@Override
	public ScoreStatus getById(String userId, String appId, Long id , Date createDate) {
		return scoreStatusDao.getById(userId, appId, id , createDate);
	}

	@Override
	public ScoreStatus getByCode(String userId, String appId, String eventType , Date createDate) {
		return scoreStatusDao.getByCode(userId, appId, eventType , createDate);
	}
}
