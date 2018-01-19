/**
 * 
 */
package com.yryz.quanhu.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.user.contants.SmsContants;
import com.yryz.quanhu.user.contants.SmsType;
import com.yryz.quanhu.user.manager.SmsManager;
import com.yryz.quanhu.user.service.AccountService;
import com.yryz.quanhu.user.service.SmsService;
import com.yryz.quanhu.user.vo.SmsVerifyCodeVO;


/**
 * @author danshiyu
 * 短信验证码管理
 */
@Service
public class SmsServiceImpl implements SmsService{
	
	@Autowired
	private SmsManager smsManager;
	@Autowired
	private AccountService accountService;
	
	@Override
	public SmsVerifyCodeVO sendVerifyCode(String phone, String type,String appId) {
		SmsVerifyCodeVO result = null;
		String msg = null;

		boolean accountFlag = accountService.checkUserByPhone(phone,appId);
		SmsType smsType = null;
		if(StringUtils.equals(type, SmsContants.CODE_LOGIN)){
			smsType = accountFlag ? SmsType.CODE_LOGIN : SmsType.CODE_REGISTER;
		}
		if (type == null) {
			smsType = accountFlag ? SmsType.CODE_REGISTER : SmsType.CODE_FIND_PWD;
		} else {
			switch (type) {
			case SmsContants.CODE_REGISTER:
				smsType = SmsType.CODE_REGISTER;
				msg = !accountFlag ? null : "该手机号已注册";
				break;
			case SmsContants.CODE_CHANGE_PHONE:		
				smsType = SmsType.CODE_CHANGE_PHONE;
				msg = !accountFlag ? null : "该手机号已注册";
				break;
			case SmsContants.CODE_FIND_PWD:
				smsType = SmsType.CODE_FIND_PWD;
				msg = !accountFlag ? "该手机用户不存在" : null;
				break;
			case SmsContants.CODE_IDENTITY:
				smsType = SmsType.CODE_IDENTITY;
				msg = !accountFlag ? "该手机用户不存在" : null;
				break;
			case SmsContants.CODE_TAKE_CASH:
				smsType = SmsType.CODE_TAKE_CASH;
				msg = !accountFlag ? "该手机用户不存在" : null;
				break;
			case SmsContants.CODE_SET_PAYPWD:
				smsType = SmsType.CODE_SET_PAYPWD;
				msg = !accountFlag ? "该手机用户不存在" : null;
				break;
			case SmsContants.CODE_CHANGE_PAYPWD:
				smsType = SmsType.CODE_CHANGE_PAYPWD;
				msg = !accountFlag ? "该手机用户不存在" : null;
				break;
			case SmsContants.CODE_LOGIN:
				smsType = SmsType.CODE_LOGIN;
				msg = !accountFlag ? "该手机用户不存在" : null;
				break;
			case SmsContants.CODE_OTHER:	
				smsType = SmsType.CODE_OTHER;
				break;
			default:
				break;
			}
		}

		if (msg != null) {
			throw new QuanhuException(ExceptionEnum.BusiException.getCode(),msg,ExceptionEnum.BusiException.getErrorMsg());
		}
		result = this.smsManager.sendCode(phone, smsType,appId);
		return result;
	}

	@Override
	public boolean checkVerifyCode(String phone, String code, String verifyCode,String appId) {
		return smsManager.checkVerifyCode(phone, verifyCode, code,appId,false);
	}

	/*@Override
	public void saveIpSendVerifyCodeCount(Map<String,Object> params) {
		smsManager.saveIpSendVerifyCodeCount(WebUtil.getClientIP(params));
	}

	@Override
	public boolean checkIpSendVerifyCodeLimit(Map<String,Object> params) {
		return smsManager.checkIpSendVerifyCodeLimit(WebUtil.getClientIP(params));
	}

	@Override
	public void getSmsImgCode(String phone, HttpServletResponse response) {
		String code = smsManager.getSmsImgCode(phone);
		CommonUtils.getSmsImgByCode(code, response);
	}

	@Override
	public boolean checkSmsImgCode(String phone, String code) {
		return smsManager.checkSmsImgCode(phone, code);
	}*/

}
