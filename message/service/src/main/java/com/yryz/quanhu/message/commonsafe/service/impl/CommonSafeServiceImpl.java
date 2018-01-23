/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年12月4日
 * Id: CommonSafeServiceImpl.java, 2017年12月4日 下午4:59:36 Administrator
 */
package com.yryz.quanhu.message.commonsafe.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.yryz.common.aliyun.jaq.AfsCheckManager;
import com.yryz.common.context.Context;
import com.yryz.common.entity.AfsCheckRequest;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.message.push.enums.CheckVerifyCodeReturnCode;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.config.VerifyCodeConfigVO;
import com.yryz.common.constant.IdConstants;
import com.yryz.common.exception.MysqlOptException;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.message.commonsafe.constants.CommonServiceType;
import com.yryz.quanhu.message.commonsafe.constants.RedisConstants;
import com.yryz.quanhu.message.commonsafe.dao.CommonSafeRedisDao;
import com.yryz.quanhu.message.commonsafe.dao.VerifyCodeDao;
import com.yryz.quanhu.message.commonsafe.dto.IpLimitDTO;
import com.yryz.quanhu.message.commonsafe.dto.VerifyCodeDTO;
import com.yryz.quanhu.message.commonsafe.entity.VerifyCode;
import com.yryz.quanhu.message.commonsafe.service.CommonSafeService;
import com.yryz.quanhu.message.commonsafe.utils.CommonUtils;
import com.yryz.quanhu.message.commonsafe.utils.DateUtils;
import com.yryz.quanhu.message.commonsafe.vo.VerifyCodeVO;
import com.yryz.quanhu.message.commonsafe.vo.VerifyCodeVO.VerifyStatus;
import com.yryz.quanhu.message.sms.dto.SmsDTO;
import com.yryz.quanhu.message.sms.dto.SmsDTO.SmsType;
import com.yryz.quanhu.message.sms.service.SmsService;
import com.yryz.quanhu.support.id.api.IdAPI;

/**
 * 公共安全业务管理
 * 
 * @author danshiyu
 * @version 1.0
 * @date 2017年12月4日 下午4:59:36
 */
@Service
public class CommonSafeServiceImpl implements CommonSafeService {
	private static final Logger Logger = LoggerFactory.getLogger(CommonSafeServiceImpl.class);

	@Autowired
	private CommonSafeRedisDao redisDao;
	@Autowired
	private SmsService smsService;
	@Reference
	private IdAPI idApi;
	@Autowired
	private VerifyCodeConfigVO configVO;

	@Autowired
	private VerifyCodeDao persistenceDao;

	@Override
	@Transactional
	public VerifyCodeVO getVerifyCode(VerifyCodeDTO codeDTO) {
		String code = null;
		VerifyStatus status = VerifyStatus.SUCCESS;
		try {
			status = checkVerifyCodeSendTime(configVO, codeDTO);
			code = CommonUtils.getRandomNum(configVO.getCodeNum());
			VerifyCode infoModel = new VerifyCode(codeDTO.getVerifyKey(),
					String.format("%s.%s", codeDTO.getCommonServiceType(), codeDTO.getAppId()), code,
					codeDTO.getServiceCode().byteValue(), new Date());
			infoModel.setKid(idApi.getKid(IdConstants.QUANHU_VERIFY_CODE).getData());
			persistenceDao.insert(infoModel);
		} catch (Exception e) {
			Logger.error("getVerifyCode", e);
			throw new MysqlOptException(e);
		}
		redisDao.saveVerifyCode(codeDTO.getVerifyKey(), codeDTO.getAppId(), codeDTO.getServiceCode(), code, configVO);
		// 发送短信验证码
		if (CommonServiceType.PHONE_VERIFYCODE_SEND.getName().equals(codeDTO.getCommonServiceType())) {
			Map<String, Object> params = new HashMap<>();
			params.put("verifyCode", code);
			params.put("expire", configVO.getNormalCodeExpireTime() / 60);
			try {
				smsService.sendSms(
						new SmsDTO(codeDTO.getVerifyKey(), codeDTO.getAppId(), SmsType.VERIFY_CODE, params));
			} catch (Exception e) {
				Logger.error("短信验证码发送失败", e);
			}
		}
		return new VerifyCodeVO(codeDTO.getServiceCode(), codeDTO.getCommonServiceType(), code, status,
				DateUtils.addDateSecond(configVO.getNormalCodeExpireTime()));
	}

