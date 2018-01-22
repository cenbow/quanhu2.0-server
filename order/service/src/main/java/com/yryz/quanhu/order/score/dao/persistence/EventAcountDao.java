package com.yryz.quanhu.order.score.dao.persistence;

import org.apache.ibatis.annotations.Mapper;

import com.yryz.quanhu.score.vo.EventAcount;
@Mapper
public interface EventAcountDao {

	void save(EventAcount ea);

	int update(EventAcount ea);

	EventAcount getLastAcount(String custId);
}