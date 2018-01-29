package com.yryz.quanhu.other.activity.api;


import com.yryz.common.response.Response;
import com.yryz.quanhu.other.activity.entity.ActivityEnrolConfig;

public interface AdminActivityEnrolConfigApi {

	/**
	 * 根据活动id获取配置信息
	 * @param id
	 * @return
	 */
	Response<ActivityEnrolConfig> getActivityEnrolConfigByActId(Long activityId);
}
