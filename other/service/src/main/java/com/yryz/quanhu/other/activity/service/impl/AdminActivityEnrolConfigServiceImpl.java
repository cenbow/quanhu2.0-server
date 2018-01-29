package com.yryz.quanhu.other.activity.service.impl;

import com.yryz.quanhu.other.activity.dao.ActivityEnrolConfigDao;
import com.yryz.quanhu.other.activity.entity.ActivityEnrolConfig;
import com.yryz.quanhu.other.activity.service.AdminActivityEnrolConfigService;
import com.yryz.quanhu.other.activity.service.AdminActivityRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdminActivityEnrolConfigServiceImpl implements AdminActivityEnrolConfigService {
	@Autowired
	ActivityEnrolConfigDao activityEnrolConfigDao;
	@Autowired
	AdminActivityRecordService activityRecordService;

	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 根据活动id获取配置信息
	 * @param id
	 * @return
	 */
	@Override
	public ActivityEnrolConfig getActivityEnrolConfigByActId(Long activityId) {
		// TODO Auto-generated method stub
		return activityEnrolConfigDao.selectByActivityId(activityId);
	}
}
