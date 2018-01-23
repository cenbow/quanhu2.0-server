package com.yryz.quanhu.order.grow.dao.persistence;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yryz.quanhu.order.grow.entity.GrowStatusOnce;

@Mapper
public interface GrowStatusOnceDao {

	void save(GrowStatusOnce gso);

	GrowStatusOnce getByCode(@Param("userId")String userId, @Param("eventCode")String eventCode , @Param("resourceId")String resourceId);
}
