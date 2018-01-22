package com.yryz.quanhu.order.score.dao.persistence;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yryz.quanhu.score.vo.EventSign;

@Mapper
public interface ScoreStatusSignDao {
	
	void save(EventSign sss);
	
	int update(EventSign sss);
	
	EventSign getByCode(@Param("custId")String custId , @Param("eventCode")String eventCode);

}
