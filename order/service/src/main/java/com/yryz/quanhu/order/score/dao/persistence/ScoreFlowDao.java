package com.yryz.quanhu.order.score.dao.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yryz.quanhu.score.entity.ScoreFlow;
import com.yryz.quanhu.score.entity.ScoreFlowQuery;
import com.yryz.quanhu.score.vo.EventReportVo;

/**
 * Created by lsn on 2017/8/28.
 */
@Mapper
public interface ScoreFlowDao {

	void save(ScoreFlow sf);

	int update(ScoreFlow sf);

	List<EventReportVo> getAll(@Param("userId")String userId);
	
	List<EventReportVo> getOne(@Param("userId")String userId);

	List<ScoreFlow> getPage(@Param("sfq") ScoreFlowQuery sfq);
	
	long countgetPage(@Param("sfq") ScoreFlowQuery sfq);
	
	List<ScoreFlow> getAll(@Param("sfq") ScoreFlowQuery sfq);

}
