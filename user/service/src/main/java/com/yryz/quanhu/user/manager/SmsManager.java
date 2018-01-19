/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年11月9日
 * Id: SmsManager.java, 2017年11月9日 下午2:06:07 Administrator
 */
package com.yryz.quanhu.user.manager;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.rpc.RpcContext;
import com.yryz.common.constant.AppConstants;
import com.yryz.common.context.Context;
import com.yryz.common.exception.QuanhuException;
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

	
	
	/**
	 * 发送短信验证码
	 * @param phone
	 * @param smsType 功能类型
	 * @return
	 * @Description 
	 */
	public SmsVerifyCodeVO sendCode(String phone,SmsType smsType,String appId){
		//SmsReqVo reqVo = new SmsReqVo();
		//reqVo.setPhone(phone);
		//reqVo.setCode(String.valueOf(smsType.getType()));
		try {
			//SmsVerifyCode codeVO = smsAPI.sendVerifyCode(reqVo);
			//smsLogger.info("[send_verifyCode]:phone->{},code->{},type->{},status->success",phone,codeVO.getCode(),smsType.getType());
			//return new SmsVerifyCodeVO(codeVO.getCode(), phone, codeVO.getExpire());
		} catch (QuanhuException e) {
			logger.error("[SmsAPI.sendCode]",e);
			smsLogger.info("[send_verifyCode]:phone->{},code->{},type->{},status->fail",phone,null,smsType.getType());
			//throw ExceptionUtils.throwWarnException("验证码发送失败");
		} catch (Exception e) {
			logger.error("[SmsAPI.sendCode]",e);
			smsLogger.info("[send_verifyCode]:phone->{},code->{},type->{},status->fail",phone,null,smsType.getType());
			//throw ExceptionUtils.throwWarnException("验证码发送失败");
		}
		return null;
	}
	
	/**
	 * 校验短信验证码
	 * @param phone
	 * @param code 验证码
	 * @param type 功能类型
	 * @return
	 */
	public boolean checkVerifyCode(String phone,String code,String type,String appId){
		try {
			smsLogger.info("[check_verifyCode]:phone->{},code->{},type->{},status->success",phone,code,type);
			//return smsAPI.checkVerifyCode(phone, type, code);
		} catch (QuanhuException e) {
			logger.error("[SmsAPI.checkVerifyCode]",e);
			smsLogger.info("[check_verifyCode]:phone->{},code->{},type->{},status->fail",phone,null,type);
			//throw ExceptionUtils.throwWarnException("验证码校验失败");
		} catch (Exception e) {
			logger.error("[SmsAPI.checkVerifyCode]",e);
			smsLogger.info("[check_verifyCode]:phone->{},code->{},type->{},status->fail",phone,null,type);
			//throw ExceptionUtils.throwWarnException("验证码校验失败");
		}
		return true;
	}
	
	/**
	 * 校验短信验证码
	 * @param phone
	 * @param code 验证码
	 * @param smsType 功能类型
	 * @return
	 */
	public boolean checkVerifyCode(String phone,String code,SmsType smsType,String appId){
		return checkVerifyCode(phone, code, String.valueOf(smsType.getType()),appId);
	}
	
	/**
	 * 更新某个ip发送短信验证码次数
	 * @param request
	 * @Description 
	 *//*
	public void saveIpSendVerifyCodeCount(String ip){
		String appId = Context.getProperty(Constants.DEFAULT_APPID);
		RpcContext.getContext().setAttachment(Constants.APP_ID, appId);
		try {
			smsAPI.saveIpSendVerifyCodeCount(ip);
		} catch (ServiceException e) {
			logger.error("[SmsAPI.saveIpSendVerifyCodeCount]",e);
			return;
		} catch (Exception e) {
			logger.error("[SmsAPI.saveIpSendVerifyCodeCount]",e);
			return;
		}
	}
	
	*//**
	 * 检查当前ip是否可以发送短信验证码
	 * @param request
	 * @return
	 * @Description 限制每个ip每天发送1000次
	 *//*
	public boolean checkIpSendVerifyCodeLimit(String ip){
		String appId = Context.getProperty(Constants.DEFAULT_APPID);
		RpcContext.getContext().setAttachment(Constants.APP_ID, appId);
		try {
			return smsAPI.checkIpSendVerifyCodeLimit(ip);
		} catch (ServiceException e) {
			logger.error("[SmsAPI.checkIpSendVerifyCodeLimit]",e);
			return true;
		} catch (Exception e) {
			logger.error("[SmsAPI.checkIpSendVerifyCodeLimit]",e);
			return true;
		}
	}
	
	*//**
	 * 获取图形验证码
	 * @param phone
	 * @return
	 * @Description 获取图形验证码
	 *//*
	public String getSmsImgCode(String phone){
		String appId = Context.getProperty(Constants.DEFAULT_APPID);
		RpcContext.getContext().setAttachment(Constants.APP_ID, appId);
		try {
			return smsAPI.getSmsImgCode(phone);
		} catch (ServiceException e) {
			logger.error("[SmsAPI.getSmsImgCode]",e);
			throw ExceptionUtils.throwWarnException("获取图形码失败");
		} catch (Exception e) {
			logger.error("[SmsAPI.getSmsImgCode]",e);
			throw ExceptionUtils.throwWarnException("验证图形码失败");
		}
	}
	
	*//**
	 * 校验图形验证码
	 * @param phone
	 * @param code
	 * @return
	 * @Description 校验图形验证码
	 *//*
	public boolean checkSmsImgCode(String phone,String code){
		String appId = Context.getProperty(Constants.DEFAULT_APPID);
		RpcContext.getContext().setAttachment(Constants.APP_ID, appId);
		try {
			return smsAPI.checkSmsImgCode(phone, code);
		} catch (ServiceException e) {
			logger.error("[SmsAPI.checkSmsImgCode]",e);
			throw ExceptionUtils.throwWarnException("校验图形码失败");
		} catch (Exception e) {
			logger.error("[SmsAPI.checkSmsImgCode]",e);
			throw ExceptionUtils.throwWarnException("校验图形码失败");
		}
	}*/
}
