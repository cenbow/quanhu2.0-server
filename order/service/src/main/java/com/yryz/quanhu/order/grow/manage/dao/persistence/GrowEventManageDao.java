package com.yryz.quanhu.order.grow.manage.dao.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yryz.quanhu.grow.entity.GrowEventInfo;

/**
 * 积分事件管理
 * Created by lsn on 2017/8/28.
 */
@Mapper
public interface GrowEventManageDao {

    void save(GrowEventInfo se);

    int delById(Long id);

    int update(GrowEventInfo se);

    GrowEventInfo getByCode(String code);

    List<GrowEventInfo> getAll();
    
    List<GrowEventInfo> getPage(int start , int limit);
}
