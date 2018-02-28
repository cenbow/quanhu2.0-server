package com.yryz.quanhu.order.score.dao.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yryz.quanhu.score.entity.ScoreFlowQuery;
import com.yryz.quanhu.score.vo.EventAcount;
@Mapper
public interface EventAcountDao {
	
	int saveOrUpdate(EventAcount ea);

	void save(EventAcount ea);

	int update(EventAcount ea);

	EventAcount getLastAcount(String userId);

	List<EventAcount> getAll(ScoreFlowQuery sfq);
}
