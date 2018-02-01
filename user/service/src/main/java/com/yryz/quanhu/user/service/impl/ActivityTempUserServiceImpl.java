package com.yryz.quanhu.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.coterie.coterie.exception.MysqlOptException;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.dao.ActivityTempUserDao;
import com.yryz.quanhu.user.entity.ActivityTempUser;
import com.yryz.quanhu.user.entity.UserBaseInfo;
import com.yryz.quanhu.user.service.ActivityTempUserService;

@Service
public class ActivityTempUserServiceImpl implements ActivityTempUserService {
	private static final Logger logger = LoggerFactory.getLogger(ActivityTempUserServiceImpl.class);

	@Autowired
	private ActivityTempUserDao mysqlDao;
	@Reference
	private IdAPI idApi;

	@Override
	public Long save(ActivityTempUser tempUser) {
		try {
			tempUser.setCreateDate(new Date());
			tempUser.setKid(NumberUtils.createLong(ResponseUtils.getResponseData(idApi.getUserId())));
			mysqlDao.insert(tempUser);
			return tempUser.getKid();
		} catch (Exception e) {
			logger.error("[ActivityTempUserDao.insert]", e);
			throw new MysqlOptException(e);
		}
	}

	@Override
	public ActivityTempUser get(Long userId, String thirdId) {
		try {
			return mysqlDao.selectOne(userId, thirdId);
		} catch (Exception e) {
			logger.error("[ActivityTempUserDao.selectOne]", e);
			throw new MysqlOptException(e);
		}
	}

	@Override
	public List<UserBaseInfo> getUserBaseInfoByTempUser(List<String> userIds) {
		List<UserBaseInfo> infos = null;
		List<ActivityTempUser> tempUsers = null;
		try {
			tempUsers = mysqlDao.getByUserIds(userIds);
		} catch (Exception e) {
			logger.error("[ActivityTempUserDao.getByUserIds]", e);
			throw new MysqlOptException(e);
		}
		if (CollectionUtils.isNotEmpty(tempUsers)) {
			int tempLength = tempUsers.size();
			infos = new ArrayList<>(tempLength);
			for (int i = 0; i < tempLength; i++) {
				UserBaseInfo info = new UserBaseInfo();
				ActivityTempUser tempUser = tempUsers.get(i);
				info.setAppId(tempUser.getAppId());
				info.setUserNickName(tempUser.getNickName());
				info.setUserImg(tempUser.getHeadImg());
				info.setCreateDate(tempUser.getCreateDate());
				infos.add(info);
			}
		}
		return infos;
	}

}
