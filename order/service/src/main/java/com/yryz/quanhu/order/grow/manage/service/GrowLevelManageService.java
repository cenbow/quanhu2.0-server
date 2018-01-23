package com.yryz.quanhu.order.grow.manage.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yryz.quanhu.order.grow.entity.GrowLevel;


public interface GrowLevelManageService {

	Long save(GrowLevel level);
	
	int update(GrowLevel level);
	
	int delById(Long id);
	
	List<GrowLevel> getAll();
	
	List<GrowLevel> getPage(int start , int limit);
	
	GrowLevel getByLevelValue(int levelValue);
	
	GrowLevel getByLevel(String level);
}