	@Override
	public Integer checkVerifyCode(VerifyCodeDTO codeDTO) {
		Integer exist = null;
		try {
			exist = persistenceDao.checkCode(new VerifyCode(codeDTO.getVerifyKey(),
					String.format("%s.%s", codeDTO.getCommonServiceType(), codeDTO.getAppId()), codeDTO.getVerifyCode(),
					codeDTO.getServiceCode().byteValue(), new Date()));
		} catch (Exception e) {
			Logger.error("checkVerifyCode", e);
			throw new MysqlOptException(e);
		}
		if (exist == null || exist == 0) {
			return CheckVerifyCodeReturnCode.FAIL.getCode();
		}
		String verifyCode = redisDao.getVerifyCode(codeDTO.getVerifyKey(), codeDTO.getAppId(),
				codeDTO.getServiceCode());
		if (codeDTO.isNeedDelete()) {
			redisDao.clearVerifyCode(codeDTO.getVerifyKey(), codeDTO.getAppId(), codeDTO.getServiceCode());
		}
		if (StringUtils.isBlank(verifyCode)) {
			return CheckVerifyCodeReturnCode.EXPIRE.getCode();
		}
		return CheckVerifyCodeReturnCode.SUCCESS.getCode();
	}

	@Override
	public String getImgVerifyCode(VerifyCodeDTO codeDTO) {
		String code = CommonUtils.getRandomStr(configVO.getCodeNum());
		redisDao.saveImgCode(codeDTO.getVerifyKey(), codeDTO.getAppId(), code, configVO);
		return code;
	}

	@Override
	public boolean checkImgVerifyCode(VerifyCodeDTO codeDTO) {
		if (!checkNeedImgCode(codeDTO, configVO) && StringUtils.isBlank(codeDTO.getVerifyCode())) {
			return false;
		}
		// 图形码为空直接返回false
		if (StringUtils.isBlank(codeDTO.getVerifyCode())) {
			return false;
		}
		String imgCode = redisDao.getImgCode(codeDTO.getVerifyKey(), codeDTO.getAppId());
		redisDao.clearImgCode(codeDTO.getVerifyKey(), codeDTO.getAppId());
		// 防止别人在没有获取图形验证码的情况下，随便输入验证码调短信接口
		if (StringUtils.isBlank(imgCode)) {
			return false;
		}
		if (StringUtils.equalsIgnoreCase(codeDTO.getVerifyCode(), imgCode)) {
			return true;
		}
		return false;
	}

	@Override
	public void saveIpCount(IpLimitDTO dto) {
		redisDao.saveIpCount(dto.getIp(), dto.getAppId(), dto.getServiceType());
		redisDao.saveIpRunTime(dto.getIp(), dto.getAppId(), dto.getServiceType());
	}

	@Override
	public boolean checkIpLimit(IpLimitDTO dto) {
		/*
		 * IpLimitConfigVO configVO = null;//getIpLimitConfig(dto.getAppId(),
		 * dto.getServiceType()); int total = redisDao.getIpCount(dto.getIp(),
		 * dto.getAppId(), dto.getServiceType()); long lastTime =
		 * redisDao.getIpRunTime(dto.getIp(), dto.getAppId(),
		 * dto.getServiceType()); if (configVO.getIpLimitFlag() && total >
		 * configVO.getIpLimitMax()) { return false; } if
		 * (configVO.getIpLimitFlag() && System.currentTimeMillis() - lastTime <
		 * configVO.getIpPerLimit()) { return false; }
		 */
		return false;
	}

	/**
	 * 检查当前是否需要图像验证码
	 * 
	 * @param codeDTO
	 * @param configVO
	 * @return
	 */
	private boolean checkNeedImgCode(VerifyCodeDTO codeDTO, VerifyCodeConfigVO configVO) {
		int num = redisDao.getImgCodeCount(codeDTO.getVerifyKey(), codeDTO.getAppId());
		redisDao.saveImgCodeCount(codeDTO.getVerifyKey(), codeDTO.getAppId(), configVO);
		return configVO.getImgCodeNumLimit() < num;
	}

