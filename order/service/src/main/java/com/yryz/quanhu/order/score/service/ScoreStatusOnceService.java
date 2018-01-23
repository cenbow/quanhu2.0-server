package com.yryz.quanhu.order.score.service;

import com.yryz.quanhu.order.score.entity.ScoreStatusOnce;


public interface ScoreStatusOnceService {
	
	Long save(ScoreStatusOnce sso);
	
	ScoreStatusOnce getByCode(String userId , String eventCode);

}
