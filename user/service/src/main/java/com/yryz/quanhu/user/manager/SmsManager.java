/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年11月9日
 * Id: SmsManager.java, 2017年11月9日 下午2:06:07 Administrator
 */
package com.yryz.quanhu.user.manager;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.exception.QuanhuException;
import com.yryz.quanhu.message.commonsafe.api.CommonSafeApi;
import com.yryz.quanhu.message.commonsafe.constants.CommonServiceType;
import com.yryz.quanhu.message.commonsafe.dto.VerifyCodeDTO;
import com.yryz.quanhu.message.commonsafe.vo.VerifyCodeVO;
import com.yryz.quanhu.user.contants.SmsType;
import com.yryz.quanhu.user.vo.SmsVerifyCodeVO;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年11月9日 下午2:06:07
 */
@Component
public class SmsManager {
	private static final Logger logger = LoggerFactory.getLogger(SmsManager.class);
	private static final Logger smsLogger = LoggerFactory.getLogger("sms.Logger");

	@Reference(lazy=true,check=false)
	private CommonSafeApi commonSafeApi;

	/**
	 * 发送短信验证码
	 * 
	 * @param phone
	 * @param smsType
	 *            功能类型
	 * @return
	 * @Description
	 */
	public SmsVerifyCodeVO sendCode(String phone, SmsType smsType, String appId) {
		try {
			VerifyCodeVO codeVO = commonSafeApi.getVerifyCode(new VerifyCodeDTO(smsType.getType(),
					CommonServiceType.PHONE_VERIFYCODE_SEND.getName(), phone, appId)).getData();
			return new SmsVerifyCodeVO(String.valueOf(smsType.getType()), phone, String.valueOf(codeVO.getExpireAt()));
		} catch (QuanhuException e) {
			logger.error("[SmsAPI.sendCode]", e);
			smsLogger.info("[send_verifyCode]:phone->{},code->{},type->{},status->fail", phone, null,
					smsType.getType());
			throw QuanhuException.busiError("验证码发送失败");
		} catch (Exception e) {
			logger.error("[SmsAPI.sendCode]", e);
			smsLogger.info("[send_verifyCode]:phone->{},code->{},type->{},status->fail", phone, null,
					smsType.getType());
			throw QuanhuException.busiError("验证码发送失败");
		}
	}

	/**
	 * 校验短信验证码
	 * 
	 * @param phone
	 * @param code
	 *            验证码
	 * @param type
	 *            功能类型
	 * @return
	 */
	public boolean checkVerifyCode(String phone, String code, String type, String appId, boolean needDelete) {
		try {
			smsLogger.info("[check_verifyCode]:phone->{},code->{},type->{},status->success", phone, code, type);
			int result = commonSafeApi
					.checkVerifyCode(new VerifyCodeDTO(NumberUtils.toInt(type),
							CommonServiceType.PHONE_VERIFYCODE_SEND.getName(), phone, appId, code, needDelete))
					.getData();
			if (result != 0) {
				return false;
			}
			return true;
		} catch (QuanhuException e) {
			logger.error("[SmsAPI.checkVerifyCode]", e);
			smsLogger.info("[check_verifyCode]:phone->{},code->{},type->{},status->fail", phone, null, type);
			throw QuanhuException.busiError("验证码验证失败");
		} catch (Exception e) {
			logger.error("[SmsAPI.checkVerifyCode]", e);
			smsLogger.info("[check_verifyCode]:phone->{},code->{},type->{},status->fail", phone, null, type);
			throw QuanhuException.busiError("验证码验证失败");
		}
	}

	/**
	 * 校验短信验证码
	 * 
	 * @param phone
	 * @param code
	 *            验证码
	 * @param smsType
	 *            功能类型
	 * @return
	 */
	public boolean checkVerifyCode(String phone, String code, SmsType smsType, String appId) {
		return checkVerifyCode(phone, code, String.valueOf(smsType.getType()), appId, true);
	}

	/**
	 * 更新某个ip发送短信验证码次数
	 * 
	 * @param request
	 * @Description
	 */
	/*
	 * public void saveIpSendVerifyCodeCount(String ip){ String appId =
	 * Context.getProperty(Constants.DEFAULT_APPID);
	 * RpcContext.getContext().setAttachment(Constants.APP_ID, appId); try {
	 * smsAPI.saveIpSendVerifyCodeCount(ip); } catch (ServiceException e) {
	 * logger.error("[SmsAPI.saveIpSendVerifyCodeCount]",e); return; } catch
	 * (Exception e) { logger.error("[SmsAPI.saveIpSendVerifyCodeCount]",e);
	 * return; } }
	 * 
	 *//**
		 * 检查当前ip是否可以发送短信验证码
		 * 
		 * @param request
		 * @return
		 * @Description 限制每个ip每天发送1000次
		 */
	/*
	 * public boolean checkIpSendVerifyCodeLimit(String ip){ String appId =
	 * Context.getProperty(Constants.DEFAULT_APPID);
	 * RpcContext.getContext().setAttachment(Constants.APP_ID, appId); try {
	 * return smsAPI.checkIpSendVerifyCodeLimit(ip); } catch (ServiceException
	 * e) { logger.error("[SmsAPI.checkIpSendVerifyCodeLimit]",e); return true;
	 * } catch (Exception e) {
	 * logger.error("[SmsAPI.checkIpSendVerifyCodeLimit]",e); return true; } }
	 * 
	 *//**
		 * 获取图形验证码
		 * 
		 * @param phone
		 * @return
		 * @Description 获取图形验证码
		 */
	/*
	 * public String getSmsImgCode(String phone){ String appId =
	 * Context.getProperty(Constants.DEFAULT_APPID);
	 * RpcContext.getContext().setAttachment(Constants.APP_ID, appId); try {
	 * return smsAPI.getSmsImgCode(phone); } catch (ServiceException e) {
	 * logger.error("[SmsAPI.getSmsImgCode]",e); throw
	 * ExceptionUtils.throwWarnException("获取图形码失败"); } catch (Exception e) {
	 * logger.error("[SmsAPI.getSmsImgCode]",e); throw
	 * ExceptionUtils.throwWarnException("验证图形码失败"); } }
	 * 
	 *//**
		 * 校验图形验证码
		 * 
		 * @param phone
		 * @param code
		 * @return
		 * @Description 校验图形验证码
		 *//*
		 * public boolean checkSmsImgCode(String phone,String code){ String
		 * appId = Context.getProperty(Constants.DEFAULT_APPID);
		 * RpcContext.getContext().setAttachment(Constants.APP_ID, appId); try {
		 * return smsAPI.checkSmsImgCode(phone, code); } catch (ServiceException
		 * e) { logger.error("[SmsAPI.checkSmsImgCode]",e); throw
		 * ExceptionUtils.throwWarnException("校验图形码失败"); } catch (Exception e) {
		 * logger.error("[SmsAPI.checkSmsImgCode]",e); throw
		 * ExceptionUtils.throwWarnException("校验图形码失败"); } }
		 */
}
