package com.yryz.quanhu.user.service;

import com.yryz.common.response.Response;
import com.yryz.quanhu.user.vo.MyInviterVO;
import com.yryz.quanhu.user.vo.UserRegInviterLinkVO;

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
}
