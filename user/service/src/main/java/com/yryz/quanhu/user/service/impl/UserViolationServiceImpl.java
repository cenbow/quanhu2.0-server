/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年5月24日
 * Id: CustViolationServiceImpl.java, 2017年5月24日 下午6:58:17 danshiyu
 */
package com.yryz.quanhu.user.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yryz.common.constant.IdConstants;
import com.yryz.common.exception.MysqlOptException;
import com.yryz.common.exception.RedisOptException;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.framework.core.cache.RedisTemplateBuilder;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.contants.Constants;
import com.yryz.quanhu.user.contants.UserAccountStatus;
import com.yryz.quanhu.user.contants.ViolatType;
import com.yryz.quanhu.user.dao.UserViolationDao;
import com.yryz.quanhu.user.entity.UserBaseInfo;
import com.yryz.quanhu.user.entity.UserViolation;
import com.yryz.quanhu.user.manager.ImManager;
import com.yryz.quanhu.user.manager.MessageManager;
import com.yryz.quanhu.user.service.AuthService;
import com.yryz.quanhu.user.service.UserService;
import com.yryz.quanhu.user.service.UserViolationService;
import com.yryz.quanhu.user.service.ViolationApi;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年5月24日 下午6:58:17
 * @Description 用户违规登记
 */
