/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年11月9日
 * Id: AccountCotroller.java, 2017年11月9日 下午2:19:48 Administrator
 */
package com.yryz.quanhu.user.provider;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.constant.DevType;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.entity.RequestHeader;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.framework.core.lock.DistributedLockManager;
import com.yryz.quanhu.user.contants.Constants;
import com.yryz.quanhu.user.contants.LoginType;
import com.yryz.quanhu.user.contants.RegType;
import com.yryz.quanhu.user.contants.SmsType;
import com.yryz.quanhu.user.contants.ThirdConstants;
import com.yryz.quanhu.user.contants.UserAccountStatus;
import com.yryz.quanhu.user.contants.UserIdentity;
import com.yryz.quanhu.user.dto.AgentRegisterDTO;
import com.yryz.quanhu.user.dto.AuthRefreshDTO;
import com.yryz.quanhu.user.dto.AuthTokenDTO;
import com.yryz.quanhu.user.dto.BindPhoneDTO;
import com.yryz.quanhu.user.dto.BindThirdDTO;
import com.yryz.quanhu.user.dto.ForgotPasswordDTO;
import com.yryz.quanhu.user.dto.LoginDTO;
import com.yryz.quanhu.user.dto.RegisterDTO;
import com.yryz.quanhu.user.dto.SmsVerifyCodeDTO;
import com.yryz.quanhu.user.dto.ThirdLoginDTO;
import com.yryz.quanhu.user.dto.UnBindThirdDTO;
import com.yryz.quanhu.user.dto.WebThirdLoginDTO;
import com.yryz.quanhu.user.entity.ActivityTempUser;
import com.yryz.quanhu.user.entity.UserBaseInfo;
import com.yryz.quanhu.user.entity.UserLoginLog;
import com.yryz.quanhu.user.entity.UserThirdLogin;
import com.yryz.quanhu.user.manager.SmsManager;
import com.yryz.quanhu.user.service.AccountApi;
import com.yryz.quanhu.user.service.AccountService;
import com.yryz.quanhu.user.service.ActivityTempUserService;
import com.yryz.quanhu.user.service.AuthService;
import com.yryz.quanhu.user.service.OatuhQq;
import com.yryz.quanhu.user.service.OatuhWeibo;
import com.yryz.quanhu.user.service.OatuhWeixin;
import com.yryz.quanhu.user.service.SmsService;
import com.yryz.quanhu.user.service.UserService;
import com.yryz.quanhu.user.service.UserThirdLoginService;
import com.yryz.quanhu.user.utils.PhoneUtils;
import com.yryz.quanhu.user.utils.UserUtils;
import com.yryz.quanhu.user.vo.AuthTokenVO;
import com.yryz.quanhu.user.vo.LoginMethodVO;
import com.yryz.quanhu.user.vo.RegisterLoginVO;
import com.yryz.quanhu.user.vo.SmsVerifyCodeVO;
import com.yryz.quanhu.user.vo.ThirdLoginConfigVO;
import com.yryz.quanhu.user.vo.ThirdUser;
import com.yryz.quanhu.user.vo.UserLoginSimpleVO;
import com.yryz.quanhu.user.vo.WeiboToken;
import com.yryz.quanhu.user.vo.WxToken;

/**
 * 账户管理
 * 
 * @author danshiyu
 * @version 1.0
 * @date 2017年11月9日 下午2:19:48
 * @Description 考虑到不必要的事务回滚，注册、登录业务大部分校验、获取token、用户信息放到了controller里面，如果你的系统还有上一层
 *              ，可以把这些业务放到上一层处理
 */
@Service(interfaceClass = AccountApi.class)
public class AccountProvider implements AccountApi {
	private static final Logger logger = LoggerFactory.getLogger(AccountProvider.class);

	private static final String CHAR_3F = "%3F";
	private static final String CHAR_63 = "?";
	@Autowired
	private DistributedLockManager lockManager;
	@Autowired
	private AccountService accountService;
	@Autowired
	private UserThirdLoginService thirdLoginService;
	@Autowired
	private SmsManager smsManager;
	@Autowired
	private SmsService smsService;
	@Autowired
	private AuthService authService;
	@Autowired
	private UserService userService;
	@Autowired
	private ActivityTempUserService tempUserService;

	/**
	 * 手机号注册
	 * 
	 * @param registerDTO
	 * @return
	 * @Description
	 */
	@Override
	public Response<RegisterLoginVO> register(RegisterDTO registerDTO, RequestHeader header) {
		try {
			checkRegisterDTO(registerDTO, RegType.PHONE);
			checkHeader(header);
			if (!smsManager.checkVerifyCode(registerDTO.getUserPhone(), registerDTO.getVeriCode(),
					SmsType.CODE_REGISTER, header.getAppId())) {
				throw new QuanhuException(ExceptionEnum.SMS_VERIFY_CODE_ERROR);
			}
			// 手机号加锁
			lockManager.lock(Constants.BIND_PHONE, registerDTO.getUserPhone());

			boolean flag = accountService.checkUserByPhone(registerDTO.getUserPhone(), header.getAppId());
			if (flag) {
				throw QuanhuException.busiError("该用户已存在");
			}

			Long userId = accountService.register(registerDTO);

			return ResponseUtils.returnObjectSuccess(returnRegisterLoginVO(userId, header, null,registerDTO.getRegLogDTO().getIp()));
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("注册未知异常", e);
			return ResponseUtils.returnException(e);
		} finally {
			lockManager.unlock(Constants.BIND_PHONE, registerDTO.getUserPhone());
		}

	}

