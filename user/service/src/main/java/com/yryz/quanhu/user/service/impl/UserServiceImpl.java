/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年11月9日
 * Id: UserServiceImpl.java, 2017年11月9日 下午12:03:33 Administrator
 */
package com.yryz.quanhu.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.math.NumberUtils;
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
import com.yryz.common.constant.IdConstants;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.JsonUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.score.vo.EventAcount;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.contants.UserRelationConstant.STATUS;
import com.yryz.quanhu.user.dao.UserBaseInfoDao;
import com.yryz.quanhu.user.dao.UserBaseInfoRedisDao;
import com.yryz.quanhu.user.dao.UserImgAuditDao;
import com.yryz.quanhu.user.dto.AdminUserInfoDTO;
import com.yryz.quanhu.user.dto.UserRelationDto;
import com.yryz.quanhu.user.entity.UserBaseInfo;
import com.yryz.quanhu.user.entity.UserImgAudit;
import com.yryz.quanhu.user.entity.UserImgAudit.ImgAuditStatus;
import com.yryz.quanhu.user.entity.UserStarAuth;
import com.yryz.quanhu.user.entity.UserBaseInfo.UserRole;
import com.yryz.quanhu.user.manager.EventManager;
import com.yryz.quanhu.user.mq.UserSender;
import com.yryz.quanhu.user.service.ActivityTempUserService;
import com.yryz.quanhu.user.service.UserImgAuditService;
import com.yryz.quanhu.user.service.UserRelationService;
import com.yryz.quanhu.user.service.UserService;
import com.yryz.quanhu.user.service.UserStarService;
import com.yryz.quanhu.user.utils.PhoneUtils;
import com.yryz.quanhu.user.utils.UserUtils;
import com.yryz.quanhu.user.vo.UserBaseInfoVO;
import com.yryz.quanhu.user.vo.UserLoginSimpleVO;
import com.yryz.quanhu.user.vo.UserSimpleVO;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年11月9日 下午12:03:33
 * @Description TODO 用户信息管理
 */
