package com.yryz.quanhu.support.activity.service;


import com.yryz.quanhu.support.activity.entity.ActivityEnrolConfig;

public interface AdminActivityEnrolConfigService {

	/**
	 * 根据活动id获取配置信息
	 * @param id
	 * @return
	 */
	ActivityEnrolConfig getActivityEnrolConfigByActId(Long activityId);
}
