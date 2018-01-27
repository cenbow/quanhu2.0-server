package com.yryz.quanhu.order.score.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.yryz.common.utils.PageUtils;
import com.yryz.quanhu.grow.entity.GrowFlowQuery;
import com.yryz.quanhu.order.entity.RrzOrderIntegralHistory;
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
	public List<EventReportVo> getAll(String userId) {
		return scoreFlowDao.getAll(userId);
	}

	@Override
	public List<ScoreFlow> getPage(ScoreFlowQuery sfq) {
		
		//PageUtils.startPage(sfq.getCurrentPage(), sfq.getPageSize());
//com.github.pagehelper.Page<ScoreFlowQuery> pageHelp =PageHelper.startPage(sfq.getCurrentPage(), sfq.getPageSize());

		return scoreFlowDao.getPage(sfq);
	}
	
	
    @Override
    public long countgetPage(ScoreFlowQuery gfq) {
        return scoreFlowDao.countgetPage(gfq);
    }


}
