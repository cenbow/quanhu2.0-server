package com.yryz.quanhu.order.score.dao.persistence;

import java.util.Date;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yryz.quanhu.order.score.entity.ScoreStatus;

/**
 * 积分事件状态记录
 * Created by lsn on 2017/8/28.
 */
@Mapper
public interface ScoreStatusDao {

    void save(ScoreStatus ss);

    int update(ScoreStatus ss);

    ScoreStatus getById(@Param("custId") String custId , @Param("appId") String appId , @Param("id") Long id , @Param("createDate") Date createDate);

    ScoreStatus getByCode(@Param("custId") String custId , @Param("appId") String appId , @Param("eventCode") String eventCode , @Param("createDate") Date createDate);
}
