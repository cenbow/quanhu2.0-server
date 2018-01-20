package com.yryz.quanhu.order.score.dao.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yryz.quanhu.score.entity.ScoreFlow;
import com.yryz.quanhu.score.entity.ScoreFlowQuery;

/**
 * Created by lsn on 2017/8/28.
 */
@Mapper
public interface ScoreFlowDao {

	void save(ScoreFlow sf);

	int update(ScoreFlow sf);

	List<ScoreFlow> getAll();

	List<ScoreFlow> getPage(@Param("sfq") ScoreFlowQuery sfq, @Param("consumeFlag") int consumeFlag,
			@Param("start") int start, @Param("limit") int limit);

}
