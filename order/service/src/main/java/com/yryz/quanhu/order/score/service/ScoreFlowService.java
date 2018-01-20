package com.yryz.quanhu.order.score.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yryz.quanhu.score.entity.ScoreFlow;
import com.yryz.quanhu.score.entity.ScoreFlowQuery;

/**
 * Created by lsn on 2017/8/28.
 */

public interface ScoreFlowService {

	Long save(ScoreFlow sf);

	int update(ScoreFlow sf);

	List<ScoreFlow> getAll(@Param("custId")String custId);

	List<ScoreFlow> getPage(ScoreFlowQuery sfq, int flowType, int start, int limit);
}