	@Override
	public Response<Boolean> agentResiter(AgentRegisterDTO registerDTO) {
		try {
			checkAgentRegisterDTO(registerDTO);
			// 默认不是马甲
			if (registerDTO.getIsVest() == null) {
				registerDTO.setIsVest(10);
			}
			boolean flag = accountService.checkUserByPhone(registerDTO.getUserPhone(), registerDTO.getAppId());
			if (flag) {
				throw QuanhuException.busiError("该用户已存在");
			}
			accountService.agentRegister(registerDTO);
			return ResponseUtils.returnObjectSuccess(true);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("代理注册未知异常", e);
			return ResponseUtils.returnException(e);
		}
	}

	/**
	 * 手机号密码登录
	 * 
	 * @param loginDTO
	 * @return
	 * @Description
	 */
	@Override
	public Response<RegisterLoginVO> login(LoginDTO loginDTO, RequestHeader header) {
		try {
			logger.info("user login request: {}", GsonUtils.parseJson(loginDTO));
			checkLoginDTO(loginDTO, LoginType.PHONE);
			checkHeader(header);
			loginDTO.setDeviceId(header.getDevId());
			Long userId = accountService.login(loginDTO, header.getAppId());

			// 判断用户状态
			if (ResponseUtils.getResponseData(checkUserDisable(userId))) {
				throw new QuanhuException(ExceptionEnum.USER_FREEZE);
			}

			return ResponseUtils.returnObjectSuccess(returnRegisterLoginVO(userId, header, null,loginDTO.getIp()));
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("手机号密码登录未知异常", e);
			return ResponseUtils.returnException(e);
		}
	}

	/**
	 * 手机短信登录
	 * 
	 * @param loginDTO
	 * @return
	 * @Description
	 */
	@Override
	public Response<RegisterLoginVO> loginByVerifyCode(RegisterDTO registerDTO, RequestHeader header) {
		try {
			logger.info("loginByVerifyCode request, registerDTO: {}, header: {}", GsonUtils.parseJson(registerDTO),
					GsonUtils.parseJson(header));
			checkRegisterDTO(registerDTO, RegType.PHONE);
			checkHeader(header);
			registerDTO.setDeviceId(header.getDevId());

			Long userId = accountService.loginByVerifyCode(registerDTO, header.getAppId());

			// 判断用户状态
			if (ResponseUtils.getResponseData(checkUserDisable(userId))) {
				throw new QuanhuException(ExceptionEnum.USER_FREEZE);
			}
			return ResponseUtils.returnObjectSuccess(returnRegisterLoginVO(userId, header, null,registerDTO.getRegLogDTO().getIp()));
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("手机验证码登录未知异常", e);
			return ResponseUtils.returnException(e);
		}
	}

	/**
	 * (app)第三方登录
	 * 
	 * @param loginDTO
	 * @return
	 * @Description
	 */
	@Override
	public Response<RegisterLoginVO> loginThird(ThirdLoginDTO loginDTO, RequestHeader header) {
		try {
			checkThirdLoginDTO(loginDTO);
			checkHeader(header);
			ThirdUser thirdUser = getThirdUser(loginDTO, header.getAppId());

			UserThirdLogin login = thirdLoginService.selectByThirdId(thirdUser.getThirdId(), header.getAppId(),
					loginDTO.getType());
			Long userId = null;
			// 已存在账户直接登录
			if (login != null) {
				userId = login.getUserId();
				// 判断用户状态
				if (ResponseUtils.getResponseData(checkUserDisable(userId))) {
					throw new QuanhuException(ExceptionEnum.USER_FREEZE);
				}
			} else {
				userId = accountService.loginThird(loginDTO, thirdUser, userId);
			}
			RegisterLoginVO registerLoginVO = returnRegisterLoginVO(userId, header, null,loginDTO.getRegLogDTO().getIp());
			return ResponseUtils.returnObjectSuccess(registerLoginVO);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("loginThird error", e);
			return ResponseUtils.returnException(e);
		}
	}

	/**
	 * (app)第三方登录绑定手机号
	 * 
	 * @param thirdId
	 *            第三方唯一id,由第三方登录接口返回
	 * @param phone
	 *            手机号
	 * @param verifyCode
	 *            验证码
	 * @return
	 * @Description
	 */
	@Override
	public Response<RegisterLoginVO> loginThirdBindPhone(ThirdLoginDTO loginDTO, RequestHeader header) {
		try {
			checkThirdLoginDTO(loginDTO);
			checkHeader(header);
			ThirdUser thirdUser = getThirdUser(loginDTO, header.getAppId());
			UserThirdLogin login = thirdLoginService.selectByThirdId(thirdUser.getThirdId(), header.getAppId(),
					loginDTO.getType());
			// 已存在账户直接登录
			if (login != null) {
				throw QuanhuException.busiError("该用户已存在");
			} else {
				if (!smsManager.checkVerifyCode(loginDTO.getPhone(), loginDTO.getVerifyCode(),
						SmsType.CODE_CHANGE_PHONE, header.getAppId())) {
					throw new QuanhuException(ExceptionEnum.SMS_VERIFY_CODE_ERROR);
				}
				// 手机号加锁
				lockManager.lock(Constants.BIND_PHONE, loginDTO.getPhone());

				boolean accountFlag = accountService.checkUserByPhone(loginDTO.getPhone(), header.getAppId());
				if (accountFlag) {
					throw QuanhuException.busiError("该手机号已存在");
				} else {
					Long userId = accountService.loginThirdBindPhone(loginDTO, thirdUser);
					return ResponseUtils.returnObjectSuccess(returnRegisterLoginVO(userId, header, null,loginDTO.getRegLogDTO().getIp()));
				}
			}
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("第三方登录绑定手机号未知异常", e);
			return ResponseUtils.returnException(e);
		} finally {
			lockManager.unlock(Constants.BIND_PHONE, loginDTO.getPhone());
		}
	}

