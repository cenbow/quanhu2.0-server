package com.yryz.quanhu.order.grow.manage.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yryz.quanhu.grow.entity.GrowEventInfo;

/**
 * Created by lsn on 2017/8/28.
 */

public interface GrowEventManageService {
	
	Long save(GrowEventInfo se);

    int delById(Long id);

    int update(GrowEventInfo se);

    GrowEventInfo getByCode(String code);

    List<GrowEventInfo> getAll();
    
    List<GrowEventInfo> getPage(int start , int limit);

}
