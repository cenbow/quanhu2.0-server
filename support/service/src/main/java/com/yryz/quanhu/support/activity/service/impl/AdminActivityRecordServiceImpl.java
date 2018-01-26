package com.yryz.quanhu.support.activity.service.impl;

import com.yryz.quanhu.support.activity.dao.ActivityRecordDao;
import com.yryz.quanhu.support.activity.service.AdminActivityRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdminActivityRecordServiceImpl implements AdminActivityRecordService {
	@Autowired
	ActivityRecordDao activityRecordDao;

	/**
	 * 当前用户在当前活动中的报名状态(1未参加    2已参加)
	 * @param custId
	 * @return
	 */
	/*@Override
	public Integer getEnrolStatusByCustId(String custId,Long activityId) {
		Map<String,String> map= new HashMap<String,String>();
		map.put("custId", custId);
		map.put("activityId", String.valueOf(activityId));
		List<ActivityRecord> activityRecordList = activityRecordDao.getEnrolStatusByCustId(map);
		if(CollectionUtils.isEmpty(activityRecordList)){
			return 1;
		}
		return 2;
	}*/
	/**
	 * 获取活动当前报名的人数
	 * @param activityId 
	 * @return
	 */
	/*@Override
	public Integer getEnrolAlready(Long activityId) {
		List<ActivityRecord> activityRecordList = activityRecordDao.getEnrolAlready(activityId);
		if(CollectionUtils.isEmpty(activityRecordList)){
			return 0;
		}
		return 2;
	}*/
}
