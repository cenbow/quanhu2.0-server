package com.yryz.quanhu.order.grow.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yryz.common.utils.PageUtils;
import com.yryz.quanhu.grow.entity.GrowFlow;
import com.yryz.quanhu.grow.entity.GrowFlowQuery;
import com.yryz.quanhu.order.grow.dao.persistence.GrowFlowDao;
import com.yryz.quanhu.order.grow.service.GrowFlowService;

/**
 * Created by lsn on 2017/8/28.
 */

@Transactional
@Service
public class GrowFlowServiceImpl implements GrowFlowService{
	
	@Autowired
	GrowFlowDao growFlowDao;

	@Override
	public Long save(GrowFlow sf) {
		growFlowDao.save(sf);
		return sf.getId();
	}

	@Override
	public int update(GrowFlow sf) {
		return growFlowDao.update(sf);
	}

	@Override
	public List<GrowFlow> getAll() {
		return growFlowDao.getAll();
	}

	@Override
	public List<GrowFlow> getPage(GrowFlowQuery gfq) {
		//PageUtils.startPage(gfq.getCurrentPage(), gfq.getPageSize());
		return growFlowDao.getPage(gfq);
	}
	

    @Override
    public long countgetPage(GrowFlowQuery gfq) {
        return growFlowDao.countgetPage(gfq);
    }




}
