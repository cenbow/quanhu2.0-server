/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年11月10日
 * Id: UserOperateServiceImpl.java, 2017年11月10日 下午5:11:05 Administrator
 */
package com.yryz.quanhu.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yryz.common.constant.IdConstants;
import com.yryz.common.exception.MysqlOptException;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.dao.UserOperateInfoDao;
import com.yryz.quanhu.user.dao.UserRegLogDao;
import com.yryz.quanhu.user.dto.UserRegLogDTO;
import com.yryz.quanhu.user.dto.UserRegQueryDTO;
import com.yryz.quanhu.user.entity.UserOperateInfo;
import com.yryz.quanhu.user.entity.UserRegLog;
import com.yryz.quanhu.user.manager.EventManager;
import com.yryz.quanhu.user.service.UserOperateService;
import com.yryz.quanhu.user.service.UserService;
import com.yryz.quanhu.user.vo.MyInviterDetailVO;
import com.yryz.quanhu.user.vo.MyInviterVO;
import com.yryz.quanhu.user.vo.UserSimpleVO;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年11月10日 下午5:11:05
 * @Description 运营信息管理
 */
@Service
public class UserOperateServiceImpl implements UserOperateService {
	private static final Logger logger = LoggerFactory.getLogger(UserOperateServiceImpl.class);

	@Autowired
	private UserOperateInfoDao mysqlDao;

	@Autowired
	private UserRegLogDao regLogDao;
	@Reference(check=false)
	private IdAPI idApi;
	@Autowired
	private UserService userService;
	@Autowired
	private EventManager eventService;
	
	@Override
	public int save(UserOperateInfo record) {
		record.setCreateDate(new Date());
		record.setKid(idApi.getKid(IdConstants.QUANHU_USER_OPERATION_INFO).getData());
		record.setUserInviterCode(idApi.getKid(IdConstants.QUANHU_USER_OPERATION_INFO).getData().toString());
		if (StringUtils.isNotBlank(record.getUserRegInviterCode())) {
			String userRegId = selectUserIdByInviter(record.getUserRegInviterCode());
			record.setUserRegId(userRegId);
		}
		try {
			return mysqlDao.save(record);
		} catch (Exception e) {
			logger.error("[UserRegDao.save]", e);
			throw new MysqlOptException(e);
		}
	}

	@Override
	public int saveRegLog(UserRegLogDTO logDTO) {
		UserRegLog model = (UserRegLog) GsonUtils.parseObj(logDTO, UserRegLog.class);
		if (model == null) {
			return 0;
		}
		model.setKid(idApi.getKid(IdConstants.QUANHU_USER_REG_LOG).getData());
		model.setCreateDate(new Date());
		try {
			return regLogDao.insert(model);
		} catch (Exception e) {
			logger.error("[UserRegLogDao.save]", e);
			throw new MysqlOptException(e);
		}
	}

	@Override
	public String selectInviterByUserId(Long userId) {
		try {
			return mysqlDao.selectInviterByUserId(userId);
		} catch (Exception e) {
			logger.error("[UserRegDao.selectInviterByuserId]", e);
			throw new MysqlOptException(e);
		}
	}

	@Override
	public String selectUserIdByInviter(String inviterCode) {
		try {
			return mysqlDao.selectUserIdByInviter(inviterCode);
		} catch (Exception e) {
			logger.error("[UserRegDao.selectuserIdByInviter]", e);
			throw new MysqlOptException(e);
		}
	}

	/**
	 * 查询我邀请的好友详情
	 * 
	 * @param userId
	 * @param limit
	 * @param inviterId
	 * @return
	 */
	private List<UserOperateInfo> listByUserId(Long userId, Integer limit, Integer inviterId) {
		try {
			return mysqlDao.listByUserId(userId, limit, inviterId);
		} catch (Exception e) {
			logger.error("[UserRegDao.listByuserId]", e);
			throw new MysqlOptException(e);
		}
	}

	@SuppressWarnings("unused")
	@Override
	public Page<UserOperateInfo> listByParams(Integer pageNo, Integer pageSize, UserRegQueryDTO queryDTO) {
		Page<UserOperateInfo> page = PageHelper.startPage(pageNo, pageSize);
		try {
			List<UserOperateInfo> models = mysqlDao.listByParams(queryDTO);
		} catch (Exception e) {
			logger.error("[UserRegDao.listByParams]", e);
			throw new MysqlOptException(e);
		}
		return page;
	}

	@Override
	public void updateInviterNum(String inviterCode) {
		try {
			mysqlDao.updateInviterNum(inviterCode);
		} catch (Exception e) {
			logger.error("[UserRegDao.updateInviterNum]", e);
			throw new MysqlOptException(e);
		}
	}

	/**
	 * 查询我邀请的好友数
	 * 
	 * @param userId
	 * @return
	 */
	private int getInviterNum(Long userId) {
		try {
			return mysqlDao.getInviterNum(userId);
		} catch (Exception e) {
			logger.error("[UserRegDao.getInviterNum]", e);
			throw new MysqlOptException(e);
		}
	}

	@Override
	public MyInviterVO getMyInviter(Long userId, Integer limit, Integer inviterId) {
		List<UserOperateInfo> list = this.listByUserId(userId, limit, inviterId);
		int regLength = list == null ? 0 : list.size();
		if (regLength == 0) {
			return null;
		}

		int total = this.getInviterNum(userId);

		Set<String> userIds = new HashSet<>(regLength);
		Map<String, UserSimpleVO> userMap = new HashMap<>(regLength);

		for (int i = 0; i < regLength; i++) {
			userIds.add(list.get(i).getUserId().toString());
		}
		if (CollectionUtils.isNotEmpty(userIds)) {
			userMap = userService.getUserSimple(userId,userIds);
		}

		List<MyInviterDetailVO> detailVOs = new ArrayList<>(regLength);
		for (int i = 0; i < regLength; i++) {
			UserOperateInfo model = list.get(i);
			UserSimpleVO simpleVo = userMap.get(model.getUserId());
			if (simpleVo != null) {
				detailVOs.add(new MyInviterDetailVO(model.getKid(), simpleVo.getUserNickName(),
						model.getCreateDate().getTime()));
			}
		}
		return new MyInviterVO(total, detailVOs);
	}

	@Override
	public void commitInviterEvent(String inviterCode) {
		String userId = this.selectUserIdByInviter(inviterCode);
		// TODO:注册加积分
		eventService.inviterRegister(userId);
	}

}
