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
import com.yryz.quanhu.user.dto.SmsVerifyCodeDTO;
import com.yryz.quanhu.user.entity.UserAccount;
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
	public SmsVerifyCodeVO sendVerifyCode(SmsVerifyCodeDTO codeDTO) {
		SmsVerifyCodeVO result = null;
		String msg = null;
		boolean accountFlag = true;
		
		if(codeDTO.getUserId() == null){
		   accountFlag = accountService.checkUserByPhone(codeDTO.getPhone(),codeDTO.getAppId());
		}else{
		   UserAccount account = accountService.getUserAccountByUserId(codeDTO.getUserId());
		   if(account == null){
			   throw QuanhuException.busiError("用户不存在");
		   }
		   if(StringUtils.isBlank(account.getUserPhone())){
			   throw QuanhuException.busiError("手机号码不存在");
		   }		   
		}
		
		SmsType smsType = null;
		if(StringUtils.equals(codeDTO.getCode(), SmsContants.CODE_LOGIN)){
			smsType = accountFlag ? SmsType.CODE_LOGIN : SmsType.CODE_REGISTER;
		}
		if (codeDTO.getCode() == null) {
			smsType = accountFlag ? SmsType.CODE_REGISTER : SmsType.CODE_FIND_PWD;
		} else {
			switch (codeDTO.getCode()) {
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
			case SmsContants.CODE_OTHER:	
				smsType = SmsType.CODE_OTHER;
				break;
			case SmsContants.ACTIVITY_PHONE_CHECK:	
				smsType = SmsType.ACTIVITY_BIND_PHONE;
				break;
			default:
				break;
			}
		}

		if (msg != null) {
			throw new QuanhuException(ExceptionEnum.BusiException.getCode(),msg,ExceptionEnum.BusiException.getErrorMsg());
		}
		result = this.smsManager.sendCode(codeDTO.getPhone(), smsType,codeDTO.getAppId());
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
