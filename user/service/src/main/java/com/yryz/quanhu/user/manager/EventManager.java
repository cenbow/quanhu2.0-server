/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年9月11日
 * Id: EventManager.java, 2017年9月11日 上午9:36:28 Administrator
 */
package com.yryz.quanhu.user.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.constant.AppConstants;
import com.yryz.common.context.Context;
import com.yryz.common.utils.DateUtils;
import com.yryz.common.utils.JsonUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.grow.service.GrowAPI;
import com.yryz.quanhu.score.enums.EventEnum;
import com.yryz.quanhu.score.service.EventAPI;
import com.yryz.quanhu.score.service.EventAcountAPI;
import com.yryz.quanhu.score.vo.EventAcount;
import com.yryz.quanhu.score.vo.EventInfo;
import com.yryz.quanhu.user.contants.RegType;
import com.yryz.quanhu.user.entity.UserBaseInfo;
import com.yryz.quanhu.user.service.UserOperateService;
import com.yryz.quanhu.user.utils.ThreadPoolUtil;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年9月11日 上午9:36:28
 * @Description 事件提交
 */
@Component
public class EventManager {
	private static final Logger logger = LoggerFactory.getLogger(EventManager.class);
	private static final String STAR_AUTH_LEVEL = "5";
	@Autowired
	private UserOperateService regService;
	@Reference
	private EventAPI evenApi;
	@Reference
	private GrowAPI growApi;
	@Reference
	private EventAcountAPI acountAPI;

	/**
	 * 注册事件<br/>
	 * 注册触发事件收集初始化用户积分账户初始化 影响用户积分<br/>
	 * 有邀请码触发邀请注册事件
	 * 
	 * @param userId
	 * @param phone
	 * @param inviter
	 *            邀请码
	 */
	public void register(String userId, String phone, String regType, String inviter) {
		if (StringUtils.isEmpty(userId)) {
			return;
		}
		EventInfo eventInfo = null;
		try {
			acountAPI.initAcount(userId);
			// 手机号注册的用户提交会员注册事件
			if (StringUtils.isNotBlank(phone) && StringUtils.equals(regType, RegType.PHONE.getText())) {
				eventInfo = new EventInfo();
				eventInfo.setEventCode(EventEnum.REGISTER.getCode());
				eventInfo.setUserId(userId);
				eventInfo.setEventNum(1);
				commit(eventInfo);
			}
			// 邀请注册事件
			if (StringUtils.isNotBlank(inviter)) {
				String userRegId = regService.selectUserIdByInviter(inviter);
				if (StringUtils.isNotBlank(userRegId)) {
					inviterRegister(userRegId);
				}
			}
			logger.info("[event_regiter]:params:{},result:{}", JsonUtils.toFastJson(eventInfo), "");
		} catch (Exception e) {
			logger.info("[event_regiter]:params:{},result:{}", JsonUtils.toFastJson(eventInfo), e.getMessage());
			logger.error("[event_regiter]", e);
		}
	}

	/**
	 * 邀请注册事件
	 * 
	 * @param userId
	 */
	public void inviterRegister(String userId) {
		if (StringUtils.isEmpty(userId)) {
			return;
		}
		EventInfo eventInfo = null;
		try {
			eventInfo = new EventInfo();
			eventInfo.setEventCode(EventEnum.INVITE_FRIENDS_TO_REGISTER.getCode());
			eventInfo.setUserId(userId);
			eventInfo.setEventNum(1);
			commit(eventInfo);
			logger.info("[event_inviter_register]:params:{},result:{}", JsonUtils.toFastJson(eventInfo), "");
		} catch (Exception e) {
			logger.info("[event_inviter_register]:params:{},result:{}", JsonUtils.toFastJson(eventInfo),
					e.getMessage());
			logger.error("[event_inviter_register]", e);
		}
	}

