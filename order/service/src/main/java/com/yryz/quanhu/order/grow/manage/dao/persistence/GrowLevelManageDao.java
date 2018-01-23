package com.yryz.quanhu.order.grow.manage.dao.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yryz.quanhu.order.grow.entity.GrowLevel;

@Mapper
public interface GrowLevelManageDao {

	void save(GrowLevel level);

	int update(GrowLevel level);

	int delById(Long id);

	List<GrowLevel> getAll();

	List<GrowLevel> getPage(@Param("start") int start, @Param("limit") int limit);
	
	GrowLevel getByLevelValue(int levelValue);
	
	GrowLevel getByLevel(String level);

}
