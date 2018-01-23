package com.yryz.quanhu.order.grow.dao.persistence;

import java.util.Date;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yryz.quanhu.order.grow.entity.GrowStatus;

/**
 * 积分事件状态记录
 * Created by lsn on 2017/8/28.
 */
@Mapper
public interface GrowStatusDao {

    void save(GrowStatus gs);

    int update(GrowStatus gs);

    GrowStatus getById(@Param("userId") String userId , @Param("appId") String appId , @Param("id") Long id);

    GrowStatus getByCode(@Param("userId") String userId , @Param("createDate") Date createDate , @Param("eventCode") String eventCode);
}
