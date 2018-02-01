package com.yryz.quanhu.user.service;

import java.util.List;

import com.yryz.common.response.Response;
import com.yryz.quanhu.user.vo.MyInviterVO;
import com.yryz.quanhu.user.vo.UserRegInviterLinkVO;
import com.yryz.quanhu.user.vo.UserRegLogVO;

/**
 * 用户运营、注册等服务
 * @author danshiyu
 *
 */
public interface UserOperateApi {
	/**
	 * 根据邀请码获取邀请链接
	 * @param userId
	 * @return
	 */
	Response<UserRegInviterLinkVO> getInviterLinkByUserId(Long userId);
	/**
	 * 查询我邀请的用户详情
	 * 
	 * @param userId
	 * @param limit
	 * @param inviterId
	 *            主键
	 * @return
	 */
	Response<MyInviterVO> getMyInviter(Long userId, Integer limit,
			Long inviterId);
	
	/**
	 * 根据创建时间查询用户id
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	Response<List<Long>> getUserIdByCreateDate(String startDate,String endDate);
	/**
	 * 根据用户id查询
	 * @param userIds
	 * @return
	 */
	Response<List<UserRegLogVO>> listByUserId(List<Long> userIds);
}
