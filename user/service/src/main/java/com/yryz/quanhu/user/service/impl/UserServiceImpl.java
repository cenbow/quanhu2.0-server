/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年11月9日
 * Id: UserServiceImpl.java, 2017年11月9日 下午12:03:33 Administrator
 */
package com.yryz.quanhu.user.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.yryz.common.constant.AppConstants;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.constant.IdConstants;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.utils.BeanUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.dao.UserBaseInfoDao;
import com.yryz.quanhu.user.dao.UserImgAuditDao;
import com.yryz.quanhu.user.dto.AdminUserInfoDTO;
import com.yryz.quanhu.user.entity.UserBaseInfo;
import com.yryz.quanhu.user.entity.UserImgAudit;
import com.yryz.quanhu.user.entity.UserImgAudit.ImgAuditStatus;
import com.yryz.quanhu.user.manager.QrManager;
import com.yryz.quanhu.user.service.UserImgAuditService;
import com.yryz.quanhu.user.service.UserService;
import com.yryz.quanhu.user.utils.ThreadPoolUtil;
import com.yryz.quanhu.user.utils.UserUtils;
import com.yryz.quanhu.user.vo.UserBaseInfoVO;
import com.yryz.quanhu.user.vo.UserLoginSimpleVO;
import com.yryz.quanhu.user.vo.UserSimpleVO;

/**
 * @author suyongcheng
 * @version 1.0
 * @date 2017年11月9日 下午12:03:33
 * @Description TODO 用户信息管理
 */
