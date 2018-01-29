package com.yryz.quanhu.dymaic.canal.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.Response;
import com.yryz.common.utils.DateUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.dymaic.canal.dao.UserRepository;
import com.yryz.quanhu.dymaic.canal.entity.UserBaseInfo;
import com.yryz.quanhu.dymaic.canal.entity.UserInfo;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserBaseInfoVO;

@Component
public class UserDiffHandler implements DiffHandler {
	private static final Log logger = LogFactory.getLog(UserDiffHandler.class);
	@Resource
	private UserRepository userRepository;
	@Reference
	private UserApi userApi;
	@Resource
	private DiffExecutor diffExecutor;

	@PostConstruct
	public void register() {
		diffExecutor.register(this);		
	}
	
	@Override
	public void handler() {
		String yesterday = DateUtils.getNextDay();
		Response<List<Long>> res = userApi.getUserIdByCreateDate(yesterday + " 00:00:00", yesterday + " 23:59:59");
		if (!res.success()) {
			logger.error("diff user error:" + res.getErrorMsg());
			return;
		}

		List<Long> diffList = new ArrayList<>();
		List<Long> userIdList = res.getData();
		for (int i = 0; i < userIdList.size(); i++) {
			long userId = userIdList.get(i);
			Optional<UserInfo> info = userRepository.findById(userId);
			if (!info.isPresent()) {
				diffList.add(userId);
			}
		}

		if (!diffList.isEmpty()) {
			Response<List<UserBaseInfoVO>> resList = userApi.getAllByUserIds(diffList);
			if (resList.success()) {
				List<UserBaseInfoVO> volist = resList.getData();
				List<UserInfo> list = new ArrayList<>();
				for (int i = 0; i < volist.size(); i++) {
					UserBaseInfo baseInfo = GsonUtils.parseObj(volist.get(i), UserBaseInfo.class);
					UserInfo userInfo = new UserInfo();
					userInfo.setUserBaseInfo(baseInfo);
					userInfo.setUserId(baseInfo.getUserId());
					list.add(userInfo);
				}
				userRepository.saveAll(list);
			}
		}
	}

}
