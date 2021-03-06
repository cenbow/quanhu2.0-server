package com.yryz.quanhu.openapi.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.annotation.UserBehaviorValidation;
import com.yryz.common.entity.AfsCheckRequest;
import com.yryz.common.entity.RequestHeader;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.common.utils.WebUtil;
import com.yryz.quanhu.message.commonsafe.api.CommonSafeApi;
import com.yryz.quanhu.message.commonsafe.constants.CheckSlipCodeReturn;
import com.yryz.quanhu.message.commonsafe.constants.CommonServiceType;
import com.yryz.quanhu.message.commonsafe.dto.VerifyCodeDTO;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.openapi.service.AuthService;
import com.yryz.quanhu.openapi.utils.ComponentUtils;
import com.yryz.quanhu.user.contants.SmsType;
import com.yryz.quanhu.user.dto.SmsVerifyCodeDTO;
import com.yryz.quanhu.user.service.AccountApi;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.SmsVerifyCodeVO;
import com.yryz.quanhu.user.vo.UserLoginSimpleVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(description = "公共组件接口")
@RestController
@RequestMapping(value="services/app")
public class ComponentController {
	private static final Logger logger = LoggerFactory.getLogger(ComponentController.class);
	
	@Reference(lazy=true,check=false,cluster="failfast")
	private AccountApi accountApi;
	@Reference(lazy=true,check=false,cluster="failfast")
	private CommonSafeApi commonSafeApi;
	@Reference
	private UserApi userApi;
	@Autowired
	private AuthService authService;
	
	@ApiOperation("短信验证码发送")
	@UserBehaviorValidation(login=false)
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@PostMapping(value = "/{version}/component/sendVerifyCode")
	public Response<SmsVerifyCodeVO> sendVerifyCode(@RequestBody SmsVerifyCodeDTO codeDTO, HttpServletRequest request) {
		RequestHeader header = WebUtil.getHeader(request);
		codeDTO.setAppId(header.getAppId());
		codeDTO.setIp(WebUtil.getClientIP(request));
		//没有手机号需要验证token
		if(StringUtils.isBlank(codeDTO.getPhone())){
			authService.checkToken(request);
			codeDTO.setUserId(NumberUtils.createLong(header.getUserId()));
		}
		//设置支付密码、提现、验证身份、手机号变更 必须登录 
		if(StringUtils.equals(codeDTO.getCode(),String.valueOf(SmsType.CODE_SET_PAYPWD.getType())) 
				|| StringUtils.equals(codeDTO.getCode(),String.valueOf(SmsType.CODE_TAKE_CASH.getType())) || 
				StringUtils.equals(codeDTO.getCode(),String.valueOf(SmsType.CODE_OTHER.getType())) || 
				StringUtils.equals(codeDTO.getCode(),String.valueOf(SmsType.CODE_CHANGE_PHONE.getType()))){
			authService.checkToken(request);
			//非变更手机号的业务输入的手机号无效
			if(!StringUtils.equals(codeDTO.getCode(),String.valueOf(SmsType.CODE_CHANGE_PHONE.getType()))){
				codeDTO.setPhone(null);
				codeDTO.setUserId(NumberUtils.createLong(header.getUserId()));
			}
		}
		
		SmsVerifyCodeVO codeVO = ResponseUtils.getResponseData(accountApi.sendVerifyCode(codeDTO));
		return ResponseUtils.returnApiObjectSuccess(codeVO);
	}

	@ApiOperation("验证码校验（只校验不删除）")
	@UserBehaviorValidation(login=false)
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@PostMapping(value = "/{version}/component/checkVerifyCode")
	public Response<Map<String,Integer>> checkVerifyCode(@RequestBody SmsVerifyCodeDTO codeDTO, HttpServletRequest request) {
		RequestHeader header = WebUtil.getHeader(request);
		codeDTO.setAppId(header.getAppId());
		String phone = null;
		//没传手机号就验证token,并获取用户手机号
		if(StringUtils.isBlank(codeDTO.getPhone())){
			authService.checkToken(request);
			codeDTO.setUserId(NumberUtils.createLong(header.getUserId()));
			UserLoginSimpleVO simpleVO = ResponseUtils.getResponseData(userApi.getUserLoginSimpleVO(codeDTO.getUserId()));
			if(simpleVO != null){
				phone = simpleVO.getUserPhone();
				if(StringUtils.isBlank(phone)){
					return ResponseUtils.returnCommonException("手机号不存在");
				}
				codeDTO.setPhone(phone);
			}
		}
		Integer result = ResponseUtils.getResponseData(commonSafeApi.checkVerifyCode(new VerifyCodeDTO(NumberUtils.toInt(codeDTO.getCode()),
				CommonServiceType.PHONE_VERIFYCODE_SEND.getName(), codeDTO.getPhone(), header.getAppId(),
				codeDTO.getVeriCode(), false)));
		Map<String,Integer> map = new HashMap<>();
		map.put("check", result);
		return ResponseUtils.returnApiObjectSuccess(map);
	}


	@ApiOperation("发送短信验证码（滑动验证）")
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@UserBehaviorValidation(login=false)
	@PostMapping(value = "/{version}/component/sendVerifyCodeForSlip")
	public Response<SmsVerifyCodeVO> sendVerifyCodeForSlip(@RequestBody SmsVerifyCodeDTO codeDTO, HttpServletRequest request) {
		logger.info("sendVerifyCodeForSlip request, codeDTO: {}", GsonUtils.parseJson(codeDTO));
		RequestHeader header = WebUtil.getHeader(request);
		codeDTO.setAppId(header.getAppId());
		codeDTO.setIp(WebUtil.getClientIP(request));
		VerifyCodeDTO verifyCodeDTO = new VerifyCodeDTO(NumberUtils.toInt(codeDTO.getCode()),
				CommonServiceType.PHONE_VERIFYCODE_SEND.getName(), codeDTO.getPhone(), header.getAppId(),
				codeDTO.getVeriCode(), false);

		AfsCheckRequest afsCheckRequest = ComponentUtils.getAfsCheckRequest(codeDTO);
		Integer checkSlipResult = ResponseUtils.getResponseData(commonSafeApi.checkSlipCode(verifyCodeDTO, afsCheckRequest));
		if(CheckSlipCodeReturn.NEED_CODE.getCode() == checkSlipResult){
			return ResponseUtils.returnApiObjectSuccess(new SmsVerifyCodeVO("1"));
		}
		if(CheckSlipCodeReturn.FAIL.getCode() == checkSlipResult){
			return ResponseUtils.returnCommonException("滑动验证码不正确");
		}
		SmsVerifyCodeVO codeVO = ResponseUtils.getResponseData(accountApi.sendVerifyCode(codeDTO));
		return ResponseUtils.returnApiObjectSuccess(codeVO);
	}
}
