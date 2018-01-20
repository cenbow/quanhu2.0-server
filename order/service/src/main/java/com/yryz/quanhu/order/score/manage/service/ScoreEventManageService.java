package com.yryz.quanhu.order.score.manage.service;

import java.util.List;

import com.yryz.quanhu.score.entity.ScoreEventInfo;

/**
 * Created by lsn on 2017/8/28.
 */
public interface ScoreEventManageService {
	
	Long save(ScoreEventInfo se);

    int delById(Long id);

    int update(ScoreEventInfo se);

    ScoreEventInfo getByCode(String code);

    List<ScoreEventInfo> getAll();
    
    List<ScoreEventInfo> getPage(int start , int limit);

}