	/**
	 * (web)第三方登录
	 * 
	 * @param loginType
	 *            类型 weibo:微博 weixin:微信
	 * @param returnUrl
	 *            web端登录成功返回地址
	 * @return
	 * @Description
	 */
	@Override
	public Response<String> webLoginThird(String loginType, String returnUrl) {
		try {
			if (StringUtils.isEmpty(loginType) || StringUtils.isEmpty(returnUrl)) {
				throw QuanhuException.busiError("longin returnUrl不能为空");
			}
			if (!RegType.SINA.getText().equals(loginType) && !RegType.WEIXIN.getText().equals(loginType)) {
				throw QuanhuException.busiError("loginType不支持");
			}
			return ResponseUtils.returnObjectSuccess(accountService.webLoginThird(loginType, returnUrl));
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("web第三方登未知异常", e);
			return ResponseUtils.returnException(e);
		}
	}

	/**
	 * (web)第三方登录回调
	 * 
	 * @return
	 * @throws IOException
	 * @Description 第三方回调成功后跳转到web端登录时传的地址
	 */
	@Override
	public Response<String> webThirdLoginNotify(String code, String state, String appId) {
		/*
		 * String code = request.getParameter("code"); String state =
		 * request.getParameter("state");
		 * response.setContentType("text/html;charset=UTF-8");
		 * response.setCharacterEncoding("utf-8"); if (StringUtils.isEmpty(code)
		 * || StringUtils.isEmpty(state)) { response.getWriter().write("授权失败");
		 * response.getWriter().flush(); return; }
		 */
		/*
		 * try { String[] stateArray = state.split("_"); if (stateArray.length <
		 * CHAR_51) { throw new BaseException("授权失败"); } String loginType =
		 * stateArray[1]; ThirdUser thirdUser = getThirdUser(code, state); Long
		 * userId = null; // 查询第三方账户 CustThirdLogin login =
		 * thirdLoginService.selectByThirdId(thirdUser.getThirdId());
		 * 
		 * // 已存在账户直接登录 if (login != null) { userId = login.getCustId(); //
		 * 判断用户状态 violationService.checkUserStatus(userId); } else { userId =
		 * accountService.webThirdLoginNotify(thirdUser, loginType); } if
		 * (StringUtils.isEmpty(userId)) { response.getWriter().write("登录失败");
		 * response.getWriter().flush(); return; }
		 * 
		 * String token = authService.getToken(userId, false, TokenType.PC); //
		 * 查询用户基础信息 CustSimpleVO user = userService.getCustSimple(userId);
		 * String returnUrl = returnUrl(user, token, state);
		 * response.sendRedirect(returnUrl);
		 * 
		 * return; } catch (BaseException e) { logger.error(e.getMessage(), e);
		 * response.getWriter().write("登录失败"); response.getWriter().flush();
		 * return; } catch (Exception e) { response.getWriter().write("登录失败");
		 * response.getWriter().flush(); return; }
		 */
		return ResponseUtils.returnSuccess();
	}

