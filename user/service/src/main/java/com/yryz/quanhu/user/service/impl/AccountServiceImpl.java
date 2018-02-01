package com.yryz.quanhu.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
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
import com.yryz.quanhu.user.dao.UserAccountRedisDao;
import com.yryz.quanhu.user.dao.UserLoginLogDao;
import com.yryz.quanhu.user.dto.AgentRegisterDTO;
import com.yryz.quanhu.user.dto.LoginDTO;
import com.yryz.quanhu.user.dto.RegisterDTO;
import com.yryz.quanhu.user.dto.ThirdLoginDTO;
import com.yryz.quanhu.user.dto.UserRegLogDTO;
import com.yryz.quanhu.user.dto.WebThirdLoginDTO;
import com.yryz.quanhu.user.entity.ActivityTempUser;
import com.yryz.quanhu.user.entity.UserAccount;
import com.yryz.quanhu.user.entity.UserBaseInfo;
import com.yryz.quanhu.user.entity.UserLoginLog;
import com.yryz.quanhu.user.entity.UserThirdLogin;
import com.yryz.quanhu.user.manager.SmsManager;
import com.yryz.quanhu.user.mq.UserSender;
import com.yryz.quanhu.user.service.AccountService;
import com.yryz.quanhu.user.service.ActivityTempUserService;
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
public class AccountServiceImpl implements AccountService {
	private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
	@Autowired
	private UserAccountDao mysqlDao;
	@Reference(lazy = true, check = false)
	private IdAPI idApi;
	@Autowired
	private UserAccountRedisDao accountRedisDao;
	@Autowired
	private UserLoginLogDao logDao;
	@Autowired
	protected UserThirdLoginService thirdLoginService;
	@Autowired
	private SmsManager smsService;
	@Autowired
	protected UserService userService;
	@Autowired
	private ActivityTempUserService tempUserService;
	@Autowired
	private UserSender mqSender;

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Long register(RegisterDTO registerDTO) {
		return createUser(registerDTO, null);
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public void agentRegister(AgentRegisterDTO registerDTO) {
		UserAccount checkAccount = selectOne(null, registerDTO.getUserPhone(), registerDTO.getAppId());
		if (checkAccount != null) {
			throw QuanhuException.busiError("该用户已存在");
		}
		String regChannel = Constants.ADMIN_REG_CHANNEL;
		if (registerDTO.getIsVest() == 11) {
			regChannel = Constants.ADMIN_REG_VEST_CHANNEL;
		}
		UserRegLogDTO logDTO = new UserRegLogDTO(regChannel, regChannel, regChannel);
		// 在上层据手机号判断根用户是否已存在避免不必要的事务回滚
		createUser(new RegisterDTO(regChannel, registerDTO.getUserPhone(), registerDTO.getUserPwd(), logDTO), null);
	}

	@Override
	public void mergeActivityUser(Long userId, String phone) {
		ActivityTempUser tempUser = null;
		try {
			tempUser = tempUserService.get(userId, null);
			if (tempUser == null) {
				logger.info("[mergeActivityUser]:msg:参与者不存在");
				throw QuanhuException.busiError("参与者不存在");
			}
			RegisterDTO registerDTO = new RegisterDTO();
			registerDTO.setUserChannel(String.format("wap_%s", RegType.WEIXIN_OAUTH.getText()));
			registerDTO.setUserNickName(tempUser.getNickName());
			registerDTO.setUserLocation("");
			registerDTO.setDeviceId("");
			registerDTO.setUserPhone(phone);
			UserRegLogDTO logDTO = new UserRegLogDTO(userId, "WAP", "", RegType.WEIXIN_OAUTH.getText(), "WAP", "",
					tempUser.getAppId(), tempUser.getIp(), "", tempUser.getActivivtyChannelCode(),
					StringUtils.join(
							new String[] { "WAP", RegType.WEIXIN_OAUTH.getText(), tempUser.getActivivtyChannelCode() },
							" "));
			registerDTO.setRegLogDTO(logDTO);
			userId = createUser(registerDTO, tempUser.getKid());
			// 创建第三方账户
			thirdLoginService.insert(new UserThirdLogin(userId, tempUser.getThirdId(),
					(byte) RegType.WEIXIN.getType(), tempUser.getNickName(), tempUser.getAppId()));
			logger.info("[user_bindPhone]:userId:{},bindType:activityMergeByPhone,oldPhone:,newPhone:{}", userId,
					phone);
		} catch (Exception e) {
			logger.info("[mergeActivityUser]:userId:{},phone:{},tempUser:{}", userId, phone,
					JsonUtils.toFastJson(tempUser));
			logger.error("[mergeActivityUser]", e);
		}

	}

	@Override
	public Long login(LoginDTO loginDTO, String appId) {
		UserAccount account = selectOne(null, loginDTO.getPhone(), appId);
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
		UserAccount account = selectOne(null, registerDTO.getUserPhone(), appId);
		// 用户不存在直接注册
		if (account == null) {
			if (!smsService.checkVerifyCode(registerDTO.getUserPhone(), registerDTO.getVeriCode(),
					SmsType.CODE_REGISTER, appId)) {
				throw new QuanhuException(ExceptionEnum.SMS_VERIFY_CODE_ERROR);
			}
			return createUser(registerDTO, null);
		}
		if (!smsService.checkVerifyCode(registerDTO.getUserPhone(), registerDTO.getVeriCode(), SmsType.CODE_LOGIN,
				appId)) {
			throw new QuanhuException(ExceptionEnum.SMS_VERIFY_CODE_ERROR);
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
		// 根据第三方账户查询临时用户表生成用户
		ActivityTempUser tempUser = tempUserService.get(null, thirdUser.getThirdId());
		Long userId = null;
		if (tempUser != null) {
			userId = tempUser.getKid();
		}

		userId = createUser(registerDTO, tempUser.getKid());
		// 创建第三方账户
		thirdLoginService.insert(new UserThirdLogin(userId, thirdUser.getThirdId(), loginDTO.getType().byteValue(),
				thirdUser.getNickName(), loginDTO.getRegLogDTO().getAppId()));
		logger.info("[user_bindPhone]:userId:{},bindType:thirdLoginBindPhone,oldPhone:,newPhone:{}", userId,
				loginDTO.getPhone());
		return userId;
	}

	@Override
	public List<LoginMethodVO> getLoginMethod(Long userId) {
		List<UserThirdLogin> logins = getViewThirdLogin(thirdLoginService.selectByUserId(userId), userId);
		UserAccount account = selectOne(userId, null, null);
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
		Long userId = createUser(registerDTO, null);
		// 创建第三方账户
		thirdLoginService.insert(new UserThirdLogin(userId, thirdUser.getThirdId(),
				(byte) RegType.getEnumByText(loginType).getType(), thirdUser.getNickName(), "appId"));

		return userId;
	}

	@Override
	public String wxOauthLogin(WebThirdLoginDTO loginDTO) {
		// 得到第三方登录回调的host
		String apiHost = UserUtils.getReturnOauthApiHost(loginDTO.getReturnUrl());
		// 读取配置
		ThirdLoginConfigVO configVO = new ThirdLoginConfigVO();
		return OatuhWeixin.getWxOauthUrl(configVO.getWxOauthAppKey(), apiHost, loginDTO.getReturnUrl(),
				loginDTO.getActivityChannelCode(), configVO.getWxOauthNotifyUrl());
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public void bindPhone(Long userId, String phone, String password) {
		UserAccount oldAccount = selectOne(userId, null, null);
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
		UserThirdLogin thirdLogin = thirdLoginService.selectByThirdId(thirdId, appId, type);
		if (thirdLogin == null) {
			throw QuanhuException.busiError("第三方账户不存在");
		}
		if (!thirdLogin.getUserId().equals(userId)) {
			throw QuanhuException.busiError("不是本人不能操作");
		}
		// 判断用户手机号是否都为空？
		UserAccount account = selectOne(thirdLogin.getUserId(), null, null);
		if (StringUtils.isBlank(account.getUserPhone())) {
			throw QuanhuException.busiError("主账号没有其他登录方式，不能解绑第三方");
		}
		thirdLoginService.delete(userId, thirdId);
	}

	@Override
	public void editPassword(Long userId, String newPassword, String oldPassword) {
		UserAccount account = selectOne(userId, null, null);
		if (account == null) {
			throw QuanhuException.busiError("用户不存在");
		}
		if (!StringUtils.equals(oldPassword, account.getUserPwd())) {
			throw QuanhuException.busiError("旧密码不正确");
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
		UserAccount account = selectOne(null, phone, appId);
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
		UserAccount account = selectOne(null, phone, appId);
		if (account == null) {
			return false;
		}
		return true;
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
	 * 根据id、手机号、登录密码查询账户
	 * 
	 * @param userId
	 * @param custPhone
	 * @param appId
	 * @return
	 */
	private UserAccount selectOne(Long userId, String custPhone, String appId) {
		UserAccount account = accountRedisDao.getAccount(userId, custPhone, appId);
		if (account != null) {
			return account;
		}
		try {
			account = mysqlDao.selectOne(userId, custPhone, appId);
		} catch (Exception e) {
			logger.error("[accountDao.selectOne]", e);
			throw new MysqlOptException(e);
		}
		if (account != null) {
			accountRedisDao.saveAccount(account);
		}
		return account;
	}

	/**
	 * 更新用户账户信息
	 * 
	 * @param record
	 * @return
	 */
	private int update(UserAccount record) {
		try {
			UserAccount account = mysqlDao.selectOne(record.getKid(), null, null);
			int result = mysqlDao.update(record);
			accountRedisDao.deleteAccount(account.getKid(), account.getUserPhone(), account.getAppId());
			return result;
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
	@Override
	public void saveLoginLog(UserLoginLog loginLog) {
		loginLog.setCreateDate(new Date());
		loginLog.setLoginX(0L);
		loginLog.setLoginY(0L);
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
	protected Long createUser(RegisterDTO registerDTO, Long userId) {
		if (userId == null) {
			userId = NumberUtils.toLong(ResponseUtils.getResponseData(idApi.getUserId()));
		}
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
		return selectOne(userId, null, null);
	}

	@Override
	public List<Map<String, String>> getUserAccountByPhone(Set<String> phones, String appId) {
		List<UserAccount> accounts = getUserAccount(phones, appId);
		List<Map<String, String>> maps = new ArrayList<>(phones.size());
		for (Iterator<String> iterator = phones.iterator(); iterator.hasNext();) {
			String phone = iterator.next();
			boolean nullFlag = true;
			Map<String, String> map = new HashMap<>();
			for (UserAccount account : accounts) {
				if (account != null) {
					map.put("phone", phone);
					map.put("userId", account.getKid().toString());
					nullFlag = false;
				}
			}
			if (nullFlag) {
				map.put("phone", phone);
				map.put("userId", "");
			}
			maps.add(map);
		}
		return maps;
	}

	/**
	 * 根据手机号查询缓存以及mysql中的用户账户
	 * 
	 * @param phones
	 * @param appId
	 * @return
	 */
	private List<UserAccount> getUserAccount(Set<String> phones, String appId) {
		List<UserAccount> accounts = accountRedisDao.getAccount(phones, appId);
		Set<String> nullPhone = new HashSet<>();
		// 收集缓存不存在的手机号
		for (Iterator<String> iterator = phones.iterator(); iterator.hasNext();) {
			String phone = iterator.next();
			boolean nullFlag = true;
			for (UserAccount account : accounts) {
				if (account != null) {
					nullFlag = false;
				}
			}
			if (nullFlag) {
				nullPhone.add(phone);
			}
		}
		if (CollectionUtils.isEmpty(nullPhone)) {
			return accounts;
		}
		// 查询mysql
		List<UserAccount> myAccounts = mysqlDao.selectByPhones(Lists.newArrayList(phones), appId);
		// 合并缓存的账户
		if (CollectionUtils.isNotEmpty(accounts)) {
			myAccounts.addAll(accounts);
		}
		return myAccounts;
	}

	/**
	 * 把查询得到的登录方式转换成前端需要的
	 * 
	 * @param logins
	 * @param userId
	 * @return
	 */
	private List<UserThirdLogin> getViewThirdLogin(List<UserThirdLogin> logins, Long userId) {
		if (CollectionUtils.isEmpty(logins)) {
			logins = new ArrayList<>(3);
		}
		for (int i = 10; i < 13; i++) {
			RegType regType = RegType.getEnumByTye(i);
			boolean nullType = true;
			for (UserThirdLogin login : logins) {
				if (regType.getType() == (int) login.getLoginType()) {
					nullType = false;
				}
			}
			if (nullType) {
				UserThirdLogin login = new UserThirdLogin();
				login.setThirdId("");
				login.setLoginType((byte) regType.getType());
				login.setUserId(userId);
				login.setNickName("");
				logins.add(login);
			}
		}
		return logins;
	}
}
