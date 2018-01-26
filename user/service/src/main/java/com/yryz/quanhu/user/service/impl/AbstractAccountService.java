package com.yryz.quanhu.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.core.type.TypeReference;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.constant.IdConstants;
import com.yryz.common.exception.MysqlOptException;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.JsonUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.contants.Constants;
import com.yryz.quanhu.user.contants.RegType;
import com.yryz.quanhu.user.contants.SmsType;
import com.yryz.quanhu.user.dao.UserAccountDao;
import com.yryz.quanhu.user.dao.UserLoginLogDao;
import com.yryz.quanhu.user.dto.AgentRegisterDTO;
import com.yryz.quanhu.user.dto.LoginDTO;
import com.yryz.quanhu.user.dto.RegisterDTO;
import com.yryz.quanhu.user.dto.ThirdLoginDTO;
import com.yryz.quanhu.user.dto.UserRegLogDTO;
import com.yryz.quanhu.user.entity.UserAccount;
import com.yryz.quanhu.user.entity.UserBaseInfo;
import com.yryz.quanhu.user.entity.UserLoginLog;
import com.yryz.quanhu.user.entity.UserThirdLogin;
import com.yryz.quanhu.user.manager.SmsManager;
import com.yryz.quanhu.user.mq.UserSender;
import com.yryz.quanhu.user.service.AccountService;
import com.yryz.quanhu.user.service.OatuhWeibo;
import com.yryz.quanhu.user.service.OatuhWeixin;
import com.yryz.quanhu.user.service.UserService;
import com.yryz.quanhu.user.service.UserThirdLoginService;
import com.yryz.quanhu.user.utils.UserUtils;
import com.yryz.quanhu.user.vo.LoginMethodVO;
import com.yryz.quanhu.user.vo.ThirdLoginConfigVO;
import com.yryz.quanhu.user.vo.ThirdUser;

/**
 * @author danshiyu
 * @version 1.0
 * @data 2017/11/9 0009 45
 */
