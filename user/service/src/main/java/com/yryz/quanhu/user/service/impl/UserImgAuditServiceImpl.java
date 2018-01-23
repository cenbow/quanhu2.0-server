/**
 * 
 */
package com.yryz.quanhu.user.service.impl;

import java.util.ArrayList;
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
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.dao.UserImgAuditDao;
import com.yryz.quanhu.user.entity.UserBaseInfo;
import com.yryz.quanhu.user.entity.UserImgAudit;
import com.yryz.quanhu.user.entity.UserImgAudit.ImgAuditStatus;
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
	@Reference(check=false)
	private IdAPI idApi;
	@Autowired
	private UserService userService;

	@Override
	@Transactional(rollbackFor=RuntimeException.class)
	public int auditImg(UserImgAudit record, Integer aduitActionStatus) {
		Byte auditStatus = record.getAuditStatus();
		record.setAuditStatus(aduitActionStatus.byteValue());
		int result = 0;
		try {
			if (auditStatus.intValue() == ImgAuditStatus.NO_AUDIT.getStatus()) {
				delete(record.getUserId());
				record.setCreateDate(new Date());
				record.setKid(idApi.getKid(IdConstants.QUANHU_USER_IMG_AUDIT).getData());
				result = imgAuditDao.save(record);
			} else {
				result = imgAuditDao.update(record);
			}
			// 拒绝后清空用户头像
			if (aduitActionStatus.intValue() == ImgAuditStatus.FAIL.getStatus()) {
				userService.updateUserInfo(new UserBaseInfo(record.getUserId(), ""));
				// 消息
				
			}
		} catch (Exception e) {
			logger.error("[UserImgAuditDao.save or update]", e);
			throw new MysqlOptException(e);
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor=RuntimeException.class)
	public int batchAuditImg(List<UserImgAudit> record, Integer aduitActionStatus) {
		int auditLength = record == null ? 0 : record.size();
		List<UserImgAudit> saveLists = new ArrayList<>(auditLength);
		List<Long> userIds = new ArrayList<>();

		try {
			for (int i = 0; i < auditLength; i++) {
				UserImgAudit auditModel = record.get(i);
				UserImgAudit imgAuditModel = new UserImgAudit();
				imgAuditModel.setUserId(auditModel.getUserId());
				imgAuditModel.setOperational(auditModel.getOperational());
				imgAuditModel.setUserImg(auditModel.getUserImg());
				imgAuditModel.setAuditStatus(aduitActionStatus.byteValue());
				// 待审核图片表示不存在
				if (auditModel.getAuditStatus().intValue() == ImgAuditStatus.NO_AUDIT.getStatus()) {
					imgAuditModel.setCreateDate(new Date());
					imgAuditModel.setKid(idApi.getKid(IdConstants.QUANHU_USER_IMG_AUDIT).getData());
					saveLists.add(imgAuditModel);
					delete(imgAuditModel.getUserId());
				} else {
					userIds.add(auditModel.getUserId());
				}
			}

			if (CollectionUtils.isNotEmpty(saveLists)) {
				imgAuditDao.batchSave(saveLists);
			}
			if (CollectionUtils.isNotEmpty(userIds)) {
				imgAuditDao.batchUpdate(userIds, aduitActionStatus, null);
			}
			// 拒绝后清空用户头像
			if (aduitActionStatus.intValue() == ImgAuditStatus.FAIL.getStatus()) {
				for (int i = 0; i < auditLength; i++) {
					UserImgAudit auditModel = record.get(i);
					userService.updateUserInfo(new UserBaseInfo(auditModel.getUserId(),""));
				}
				// TODO:消息
			}

		} catch (Exception e) {
			logger.error("[UserImgAuditDao.batchSave or batchUpdate]", e);
			throw new MysqlOptException(e);
		}
		return 0;
	}

	@Override
	public Page<UserImgAudit> listByUserId(Integer pageNo, Integer pageSize, Long userId, Integer auditStatus) {
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

}
