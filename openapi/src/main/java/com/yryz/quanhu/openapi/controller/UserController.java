package com.yryz.quanhu.openapi.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.annotation.NotLogin;
import com.yryz.common.constant.DevType;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.entity.RequestHeader;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.common.utils.WebUtil;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.user.contants.RegType;
import com.yryz.quanhu.user.dto.AuthRefreshDTO;
import com.yryz.quanhu.user.dto.BindPhoneDTO;
import com.yryz.quanhu.user.dto.BindThirdDTO;
import com.yryz.quanhu.user.dto.ForgotPasswordDTO;
import com.yryz.quanhu.user.dto.LoginDTO;
import com.yryz.quanhu.user.dto.RegisterDTO;
import com.yryz.quanhu.user.dto.ThirdLoginDTO;
import com.yryz.quanhu.user.dto.UnBindThirdDTO;
import com.yryz.quanhu.user.dto.UpdateBaseInfoDTO;
import com.yryz.quanhu.user.dto.UserRegLogDTO;
import com.yryz.quanhu.user.service.AccountApi;
import com.yryz.quanhu.user.service.AuthApi;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.AuthTokenVO;
import com.yryz.quanhu.user.vo.LoginMethodVO;
import com.yryz.quanhu.user.vo.RegisterLoginVO;
import com.yryz.quanhu.user.vo.UserLoginSimpleVO;
import com.yryz.quanhu.user.vo.UserSimpleVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(description = "用户接口")
@RestController
@RequestMapping(value="services/app")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class );

	private static final int CHAR_51 = 3;
	private static final String CHAR_3F = "%3F";
	private static final String CHAR_63 = "?";
	@Reference(lazy=true,check=false)
	private AccountApi accountApi;
	@Reference(lazy=true,check=false)
	private UserApi userApi;
	@Reference(lazy=true,check=false)
	private AuthApi authApi;

	@ApiOperation("用户token刷新")
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@PostMapping(value = "/{version}/user/refreshToken")
	public Response<AuthTokenVO> refreshToken(@RequestBody String refreshToken, HttpServletRequest request) {
		RequestHeader header = WebUtil.getHeader(request);
		AuthRefreshDTO refreshDTO = new AuthRefreshDTO(refreshToken, true);
		refreshDTO.setAppId(header.getAppId());
		refreshDTO.setToken(header.getToken());
		refreshDTO.setUserId(header.getUserId());
		refreshDTO.setType(DevType.getEnumByType(header.getDevType(), header.getUserAgent()));
		return authApi.refreshToken(refreshDTO);
	}

	@ApiOperation("用户信息查询")
	@NotLogin
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@GetMapping(value = "/{version}/user/find")
	public Response<UserLoginSimpleVO> findUser(String userId, HttpServletRequest request) {
		RequestHeader header = WebUtil.getHeader(request);
		if (StringUtils.isBlank(userId)) {
			return userApi.getUserLoginSimpleVO(header.getUserId());
		} else {
			return userApi.getUserLoginSimpleVO(header.getUserId(), userId);
		}
	}
	
	@ApiOperation("用户信息编辑")
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@PostMapping(value = "/{version}/user/update")
	public Response<Boolean> userUpdate(@RequestBody UpdateBaseInfoDTO infoDTO, HttpServletRequest request) {
		RequestHeader header = WebUtil.getHeader(request);
		infoDTO.setUserId(header.getUserId());
		return userApi.updateUserInfo(infoDTO);
	}
	
	/**
	 * 手机号注册
	 * 
	 * @param registerDTO
	 * @param header
	 * @return
	 */
	@ApiOperation("手机号注册")
	@NotLogin
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@PostMapping(value = "/{version}/user/register")
	public Response<RegisterLoginVO> register(@RequestBody RegisterDTO registerDTO, HttpServletRequest request) {
		RequestHeader header = WebUtil.getHeader(request);
		String ip = WebUtil.getClientIP(request);
		registerDTO.setDeviceId(header.getDevId());
		DevType devType = DevType.getEnumByType(header.getDevType(), header.getUserAgent());
		UserRegLogDTO logDTO = getUserRegLog(header, RegType.PHONE, registerDTO.getUserLocation(),
				registerDTO.getActivityChannelCode(), devType, ip);
		registerDTO.setRegLogDTO(logDTO);
		return accountApi.register(registerDTO, header);
	}

	/**
	 * 手机号密码登录
	 * 
	 * @param loginDTO
	 * @param header
	 * @return
	 * @Description
	 */
	@ApiOperation("手机号密码登录")
	@NotLogin
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@PostMapping(value = "/{version}/user/login")
	public Response<RegisterLoginVO> login(@RequestBody LoginDTO loginDTO, HttpServletRequest request) {
		RequestHeader header = WebUtil.getHeader(request);
		return accountApi.login(loginDTO, header);
	}

	/**
	 * 手机短信登录
	 * 
	 * @param loginDTO
	 * @param header
	 * @return
	 * @Description
	 */
	@ApiOperation("手机号验证码登录")
	@NotLogin
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@PostMapping(value = "/{version}/user/loginVerifyCode")
	public Response<RegisterLoginVO> loginByVerifyCode(@RequestBody RegisterDTO registerDTO, HttpServletRequest request) {
		RequestHeader header = WebUtil.getHeader(request);
		String ip = WebUtil.getClientIP(request);
		registerDTO.setDeviceId(header.getDevId());
		DevType devType = DevType.getEnumByType(header.getDevType(), header.getUserAgent());
		UserRegLogDTO logDTO = getUserRegLog(header, RegType.PHONE, registerDTO.getUserLocation(),
				registerDTO.getActivityChannelCode(), devType, ip);
		registerDTO.setRegLogDTO(logDTO);
		return accountApi.loginByVerifyCode(registerDTO,header);
	}

	/**
	 * (app)第三方登录
	 * 
	 * @param loginDTO
	 * @param header
	 * @return
	 * @Description
	 */
	@ApiOperation("app第三方登录")
	@NotLogin
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@PostMapping(value = "/{version}/user/loginThird")
	public Response<Map<String,Object>> loginThird(@RequestBody ThirdLoginDTO loginDTO, HttpServletRequest request) {
		RequestHeader header = WebUtil.getHeader(request);
		String ip = WebUtil.getClientIP(request);
		loginDTO.setDeviceId(header.getDevId());
		DevType devType = DevType.getEnumByType(header.getDevType(), header.getUserAgent());
		UserRegLogDTO logDTO = getUserRegLog(header, RegType.PHONE, loginDTO.getLocation(), null, devType, ip);
		loginDTO.setRegLogDTO(logDTO);
		Response<RegisterLoginVO> response = accountApi.loginThird(loginDTO, header);
		Map<String,Object> map = new HashMap<>();
		map.put("needPhone", false);
		if(StringUtils.equals(response.getCode(),ExceptionEnum.NEED_PHONE.getCode())){
			map.put("needPhone", true);
		}else{
			map.put("authInfo", response.getData().getAuthInfo());
			map.put("user", response.getData().getUser());
		}
		return ResponseUtils.returnObjectSuccess(map);
	}

	/**
	 * (app)第三方登录绑定手机号
	 * 
	 * @param header
	 * @param loginDTO
	 * @return
	 * @Description
	 */
	@ApiOperation("app第三方登录绑定手机号")
	@NotLogin
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@PostMapping(value = "/{version}/user/loginThirdBindPhone")
	public Response<RegisterLoginVO> loginThirdBindPhone(@RequestBody ThirdLoginDTO loginDTO,
			HttpServletRequest request) {
		RequestHeader header = WebUtil.getHeader(request);
		String ip = WebUtil.getClientIP(request);
		DevType devType = DevType.getEnumByType(header.getDevType(), header.getUserAgent());
		loginDTO.setDevType(devType);
		loginDTO.setDeviceId(header.getDevId());
		UserRegLogDTO logDTO = getUserRegLog(header, RegType.PHONE, loginDTO.getLocation(), null, devType, ip);
		loginDTO.setRegLogDTO(logDTO);

		return accountApi.loginThird(loginDTO, header);
	}

	/**
	 * (web)第三方登录
	 * 
	 * @param loginType
	 *            类型 sina:微博 weixin:微信
	 * @param returnUrl
	 *            web端登录成功返回地址
	 * @return
	 * @Description
	 */
	@ApiOperation("web第三方登录")
	@NotLogin
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@GetMapping(value = "/{version}/user/webLoginThird")
	public Response<String> webLoginThird(String loginType, String returnUrl) {
		return accountApi.webLoginThird(loginType, returnUrl);
	}

	/**
	 * (web)第三方登录回调
	 * 
	 * @return
	 * @throws IOException
	 * @Description 第三方回调成功后跳转到web端登录时传的地址
	 */
	@ApiOperation("web第三方登录回调")
	@NotLogin
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@GetMapping(value = "/{version}/user/thirdLoginNotify")
	public void webThirdLoginNotify(HttpServletRequest request, HttpServletResponse response) {

	}

	/**
	 * 获取登录方式
	 * 
	 * @param userId
	 * @return
	 * @Description 根据header信息获取登录方式
	 */
	@ApiOperation("获取登录方式")
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@GetMapping(value = "/{version}/user/getLoginMethod")
	public Response<List<LoginMethodVO>> getLoginMethod(HttpServletRequest request) {
		RequestHeader header = WebUtil.getHeader(request);
		return accountApi.getLoginMethod(header.getUserId());
	}

	/**
	 * 退出登录
	 * 
	 * @param request
	 * @return
	 */
	@ApiOperation("退出登录")
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@PostMapping(value = "/{version}/user/loginOut")
	public Response<Boolean> loginOut() {
		return ResponseUtils.returnSuccess();
	}

	/**
	 * 绑定手机号
	 * 
	 * @param phoneDTO
	 * @return
	 */
	@ApiOperation("绑定手机号")
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@PostMapping(value = "/{version}/user/bindPhone")
	public Response<Boolean> bindPhone(@RequestBody BindPhoneDTO phoneDTO,HttpServletRequest request) {
		RequestHeader header = WebUtil.getHeader(request);
		phoneDTO.setUserId(header.getUserId());
		return accountApi.bindPhone(phoneDTO);
	}

	/**
	 * 绑定 第三方账户
	 * 
	 * @param thirdDTO
	 * @return
	 */
	@ApiOperation("绑定 第三方账户")
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@PostMapping(value = "/{version}/user/bindThird")
	public Response<Boolean> bindThird(@RequestBody BindThirdDTO thirdDTO,HttpServletRequest request) {
		RequestHeader header = WebUtil.getHeader(request);
		thirdDTO.setUserId(header.getUserId());
		return accountApi.bindThird(thirdDTO);
	}

	/**
	 * 解绑 第三方账户
	 * 
	 * @param thirdDTO
	 * @return
	 */
	@ApiOperation("绑定 第三方账户")
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@PostMapping(value = "/{version}/user/unbindThird")
	public Response<Boolean> unbindThird(@RequestBody UnBindThirdDTO thirdDTO,HttpServletRequest request) {
		RequestHeader header = WebUtil.getHeader(request);
		thirdDTO.setUserId(header.getUserId());
		return accountApi.unbindThird(thirdDTO);
	}

	/**
	 * 修改密码
	 * 
	 * @param request
	 * @param newPassword
	 *            新密码
	 * @param oldPassword
	 *            旧密码
	 * @return
	 * @Description 需要在header获取用户id
	 */
	@ApiOperation("修改密码")
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@PostMapping(value = "/{version}/user/editPassword")
	public Response<Boolean> editPassword(@RequestBody String oldPassword,
			@RequestBody String newPassword,HttpServletRequest request) {
		RequestHeader header = WebUtil.getHeader(request);
		return accountApi.editPassword(header.getUserId(), oldPassword, newPassword);
	}

	/**
	 * 手机短信重置密码
	 * 
	 * @param passwordDTO
	 */
	@ApiOperation("手机短信重置密码")
	@NotLogin
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@PostMapping(value = "/{version}/user/forgotPassword")
	public Response<Boolean> forgotPassword(@RequestBody ForgotPasswordDTO passwordDTO,HttpServletRequest request) {
		RequestHeader header = WebUtil.getHeader(request);
		passwordDTO.setAppId(header.getAppId());
		return accountApi.forgotPassword(passwordDTO);
	}

	/**
	 * 拼装web登录返回地址
	 * 
	 * @param base
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String returnUrl(UserSimpleVO base, String token, String state) {
		String[] stateArray = state.split("_");
		String backUrl = null;
		if (stateArray.length < CHAR_51) {
			return null;
		}
		backUrl = stateArray[2];
		StringBuffer returnUrl = new StringBuffer();
		returnUrl.append(backUrl);
		if (backUrl.indexOf(CHAR_63) != -1 || backUrl.indexOf(CHAR_3F) != -1) {
			returnUrl.append("&token=");
		} else {
			returnUrl.append("?token=");
		}
		returnUrl.append(token);
		returnUrl.append("&userId=").append(base.getUserId());
		try {
			returnUrl.append("&userNickname=").append(StringUtils.isNotBlank(base.getUserNickName())
					? URLEncoder.encode(base.getUserNickName(), "utf-8") : "");
		} catch (UnsupportedEncodingException e) {
		}
		returnUrl.append("&userImg=").append(base.getUserImg());
		return returnUrl.toString();
	}

	/**
	 * 得到初始化后的注册日志
	 * 
	 * @param request
	 * @param regType
	 * @param location
	 * @param activityChannelCode
	 * @return
	 */
	private static UserRegLogDTO getUserRegLog(RequestHeader header, RegType regType, String location,
			String activityChannelCode, DevType type, String ip) {
		UserRegLogDTO logDTO = new UserRegLogDTO(null, header.getDitchCode(), header.getAppVersion(), regType.getText(),
				type.getLabel(), header.getDevName(), header.getAppId(), ip, location, activityChannelCode, null);
		// 拼接组合的圈乎渠道码
		String channelCode = StringUtils.join(new String[] { logDTO.getAppChannel(), logDTO.getDevType(),
				logDTO.getRegType(), logDTO.getActivityChannelCode() }, " ");
		logDTO.setChannelCode(channelCode);
		return logDTO;
	}
}