@Service
public class AbstractAccountService implements AccountService {
	private static final Logger logger = LoggerFactory.getLogger(AbstractAccountService.class);
	@Autowired
	private UserAccountDao mysqlDao;
	@Reference(lazy = true, check = false)
	private IdAPI idApi;
	@Autowired
	private UserLoginLogDao logDao;
	@Autowired
	protected UserThirdLoginService thirdLoginService;
	@Autowired
	private SmsManager smsService;
	@Autowired
	protected UserService userService;
	@Autowired
	private UserSender mqSender;

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Long register(RegisterDTO registerDTO) {
		return createUser(registerDTO);
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public String agentRegister(AgentRegisterDTO registerDTO) {
		UserAccount checkAccount = selectOne(null, registerDTO.getUserPhone(), registerDTO.getAppId(), null);
		if (checkAccount != null) {
			throw QuanhuException.busiError("该用户已存在");
		}
		String regChannel = Constants.ADMIN_REG_CHANNEL;
		if (registerDTO.getIsVest() == null || registerDTO.getIsVest() == 0) {
			regChannel = Constants.ADMIN_REG_VEST_CHANNEL;
		}
		UserRegLogDTO logDTO = new UserRegLogDTO(regChannel, regChannel, regChannel);
		// 在上层据手机号判断根用户是否已存在避免不必要的事务回滚
		Long userId = createUser(
				new RegisterDTO(regChannel, registerDTO.getUserPhone(), registerDTO.getUserPwd(), logDTO));
		return userId.toString();
	}

	@Override
	public Long login(LoginDTO loginDTO, String appId) {
		UserAccount account = selectOne(null, loginDTO.getPhone(), appId, null);
		if (account == null) {
			throw QuanhuException.busiError("该用户不存在");
		}
		if (!StringUtils.equals(account.getUserPwd(), loginDTO.getPassword())) {
			throw QuanhuException.busiError("登录密码错误");
		}
		if (StringUtils.isNotBlank(loginDTO.getDeviceId())) {
			// 更新设备号
			userService.updateUserInfo(new UserBaseInfo(account.getKid(), null, loginDTO.getDeviceId(), null));
			;
		}

		return account.getKid();
	}

	/**
	 * 手机验证码登录
	 */
	@Override
	public Long loginByVerifyCode(RegisterDTO registerDTO, String appId) {
		UserAccount account = selectOne(null, registerDTO.getUserPhone(), appId, null);
		// 用户不存在直接注册
		if (account == null) {
			if (!smsService.checkVerifyCode(registerDTO.getUserPhone(), registerDTO.getVeriCode(),
					SmsType.CODE_REGISTER, appId)) {
				throw QuanhuException.busiError("验证码错误");
			}
			return createUser(registerDTO);
		}
		if (!smsService.checkVerifyCode(registerDTO.getUserPhone(), registerDTO.getVeriCode(), SmsType.CODE_LOGIN,
				appId)) {
			throw QuanhuException.busiError("验证码错误");
		}
		// 更新设备号
		if (StringUtils.isNotBlank(registerDTO.getDeviceId())) {
			// 更新设备号
			userService.updateUserInfo(new UserBaseInfo(account.getKid(), null, registerDTO.getDeviceId(), null));
		}
		return account.getKid();
	}

	/**
	 * 第三方登录两种实现，分为非强绑和强绑两种模式
	 */
	@Override
	public Long loginThird(ThirdLoginDTO loginDTO, ThirdUser thirdUser, Long userId) {
		if (userId == null) {
			throw new QuanhuException(ExceptionEnum.NEED_PHONE);
		}
		// 兼容未绑定手机号的老用户
		UserAccount account = getUserAccountByUserId(userId);
		if (StringUtils.isBlank(account.getUserPhone())) {
			throw new QuanhuException(ExceptionEnum.NEED_PHONE);
		}
		// 更新设备号
		if (StringUtils.isNotBlank(loginDTO.getDeviceId())) {
			userService.updateUserInfo(new UserBaseInfo(userId, null, loginDTO.getDeviceId(), null));
		}
		return userId;
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Long loginThirdBindPhone(ThirdLoginDTO loginDTO, ThirdUser thirdUser) {
		RegisterDTO registerDTO = new RegisterDTO();
		registerDTO.setUserChannel(String.format("app_%s", RegType.getEnumByTye(loginDTO.getType()).getText()));
		registerDTO.setUserNickName(thirdUser.getNickName());
		registerDTO.setUserLocation(thirdUser.getLocation());
		registerDTO.setDeviceId(loginDTO.getDeviceId());
		registerDTO.setRegLogDTO(loginDTO.getRegLogDTO());
		registerDTO.setUserPhone(loginDTO.getPhone());
		Long userId = createUser(registerDTO);
		// 创建第三方账户
		thirdLoginService.insert(new UserThirdLogin(userId, thirdUser.getThirdId(), loginDTO.getType().byteValue(),
				thirdUser.getNickName(), loginDTO.getRegLogDTO().getAppId()));
		logger.info("[user_bindPhone]:userId:{},bindType:thirdLoginBindPhone,oldPhone:,newPhone:{}", userId,
				loginDTO.getPhone());
		return userId;
	}

	@Override
	public List<LoginMethodVO> getLoginMethod(Long userId) {
		List<UserThirdLogin> logins = thirdLoginService.selectByUserId(userId);
		if (CollectionUtils.isEmpty(logins)) {
			logins = new ArrayList<>();
		}
		UserAccount account = selectOne(userId, null, null, null);
		if (account == null) {
			throw QuanhuException.busiError("该用户不存在");
		}
		List<LoginMethodVO> list = JsonUtils.fromJson(JsonUtils.toFastJson(logins),
				new TypeReference<List<LoginMethodVO>>() {
				});

		if (StringUtils.isNotBlank(account.getUserPhone())) {
			boolean havePwd = StringUtils.isBlank(account.getUserPwd()) ? false : true;
			LoginMethodVO methodVO = new LoginMethodVO(account.getKid(), account.getUserPhone(),
					RegType.PHONE.getType(), havePwd);
			list.add(methodVO);
		}

		return list;
	}

	@Override
	public Map<String, Date> getLastLoginTime(List<String> userIds) {
		try {
			Map<String, Date> map = new HashMap<>(userIds.size());
			List<UserLoginLog> loginLogs = logDao.getLastLoginTime(userIds);
			if (CollectionUtils.isNotEmpty(loginLogs)) {
				for (int i = 0; i < loginLogs.size(); i++) {
					UserLoginLog custLoginLog = loginLogs.get(i);
					map.put(custLoginLog.getUserId().toString(), custLoginLog.getCreateDate());
				}
			}
			return map;
		} catch (Exception e) {
			logger.error("[getLastLoginTime]", e);
			throw new MysqlOptException("[getLastLoginTime]", e);
		}
	}

	@Override
	public String webLoginThird(String loginType, String returnUrl) {
		// 得到第三方登录回调的host
		String apiHost = UserUtils.getReturnApiHost(returnUrl);
		// 读取配置
		ThirdLoginConfigVO configVO = new ThirdLoginConfigVO();
		if (RegType.SINA.getText().equals(loginType)) {
			return OatuhWeibo.getAuthUrl(configVO.getWeiboAppKey(), returnUrl, apiHost, configVO.getNotifyUrl());
		}
		return OatuhWeixin.getQrUrl(configVO.getWxAppKey(), returnUrl, apiHost, configVO.getNotifyUrl());
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Long webThirdLoginNotify(ThirdUser thirdUser, String loginType) {
		RegisterDTO registerDTO = new RegisterDTO();
		registerDTO.setUserChannel(String.format("web_%s", loginType));
		registerDTO.setUserNickName(thirdUser.getNickName());
		registerDTO.setUserLocation(thirdUser.getLocation());
		Long userId = createUser(registerDTO);
		// 创建第三方账户
		thirdLoginService.insert(new UserThirdLogin(userId, thirdUser.getThirdId(),
				(byte) RegType.getEnumByText(loginType).getType(), thirdUser.getNickName(), "appId"));

		return userId;
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public void bindPhone(Long userId, String phone, String password) {
		UserAccount oldAccount = selectOne(userId, null, null, null);
		UserAccount account = new UserAccount(null, phone, password);
		account.setKid(userId);
		account.setCreateDate(new Date());
		update(account);

		// 同步手机号到用户基础信息表
		userService.updateUserInfo(new UserBaseInfo(account.getKid(), phone, null, null));
		logger.info("[user_bindPhone]:userId:{},bindType:bindPhone,oldPhone:{},newPhone:{}", userId,
				oldAccount.getUserPhone(), phone);
	}

	@Override
	public void bindThird(Long userId, ThirdUser thirdUser, Integer type, String appId) {
		UserThirdLogin thirdLogin = new UserThirdLogin();
		thirdLogin.setUserId(userId);
		thirdLogin.setLoginType(type.byteValue());
		thirdLogin.setNickName(thirdLogin.getNickName());
		thirdLogin.setThirdId(thirdUser.getThirdId());
		thirdLogin.setAppId(appId);
		thirdLoginService.insert(thirdLogin);
	}

	@Override
	public void unbindThird(Long userId, String thirdId, Integer type, String appId) {
		UserThirdLogin thirdLogin = thirdLoginService.selectByThirdId(thirdId, appId);
		if (thirdLogin == null) {
			throw QuanhuException.busiError(ExceptionEnum.BusiException.getCode(), "第三方账户不存在");
		}
		if (!thirdLogin.getUserId().equals(userId)) {
			throw QuanhuException.busiError(ExceptionEnum.BusiException.getCode(), "不是本人不能操作");
		}
		// 判断用户手机号是否都为空？
		UserAccount account = selectOne(thirdLogin.getUserId(), null, null, null);
		if (StringUtils.isBlank(account.getUserEmail()) && StringUtils.isBlank(account.getUserPhone())) {
			throw QuanhuException.busiError(ExceptionEnum.BusiException.getCode(), "主账号没有其他登录方式，不能解绑第三方");
		}
		thirdLoginService.delete(userId, thirdId);
	}

	@Override
	public void editPassword(Long userId, String newPassword, String oldPassword) {
		UserAccount account = selectOne(userId, null, null, oldPassword);
		if (account == null) {
			throw QuanhuException.busiError(ExceptionEnum.BusiException.getCode(), "旧密码不正确",
					ExceptionEnum.BusiException.getErrorMsg());
		}
		account.setDelFlag(null);
		account.setId(null);
		account.setCreateDate(null);
		account.setUserPhone(null);
		account.setUserEmail(null);
		account.setAppId(null);
		account.setUserPwd(newPassword);
		update(account);
	}

	/**
	 * 手机号重置密码
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	public void forgotPasswordByPhone(String phone, String password, String verifyCode, String appId) {
		if (!smsService.checkVerifyCode(phone, verifyCode, SmsType.CODE_FIND_PWD, appId)) {
			throw QuanhuException.busiError("验证码错误");
		}
		UserAccount account = selectOne(null, phone, null, null);
		if (account == null) {
			throw QuanhuException.busiError("该用户不存在");
		}
		account.setDelFlag(null);
		account.setId(null);
		account.setCreateDate(null);
		account.setUserPhone(null);
		account.setUserEmail(null);
		account.setAppId(null);
		account.setUserPwd(password);
		update(account);
	}

	/**
	 * 删除用户信息
	 * 
	 * @param userId
	 * @return
	 */
	@Transactional(rollbackFor = RuntimeException.class)
	public int delete(Long userId) {
		try {
			// 删除用户信息
			// 删除第三方账户
			// 清理违规记录
			return 0; // mysqlDao.delete(userId);
		} catch (Exception e) {
			logger.error("[accountDao.delete]", e);
			throw new MysqlOptException(e);
		}
	}

	@Override
	public boolean checkUserByPhone(String phone, String appId) {
		UserAccount account = selectOne(null, phone, appId, null);
		if (account == null) {
			return false;
		}
		return true;
	}

	/**
	 * 根据手机号,登录密码检查用户是否存在
	 * 
	 * @param phone
	 * @param password
	 * @return
	 */
	@Override
	public UserAccount checkUserByPhonePassword(String phone, String password, String appId) {
		return selectOne(null, phone, appId, password);
	}

	/**
	 * 创建主账户
	 * 
	 * @param record
	 * @return
	 */
	private int insert(UserAccount record) {
		if (record.getCreateDate() == null) {
			record.setCreateDate(new Date());
		}
		try {
			return mysqlDao.insert(record);
		} catch (Exception e) {
			logger.error("[accountDao.insert]", e);
			throw new MysqlOptException(e);
		}
	}

	/**
	 * 根据id、手机号、邮箱、登录密码查询账户
	 * 
	 * @param userId
	 * @param custPhone
	 * @param custEmail
	 * @param appId
	 * @return
	 */
	private UserAccount selectOne(Long userId, String custPhone, String appId, String custPwd) {
		try {
			return mysqlDao.selectOne(userId, custPhone, appId, custPwd);
		} catch (Exception e) {
			logger.error("[accountDao.selectOne]", e);
			throw new MysqlOptException(e);
		}
	}

	/**
	 * 更新用户账户信息
	 * 
	 * @param record
	 * @return
	 */
	private int update(UserAccount record) {
		try {
			return mysqlDao.update(record);
		} catch (Exception e) {
			logger.error("[accountDao.update]", e);
			throw new MysqlOptException(e);
		}
	}

	/**
	 * 保存登录日志
	 * 
	 * @param loginLog
	 */
	public void saveLoginLog(UserLoginLog loginLog) {
		loginLog.setCreateDate(new Date());
		loginLog.setLoginX(0l);
		loginLog.setLoginY(0l);
		loginLog.setKid(ResponseUtils.getResponseData(idApi.getKid(IdConstants.QUNAHU_LOGIN_LOG)));
		try {
			logDao.insert(loginLog);
		} catch (Exception e) {
			logger.error("[loginlog.save]", e);
			throw new MysqlOptException(e);
		}
	}

	/**
	 * 创建用户账户相关信息
	 * 
	 * @author
	 * @date 2017年11月10日
	 * @param phone
	 * @param custPwd
	 */
	@Transactional(rollbackFor = RuntimeException.class)
	protected Long createUser(RegisterDTO registerDTO) {
		Long userId = NumberUtils.toLong(ResponseUtils.getResponseData(idApi.getUserId()));
		UserAccount account = new UserAccount(null, registerDTO.getUserPhone(), registerDTO.getUserPwd());
		account.setKid(userId);
		account.setAppId(registerDTO.getRegLogDTO().getAppId());
		account.setCreateDate(new Date());
		insert(account);
		// 创建用户基础信息
		userService
				.createUser(new UserBaseInfo(userId, registerDTO.getRegLogDTO().getAppId(), registerDTO.getUserPhone(),
						registerDTO.getUserLocation(), registerDTO.getDeviceId(), registerDTO.getCityCode()));
		registerDTO.getRegLogDTO().setUserId(userId);

		// 异步处理
		// 创建运营信息
		// 发送注册消息 加积分
		mqSender.userCreate(registerDTO);

		return userId;
	}

	@Override
	public UserAccount getUserAccountByUserId(Long userId) {
		return selectOne(userId, null, null, null);
	}

}
