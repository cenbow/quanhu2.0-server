package com.yryz.quanhu.order.score.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yryz.quanhu.order.score.dao.persistence.ScoreStatusOnceDao;
import com.yryz.quanhu.order.score.entity.ScoreStatusOnce;
import com.yryz.quanhu.order.score.service.ScoreStatusOnceService;

@Transactional
@Service
public class ScoreStatusOnceServiceImpl implements ScoreStatusOnceService {

	@Autowired
	ScoreStatusOnceDao scoreStatusOnceDao;
	@Override
	public Long save(ScoreStatusOnce sso) {
		scoreStatusOnceDao.save(sso);
		return sso.getId();
	}

	@Override
	public ScoreStatusOnce getByCode(String custId, String eventCode) {
		return scoreStatusOnceDao.getByCode(custId, eventCode);
	}

}
