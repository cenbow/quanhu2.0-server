package com.yryz.quanhu.user.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.user.imsync.ImSyncService;
import com.yryz.quanhu.user.service.UserSyncApi;

@Service(interfaceClass=UserSyncApi.class)
public class UserSyncProvider implements UserSyncApi {
	private static final Logger logger = LoggerFactory.getLogger(UserSyncProvider.class);
	
	@Autowired
	ImSyncService syncService;
	
	
	@Override
	public Response<Boolean> syncUser(Integer actionType) {
		if(actionType == null || actionType < 0 ){
			return ResponseUtils.returnObjectSuccess(true);
		}
		try {
			switch (actionType) {
			case 1:
				syncService.syncImUser();
				break;
			case 2:
				syncService.syncImFriend();
			default:
				break;
			}
			return ResponseUtils.returnObjectSuccess(true);
		} catch (Exception e) {
			logger.error("[syncUser]", e);
			return ResponseUtils.returnException(e);
		}
	}

}
