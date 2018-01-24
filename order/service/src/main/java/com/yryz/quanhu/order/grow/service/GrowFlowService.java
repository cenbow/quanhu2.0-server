package com.yryz.quanhu.order.grow.service;

import java.util.List;

import com.yryz.quanhu.grow.entity.GrowFlow;
import com.yryz.quanhu.grow.entity.GrowFlowQuery;

/**
 * Created by lsn on 2017/8/28.
 */
public interface GrowFlowService {
	
	Long save(GrowFlow sf);

    int update(GrowFlow sf);

    List<GrowFlow> getAll();
    
    List<GrowFlow> getPage(GrowFlowQuery gfq , int start , int limit);
}
