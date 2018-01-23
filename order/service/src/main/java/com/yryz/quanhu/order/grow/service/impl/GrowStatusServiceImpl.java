package com.yryz.quanhu.order.grow.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yryz.quanhu.order.grow.dao.persistence.GrowStatusDao;
import com.yryz.quanhu.order.grow.entity.GrowStatus;
import com.yryz.quanhu.order.grow.service.GrowStatusService;

/**
 * Created by lsn on 2017/8/28.
 */

@Transactional
@Service
public class GrowStatusServiceImpl implements GrowStatusService{
	
	@Autowired
	GrowStatusDao growStatusDao;

	@Override
	public Long save(GrowStatus ss) {
		growStatusDao.save(ss);
		return ss.getId();
	}

	@Override
	public int update(GrowStatus ss) {
		return growStatusDao.update(ss);
	}

	@Override
	public GrowStatus getById(String userId, String appId, Long id) {
		return growStatusDao.getById(userId, appId, id);
	}

	@Override
	public GrowStatus getByCode(String userId, Date createTime, String eventType) {
		return growStatusDao.getByCode(userId, createTime, eventType);
	}
}
