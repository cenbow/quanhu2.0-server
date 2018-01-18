/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年9月11日
 * Id: EventManager.java, 2017年9月11日 上午9:36:28 Administrator
 */
package com.yryz.quanhu.user.manager;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yryz.quanhu.user.entity.UserBaseInfo;
import com.yryz.quanhu.user.service.UserOperateService;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年9月11日 上午9:36:28
 * @Description 事件提交
 */
@Component
public class EventManager {
	private static final Logger logger = LoggerFactory.getLogger("event.logger");
	private static final Logger LOGGER2 = LoggerFactory.getLogger(EventManager.class);
	private static final String STAR_AUTH_LEVEL = "5";
	@Autowired
	private UserOperateService regService;
/*	@Autowired
	private EventAPI evenApi;
	@Autowired
	private GrowAPI growApi;
	@Autowired
	private EventAcountAPI acountAPI;*/

	/**
	 *  注册事件<br/>
	 *  注册触发事件收集初始化用户积分账户初始化 影响用户积分<br/>
	 *  有邀请码触发邀请注册事件
	 * @param custId
	 * @param phone
	 * @param inviter 邀请码
	 */
	public void register(String custId,String phone,String inviter) {
		if (StringUtils.isEmpty(custId)) {
			return;
		}
		try {
			/*acountAPI.initAcount(custId);
			if(StringUtils.isNotBlank(phone)){
				EventInfo eventInfo = new EventInfo();
				eventInfo.setEventCode(EventEnum.REGISTER.getCode());
				eventInfo.setCustId(custId);
				eventInfo.setEventNum(1);
				evenApi.commit(eventInfo);
			}
			//邀请注册事件
			if(StringUtils.isNotBlank(inviter)){
				String custRegId = regService.selectCustIdByInviter(inviter);
				if(StringUtils.isNotBlank(custRegId)){
					inviterRegister(custId);
				}
			}*/
			logger.info("event commit msg register custId:{}", custId);
		} catch (RuntimeException e) {
			LOGGER2.error("[event register]", e);
			logger.info("event commit msg register error custId:{}", custId);
		} catch (Exception e) {
			LOGGER2.error("[event register]", e);
			logger.info("event commit msg register error custId:{}", custId);
		}

	}
	
	/**
	 * 邀请注册事件
	 * @param custId
	 */
	public void inviterRegister(String custId){
		if (StringUtils.isEmpty(custId)) {
			return;
		}
		try {
			/*EventInfo eventInfo = new EventInfo();
			eventInfo.setEventCode(EventEnum.INVITER_REGISTER.getCode());
			eventInfo.setCustId(custId);
			eventInfo.setEventNum(1);
			evenApi.commit(eventInfo);*/
			logger.info("event commit msg inviter_register custId:{}", custId);
		} catch (RuntimeException e) {
			LOGGER2.error("[event inviter_register]", e);
			logger.info("event commit msg inviter_register error custId:{}", custId);
		} catch (Exception e) {
			LOGGER2.error("[event inviter_register]", e);
			logger.info("event commit msg inviter_register error custId:{}", custId);
		}
	}
	
	/**
	 * 完善资料
	 * 
	 * @author danshiyu
	 * @date 2017年9月11日
	 * @param custInfo
	 *            更新后的用户信息
	 * @param custInfo2
	 *            旧的用户信息
	 * @Description 用户完善资料后触发事件收集 影响用户积分
	 */
	public void userDataImprove(UserBaseInfo custInfo, UserBaseInfo custInfo2) {
		if (custInfo == null || custInfo.getUserId() == null) {
			return;
		}
		// 表示已经完善过的老用户
		if (custInfo2 != null && StringUtils.isNotBlank(custInfo2.getUserDesc())
				&& StringUtils.isNotBlank(custInfo2.getUserImg()) && StringUtils.isNotBlank(custInfo2.getUserLocation())
				&& StringUtils.isNotBlank(custInfo2.getUserNickName()) && custInfo2.getUserGenders()== null) {
			return;
		}
		// 资料刚刚完善
		if (StringUtils.isNotBlank(custInfo.getUserDesc()) && StringUtils.isNotBlank(custInfo.getUserImg())
				&& StringUtils.isNotBlank(custInfo.getUserLocation()) && StringUtils.isNotBlank(custInfo.getUserNickName())
				&& custInfo.getUserGenders() == null) {

		} else {
			return;
		}

/*		EventInfo info = new EventInfo();
		info.setCustId(custInfo.getCustId());
		info.setEventCode(EventEnum.USER_DATA_IMPROVE.getCode());
		info.setCreateTime(DateUtil.nowToDetailString());
		info.setEventNum(1);*/
/*		try {
			//evenApi.commit(info);
			logger.info("event commit msg userDataImproveInfo:{}", JsonUtils.toFastJson(info));
		} catch (RuntimeException e) {
			LOGGER2.error("[event userDataImprove]", e);
			logger.info("event commit msg userDataImprove error custId:{}", custInfo.getCustId());
		} catch (Exception e) {
			LOGGER2.error("[event userDataImprove]", e);
			logger.info("event commit msg userDataImprove error custId:{}", custInfo.getCustId());
		}*/
	}

	/**
	 * 达人认证成功设置等级
	 * 
	 * @author danshiyu
	 * @date 2017年9月20日
	 * @param custId
	 * @Description 设置达人成功后等级不足5级的升到5级，其他不管，取消认证不回退
	 */
	public void starAuth(String custId) {
		if (StringUtils.isEmpty(custId)) {
			return;
		}
		try {
			//growApi.promoteGrowLevel(custId, STAR_AUTH_LEVEL,EventEnum.STAR_AUTH_SUCCESS.getCode());
			logger.info("event commit msg starAuth success custId:{}", custId);
		} catch (RuntimeException e) {
			LOGGER2.error("[event starAuth]", e);
			logger.info("event commit msg starAuth error custId:{}", custId);
		} catch (Exception e) {
			LOGGER2.error("[event starAuth]", e);
			logger.info("event commit msg starAuth error custId:{}", custId);
		}
	}

	/**
	 * 获取用户等级和积分
	 * 
	 * @param custId
	 * @return
	 *//*
	public EventAcount getGrow(String custId) {
		if (StringUtils.isEmpty(custId)) {
			return null;
		}
		try {
			EventAcount acount = acountAPI.getEventAcount(custId);
			return acount;
		} catch (RuntimeException e) {
			LOGGER2.error("[event acount]", e);
			return null;
		} catch (Exception e) {
			LOGGER2.error("[event acount]", e);
			return null;
		}
	}*/

	/**
	 * 获取用户等级
	 * 
	 * @date 2017年9月25日
	 * @param custIds
	 * @return
	 */
	/*public Map<String, UserAcountSimpleVo> getCustLevel(Set<String> custIds) {
		if (CollectionUtils.isEmpty(custIds)) {
			return null;
		}
		Map<String, UserAcountSimpleVo> map = new HashMap<>(custIds.size());
		for (Iterator<String> iterator = custIds.iterator(); iterator.hasNext();) {
			String custId = iterator.next();
			EventAcount acount = getGrow(custId);
			if (acount != null) {
				map.put(custId, new UserAcountSimpleVo(custId, acount.getScore(),
						"0".equals(acount.getGrowLevel()) ? "1" : acount.getGrowLevel()));
			} else {
				map.put(custId, new UserAcountSimpleVo(custId, 0L, "1"));
			}
		}
		return map;
	}*/
}