	@Override
	public Response<String> wxOauthLogin(WebThirdLoginDTO loginDTO) {
		try {
			checkWebThirdLoginDTO(loginDTO);
			return ResponseUtils.returnObjectSuccess(accountService.wxOauthLogin(loginDTO));
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("微信授权登录未知异常", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<String> wxOauthLoginNotify(WebThirdLoginDTO loginDTO) {
		Long userId = null;
		ActivityTempUser tempUser = null;
		UserIdentity identity = UserIdentity.ACTIVITY_VIEW;
		RequestHeader header = new RequestHeader();
		header.setAppId(loginDTO.getAppId());
		header.setDevType(DevType.WAP.getType());
		try {
			checkWebThirdLoginDTONotify(loginDTO);
			
			ThirdUser thirdUser = getThirdUser(loginDTO.getCode(), loginDTO.getState(), loginDTO.getAppId());
			UserThirdLogin thirdLogin = thirdLoginService.selectByThirdId(thirdUser.getThirdId(), loginDTO.getAppId(),
					RegType.WEIXIN.getType());
						
			if (thirdLogin != null) {
				userId = thirdLogin.getUserId();
				identity = UserIdentity.NORMAL;
				// 判断用户状态
				if (ResponseUtils.getResponseData(checkUserDisable(userId))) {
					throw new QuanhuException(ExceptionEnum.USER_FREEZE);
				}
			} else {
				tempUser = tempUserService.get(null, thirdUser.getThirdId());
				if (tempUser != null) {
					userId = tempUser.getKid();
				} else {
					tempUser = new ActivityTempUser(thirdUser.getThirdId(), thirdUser.getNickName(),
							thirdUser.getHeadImg(), (byte) RegType.WEIXIN.getType(), UserUtils.getActivityChannelCode(loginDTO.getState()),
							loginDTO.getAppId(), null);
					userId = tempUserService.save(tempUser);
					tempUser.setKid(userId);
				}
			}
			RegisterLoginVO loginVO = returnRegisterLoginVO(userId, header, tempUser,null);

			return ResponseUtils.returnObjectSuccess(returnUrl(loginVO, loginDTO.getState(),identity));
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("微信授权登录回调未知异常", e);
			return ResponseUtils.returnException(e);
		}
	}

	/**
	 * 获取登录方式
	 * 
	 * @param req
	 * @return
	 * @Description 根据header信息获取登录方式
	 */
	@Override
	public Response<List<LoginMethodVO>> getLoginMethod(Long userId) {
		try {
			if (userId == null) {
				throw QuanhuException.busiError("userId不能为空");
			}
			return ResponseUtils.returnListSuccess(accountService.getLoginMethod(userId));
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("获取登录方式未知异常", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<Map<String, Date>> getLastLoginTime(List<String> userIds) {
		try {
			if (CollectionUtils.isEmpty(userIds)) {
				throw QuanhuException.busiError("userIds不能为空");
			}
			return ResponseUtils.returnObjectSuccess(accountService.getLastLoginTime(userIds));
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("查询最后登录时间未知异常", e);
			return ResponseUtils.returnException(e);
		}
	}

	/**
	 * 绑定手机号
	 * 
	 * @param request
	 * @param phone
	 * @param password
	 *            登录密码
	 * @param verifyCode
	 *            验证码
	 * @return
	 * @Description 根据header信息绑定用户手机号码,也可以修改密码
	 */
	@Override
	public Response<Boolean> bindPhone(BindPhoneDTO phoneDTO) {
		try {
			checkBindPhoneDTO(phoneDTO);
			if (!smsManager.checkVerifyCode(phoneDTO.getPhone(), phoneDTO.getVerifyCode(), SmsType.CODE_CHANGE_PHONE,
					phoneDTO.getAppId())) {
				throw QuanhuException.busiError("验证码错误");
			}
			// 手机号加锁
			lockManager.lock(Constants.BIND_PHONE, phoneDTO.getPhone());

			boolean accountFlag = accountService.checkUserByPhone(phoneDTO.getPhone(), phoneDTO.getAppId());
			if (accountFlag) {
				throw QuanhuException.busiError("手机号已存在");
			}
			accountService.bindPhone(phoneDTO.getUserId(), phoneDTO.getPhone(), null);
			return ResponseUtils.returnObjectSuccess(true);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("绑定手机号未知异常", e);
			return ResponseUtils.returnException(e);
		} finally {
			lockManager.unlock(Constants.BIND_PHONE, phoneDTO.getPhone());
		}
	}

	@Override
	public Response<Boolean> activityCheckPhone(BindPhoneDTO phoneDTO) {
		try {
			checkBindPhoneDTO(phoneDTO);
			if (!smsManager.checkVerifyCode(phoneDTO.getPhone(), phoneDTO.getVerifyCode(), SmsType.ACTIVITY_BIND_PHONE,
					phoneDTO.getAppId())) {
				throw new QuanhuException(ExceptionEnum.SMS_VERIFY_CODE_ERROR);
			}
			// 手机号加锁
			lockManager.lock(Constants.BIND_PHONE, phoneDTO.getPhone());
			boolean accountFlag = accountService.checkUserByPhone(phoneDTO.getPhone(), phoneDTO.getAppId());
			// 用户不存在就根据参与者信息创建正常用户
			if (!accountFlag) {
				accountService.mergeActivityUser(phoneDTO.getUserId(), phoneDTO.getPhone());
			}
			return ResponseUtils.returnObjectSuccess(true);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("活动检查手机号未知异常", e);
			return ResponseUtils.returnException(e);
		} finally {
			lockManager.unlock(Constants.BIND_PHONE, phoneDTO.getPhone());
		}
	}

	/**
	 * 绑定 第三方账户
	 * 
	 * @param accessToken
	 * @param openId
	 * @param type
	 *            1，微信 2，微博 3，qq
	 * @return 需要在header获取用户id
	 */
	@Override
	public Response<Boolean> bindThird(BindThirdDTO thirdDTO) {
		try {
			checkBindThirdDTO(thirdDTO);

			ThirdLoginDTO loginDTO = new ThirdLoginDTO();
			loginDTO.setAccessToken(thirdDTO.getAccessToken());
			loginDTO.setOpenId(thirdDTO.getOpenId());
			loginDTO.setType(thirdDTO.getThirdType());
			ThirdUser thirdUser = getThirdUser(loginDTO, thirdDTO.getAppId());
			UserThirdLogin thirdLogin = thirdLoginService.selectByThirdId(thirdUser.getThirdId(), thirdDTO.getAppId(),
					thirdDTO.getThirdType());
			if (thirdLogin != null) {
				throw QuanhuException.busiError("第三方账户已存在");
			}
			accountService.bindThird(thirdDTO.getUserId(), thirdUser, thirdDTO.getThirdType(), thirdDTO.getAppId());
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("绑定第三方未知异常", e);
			return ResponseUtils.returnException(e);
		}
		return ResponseUtils.returnObjectSuccess(true);
	}

	/**
	 * 解绑 第三方账户
	 * 
	 * @param request
	 * @param thirdId
	 * @param type
	 *            11，微信 12，微博 13，qq
	 * @return 需要在header获取用户id
	 */
	@Override
	public Response<Boolean> unbindThird(UnBindThirdDTO thirdDTO) {
		try {
			checkUnBindThirdDTO(thirdDTO);

			accountService.unbindThird(thirdDTO.getUserId(), thirdDTO.getThirdId(), thirdDTO.getType(),
					thirdDTO.getAppId());
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("绑定第三方未知异常", e);
			return ResponseUtils.returnException(e);
		}
		return ResponseUtils.returnObjectSuccess(true);
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
	@Override
	public Response<Boolean> editPassword(Long userId, String oldPassword, String newPassword) {
		try {
			if (userId == null) {
				throw QuanhuException.busiError("please check paramter: userId ");
			}
			if (StringUtils.isBlank(oldPassword)) {
				throw QuanhuException.busiError("please check paramter: oldPassword ");
			}
			if (StringUtils.isBlank(newPassword)) {
				throw QuanhuException.busiError("please check paramter: newPassword");
			}
			accountService.editPassword(userId, newPassword, oldPassword);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("editPassword未知异常", e);
			return ResponseUtils.returnException(e);
		}
		return ResponseUtils.returnObjectSuccess(true);
	}

	/**
	 * 手机短信重置密码
	 * 
	 * @param phone
	 * @param password
	 * @param verifyCode
	 *            手机验证码
	 * @return
	 * @Description 需要在header获取用户id
	 */
	@Override
	public Response<Boolean> forgotPassword(ForgotPasswordDTO passwordDTO) {
		try {
			checkForgotPasswordDTO(passwordDTO);

			accountService.forgotPasswordByPhone(passwordDTO.getPhone(), passwordDTO.getPassword(),
					passwordDTO.getVerifyCode(), passwordDTO.getAppId());
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("forgotPassword未知异常", e);
			return ResponseUtils.returnException(e);
		}
		return ResponseUtils.returnObjectSuccess(true);
	}

	@Override
	public Response<Boolean> checkUserDisTalk(Long userId) {
		try {
			if (userId == null) {
				throw QuanhuException.busiError("please check paramter: userId ");
			}
			UserBaseInfo baseInfo = userService.getUser(userId);
			if (baseInfo == null) {
				throw QuanhuException.busiError("参数为空");
			}
			if (ResponseUtils.getResponseData(checkUserDistory(userId))) {
				return ResponseUtils.returnObjectSuccess(true);
			}
			if (baseInfo.getBanPostTime().getTime() > System.currentTimeMillis()) {
				return ResponseUtils.returnObjectSuccess(true);
			} else {
				return ResponseUtils.returnObjectSuccess(false);
			}
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("forgotPassword未知异常", e);
			return ResponseUtils.returnException(e);
		}
	}

	/**
	 * 检查用户是否冻结
	 * 
	 * @param userId
	 * @return
	 */
	public Response<Boolean> checkUserDisable(Long userId) {
		try {
			if (userId == null) {
				throw QuanhuException.busiError("please check paramter: userId ");
			}
			UserBaseInfo baseInfo = userService.getUser(userId);
			if (baseInfo == null) {
				throw QuanhuException.busiError("用户不存在");
			}
			if (ResponseUtils.getResponseData(checkUserDistory(userId))) {
				return ResponseUtils.returnObjectSuccess(true);
			}
			if (baseInfo.getUserStatus().intValue() == UserAccountStatus.FREEZE.getStatus()) {
				return ResponseUtils.returnObjectSuccess(true);
			} else {
				return ResponseUtils.returnObjectSuccess(false);
			}
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("checkUserDisable未知异常", e);
			return ResponseUtils.returnException(e);
		}
	}

	/**
	 * 检查用户是否注销
	 * 
	 * @param userId
	 * @return
	 */
	public Response<Boolean> checkUserDistory(Long userId) {
		try {
			if (userId == null) {
				throw QuanhuException.busiError("please check paramter: userId ");
			}
			UserBaseInfo baseInfo = userService.getUser(userId);
			if (baseInfo == null) {
				throw QuanhuException.busiError("参数为空");
			}
			if (baseInfo.getUserStatus().intValue() == UserAccountStatus.DISTORY.getStatus()) {
				return ResponseUtils.returnObjectSuccess(true);
			} else {
				return ResponseUtils.returnObjectSuccess(false);
			}
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("forgotPassword未知异常", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<SmsVerifyCodeVO> sendVerifyCode(SmsVerifyCodeDTO codeDTO) {
		try {
			logger.info("sendVerifyCode request codeDTO: {}", GsonUtils.parseJson(codeDTO));
			checkCodeDTO(codeDTO);
			return ResponseUtils.returnObjectSuccess(smsService.sendVerifyCode(codeDTO));
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("短信验证码发送异常", e);
			return ResponseUtils.returnException(e);
		}
	}
	
	@Override
	public Response<List<Map<String, String>>> getUserAccountByPhone(Set<String> phones, String appId) {
		try {
			if(CollectionUtils.isEmpty(phones)){
				throw QuanhuException.busiError("手机号为空");
			}
			if(StringUtils.isBlank(appId)){
				throw QuanhuException.busiError("appId为空");
			}
			return ResponseUtils.returnObjectSuccess(accountService.getUserAccountByPhone(phones, appId));
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("[getUserAccountByPhone]", e);
			return ResponseUtils.returnException(e);
		}
	}
	
	/*	*//**
			 * 邀请码补录
			 * 
			 * @param request
			 * @param regInviterCode
			 * @return
			 *//*
			 * @RequestMapping(value = "/complementRegCode", method = {
			 * RequestMethod.POST, RequestMethod.OPTIONS })
			 * 
			 * @ResponseBody public ReturnCode
			 * complementRegCode(HttpServletRequest request, String
			 * regInviterCode) { Long userId =
			 * TokenUtils.getCustIdByToken(request); if
			 * (StringUtils.isBlank(userId)) { return
			 * ReturnModel.returnException(ReturnCode.ERROR,
			 * "please check paramter: userId"); } if
			 * (StringUtils.isBlank(regInviterCode)) { return
			 * ReturnModel.returnException(ReturnCode.ERROR,
			 * "please check paramter: regInviterCode"); } try {
			 * custOperateService.inviterCodeComplement(userId, regInviterCode);
			 * } catch (BaseException e) { return
			 * ReturnModel.returnException(e.getCode(), e.getMsg()); } catch
			 * (DatasOptException e) { return ReturnModel.returnException(); }
			 * catch (Exception e) { logger.error("complementRegCode error", e);
			 * return ReturnModel.returnException(); } return
			 * ReturnModel.returnSuccess(ReturnCode.SUCCESSMSG); }
			 */

	private void checkHeader(RequestHeader header) {
		if (header == null) {
			throw QuanhuException.busiError("传参不合法");
		}
		if (StringUtils.isEmpty(header.getAppId())) {
			throw QuanhuException.busiError("应用id为空");
		}
		if (StringUtils.isBlank(header.getDevType())
				|| DevType.getEnumByType(header.getDevType(), header.getUserAgent()) == null) {
			throw QuanhuException.busiError("设备类型不能为空");
		}
	}

	private void checkRegisterDTO(RegisterDTO registerDTO, RegType type) {
		if (registerDTO == null) {
			throw QuanhuException.busiError("传参不合法");
		}
		if (StringUtils.isEmpty(registerDTO.getUserPhone()) && type == RegType.PHONE) {
			throw QuanhuException.busiError("手机号为空");
		}
		if (StringUtils.isBlank(registerDTO.getVeriCode())) {
			throw QuanhuException.busiError("验证码不能为空");
		}
		if(registerDTO.getRegLogDTO() == null){
			throw QuanhuException.busiError("注册日志信息为空");
		}
	}

	private void checkAgentRegisterDTO(AgentRegisterDTO registerDTO) {
		if (registerDTO == null) {
			throw QuanhuException.busiError("传参不合法");
		}
		if (StringUtils.isEmpty(registerDTO.getUserPhone())) {
			throw QuanhuException.busiError("手机号为空");
		}
		if (StringUtils.isBlank(registerDTO.getAppId())) {
			throw QuanhuException.busiError("应用id不能为空");
		}
	}

	private void checkBindPhoneDTO(BindPhoneDTO phoneDTO) {
		if (phoneDTO == null) {
			throw QuanhuException.busiError("传参不合法");
		}
		if (StringUtils.isEmpty(phoneDTO.getPhone())) {
			throw QuanhuException.busiError("手机号为空");
		}
		if (phoneDTO.getUserId() == null) {
			throw QuanhuException.busiError("用户id为空");
		}
		if (StringUtils.isBlank(phoneDTO.getAppId())) {
			throw QuanhuException.busiError("应用id为空");
		}
		if (StringUtils.isBlank(phoneDTO.getVerifyCode())) {
			throw QuanhuException.busiError("验证码为空");
		}
	}

	private void checkBindThirdDTO(BindThirdDTO thirdDTO) {
		if (thirdDTO == null) {
			throw QuanhuException.busiError("传参不合法");
		}
		if (StringUtils.isEmpty(thirdDTO.getOpenId())) {
			throw QuanhuException.busiError("openId为空");
		}
		if (thirdDTO.getUserId() == null) {
			throw QuanhuException.busiError("用户id为空");
		}
		if (StringUtils.isBlank(thirdDTO.getAppId())) {
			throw QuanhuException.busiError("应用id为空");
		}
		if (thirdDTO.getThirdType() == null) {
			throw QuanhuException.busiError("第三方类型为空");
		}
		if (StringUtils.isBlank(thirdDTO.getAccessToken())) {
			throw QuanhuException.busiError("第三方令牌为空");
		}
	}

	private void checkUnBindThirdDTO(UnBindThirdDTO thirdDTO) {
		if (thirdDTO.getUserId() == null) {
			throw QuanhuException.busiError("用户id为空");
		}
		if (StringUtils.isBlank(thirdDTO.getThirdId())) {
			throw QuanhuException.busiError("第三方id为空");
		}
		if (StringUtils.isBlank(thirdDTO.getAppId())) {
			throw QuanhuException.busiError("应用id为空");
		}
		if (thirdDTO.getType() == null) {
			throw QuanhuException.busiError("第三方类型为空");
		}
	}

	private void checkLoginDTO(LoginDTO loginDTO, LoginType loginType) {
		if (loginDTO == null) {
			throw QuanhuException.busiError("传参不合法");
		}
		if (StringUtils.isEmpty(loginDTO.getPhone()) && loginType == LoginType.PHONE) {
			throw QuanhuException.busiError("手机号为空");
		}
		if (StringUtils.isBlank(loginDTO.getPassword()) && loginType != LoginType.VERIFYCODE) {
			throw QuanhuException.busiError("密码为空");
		}
		if (StringUtils.isBlank(loginDTO.getVerifyCode()) && loginType == LoginType.VERIFYCODE) {
			throw QuanhuException.busiError("验证码为空");
		}
	}

	private void checkForgotPasswordDTO(ForgotPasswordDTO passwordDTO) {
		if (passwordDTO == null) {
			throw QuanhuException.busiError("传参不合法");
		}
		if (StringUtils.isEmpty(passwordDTO.getPhone())) {
			throw QuanhuException.busiError("手机号为空");
		}
		if (StringUtils.isBlank(passwordDTO.getPassword())) {
			throw QuanhuException.busiError("密码为空");
		}
		if (StringUtils.isBlank(passwordDTO.getVerifyCode())) {
			throw QuanhuException.busiError("验证码为空");
		}
		if (StringUtils.isBlank(passwordDTO.getAppId())) {
			throw QuanhuException.busiError("应用id为空");
		}
	}

	private void checkThirdLoginDTO(ThirdLoginDTO loginDTO) {
		if (loginDTO == null || StringUtils.isBlank(loginDTO.getAccessToken())) {
			throw QuanhuException.busiError("accessToken为空");
		}
		if (StringUtils.isBlank(loginDTO.getOpenId())) {
			throw QuanhuException.busiError("openId为空");
		}
		if (loginDTO.getType() < RegType.WEIXIN.getType() || loginDTO.getType() > RegType.PHONE.getType()) {
			throw QuanhuException.busiError("type不合法");
		}
		if (StringUtils.isNotBlank(loginDTO.getPhone()) && StringUtils.isBlank(loginDTO.getVerifyCode())) {
			throw QuanhuException.busiError("验证码为空");
		}
		if(loginDTO.getRegLogDTO() == null){
			throw QuanhuException.busiError("注册日志信息为空");
		}
		if (StringUtils.isBlank(loginDTO.getPhone()) && StringUtils.isNotBlank(loginDTO.getVerifyCode())) {
			throw QuanhuException.busiError("手机号为空");
		}
	}

	private void checkWebThirdLoginDTO(WebThirdLoginDTO loginDTO) {
		if (loginDTO == null || StringUtils.isBlank(loginDTO.getAppId())) {
			throw QuanhuException.busiError("appId为空");
		}
		if (StringUtils.isBlank(loginDTO.getLoginType()) || RegType.getEnumByText(loginDTO.getLoginType()) == null) {
			throw QuanhuException.busiError("type不合法");
		}
		if (StringUtils.isBlank(loginDTO.getReturnUrl())) {
			throw QuanhuException.busiError("returnUrl为空");
		}
		if (!StringUtils.startsWith(loginDTO.getReturnUrl(),"http://") && !StringUtils.startsWith(loginDTO.getReturnUrl(),"https://")) {
			throw QuanhuException.busiError("returnUrl不合法");
		}
	}

	private void checkWebThirdLoginDTONotify(WebThirdLoginDTO loginDTO) {
		if (loginDTO == null || StringUtils.isBlank(loginDTO.getAppId())) {
			throw QuanhuException.busiError("appId为空");
		}
		if (StringUtils.isBlank(loginDTO.getCode())) {
			throw QuanhuException.busiError("code为空");
		}
		if (StringUtils.isBlank(loginDTO.getState())) {
			throw QuanhuException.busiError("state为空");
		}
	}

	/**
	 * 获取第三方用户信息
	 * 
	 * @param loginDTO
	 * @return
	 */
	private ThirdUser getThirdUser(ThirdLoginDTO loginDTO, String appId) {
		ThirdUser thirdUser = null;

		try {// 微博
			if (loginDTO.getType() == RegType.SINA.getType()) {
				thirdUser = OatuhWeibo.getUser(loginDTO.getOpenId(), loginDTO.getAccessToken());
			} // qq
			else if (loginDTO.getType() == RegType.QQ.getType()) {
				thirdUser = OatuhQq.getUser(ThirdConstants.QQ_APP_ID, loginDTO.getOpenId(), loginDTO.getAccessToken());
			} // 微信
			else {
				thirdUser = OatuhWeixin.getUser(loginDTO.getOpenId(), loginDTO.getAccessToken());
			}
		} catch (Exception e) {
			logger.error("[getThirdUser]", e);
			throw QuanhuException.busiError("认证失败");
		}
		if (thirdUser == null || StringUtils.isBlank(thirdUser.getThirdId())) {
			throw QuanhuException.busiError("认证失败，thirdUser or thirdId is null !");
		}
		logger.info("getThirdUser result: {}", GsonUtils.parseJson(thirdUser));

		return thirdUser;
	}

	/**
	 * web登录获取第三方用户信息
	 * 
	 * @param code
	 * @param state
	 * @return
	 */
	private ThirdUser getThirdUser(String code, String state, String appId) {
		String loginType = UserUtils.getThirdLoginType(state);
		String returnUrl = UserUtils.getThirdLoginReturnUrl(state);
		if (StringUtils.isBlank(loginType) || StringUtils.isBlank(returnUrl)) {
			throw QuanhuException.busiError("第三方登录回调失败");
		}
		// 得到第三方登录回调的host
		String apiHost = UserUtils.getReturnApiHost(returnUrl);
		ThirdLoginConfigVO configVO = getThirdLoginConfig(appId);
		ThirdLoginDTO thirdLoginDTO = new ThirdLoginDTO();
		ThirdUser thirdUser = null; // 微博登录
		if (RegType.SINA.getText().equals(loginType)) {
			WeiboToken weiboToken = null;
			try {
				weiboToken = OatuhWeibo.getToken(configVO.getWeiboAppKey(), configVO.getWeiboAppSecret(), code, apiHost,
						configVO.getNotifyUrl());
			} catch (Exception e) {
				throw QuanhuException.busiError("认证失败");
			}
			thirdLoginDTO.setAccessToken(weiboToken.getAccess_token());
			thirdLoginDTO.setUserChannel("web_sina_login");
			thirdLoginDTO.setType(RegType.SINA.getType());
			thirdLoginDTO.setOpenId(weiboToken.getUid());
			thirdUser = getThirdUser(thirdLoginDTO, appId);
		} else if (RegType.WEIXIN.getText().equals(loginType)) { // 微信登录
			WxToken token = null;
			try {
				token = OatuhWeixin.getToken(configVO.getWxAppKey(), configVO.getWxAppSecret(), code);
			} catch (Exception e) {
				throw QuanhuException.busiError("认证失败");
			}
			thirdLoginDTO.setAccessToken(token.getAccess_token());
			thirdLoginDTO.setUserChannel("web_weixin_login");
			thirdLoginDTO.setOpenId(token.getOpenid());
			thirdLoginDTO.setType(RegType.WEIXIN.getType());
			thirdUser = getThirdUser(thirdLoginDTO, appId);
		} else {
			// 微信授权
			WxToken token = null;
			try {
				token = OatuhWeixin.getToken(configVO.getWxOauthAppKey(), configVO.getWxOauthAppSecret(), code);
			} catch (Exception e) {
				throw QuanhuException.busiError("认证失败");
			}
			thirdLoginDTO.setAccessToken(token.getAccess_token());
			thirdLoginDTO.setUserChannel("wap_weixin_oauth");
			thirdLoginDTO.setOpenId(token.getOpenid());
			thirdLoginDTO.setType(RegType.WEIXIN_OAUTH.getType());
			thirdUser = getThirdUser(thirdLoginDTO, appId);
		}
		return thirdUser;
	}

	/**
	 * 获取第三方应用配置
	 * 
	 * @param appId
	 * @return
	 */
	private ThirdLoginConfigVO getThirdLoginConfig(String appId) {
		ThirdLoginConfigVO configVO = new ThirdLoginConfigVO();
		return configVO;
	}

	/**
	 * 根据用户id和token类型得到登录返回信息
	 * 
	 * @param userId
	 * @param tokenType
	 * @return
	 */
	private RegisterLoginVO returnRegisterLoginVO(Long userId, RequestHeader header, ActivityTempUser tempUser,String ip) {
		DevType devType = DevType.getEnumByType(header.getDevType(), header.getUserAgent());
		// web登陆，不刷新token
		boolean refreshToken = (devType != DevType.ANDROID && devType != DevType.IOS) ? false : true;
		// 查询用户信息
		UserLoginSimpleVO user = userService.getUserLoginSimpleVO(userId);
		// 兼容活动参与者
		if (tempUser != null) {
			user = new UserLoginSimpleVO();
			user.setUserNickName(tempUser.getNickName());
			user.setUserImg(tempUser.getHeadImg());
			user.setUserId(userId);
		}
		if (user == null) {
			throw QuanhuException.busiError("用户不存在");
		}
		AuthTokenDTO tokenDTO = new AuthTokenDTO(userId, devType, header.getAppId(), refreshToken);

		AuthTokenVO tokenVO = null;
		if (devType == DevType.ANDROID || devType == DevType.IOS) {
			AuthRefreshDTO refreshDTO = new AuthRefreshDTO();
			refreshDTO.setAppId(tokenDTO.getAppId());
			refreshDTO.setRefreshLogin(true);
			refreshDTO.setType(devType);
			refreshDTO.setUserId(userId);
			tokenVO = authService.getToken(refreshDTO);
		} else {
			tokenVO = authService.getToken(tokenDTO);
		}
		try {
			accountService.saveLoginLog(new UserLoginLog(userId, devType.getType(), header.getDevName(),
					header.getDevId(), header.getAppId(),ip,tokenVO.getToken(),tokenVO.getRefreshToken()));
		} catch (Exception e) {
			logger.error("登录日志保存异常", e);
		}

		RegisterLoginVO loginVO = new RegisterLoginVO(tokenVO, user);

		return loginVO;
	}

	/**
	 * 拼装web登录返回地址
	 * 
	 * @param base
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String returnUrl(RegisterLoginVO loginVO, String state,UserIdentity identity) {
		String backUrl = UserUtils.getThirdLoginReturnUrl(state);
		if (StringUtils.isBlank(backUrl)) {
			throw QuanhuException.busiError("第三方登录失败");
		}
		StringBuffer returnUrl = new StringBuffer();
		returnUrl.append(backUrl);
		if (backUrl.indexOf(CHAR_63) != -1 || backUrl.indexOf(CHAR_3F) != -1) {
			returnUrl.append("&token=");
		} else {
			returnUrl.append("?token=");
		}
		returnUrl.append(loginVO.getAuthInfo().getToken());
		returnUrl.append("&userId=").append(loginVO.getUser().getUserId());
		try {
			returnUrl.append("&userNickname=").append(StringUtils.isNotBlank(loginVO.getUser().getUserNickName())
					? URLEncoder.encode(loginVO.getUser().getUserNickName(), "utf-8") : "");
		} catch (UnsupportedEncodingException e) {
		}
		returnUrl.append("&userImg=").append(loginVO.getUser().getUserImg());
		returnUrl.append("&userIdentity=").append(identity.getStatus());
		return returnUrl.toString();
	}

	private void checkCodeDTO(SmsVerifyCodeDTO codeDTO) {
		if (codeDTO == null) {
			throw QuanhuException.busiError("传参不合法");
		}
		if (codeDTO.getUserId() == null) {
			if (StringUtils.isBlank(codeDTO.getPhone()) || !PhoneUtils.checkPhone(codeDTO.getPhone())) {
				throw QuanhuException.busiError("手机号不合法");
			}
		}
		if (StringUtils.isBlank(codeDTO.getCode())) {
			throw QuanhuException.busiError("功能码不能为空");
		}
		if (StringUtils.isBlank(codeDTO.getAppId())) {
			throw QuanhuException.busiError("应用id不能为空");
		}
	}



}
