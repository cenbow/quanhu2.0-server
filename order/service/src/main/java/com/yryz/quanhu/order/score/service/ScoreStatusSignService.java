package com.yryz.quanhu.order.score.service;

import com.yryz.quanhu.score.vo.EventSign;

public interface ScoreStatusSignService {
	
	Long save(EventSign sss);
	
	int update(EventSign sss);
	
	EventSign getByCode(String userId , String eventCode);

}
