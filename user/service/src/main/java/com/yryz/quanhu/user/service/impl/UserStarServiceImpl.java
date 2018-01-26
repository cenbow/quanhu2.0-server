package com.yryz.quanhu.user.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yryz.common.constant.IdConstants;
import com.yryz.common.exception.MysqlOptException;
import com.yryz.common.utils.BeanUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.dao.UserStarAuthDao;
import com.yryz.quanhu.user.dao.UserStarAuthLogDao;
import com.yryz.quanhu.user.dto.StarAuthParamDTO;
import com.yryz.quanhu.user.entity.UserBaseInfo;
import com.yryz.quanhu.user.entity.UserStarAuth;
import com.yryz.quanhu.user.entity.UserStarAuth.StarAuditStatus;
import com.yryz.quanhu.user.entity.UserStarAuth.StarAuthWay;
import com.yryz.quanhu.user.entity.UserStarAuth.StarRecommendStatus;
import com.yryz.quanhu.user.entity.UserStarAuthLog;
import com.yryz.quanhu.user.manager.EventManager;
import com.yryz.quanhu.user.manager.MessageManager;
import com.yryz.quanhu.user.entity.UserBaseInfo.UserAuthStatus;
import com.yryz.quanhu.user.entity.UserBaseInfo.UserRole;
import com.yryz.quanhu.user.service.UserService;
import com.yryz.quanhu.user.service.UserStarService;

/**
 * 用户达人管理
 * 
 * @author danshiyu
 *
 */
@Service
public class UserStarServiceImpl implements UserStarService {
	private static final Logger logger = LoggerFactory.getLogger(UserStarServiceImpl.class);

	@Autowired
	private UserStarAuthDao persistenceDao;
	@Reference(check=false)
	private IdAPI idApi;
	@Autowired
	private UserStarAuthLogDao starAuthLogDao;
	@Autowired
	private EventManager eventService;
	@Autowired
	MessageManager messageManager;
	// @Autowired
	// private CircleRemote circleService;
	@Autowired
	private UserService userService;

	@Override
	@Transactional
	public int save(UserStarAuth record) {
		record.setCreateDate(new Date());
		record.setKid(idApi.getKid(IdConstants.QUANHU_USER_STAR_AUTH).getData());
		record.setRecommendStatus(StarRecommendStatus.FALSE.getStatus());
		record.setAuditStatus(StarAuditStatus.WAIT_AUDIT.getStatus());
		if (StringUtils.isEmpty(record.getRealName())) {
			record.setRealName("");
		}
		if (StringUtils.isEmpty(record.getResourceDesc())) {
			record.setResourceDesc("");
		}
		if (StringUtils.isEmpty(record.getLocation())) {
			record.setLocation("");
		}
		try {
			// 平台设置直接通过
			if (record.getAuthWay().intValue() == StarAuthWay.ADMIN_SET.getWay()) {
				record.setAuditStatus(StarAuditStatus.AUDIT_SUCCESS.getStatus());
				record.setAuthTime(record.getCreateDate());
				updateUserStar(record.getUserId(), UserRole.STAR, UserAuthStatus.TRUE);
				// circleService.updateExpert(record.getCustId(),(byte)1);
				// 消息
				messageManager.starSuccess(record.getUserId().toString());
			}
			saveStarAuthLog(record);
			int result = persistenceDao.save(record);
			return result;
		} catch (Exception e) {
			logger.error("[UserStarAuthDao.save]", e);
			throw new MysqlOptException(e);
		}
	}

	@Override
	public UserStarAuth get(String custId, String idCard) {
		try {
			return persistenceDao.get(custId, idCard, null);
		} catch (Exception e) {
			logger.error("[UserStarAuthDao.get]", e);
			throw new MysqlOptException(e);
		}
	}

	@Override
	public List<UserStarAuth> get(List<String> custIds) {
		try {
			return persistenceDao.getByUserIds(custIds);
		} catch (Exception e) {
			logger.error("[UserStarAuthDao.getByCustIds]", e);
			throw new MysqlOptException(e);
		}
	}

	@Override
	@Transactional
	public int update(UserStarAuth record) {
		record.setAuditStatus(StarAuditStatus.WAIT_AUDIT.getStatus());
		record.setRecommendStatus(StarRecommendStatus.FALSE.getStatus());
		record.setAuditFailReason("");
		try {
			// 平台设置直接通过
			if (record.getAuthWay().intValue() == StarAuthWay.ADMIN_SET.getWay()) {
				record.setAuditStatus(StarAuditStatus.AUDIT_SUCCESS.getStatus());
				record.setAuthTime(new Date());
			}
			int result = persistenceDao.update(record);
			saveStarAuthLog(record);
			if (record.getAuthWay() == StarAuthWay.ADMIN_SET.getWay()) {
				updateUserStar(record.getUserId(), UserRole.STAR, UserAuthStatus.TRUE);
				// circleService.updateExpert(record.getCustId(),(byte)1);
				// 消息
				messageManager.starSuccess(record.getUserId().toString());
			} else {
				updateUserStar(record.getUserId(), UserRole.NORMAL, UserAuthStatus.TRUE);
				/* circleService.updateExpert(record.getCustId(),(byte)0); */
			}
			return result;
		} catch (Exception e) {
			logger.error("[UserStarAuthDao.update]", e);
			throw new MysqlOptException(e);
		}

	}

