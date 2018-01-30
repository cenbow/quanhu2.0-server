package com.yryz.quanhu.order.score.dao.persistence;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yryz.quanhu.order.score.entity.ScoreStatus;
import com.yryz.quanhu.score.vo.EventReportVo;

/**
 * 积分事件状态记录
 * Created by lsn on 2017/8/28.
 */
@Mapper
public interface ScoreStatusDao {

    void save(ScoreStatus ss);

    int update(ScoreStatus ss);

    ScoreStatus getById(@Param("userId") String userId , @Param("appId") String appId , @Param("id") Long id , @Param("createDate") Date createDate);

    ScoreStatus getByCode(@Param("userId") String userId , @Param("appId") String appId , @Param("eventCode") String eventCode , @Param("createDate") Date createDate);
    
    List<EventReportVo> getAll(ScoreStatus ss);
}