@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UserBaseInfoDao custbaseinfoDao;
	@Autowired
	private UserBaseInfoRedisDao baseInfoRedisDao;
	@Reference(check = false)
	IdAPI idApi;
	@Autowired
	UserImgAuditDao custImgAuditDao;
	@Autowired
	UserImgAuditService userImgAuditService;
	@Autowired
	private UserRelationService relationService;
	@Autowired
	private ActivityTempUserService tempUserService;
	@Autowired
	private UserStarService starService;
	@Autowired
	private UserSender mqSender;
	@Autowired
	private EventManager eventManager;

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
		UserBaseInfo user = getUser(baseInfo.getUserId());

		if (!StringUtils.isBlank(nickName)) {
			if (user != null && nickName.equals(user.getUserNickName())) {
				baseInfo.setUserNickName(null);
			} else {
				existByName = this.getUserByNickName(baseInfo.getAppId(), nickName);
			}
			// 昵称已存在
			if (existByName != null) {
				throw QuanhuException.busiError("nickName is already ");
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

		int result = custbaseinfoDao.update(baseInfo);

		//删除缓存用户信息
		baseInfoRedisDao.deleteUserInfo(baseInfo.getUserId());
		
		// 删除旧的用户手机号信息
		if (StringUtils.isNotBlank(baseInfo.getUserPhone())) {
			baseInfoRedisDao.deleteUserPhoneInfo(user.getUserPhone(), user.getAppId());
			logger.info("[update_user_baseinfo]:oldUser:{},newUser:{}", JsonUtils.toFastJson(user),
					JsonUtils.toFastJson(baseInfo));
		}

		// 同步im
		mqSender.userUpdate(baseInfo);
		
		//更新后的用户信息
		UserBaseInfo baseInfo2 = getUser(baseInfo.getUserId());
		// 提交资料完善事件
		eventManager.userDataImprove(baseInfo2, user);
		
		return result;
	}
	
	
	@Override
	public UserLoginSimpleVO getUserLoginSimpleVO(Long userId){
		return getUserLoginSimpleVO(null, userId);
	}
	
	@Override
	public UserLoginSimpleVO getUserLoginSimpleVO(Long userId,Long friendId) {
		UserBaseInfo baseInfo = getUser(friendId);
		if(baseInfo == null){
			return new UserLoginSimpleVO();
		}
		String starTradeField = "";
		
		UserLoginSimpleVO simpleVO = UserBaseInfo.getUserLoginSimpleVO(baseInfo);
		
		//聚合达人行业数据
		if(simpleVO.getUserRole() == UserRole.STAR.getRole()){
			UserStarAuth starAuth = starService.get(friendId.toString(), null);
			starTradeField = starAuth != null ? starAuth.getTradeField() : "";
		}
		simpleVO.setStarTradeField(starTradeField);
		simpleVO.setRelationStatus(STATUS.OWNER.getCode());
		// 聚合关系数据
		if (userId != null && userId != 0L) {
			Map<String, UserRelationDto> map = getRelation(userId, Sets.newHashSet(friendId.toString()));
			UserRelationDto relationDto = map.get(friendId.toString());
			simpleVO.setUserPhone(PhoneUtils.getPhone(simpleVO.getUserPhone()));
			simpleVO.setNameNotes(relationDto.getUserRemarkName());
			simpleVO.setRelationStatus(relationDto.getRelationStatus());
		}
		// 依赖积分系统，获取用户等级
		EventAcount acount = eventManager.getGrow(friendId.toString());
		if (acount == null || NumberUtils.toLong(acount.getGrowLevel()) < 1) {
			simpleVO.setUserLevel("1");
		} else {
			simpleVO.setUserLevel(acount.getGrowLevel());
		}
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
	public UserSimpleVO getUserSimple(Long userId) {
		return getUserSimple(null, userId);
	}

	@Override
	public UserSimpleVO getUserSimple(Long userId, Long friendId) {
		// 查询单个用户基础信息
		List<UserBaseInfo> baseInfos = getUserInfo(Sets.newHashSet(friendId.toString()));
		if (CollectionUtils.isEmpty(baseInfos)) {
			return new UserSimpleVO();
		}
		UserBaseInfo baseInfo = baseInfos.get(0);
		UserSimpleVO simpleVO = UserBaseInfo.getUserSimpleVo(baseInfo);
		// 聚合关系数据
		if (userId != null && userId != 0L) {
			Map<String, UserRelationDto> map = getRelation(userId, Sets.newHashSet(friendId.toString()));
			UserRelationDto relationDto = map.get(friendId.toString());
			simpleVO.setNameNotes(relationDto.getUserRemarkName());
			simpleVO.setRelationStatus(relationDto.getRelationStatus());
		}
		return simpleVO;
	}

	@Override
	public Map<String, UserSimpleVO> getUserSimple(Long userId, Set<String> friendIds) {
		if (CollectionUtils.isEmpty(friendIds)) {
			return new HashMap<>(0);
		}
		List<UserBaseInfo> list = getUserInfo(friendIds);
		Map<String, UserSimpleVO> map = new HashMap<String, UserSimpleVO>();
		Map<String, UserRelationDto> relationMap = null;
		// 聚合关系数据
		if (userId != null && userId != 0L) {
			relationMap = getRelation(userId, friendIds);
		}
		if (list != null) {
			for (UserBaseInfo vo : list) {
				UserSimpleVO simpleVO = UserBaseInfo.getUserSimpleVo(vo);
				if (MapUtils.isNotEmpty(relationMap)) {
					UserRelationDto dto = relationMap.get(vo.getUserId().toString());
					simpleVO.setNameNotes(dto.getUserRemarkName());
					simpleVO.setRelationStatus(dto.getRelationStatus());
				}
				map.put(vo.getUserId().toString(), simpleVO);
			}
		}
		return map;
	}

	@Override
	public Map<String, UserSimpleVO> getUserSimple(Set<String> userIds) {
		return getUserSimple(null, userIds);
	}

	/**
	 * 根据手机号查询用户id
	 * 
	 * @param phone
	 * @return UserSimpleVO
	 * @Description
	 */
	@Override
	public String getUserByPhone(String phone, String appId) {
		List<UserBaseInfo> baseInfos = getUserIdByPhone(Sets.newHashSet(phone), appId);
		if (CollectionUtils.isEmpty(baseInfos)) {
			return null;
		}
		return baseInfos.get(0).getUserId().toString();
	}

	/**
	 * 根据手机号查询用户id
	 * 
	 * @param Set<String>
	 *            phones
	 * @return Map<String, String>
	 * @Description
	 */
	@Override
	public Map<String, String> getUserByPhone(Set<String> phones, String appId) {
		if (CollectionUtils.isEmpty(phones)) {
			return new HashMap<>();
		}
		List<UserBaseInfo> list = getUserIdByPhone(phones, appId);
		Map<String, String> map = new HashMap<>();
		if (list != null) {
			for (UserBaseInfo info : list) {
				map.put(info.getUserPhone(), info.getUserId().toString());
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
	public UserBaseInfo getUser(Long userId) {
		UserBaseInfo info = baseInfoRedisDao.getUserInfo(userId);
		if (info != null) {
			return info;
		}
		info = custbaseinfoDao.selectByUserId(userId.toString());
		if (info != null) {
			baseInfoRedisDao.saveUserInfo(info);
		}
		return info;
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
				UserBaseInfoVO infoVO = UserBaseInfo.getUserBaseInfoVO(vo);
				map.put(vo.getUserId().toString(), infoVO);
			}
		}
		return map;
	}
	
	@Override
	public Map<String,UserBaseInfoVO> getActivityUser(Set<String> userIds){
		if (CollectionUtils.isEmpty(userIds)) {
			return new HashMap<>();
		}
		List<UserBaseInfo> list = getUserInfo(userIds);
		List<String> tempUserIds = new ArrayList<>();
		// 收集观察者id
		for (Iterator<String> iterator = userIds.iterator(); iterator.hasNext();) {
			Long userId = NumberUtils.createLong(iterator.next());
			boolean tempUserFlag = true;
			for (UserBaseInfo baseInfo : list) {
				if (userId == baseInfo.getUserId()) {
					tempUserFlag = false;
				}
			}
			if (tempUserFlag) {
				tempUserIds.add(userId.toString());
			}
		}
		if(CollectionUtils.isEmpty(list)){
			list = new ArrayList<>(userIds.size());
		}
		//查询观察者
		List<UserBaseInfo> tempUsers = tempUserService.getUserBaseInfoByTempUser(tempUserIds);
		if(CollectionUtils.isNotEmpty(tempUsers)){
			list.addAll(tempUsers);
		}
		Map<String, UserBaseInfoVO> map = new HashMap<>(list.size());
		if (list != null) {
			for (UserBaseInfo vo : list) {
				UserBaseInfoVO infoVO = UserBaseInfo.getUserBaseInfoVO(vo);
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
	public  UserBaseInfo getUserByNickName(String appId, String nickName) {
		return custbaseinfoDao.checkUserByNname(appId, nickName);
	}

	/**
	 * 获取用户基本信息
	 * 
	 * @param userIds
	 * @return
	 */
	private List<UserBaseInfo> getUserInfo(Set<String> userIds) {
		List<UserBaseInfo> infos = baseInfoRedisDao.getUserInfo(userIds);
		Set<String> nullUserId = new HashSet<>();
		// 收集缓存不存在的用户id
		for (Iterator<String> iterator = userIds.iterator(); iterator.hasNext();) {
			Long userId = NumberUtils.createLong(iterator.next());
			boolean nullFlag = true;
			for (UserBaseInfo baseInfo : infos) {
				if (userId == baseInfo.getUserId()) {
					nullFlag = false;
				}
			}
			if (nullFlag) {
				nullUserId.add(userId.toString());
			}
		}
		if(CollectionUtils.isEmpty(infos)){
			infos = new ArrayList<>(userIds.size());
		}
		if (CollectionUtils.isEmpty(nullUserId)) {
			return infos;
		}
		// 查询mysql
		List<UserBaseInfo> mysqlInfos = custbaseinfoDao.getByUserIds(Lists.newArrayList(nullUserId));
		if (CollectionUtils.isNotEmpty(mysqlInfos)) {
			baseInfoRedisDao.saveUserInfo(mysqlInfos);
		}
		// 合并缓存的用户
		if (CollectionUtils.isNotEmpty(mysqlInfos)) {
			infos.addAll(mysqlInfos);
		}
		return mysqlInfos;
	}

	/**
	 * 根据手机号获取用户id
	 * 
	 * @param phones
	 * @param appId
	 * @return
	 */
	private List<UserBaseInfo> getUserIdByPhone(Set<String> phones, String appId) {
		List<UserBaseInfo> infos = baseInfoRedisDao.getUserPhoneInfo(phones, appId);
		Set<String> nullPhone = new HashSet<>();
		// 收集缓存不存在的用户id
		for (Iterator<String> iterator = phones.iterator(); iterator.hasNext();) {
			String phone = iterator.next();
			boolean nullFlag = true;
			for (UserBaseInfo baseInfo : infos) {
				if (StringUtils.equals(phone, baseInfo.getUserPhone())) {
					nullFlag = false;
				}
			}
			if (nullFlag) {
				nullPhone.add(phone);
			}
		}
		if(CollectionUtils.isEmpty(infos)){
			infos = new ArrayList<>(phones.size());
		}
		if (CollectionUtils.isEmpty(nullPhone)) {
			return infos;
		}
		// 查询mysql
		List<UserBaseInfo> mysqlInfos = custbaseinfoDao.getByPhones(Lists.newArrayList(phones), appId);
		//保存缓存
		if(CollectionUtils.isNotEmpty(mysqlInfos)){
			baseInfoRedisDao.saveUserPhoneInfo(mysqlInfos);
		}
		// 合并缓存的用户
		if (CollectionUtils.isNotEmpty(mysqlInfos)) {
			infos.addAll(mysqlInfos);
		}
		return mysqlInfos;
	}

	/**
	 * 根据手机号、昵称、注册时间模糊查询用户id
	 * 
	 * @param custInfoDTO
	 * @return 昵称需要加特殊字符转义
	 */
	@Override
	public List<String> getUserIdByParams(AdminUserInfoDTO custInfoDTO) {
		custInfoDTO.setNickName(replayStr(custInfoDTO.getNickName()));
		return custbaseinfoDao.getUserIdList(custInfoDTO);
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
		Page<UserBaseInfo> page = PageHelper.startPage(pageNo, pageSize);
		custbaseinfoDao.getAdminList(custInfoDTO);
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
		if (StringUtils.isNotBlank(baseInfo.getUserPhone()) && StringUtils.isBlank(baseInfo.getUserNickName())) {
			baseInfo.setUserNickName(parsePhone2Name(baseInfo.getUserPhone(), baseInfo.getUserNickName()));
		}
		baseInfo.setKid(ResponseUtils.getResponseData(idApi.getKid(IdConstants.QUNAHU_USER_BASEINFO)));
		baseInfo.setCreateDate(new Date());
		baseInfo.setBanPostTime(new Date());
		baseInfo.setUserAge((byte) 18);
		baseInfo.setUserBirthday("");
		baseInfo.setUserDesc("");
		custbaseinfoDao.insert(baseInfo);

		// 初始化用户头像审核信息
		if (StringUtils.isNotBlank(baseInfo.getUserImg())) {
			UserImgAudit record = new UserImgAudit();
			record.setUserId(baseInfo.getUserId());
			record.setAuditStatus((byte) ImgAuditStatus.NO_AUDIT.getStatus());
			record.setUserImg(baseInfo.getUserImg());
			userImgAuditService.auditImg(record, ImgAuditStatus.NO_AUDIT.getStatus());
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
	public String getDeviceIdByUserId(Long userId) {
		List<String> deviceIds = getDeviceIdByUserId(Lists.newArrayList(userId.toString()));
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

	@Override
	public List<Long> getUserIdByCreateDate(String startDate, String endDate) {
		return custbaseinfoDao.getUserIdByCreateDate(startDate, endDate);
	}

	@Override
	public List<UserBaseInfo> getAllByUserIds(List<Long> userIds) {
		return custbaseinfoDao.getAllByUserIds(userIds);
	}

	/**
	 * 获取用户关系<br/>
	 * 找不到直接是陌生人
	 * 
	 * @param userId
	 * @param friendIds
	 * @return
	 */
	private Map<String, UserRelationDto> getRelation(Long userId, Set<String> friendIds) {
		List<UserRelationDto> dtos = relationService.selectBy(userId.toString(), friendIds);
		Map<String, UserRelationDto> map = new HashMap<>();
		for (Iterator<String> iterator = friendIds.iterator(); iterator.hasNext();) {
			String friendId = iterator.next();
			UserRelationDto dto = null;
			boolean noRelation = true;
			if (CollectionUtils.isNotEmpty(dtos)) {
				for (int i = 0; i < dtos.size(); i++) {
					dto = dtos.get(i);
					if (StringUtils.equals(dto.getUserId(), friendId)) {
						map.put(friendId, dto);
						noRelation = false;
					}
				}
			}
			if (noRelation) {
				dto = new UserRelationDto();
				dto.setRelationStatus(STATUS.NONE.getCode());
				dto.setUserRemarkName("");
				map.put(friendId, dto);
			}
		}
		return map;
	}
}
