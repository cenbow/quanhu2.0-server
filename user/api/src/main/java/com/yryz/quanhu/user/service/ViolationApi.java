package com.yryz.quanhu.user.service;

import java.util.List;

import com.aliyun.oss.ServiceException;
import com.yryz.common.response.Response;
import com.yryz.quanhu.user.contants.RedisConstants;
import com.yryz.quanhu.user.vo.ViolationInfo;

/**
 * 违规处理
 * 
 * @author danshiyu
 *
 */
public interface ViolationApi {
	/**
	 * 警告计数cacheKey
	 * 
	 * @param userId
	 * @return
	 */
	static String warnTimesKey(String userId) {
		return String.format("%s.%s", RedisConstants.USER_WARN_TIMES, userId);
	}

	/**
	 * 添加违规记录
	 * 
	 * @param info
	 * @return
	 */
	public Response<Boolean> addViolation(ViolationInfo info);

	/**
	 * 解除禁言、解冻
	 * 
	 * @param violationInfo
	 * @return
	 * @throws ServiceException
	 */
	public Response<Boolean> updateViolation(ViolationInfo violationInfo);

	/**
	 * 查询违规记录
	 * @param userId
	 * @return
	 * TODO:待完成
	 */
	public Response<List<ViolationInfo>> getViolation(String userId);

}
