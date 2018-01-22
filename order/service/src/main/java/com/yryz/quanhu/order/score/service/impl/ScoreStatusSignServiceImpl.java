package com.yryz.quanhu.order.score.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yryz.quanhu.order.score.dao.persistence.ScoreStatusSignDao;
import com.yryz.quanhu.order.score.service.ScoreStatusSignService;
import com.yryz.quanhu.order.score.utils.EventUtil;
import com.yryz.quanhu.score.vo.EventSign;

@Transactional
@Service
public class ScoreStatusSignServiceImpl implements ScoreStatusSignService {

	@Autowired
	ScoreStatusSignDao scoreStatusSignDao;
	
	@Override
	public Long save(EventSign sss) {
		scoreStatusSignDao.save(sss);
		return sss.getId();
	}

	@Override
	public int update(EventSign sss) {
		return scoreStatusSignDao.update(sss);
	}

	@Override
	public EventSign getByCode(String userId, String eventCode) {
		EventSign es = scoreStatusSignDao.getByCode(userId, eventCode);
		if(es != null && es.getId() != null){
			Date lastSign = es.getLastSignTime();
			int daysInterval = EventUtil.daysInterval(lastSign, new Date());
			if(daysInterval == 0){
				es.setSignFlag(true);
			}
		}
		return es;
	}

}
