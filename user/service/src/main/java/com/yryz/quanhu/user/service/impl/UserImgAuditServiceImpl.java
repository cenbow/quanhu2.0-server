/**
 * 
 */
package com.yryz.quanhu.user.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
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
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.contants.Constants.ImgAuditStatus;
import com.yryz.quanhu.user.dao.UserImgAuditDao;
import com.yryz.quanhu.user.dto.UserImgAuditDTO;
import com.yryz.quanhu.user.entity.UserBaseInfo;
import com.yryz.quanhu.user.entity.UserImgAudit;
import com.yryz.quanhu.user.manager.MessageManager;
import com.yryz.quanhu.user.service.UserImgAuditService;
import com.yryz.quanhu.user.service.UserService;

/**
 * @author danshiyu
 *
 */
@Service
public class UserImgAuditServiceImpl implements UserImgAuditService {
	private static final Logger logger = LoggerFactory.getLogger(UserImgAuditServiceImpl.class);

	@Autowired
	private UserImgAuditDao imgAuditDao;
	@Reference(check = false)
	private IdAPI idApi;
	@Autowired
	private UserService userService;
	@Autowired
	private MessageManager messageManager;

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public int auditImg(UserImgAudit record, Integer aduitActionStatus) {
		record.setAuditStatus(aduitActionStatus.byteValue());
		int result = 0;
		try {
			UserImgAudit imgAudit = get(record);

			if (imgAudit == null) {
				record.setCreateDate(new Date());
				record.setKid(ResponseUtils.getResponseData(idApi.getKid(IdConstants.QUANHU_USER_IMG_AUDIT)));
				result = imgAuditDao.save(record);
			} else {
				record.setUserId(imgAudit.getUserId());
				result = imgAuditDao.update(record);
			}
			// 拒绝后清空用户头像
			if (aduitActionStatus.intValue() == ImgAuditStatus.FAIL.getStatus()) {
				userService.updateUserInfo(new UserBaseInfo(record.getUserId(), ""));
				// 消息
				messageManager.userImgAudit(record.getUserId().toString());
			}
		} catch (Exception e) {
			logger.error("[UserImgAuditDao.save or update]", e);
			throw new MysqlOptException(e);
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public int batchAuditImg(UserImgAuditDTO auditDTO) {
		try {
			if (CollectionUtils.isNotEmpty(auditDTO.getKids())) {
				imgAuditDao.batchUpdate(auditDTO.getKids(), auditDTO.getAuditStatus().intValue(),
						auditDTO.getOperational(), auditDTO.getLastUpdateUserId());
			}
			List<Long> userIds = auditDTO.getUserIds();
			// 拒绝后清空用户头像
			if (auditDTO.getAuditStatus().intValue() == ImgAuditStatus.FAIL.getStatus()) {
				for (int i = 0; i < userIds.size(); i++) {
					Long userId = userIds.get(i);
					userService.updateUserInfo(new UserBaseInfo(userId, ""));
					messageManager.userImgAudit(userId.toString());
				}
			}
		} catch (Exception e) {
			logger.error("[UserImgAuditDao.batchSave or batchUpdate]", e);
			throw new MysqlOptException(e);
		}
		return 0;
	}

	@Override
	public Page<UserImgAudit> listByUserId(Integer pageNo, Integer pageSize, String userId, Integer auditStatus) {
		try {
			Page<UserImgAudit> page = PageHelper.startPage(pageNo, pageSize);
			imgAuditDao.listByUserId(userId, auditStatus);
			return page;
		} catch (Exception e) {
			logger.error("[UserImgAuditDao.listByUserId]", e);
			throw new MysqlOptException(e);
		}
	}

	@Override
	public int delete(Long userId) {
		try {
			return imgAuditDao.delete(userId);
		} catch (Exception e) {
			logger.error("[UserImgAuditDao.delete]", e);
			throw new MysqlOptException(e);
		}
	}

	private UserImgAudit get(UserImgAudit record) {
		return imgAuditDao.findByUserId(record.getKid(), record.getUserId());
	}
}