	/**
	 * 完善资料
	 * 
	 * @author danshiyu
	 * @date 2017年9月11日
	 * @param userInfo
	 *            更新后的用户信息
	 * @param userInfo2
	 *            旧的用户信息
	 * @Description 用户完善资料后触发事件收集 影响用户积分
	 */
	public void userDataImprove(UserBaseInfo newUserInfo, UserBaseInfo oldUserInfo) {
		if (newUserInfo == null || newUserInfo.getUserId() == null) {
			return;
		}
		// 表示已经完善过的老用户
		if (oldUserInfo != null && StringUtils.isNotBlank(oldUserInfo.getUserDesc())
				&& StringUtils.isNotBlank(oldUserInfo.getUserImg())
				&& StringUtils.isNotBlank(oldUserInfo.getUserLocation())
				&& StringUtils.isNotBlank(oldUserInfo.getUserNickName()) && oldUserInfo.getUserGenders() != null) {
			return;
		}
		// 资料刚刚完善
		if (StringUtils.isNotBlank(newUserInfo.getUserDesc()) && StringUtils.isNotBlank(newUserInfo.getUserImg())
				&& StringUtils.isNotBlank(newUserInfo.getUserLocation())
				&& StringUtils.isNotBlank(newUserInfo.getUserNickName()) && newUserInfo.getUserGenders() != null) {

		} else {
			return;
		}

		EventInfo info = new EventInfo();
		info.setUserId(newUserInfo.getUserId().toString());
		info.setEventCode(EventEnum.USER_DATA_IMPROVE.getCode());
		info.setCreateTime(DateUtils.getDateTime());
		info.setEventNum(1);
		try {
			commit(info,newUserInfo.getAppId());
			logger.info("[event userDataImprove]:params:{},result:{}", JsonUtils.toFastJson(info), "");
		} catch (Exception e) {
			logger.error("[event userDataImprove]", e);
			logger.info("[event userDataImprove]:params:{},result:{}", JsonUtils.toFastJson(info), e.getMessage());
		}
	}

	/**
	 * 达人认证成功设置等级
	 * 
	 * @author danshiyu
	 * @date 2017年9月20日
	 * @param userId
	 * @Description 设置达人成功后等级不足5级的升到5级，其他不管，取消认证不回退
	 */
	public void starAuth(String userId) {
		if (StringUtils.isEmpty(userId)) {
			return;
		}
		try {
			growApi.promoteGrowLevel(userId, STAR_AUTH_LEVEL, EventEnum.STAR_AUTH_SUCCESS.getCode());
			logger.info("[event starAuth]:params:{},result:{}", userId, "");
		} catch (Exception e) {
			logger.error("[event starAuth]", e);
			logger.info("[event starAuth]:params:{},result:{}", userId, e.getMessage());
		}
	}

	/**
	 * 获取用户等级和积分
	 * 
	 * @param userId
	 * @return
	 */
	public EventAcount getGrow(String userId) {
		if (StringUtils.isEmpty(userId)) {
			return null;
		}
		try {
			EventAcount acount = acountAPI.getEventAcount(userId);
			return acount;
		} catch (RuntimeException e) {
			logger.error("[event_getLevel]", e);
			return null;
		} catch (Exception e) {
			logger.error("[event_getLevel]", e);
			return null;
		}
	}

	/**
	 * 圈乎用户提交事件
	 * @param info
	 * @param appId
	 */
	public void commit(EventInfo info) {
		commit(info, Context.getProperty(AppConstants.APP_ID));
	}
	
	/**
	 * 提交事件，圈乎用户才提交
	 * @param info
	 * @param appId
	 */
	public void commit(EventInfo info, String appId) {
		if (info == null || StringUtils.isBlank(appId)
				|| !StringUtils.equals(appId, Context.getProperty(AppConstants.APP_ID))) {
			return;
		}
		ThreadPoolUtil.execue(new Runnable() {
			@Override
			public void run() {
				try {
					evenApi.commit(info);
					logger.info("[event commit]:params:{},result:success", JsonUtils.toFastJson(info));
				} catch (Exception e) {
					logger.error("[event commit]", e);
					logger.info("[event commit]:params:{},result:{}", JsonUtils.toFastJson(info), e.getMessage());
				}
			}
		});

	}

	/**
	 * 获取用户等级
	 * 
	 * @date 2017年9月25日
	 * @param userIds
	 * @return
	 */
	/*
	 * public Map<String, UserAcountSimpleVo> getCustLevel(Set<String> userIds)
	 * { if (CollectionUtils.isEmpty(userIds)) { return null; } Map<String,
	 * UserAcountSimpleVo> map = new HashMap<>(userIds.size()); for
	 * (Iterator<String> iterator = userIds.iterator(); iterator.hasNext();) {
	 * String userId = iterator.next(); EventAcount acount = getGrow(userId); if
	 * (acount != null) { map.put(userId, new UserAcountSimpleVo(userId,
	 * acount.getScore(), "0".equals(acount.getGrowLevel()) ? "1" :
	 * acount.getGrowLevel())); } else { map.put(userId, new
	 * UserAcountSimpleVo(userId, 0L, "1")); } } return map; }
	 */
}
