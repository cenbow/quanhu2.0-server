package com.yryz.quanhu.order.grow.service;

import com.yryz.quanhu.order.grow.entity.GrowStatusOnce;

public interface GrowStatusOnceService {
	
	Long save(GrowStatusOnce sso);
	
	GrowStatusOnce getByCode(String userId , String eventCode , String resourceId);

}
