package com.yryz.quanhu.order.score.dao.persistence;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yryz.quanhu.order.score.entity.ScoreStatusOnce;

@Mapper
public interface ScoreStatusOnceDao {

	void save(ScoreStatusOnce sso);

	ScoreStatusOnce getByCode(@Param("userId")String userId, @Param("eventCode")String eventCode);
}