@Service
public class UserViolationServiceImpl implements UserViolationService {
	private final static Logger logger = LoggerFactory.getLogger(UserViolationServiceImpl.class);
	@Autowired
	private UserViolationDao mysqlDao;
	@Reference(check = false)
	private IdAPI idApi;
	@Resource
	private RedisTemplateBuilder redisTemplateBuilder;
	@Autowired
	private UserService userService;
	@Autowired
	private AuthService authService;
	@Autowired
	private MessageManager messageManager;
	@Autowired
	private ImManager imManager;

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public void saveViolation(UserViolation violation, String appId) {
		// 警告、解冻、解除禁言类型直接设置过期
		if (violation.getViolationType() == ViolatType.WARN.getType()
				|| violation.getViolationType() == ViolatType.ALLTAIK.getType()
				|| violation.getViolationType() == ViolatType.NOFREEZE.getType()) {
			violation.setUserStatus((byte) 11);
		} else {
			violation.setUserStatus((byte) 10);
		}
		// 设置用户其他违规记录过期

		updateViolation(violation.getUserId().toString());

		// 保存当前违规记录
		saveViolationDao(violation);
		updateUserStatus(violation, appId);

		// 判断警告是否满足禁言
		if (violation.getViolationType().intValue() == ViolatType.WARN.getType()) {
			long times = getUserWarnTimes(violation.getUserId().toString());
			if (times < Constants.WARN_TIMES) {
				times = incrUserWarnTimes(violation.getUserId().toString());
				// 禁言
				if (times == Constants.WARN_TIMES) {
					UserViolation violation2 = (UserViolation) GsonUtils.parseObj(violation, UserViolation.class);
					violation2.setUserStatus((byte) 0);
					violation2.setViolationType((byte) ViolatType.NOTALK.getType());
					saveViolationDao(violation2);
					updateUserStatus(violation2, appId);
				}
			}
		}
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public void updateViolation(UserViolation violation, String appId) {
		// 解除禁言
		if (violation.getViolationType().intValue() == Constants.WARN_TIMES) {
			// 同步用户状态
			userService.updateUserInfo(new UserBaseInfo(violation.getUserId(),
					(byte) UserAccountStatus.NORMAL.getStatus(), null, null, null, null, new Date()));
			violation.setPushMessage("解除禁言");
			violation.setReason("解除禁言");
		} // 解冻
		else {
			// 同步用户状态
			userService.updateUserInfo(new UserBaseInfo(violation.getUserId(),
					(byte) UserAccountStatus.NORMAL.getStatus(), null, null, null, null, new Date()));
			violation.setPushMessage("解冻");
			violation.setReason("解冻");
		}
		saveViolation(violation, appId);
		clearUserWarnTimes(violation.getUserId().toString());
	}

	@Override
	public List<UserViolation> getCustViolationList(String custId) {
		try {
			return mysqlDao.listViolation(custId);
		} catch (Exception e) {
			logger.error("[violation get cust list error]", e);
			throw new MysqlOptException(e);
		}
	}

	@Override
	public Page<UserViolation> getCustViolationList(int pageNo, int pageSize, List<String> custIds, Integer type,
			String startDate, String endDate) {
		// 使用pagehelper 查询实现count 分页
		Page<UserViolation> page = PageHelper.startPage(pageNo, pageSize);
		listPageViolation(custIds, type, startDate, endDate);
		return page;
	}

	/**
	 * 
	 * @author danshiyu
	 * @date 2017年5月24日
	 * @param violation
	 * @Description 保存违规记录
	 */
	private void saveViolationDao(UserViolation violation) {
		try {
			violation.setKid(ResponseUtils.getResponseData(idApi.getKid(IdConstants.QUANHU_USER_VIOLATION)));
			violation.setCreateDate(new Date());
			mysqlDao.saveViolation(violation);
		} catch (Exception e) {
			logger.error("[violation save error]", e);
			throw new MysqlOptException(e);
		}
	}

	/**
	 * @author danshiyu
	 * @date 2017年5月24日
	 * @param custId
	 * @Description 设置违规记录过期
	 */
	private void updateViolation(String custId) {
		try {
			mysqlDao.updateViolation(custId);
		} catch (Exception e) {
			logger.error("[violation update status error]", e);
			throw new MysqlOptException(e);
		}
	}

	/**
	 * 
	 * @author danshiyu
	 * @date 2017年5月24日
	 * @param nickName
	 * @param phone
	 * @param type
	 * @return
	 * @Description 管理后台查询用户违规记录
	 */
	private List<UserViolation> listPageViolation(List<String> custIds, Integer type, String startDate,
			String endDate) {
		try {
			Map<String, Object> params = new HashMap<>(4);
			params.put("custIds", custIds);
			params.put("startDate", startDate);
			params.put("endDate", endDate);
			if (type == null) {
				params.put("violation", 1);
			} else if (type.intValue() == ViolatType.NOTALK.getType()) {
				params.put("banPost", 1);
			} else if (type.intValue() == ViolatType.FREEZE.getType()) {
				params.put("freeze", 1);
			}
			return mysqlDao.listPageViolation(params);
		} catch (Exception e) {
			logger.error("[violation get page error]", e);
			throw new MysqlOptException(e);
		}
	}

	/**
	 * 用户状态更新
	 * 
	 * @param violation
	 */
	private void updateUserStatus(UserViolation violation, String appId) {
		// 警告推送
		if (violation.getViolationType() == ViolatType.WARN.getType()) {
			messageManager.warn(violation.getUserId().toString());
		} // 禁言
		else if (violation.getViolationType().intValue() == ViolatType.NOTALK.getType()) {
			// 同步用户状态
			userService
					.updateUserInfo(new UserBaseInfo(violation.getUserId(), (byte) UserAccountStatus.NORMAL.getStatus(),
							null, null, null, null, DateUtils.addDays(new Date(), Constants.NO_TALK_HOUR)));
			// 消息
			messageManager.disTalk(violation.getUserId().toString());
		} // 冻结
		else if (violation.getViolationType().intValue() == ViolatType.FREEZE.getType()) {
			// 同步用户状态
			userService.updateUserInfo(new UserBaseInfo(violation.getUserId(),
					(byte) UserAccountStatus.FREEZE.getStatus(), null, null, null, null, new Date()));
			// 请求im发送下线消息
			imManager.block(violation.getUserId(), appId);
			authService.delToken(violation.getUserId(), appId);
		} // 注销
		else {
			// 同步用户状态
			userService.updateUserInfo(new UserBaseInfo(violation.getUserId(),
					(byte) UserAccountStatus.DISTORY.getStatus(), null, null, null, null, new Date()));
			// 请求im发送下线消息
			imManager.block(violation.getUserId(), appId);
			authService.delToken(violation.getUserId(), appId);
		}
	}

	/**
	 * 增加用户警告数 ，当用户警告数已存在且达到3后直接设置1
	 * 
	 * @param custId
	 * @return
	 */
	private long incrUserWarnTimes(String custId) {
		String key = ViolationApi.warnTimesKey(custId);
		RedisTemplate<String, Long> template = redisTemplateBuilder.buildRedisTemplate(Long.class);
		try {
			long times = template.opsForValue().increment(key, 1);
			if (times == 3) {
				template.expire(key, Constants.NO_TALK_HOUR, TimeUnit.HOURS);
			}
			return times;
		} catch (Exception e) {
			logger.error("incrUserWarnTimes", e);
			throw new RedisOptException(e);
		}
	}

	/**
	 * 查询用户警告数
	 * 
	 * @param custId
	 * @return
	 */
	private long getUserWarnTimes(String custId) {
		String key = ViolationApi.warnTimesKey(custId);
		RedisTemplate<String, Long> template = redisTemplateBuilder.buildRedisTemplate(Long.class);
		try {
			Long times = template.opsForValue().get(key);
			if (times == null) {
				return 0;
			}
			return times;

		} catch (Exception e) {
			logger.error("getUserWarnTimes", e);
			throw new RedisOptException(e);
		}
	}

	/**
	 * 清除用户警告数
	 * 
	 * @param custId
	 * @return
	 */
	private void clearUserWarnTimes(String custId) {
		String key = ViolationApi.warnTimesKey(custId);
		RedisTemplate<String, Long> template = redisTemplateBuilder.buildRedisTemplate(Long.class);
		try {
			template.delete(key);
		} catch (Exception e) {
			logger.error("UserRedis.clearUserWarnTimes", e);
			throw new RedisOptException(e);
		}
	}
}
