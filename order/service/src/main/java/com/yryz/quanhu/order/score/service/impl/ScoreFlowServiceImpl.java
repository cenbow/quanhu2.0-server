package com.yryz.quanhu.order.score.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yryz.quanhu.order.score.dao.persistence.ScoreFlowDao;
import com.yryz.quanhu.order.score.service.ScoreFlowService;
import com.yryz.quanhu.score.entity.ScoreFlow;
import com.yryz.quanhu.score.entity.ScoreFlowQuery;
import com.yryz.quanhu.score.vo.EventReportVo;

/**
 * Created by lsn on 2017/8/28.
 */

@Transactional
@Service
public class ScoreFlowServiceImpl implements ScoreFlowService {

	@Autowired
	ScoreFlowDao scoreFlowDao;

	@Override
	public Long save(ScoreFlow sf) {
		scoreFlowDao.save(sf);
		return sf.getId();
	}

	@Override
	public int update(ScoreFlow sf) {
		return scoreFlowDao.update(sf);
	}

	@Override
	public List<EventReportVo> getAll(String custId) {
		return scoreFlowDao.getAll(custId);
	}

	@Override
	public List<ScoreFlow> getPage(ScoreFlowQuery sfq, int flowType, int start, int limit) {
		return scoreFlowDao.getPage(sfq, flowType, start, limit);
	}

}
