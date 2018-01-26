package com.yryz.quanhu.support.activity.api;


import com.yryz.common.response.Response;
import com.yryz.quanhu.support.activity.entity.ActivityEnrolConfig;

public interface AdminActivityEnrolConfigApi {

	/**
	 * 根据活动id获取配置信息
	 * @param id
	 * @return
	 */
	Response<ActivityEnrolConfig> getActivityEnrolConfigByActId(Long activityId);
}
