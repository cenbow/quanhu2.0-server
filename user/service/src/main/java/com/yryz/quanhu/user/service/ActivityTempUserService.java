package com.yryz.quanhu.user.service;

import java.util.List;

import com.yryz.quanhu.user.entity.ActivityTempUser;
import com.yryz.quanhu.user.entity.UserBaseInfo;

/**
 * 活动临时用户业务
 * @author danshiyu
 *
 */
public interface ActivityTempUserService {
	/**
	 * 保存临时用户
	 * @param tempUser
	 * @return 
	 */
	Long save(ActivityTempUser tempUser);
	/**
	 * 获取临时用户信息
	 * @param userId
	 * @param thirdId
	 * @return
	 */
	ActivityTempUser get(Long userId,String thirdId);
	
	/**
	 * 根据临时用户信息组装用户基础信息
	 * @param userIds
	 * @return
	 */
	List<UserBaseInfo> getUserBaseInfoByTempUser(List<String> userIds);
}
