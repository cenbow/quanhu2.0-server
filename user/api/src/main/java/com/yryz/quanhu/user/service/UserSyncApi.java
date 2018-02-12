package com.yryz.quanhu.user.service;

import com.yryz.common.response.Response;

/**
 * 用户同步服务
 * @author danshiyu
 *
 */
public interface UserSyncApi {
	
	Response<Boolean> syncUser(Integer actionType);
}
