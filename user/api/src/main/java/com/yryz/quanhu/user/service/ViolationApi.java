package com.yryz.quanhu.user.service;

import com.yryz.quanhu.user.contants.RedisConstants;

/**
 * 违规处理
 * @author danshiyu
 *
 */
public interface ViolationApi {
	/**
	 * 警告计数cacheKey
	 * @param userId
	 * @return
	 */
	static String warnTimesKey(String userId){
		return String.format("%s.%s", RedisConstants.USER_WARN_TIMES,userId);
	}
}