@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UserBaseInfoDao custbaseinfoDao;
	@Reference(check=false)
	IdAPI idApi;
	@Autowired
	UserImgAuditDao custImgAuditDao;

	@Autowired
	UserImgAuditService userImgAuditService;

	/**
	 * 更新用户信息，
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	@Transactional
	public int updateUserInfo(UserBaseInfo baseInfo) {
		// 检查电话、昵称
		String nickName = baseInfo.getUserNickName();
		UserBaseInfo existByName = null;
		UserBaseInfo user = getUser(baseInfo.getUserId().toString());

		if (!StringUtils.isBlank(nickName)) {
			if (user != null && nickName.equals(user.getUserNickName())) {
				baseInfo.setUserNickName(null);
			} else {
				existByName = this.getUserByNickName(baseInfo.getAppId(), nickName);
			}
			// 昵称已纯在
			if (existByName != null) {
				throw QuanhuException.busiError(ExceptionEnum.BusiException.getCode(), "nickName is already ");
			}
		}

		// 更新头像后清除头像审核数据
		if (StringUtils.isNotBlank(baseInfo.getUserImg())) {
			if (user != null && (user.getUserImg() == null || !user.getUserImg().equals(baseInfo.getUserImg()))) {
				UserImgAudit auditModel = new UserImgAudit();
				auditModel.setAuditStatus((byte) ImgAuditStatus.NO_AUDIT.getStatus());
				auditModel.setUserImg(baseInfo.getUserImg());
				auditModel.setUserId(baseInfo.getUserId());
				userImgAuditService.auditImg(auditModel, ImgAuditStatus.NO_AUDIT.getStatus());
			}
		}

		return custbaseinfoDao.update(baseInfo);

	}

	@Override
	public UserLoginSimpleVO getUserLoginSimpleVO(String userId) {
		UserBaseInfo baseInfo = getUser(userId);
		UserLoginSimpleVO simpleVO = new UserLoginSimpleVO();
		BeanUtils.copyProperties(simpleVO, baseInfo);
		// TODO: 依赖积分系统，获取用户积分等级
		return simpleVO;
	}

	/**
	 * 查询单个用户信息
	 * 
	 * @param userId
	 * @return UserSimpleVO
	 * @Description
	 */
	@Override
	public UserSimpleVO getUserSimple(String userId) {
		// 查询单个用户基础信息
		List<UserBaseInfo> baseInfos = getUserInfo(Sets.newHashSet(userId));
		if (CollectionUtils.isEmpty(baseInfos)) {
			return null;
		}
		UserBaseInfo baseInfo = baseInfos.get(0);
		UserSimpleVO simpleVO = new UserSimpleVO();
		BeanUtils.copyProperties(simpleVO, baseInfo);
		return simpleVO;
	}

	/**
	 * 根据手机号查询简单用户信息
	 * 
	 * @param phone
	 * @return UserSimpleVO
	 * @Description
	 */
	@Override
	public UserSimpleVO getUserSimpleByPhone(String phone, String appId) {
		List<UserBaseInfo> baseInfos = getUserInfoByPhones(Sets.newHashSet(phone), appId);
		if (CollectionUtils.isEmpty(baseInfos)) {
			return null;
		}
		UserBaseInfo baseInfo = baseInfos.get(0);
		UserSimpleVO simpleVO = new UserSimpleVO();
		BeanUtils.copyProperties(simpleVO, baseInfo);
		return simpleVO;
	}

	/**
	 * 根据手机号查询简单用户信息
	 * 
	 * @param Set<String>
	 *            phones
	 * @return Map<String, UserSimpleVO>
	 * @Description
	 */
	@Override
	public Map<String, UserSimpleVO> getUserSimpleByPhone(Set<String> phones, String appId) {
		if (CollectionUtils.isEmpty(phones)) {
			return new HashMap<>();
		}
		List<UserBaseInfo> list = getUserInfoByPhones(phones, appId);
		Map<String, UserSimpleVO> map = new HashMap<String, UserSimpleVO>();
		if (list != null) {
			for (UserBaseInfo vo : list) {
				UserSimpleVO simpleVO = new UserSimpleVO();
				BeanUtils.copyProperties(simpleVO, vo);
				map.put(vo.getUserId().toString(), simpleVO);
			}
		}
		return map;
	}

	/**
	 * 查询用户信息
	 * 
	 * @param Set<String>
	 *            userIds
	 * @return Map<String, UserSimpleVO>
	 * @Description
	 */
	@Override
	public Map<String, UserSimpleVO> getUserSimple(Set<String> userIds) {
		if (CollectionUtils.isEmpty(userIds)) {
			return new HashMap<>();
		}
		List<UserBaseInfo> list = getUserInfo(userIds);
		Map<String, UserSimpleVO> map = new HashMap<String, UserSimpleVO>();
		if (list != null) {
			for (UserBaseInfo vo : list) {
				UserSimpleVO simpleVO = new UserSimpleVO();
				BeanUtils.copyProperties(vo, simpleVO);
				map.put(vo.getUserId().toString(), simpleVO);
			}
		}
		return map;
	}

	/**
	 * 查询用户信息
	 * 
	 * @param userId
	 * @return UserBaseInfo
	 * @Description
	 */
	@Override
	public UserBaseInfo getUser(String userId) {
		return custbaseinfoDao.selectByUserId(userId);
	}

	/**
	 * 查询用户信息
	 * 
	 * @param Set<String>
	 *            userIds
	 * @return Map<String, UserBaseInfo>
	 * @Description
	 */
	@Override
	public Map<String, UserBaseInfoVO> getUser(Set<String> userIds) {
		if (CollectionUtils.isEmpty(userIds)) {
			return new HashMap<>();
		}
		List<UserBaseInfo> list = getUserInfo(userIds);
		Map<String, UserBaseInfoVO> map = new HashMap<>(list.size());
		if (list != null) {
			for (UserBaseInfo vo : list) {
				UserBaseInfoVO infoVO = new UserBaseInfoVO();
				BeanUtils.copyProperties(infoVO, vo);
				map.put(vo.getUserId().toString(), infoVO);
			}
		}
		return map;
	}

	/**
	 * 根据昵称获取用户信息
	 * 
	 * @param appId
	 * @param nickName
	 * @return
	 */
	private UserBaseInfo getUserByNickName(String appId, String nickName) {
		return custbaseinfoDao.checkUserByNname(appId, nickName);
	}

	/**
	 * 获取用户基本信息
	 * 
	 * @param userIds
	 * @return
	 */
	private List<UserBaseInfo> getUserInfo(Set<String> userIds) {
		return custbaseinfoDao.getByUserIds(Lists.newArrayList(userIds));
	}

	/**
	 * 根据手机号获取用户id
	 * 
	 * @param phones
	 * @param appId
	 * @return
	 */
	private List<String> getUserIdByPhone(Set<String> phones, String appId) {
		return custbaseinfoDao.getByPhones(Lists.newArrayList(phones), appId);
	}

	/**
	 * 根据手机号获取用户信息
	 * 
	 * @param phones
	 * @return
	 */
	private List<UserBaseInfo> getUserInfoByPhones(Set<String> phones, String appId) {
		List<String> userIds = getUserIdByPhone(phones, appId);
		List<UserBaseInfo> list = getUserInfo(Sets.newHashSet(userIds));
		return list;
	}

	/**
	 * 根据手机号查询简单用户信息
	 * 
	 * @param Set<String>
	 *            phones
	 * @return Map<String, UserSimpleVO>
	 * @Description
	 */
	@Override
	public UserBaseInfoVO getUserInfoByPhone(String phone, String appId) {
		List<UserBaseInfo> baseInfos = getUserInfoByPhones(Sets.newHashSet(phone), appId);
		if (CollectionUtils.isEmpty(baseInfos)) {
			return null;
		}
		UserBaseInfo baseInfo = baseInfos.get(0);
		UserBaseInfoVO infoVO = new UserBaseInfoVO();
		BeanUtils.copyProperties(infoVO, baseInfo);
		return infoVO;
	}

	/**
	 * 根据手机号查询简单用户信息
	 * 
	 * @param Set<String>
	 *            phones
	 * @return Map<String, UserSimpleVO>
	 * @Description
	 */
	@Override
	public Map<String, UserBaseInfoVO> getUserInfoByPhone(Set<String> phones, String appId) {
		if (CollectionUtils.isEmpty(phones)) {
			return new HashMap<>();
		}
		List<UserBaseInfo> list = getUserInfoByPhones(phones, appId);
		if (CollectionUtils.isEmpty(list)) {
			return new HashMap<>();
		}
		Map<String, UserBaseInfoVO> map = new HashMap<>(list.size());
		if (list != null) {
			for (UserBaseInfo vo : list) {
				UserBaseInfoVO infoVO = new UserBaseInfoVO();
				BeanUtils.copyProperties(infoVO, vo);
				map.put(vo.getUserId().toString(), infoVO);
			}
		}

		return map;
	}

	/**
	 * 根据手机号、昵称、注册时间模糊查询用户id
	 * 
	 * @param custInfoDTO
	 * @return 昵称需要加特殊字符转义
	 */
	@Override
	public List<String> getUserIdByParams(AdminUserInfoDTO custInfoDTO) {
		Map<String, Object> params = new HashMap<String, Object>();
		// params.put("nickName", replayStr(custInfoDTO.getNickName()));
		// params.put("phone", custInfoDTO.getPhone());
		// params.put("startDate", custInfoDTO.getStartDate());
		// params.put("endDate", custInfoDTO.getEndDate());
		if (!StringUtils.isBlank(replayStr(custInfoDTO.getNickName()))) {
			params.put("nickName", replayStr(custInfoDTO.getNickName()));
		} else {
			params.put("nickName", "");
		}
		if (!StringUtils.isBlank(custInfoDTO.getPhone())) {
			params.put("phone", custInfoDTO.getPhone());
		} else {
			params.put("phone", "");
		}
		if (!StringUtils.isBlank(custInfoDTO.getStartDate())) {
			params.put("startDate", custInfoDTO.getStartDate());
		} else {
			params.put("startDate", "");
		}
		if (!StringUtils.isBlank(custInfoDTO.getEndDate())) {
			params.put("endDate", custInfoDTO.getEndDate());
		} else {
			params.put("endDate", "");
		}
		return custbaseinfoDao.getUserIdList(params);
	}

	/**
	 * 后台管理端分页查询用户信息
	 * 
	 * @param int
	 *            pageNo, int pageSize, AdminUserInfoDTO custInfoDTO
	 * @return Page<UserBaseInfo>
	 * @Description 后台管理端分页查询用户信息
	 */
	@Override
	public Page<UserBaseInfo> listUserInfo(int pageNo, int pageSize, AdminUserInfoDTO custInfoDTO) {
		custInfoDTO.setNickName(replayStr(custInfoDTO.getNickName()));

		com.github.pagehelper.Page<UserBaseInfo> page = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nickName", replayStr(custInfoDTO.getNickName()));
		params.put("phone", custInfoDTO.getPhone());
		params.put("startDate", custInfoDTO.getStartDate());
		params.put("endDate", custInfoDTO.getEndDate());
		// 使用pagehelper 查询实现count 分页
		page = PageHelper.startPage(pageNo, pageSize);
		custbaseinfoDao.getAdminList(params);
		return page;
	}

	/**
	 * 创建用户
	 * 
	 * @param baseInfo
	 * @return void
	 * @Description 创建用户
	 */
	@Override
	@Transactional
	public void createUser(UserBaseInfo baseInfo) {
		
		// 获取二维码预存地址
		String qrUrl = QrManager.getQrUrl(baseInfo.getUserId().toString());
		baseInfo.setUserQr(qrUrl);
		if (StringUtils.isNotBlank(baseInfo.getUserPhone())) {
			baseInfo.setUserNickName(parsePhone2Name(baseInfo.getUserPhone(), baseInfo.getUserNickName()));
		}
		baseInfo.setKid(idApi.getKid(IdConstants.QUNAHU_USER_BASEINFO));
		baseInfo.setCreateDate(new Date());
		baseInfo.setBanPostTime(new Date());
		baseInfo.setUserDesc("");
		custbaseinfoDao.insert(baseInfo);
		// 异步上传二维码

		ThreadPoolUtil.insertApiLog(new Runnable() {

			@Override
			public void run() {
				try {
					QrManager.getInstance().createQr(baseInfo.getUserId().toString());
				} catch (RuntimeException e) {
				} catch (Exception e) {
				}
			}
		});

		// 初始化用户头像审核信息
		if (StringUtils.isNotBlank(baseInfo.getUserImg())) {
			UserImgAudit record = new UserImgAudit();
			record.setUserId(baseInfo.getUserId());
			record.setAuditStatus((byte) 0);
			record.setUserImg(baseInfo.getUserImg());
			userImgAuditService.auditImg(record, 0);
		}
	}

	/**
	 * 获取userId设备信息
	 * 
	 * @param iuserId
	 * @return String
	 * @Description 获取设备信息
	 */
	@Override
	public String getDeviceIdByUserId(String userId) {
		List<String> deviceIds = getDeviceIdByUserId(Lists.newArrayList(userId));
		if (CollectionUtils.isEmpty(deviceIds)) {
			return null;
		}
		return deviceIds.get(0);
	}

	/**
	 * 获取userId设备信息
	 * 
	 * @param iuserId
	 * @return String
	 * @Description 获取设备信息
	 */
	@Override
	public List<String> getDeviceIdByUserId(List<String> userIds) {
		return custbaseinfoDao.getDevIdByUserIds(userIds);
	}

	private String replayStr(String str) {
		if (StringUtils.isEmpty(str)) {
			return str;
		}
		return str.replaceAll("\\\\", "\\\\\\\\").replaceAll("_", "\\\\_").replaceAll("%", "\\\\%");
	}

	/**
	 * 根据手机号生成用户昵称
	 * 
	 * @param phone
	 * @return
	 */
	private static String parsePhone2Name(String phone, String nickName) {
		if (StringUtils.isBlank(nickName)) {
			return AppConstants.NICK_NAME_PREFIX + UserUtils.randomappId()
					+ phone.substring(phone.length() - 4, phone.length());
		}
		return nickName;
	}

}
