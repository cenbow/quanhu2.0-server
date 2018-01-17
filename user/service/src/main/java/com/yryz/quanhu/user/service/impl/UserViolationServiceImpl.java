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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.MysqlOptException;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.user.contants.ViolatType;
import com.yryz.quanhu.user.dao.UserViolationDao;
import com.yryz.quanhu.user.entity.UserViolation;
import com.yryz.quanhu.user.service.AuthService;
import com.yryz.quanhu.user.service.UserViolationService;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年5月24日 下午6:58:17
 * @Description 用户违规登记
 */
@Service
public class UserViolationServiceImpl implements UserViolationService {
	private final static Logger logger = LoggerFactory.getLogger(UserViolationServiceImpl.class);
	/**
	 * 警告禁言次数
	 */
	private static final int WARN_TIMES = 3;
	/**
	 * 禁言时间（小时）
	 */
	private static final int NO_TALK_HOUR = 8;
	
	/** 禁言类型 */
	private static final int NO_TALK_TYPE = 1;
	/** 冻结类型 */
	private static final int FREEZE_TYPE = 2;
	@Autowired
	private UserViolationDao mysqlDao;

/*	@Autowired
	private AccountRedisDao userRedisDao;*/
/*	@Autowired
	private UserService userService;*/
	@Autowired
	private AuthService authService;
/*	@Autowired
	private PushManager pushService;*/
	
	@Override
	@Transactional(rollbackFor=RuntimeException.class)
	public void saveViolation(UserViolation violation, String appId) {
		// 警告、解冻、解除禁言类型直接设置过期
		if (violation.getViolationType() == ViolatType.WARN.getType()
				|| violation.getViolationType() == ViolatType.ALLTAIK.getType()
				|| violation.getViolationType() == ViolatType.NOFREEZE.getType()) {
			violation.setStatus((byte) 1);
		} else {
			violation.setStatus((byte) 0);
		}
		// 设置用户其他违规记录过期
		violation.setCreateDate(new Date());
		updateViolation(violation.getUserId().toString());

		// 保存当前违规记录
		saveViolationDao(violation);
		updateUserStatus(violation);

		// 判断警告是否满足禁言
		if (violation.getViolationType().intValue() == ViolatType.WARN.getType()) {
			long times = 0;//userRedisDao.getUserWarnTimes(violation.getCustId());
			if (times < WARN_TIMES) {
				//times = userRedisDao.incrUserWarnTimes(violation.getCustId());
				// 禁言
				if (times == WARN_TIMES) {
					UserViolation violation2 = (UserViolation) GsonUtils.parseObj(violation, UserViolation.class);
					violation2.setStatus((byte) 0);
					violation2.setViolationType((byte) ViolatType.NOTALK.getType());
					saveViolationDao(violation2);
					updateUserStatus(violation2);
				}
			}
		}
	}

	@Override
	@Transactional(rollbackFor=RuntimeException.class)
	public void updateViolation(UserViolation violation, String appId) {
		// 解除禁言
		if (violation.getViolationType().intValue() == WARN_TIMES) {
			// 同步用户状态
			//userService.updateCustInfo(new CustBaseInfo(violation.getCustId(), (byte) 1, DateUtils.now()));
			violation.setPushMessage("解除禁言");
			violation.setReason("解除禁言");
		} // 解冻
		else {
			// 同步用户状态
			//userService.updateCustInfo(new CustBaseInfo(violation.getCustId(), (byte) 1, DateUtils.now()));
			violation.setPushMessage("解冻");
			violation.setReason("解冻");
		}
		saveViolation(violation, appId);
		//userRedisDao.clearUserWarnTimes(violation.getCustId());
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

	/**
	 * 查询用户状态有效的违规记录
	 * 
	 * @param custId
	 * @return
	 */
	private UserViolation getCustViolation(Long custId) {
		try {
			return mysqlDao.getCustViolation(custId);
		} catch (Exception e) {
			logger.error("[violation get error]", e);
			return null;
		}
	}

	@Override
	public Page<UserViolation> getCustViolationList(int pageNo, int pageSize, List<String> custIds, Integer type,
			String startDate, String endDate) {
		// 使用pagehelper 查询实现count 分页
		@SuppressWarnings("unchecked")
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
			} else if (type.intValue() == NO_TALK_TYPE) {
				params.put("freeze", 1);
			} else if (type.intValue() == FREEZE_TYPE) {
				params.put("black", 1);
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
	private void updateUserStatus(UserViolation violation) {
		// 警告推送
		if (violation.getViolationType() == ViolatType.WARN.getType()) {
			//pushService.warn(violation.getCustId());
		} // 禁言
		else if (violation.getViolationType().intValue() == ViolatType.NOTALK.getType()) {
			// 同步用户状态
			//userService
			//		.updateCustInfo(new CustBaseInfo(violation.getCustId(), (byte) 1, DateUtils.addHour(NO_TALK_HOUR)));
			// 消息
			// messageManager.disTalk(violation.getCustId());
		} // 冻结
		else if (violation.getViolationType().intValue() == ViolatType.FREEZE.getType()) {
			// 同步用户状态
			//userService.updateCustInfo(new CustBaseInfo(violation.getCustId(), (byte) 0, null));
			authService.delToken(violation.getUserId().toString(),"");
		}
	}

	@Override
	public boolean checkCustTalk(Long custId) {
		UserViolation custViolation = getCustViolation(custId);
		if (custViolation == null) {
			return false;
		}
		if (custViolation.getViolationType() == ViolatType.NOTALK.getType()
				&& custViolation.getBanPostTime().getTime() > System.currentTimeMillis()) {
			return true;
		}
		if (custViolation.getViolationType() == ViolatType.FREEZE.getType()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkCustFreeze(Long custId) {
		UserViolation custViolation = getCustViolation(custId);
		if (custViolation == null) {
			return false;
		}
		if (custViolation.getViolationType() == ViolatType.FREEZE.getType()) {
			return true;
		}
		return false;
	}

	@Override
	public void checkUserStatus(Long custId) {
		boolean flag = this.checkCustFreeze(custId);
		if (flag) {
			throw new QuanhuException(ExceptionEnum.BusiException.getCode(), "",ExceptionEnum.BusiException.getErrorMsg());
		}
	}

}