	@Override
	@Transactional
	public int updateAudit(UserStarAuth reAuthModel) {
		Byte auditStatus = reAuthModel.getAuditStatus();
		try {

			// 审核通过
			if (auditStatus == StarAuditStatus.AUDIT_SUCCESS.getStatus()) {
				reAuthModel.setAuditFailReason("");
				reAuthModel.setAuthTime(new Date());
			}
			// 审核失败
			if (auditStatus == StarAuditStatus.AUDIT_FAIL.getStatus()) {
				reAuthModel.setAuditFailTime(new Date());
			}
			// 取消认证
			if (auditStatus == StarAuditStatus.CANCEL_AUTH.getStatus()) {
				reAuthModel.setRecommendStatus((byte) 10);
				reAuthModel.setAuthCancelTime(new Date());

			}
			int result = persistenceDao.update(reAuthModel);
			saveStarAuthLog(reAuthModel);
			if (reAuthModel.getAuthTime() != null) {
				updateUserStar(reAuthModel.getUserId(), UserRole.STAR, UserAuthStatus.TRUE);
				// circleService.updateExpert(reAuthModel.getCustId(),(byte)1);
				// 消息
				messageManager.starSuccess(reAuthModel.getUserId().toString());
			}
			if (reAuthModel.getAuditFailTime() != null) {
				 messageManager.starFail(reAuthModel.getUserId().toString(),
				 reAuthModel.getAuditFailReason());
			}

			if (reAuthModel.getAuthCancelTime() != null) {
				updateUserStar(reAuthModel.getUserId(), UserRole.NORMAL, UserAuthStatus.TRUE);
				// circleService.updateExpert(reAuthModel.getCustId(),(byte)0);
				// 消息
				messageManager.starCancel(reAuthModel.getUserId().toString());
			}
			return result;
		} catch (Exception e) {
			logger.error("[UserStarAuthDao.update]", e);
			throw new MysqlOptException(e);
		}
	}

	@Override
	@Transactional
	public int updateRecommend(UserStarAuth authModel) {
		Byte starRecommendStatus = authModel.getRecommendStatus();

		// 取消推荐
		if (starRecommendStatus == StarRecommendStatus.FALSE.getStatus()) {
			authModel.setRecommendTime(new Date());
		}
		// 设置推荐
		if (starRecommendStatus == StarRecommendStatus.TRUE.getStatus()) {
			authModel.setRecommendCancelTime(new Date());
			// 设置权重
			Integer maxWeight = persistenceDao.getStarMaxWeight();
			authModel.setRecommendHeight((maxWeight == null ? 0 : maxWeight) + 10);
		}
		try {
			return persistenceDao.update(authModel);
		} catch (Exception e) {
			logger.error("[UserStarAuthDao.update]", e);
			throw new MysqlOptException(e);
		}
	}

	@Override
	public Page<UserStarAuth> listByParams(Integer pageNo, Integer pageSize, StarAuthParamDTO paramDTO) {
		Page<UserStarAuth> page = PageHelper.startPage(pageNo, pageSize);
		try {
			persistenceDao.listByParams(paramDTO);
		} catch (Exception e) {
			logger.error("[UserStarAuthDao.listByParams]", e);
			throw new MysqlOptException(e);
		}
		return page;
	}

	@Override
	public Page<UserStarAuth> starList(StarAuthParamDTO paramDTO) {
		Page<UserStarAuth> page = PageHelper.startPage(paramDTO.getCurrentPage(), paramDTO.getPageSize(),false);
		try {
			persistenceDao.starList(paramDTO);
			return page;
		} catch (Exception e) {
			logger.error("[UserStarAuthDao.starList]", e);
			throw new MysqlOptException(e);
		}
	}

	@Override
	public Page<UserStarAuth> labelStarList(StarAuthParamDTO paramDTO) {
		Page<UserStarAuth> page = PageHelper.startPage(paramDTO.getCurrentPage(), paramDTO.getPageSize(),false);
		try {
			persistenceDao.labelStarList(paramDTO);
			return page;
		} catch (Exception e) {
			logger.error("labelStarList error", e);
			throw new MysqlOptException(e);
		}
	}

	@Override
	public Integer countStar() {
		try {
			return persistenceDao.countStar();
		} catch (Exception e) {
			logger.error("[UserStarAuthDao.countStar]", e);
			throw new MysqlOptException(e);
		}
	}

	/**
	 * 更新用户为达人
	 * 
	 * @param record
	 */
	private void updateUserStar(Long userId, UserRole role, UserAuthStatus authStatus) {
		userService.updateUserInfo(
				new UserBaseInfo(userId, null, role.getRole(), null, authStatus.getStatus(), null, null));
		// 设置达人等级
		eventService.starAuth(userId.toString());
	}

	/**
	 * 保存达人审核日志
	 * 
	 * @param authModel
	 */
	private void saveStarAuthLog(UserStarAuth authModel) {
		UserStarAuth oldAuth = get(authModel.getUserId().toString(), null);
		UserStarAuthLog logModel = new UserStarAuthLog();
		if (oldAuth == null) {
			BeanUtils.copyProperties(oldAuth, logModel);
		} else {
			BeanUtils.copyProperties(authModel, oldAuth, BeanUtils.getNullPropertyNames(authModel));
			BeanUtils.copyProperties(oldAuth, logModel);
		}
		try {
			logModel.setKid(idApi.getKid(IdConstants.QUANHU_USER_STAR_AUTH_LOG).getData());
			logModel.setCreateDate(new Date());
			starAuthLogDao.insert(logModel);
		} catch (Exception e) {
			logger.error("[UserStarAuthLogDao.insert]", e);
			throw new MysqlOptException(e);
		}
	}

	@Override
	public List<UserStarAuthLog> listStarDetail(String custId) {
		try {
			return starAuthLogDao.selectByUserId(custId);
		} catch (Exception e) {
			logger.error("[UserStarAuthLogDao.selectByCustId]", e);
			throw new MysqlOptException(e);
		}
	}

}