	/*	*//**
			 * 获取ip风控配置
			 * 
			 * @param appId
			 * @param serviceType
			 * @return
			 *//*
			 * private IpLimitConfigVO getIpLimitConfig(String appId, String
			 * serviceType) { IpLimitConfigVO configVO =
			 * configService.getIpLimitConfig(appId, serviceType); if (configVO
			 * == null) { throw QuanhuException.busiError("ip限制配置不存在"); } if
			 * (configVO.getIpLimitFlag() == null) { throw
			 * QuanhuException.busiError("ip限制配置错误"); } if
			 * (configVO.getIpLimitMax() == null || configVO.getIpLimitMax() <
			 * 0) { throw QuanhuException.busiError("ip限制配置错误"); } if
			 * (configVO.getIpPerLimit() == null || configVO.getIpPerLimit() <
			 * 0) { throw QuanhuException.busiError("ip限制配置错误"); } return
			 * configVO; }
			 */

	/**
	 * 获取验证码配置
	 * 
	 * @param codeDTO
	 * @return
	 *//*
		 * private VerifyCodeConfigVO getVerifyCodeConfig(VerifyCodeDTO codeDTO)
		 * { VerifyCodeConfigVO configVO =
		 * configService.getVerifyCodeConfig(codeDTO.getAppId(),
		 * codeDTO.getCommonServiceType()); if (configVO == null) { throw
		 * QuanhuException.busiError("验证码配置不存在"); } if (configVO.getCodeNum() ==
		 * null || configVO.getCodeNum() < 0) { throw
		 * QuanhuException.busiError("验证码配置错误"); } if
		 * (configVO.getNormalCodeExpireTime() == null ||
		 * configVO.getNormalCodeExpireTime() < 0) { throw
		 * QuanhuException.busiError("验证码配置错误"); } if
		 * (configVO.getNormalCodeDelayTime() == null ||
		 * configVO.getNormalCodeDelayTime() < 0) { throw
		 * QuanhuException.busiError("验证码配置错误"); } if
		 * (configVO.getImgCodeExpireTime() == null ||
		 * configVO.getImgCodeExpireTime() < 0) { throw
		 * QuanhuException.busiError("验证码配置错误"); } if
		 * (configVO.getImgCodeNumLimit() == null ||
		 * configVO.getImgCodeNumLimit() < 0) { throw
		 * QuanhuException.busiError("验证码配置错误"); } return configVO; }
		 */
	/**
	 * 普通验证码风控检查
	 * 
	 * @param configVO
	 * @param codeDTO
	 */
	private VerifyStatus checkVerifyCodeSendTime(VerifyCodeConfigVO configVO, VerifyCodeDTO codeDTO) {
		Map<String, String> verifyCodeTime = redisDao.getVerifyCodeTime(codeDTO.getVerifyKey(), codeDTO.getAppId());
		if (MapUtils.isNotEmpty(verifyCodeTime)) {
			int total = NumberUtils.toInt(verifyCodeTime.get(RedisConstants.VERIFY_CODE_TOTAL));
			long lastTime = NumberUtils.toLong(verifyCodeTime.get(RedisConstants.VERIFY_CODE_LASTTIME));
			if (configVO.getNormalCodeTotal() < total) {
				return VerifyStatus.MORETHAN_LIMIT;
			}
			if (lastTime - System.currentTimeMillis() < configVO.getNormalCodeDelayTime()) {
				return VerifyStatus.TOO_FAST;
			}
		}
		return VerifyStatus.SUCCESS;
	}


	public boolean checkSmsSlipCode(VerifyCodeDTO verifyCodeDTO, AfsCheckRequest afsCheckReq) {
		//不需要图像验证码直接成功
		if (!checkVerifyCodeSendTime(configVO, verifyCodeDTO).equals(VerifyStatus.SUCCESS)
				&& afsCheckReq == null) {
			return true;
		}
		//图形码为空直接返回false
		if (afsCheckReq == null) {
			return false;
		}

		String accessKey = Context.getProperty("afs_check_accesskeyid");
		String accessSecret = Context.getProperty("afs_check_accesssecret");
		if (org.apache.commons.lang.StringUtils.isBlank(accessKey) || org.apache.commons.lang.StringUtils.isBlank(accessSecret)) {
			throw QuanhuException.busiError("未获取到afs_check验证码的配置信息");
		}
		AfsCheckManager afsCheckManager = new AfsCheckManager(accessKey, accessSecret);
		com.aliyuncs.jaq.model.v20161123.AfsCheckRequest req = new com.aliyuncs.jaq.model.v20161123.AfsCheckRequest();
		req.setPlatform(afsCheckReq.getPlatform());
		req.setSession(afsCheckReq.getSession());
		req.setSig(afsCheckReq.getSig());
		req.setToken(afsCheckReq.getToken());
		req.setScene(afsCheckReq.getScene());
		return afsCheckManager.afsCheck(req);
	}
}
