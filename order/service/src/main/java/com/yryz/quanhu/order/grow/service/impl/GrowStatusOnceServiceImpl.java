package com.yryz.quanhu.order.grow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yryz.quanhu.order.grow.dao.persistence.GrowStatusOnceDao;
import com.yryz.quanhu.order.grow.entity.GrowStatusOnce;
import com.yryz.quanhu.order.grow.service.GrowStatusOnceService;

@Transactional
@Service
public class GrowStatusOnceServiceImpl implements GrowStatusOnceService {

	@Autowired
	GrowStatusOnceDao growStatusOnceDao;
	@Override
	public Long save(GrowStatusOnce sso) {
		growStatusOnceDao.save(sso);
		return sso.getId();
	}

	@Override
	public GrowStatusOnce getByCode(String userId, String eventCode , String resourceId) {
		return growStatusOnceDao.getByCode(userId, eventCode , resourceId);
	}

}
