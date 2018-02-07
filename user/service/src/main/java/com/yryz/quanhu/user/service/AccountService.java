/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年11月9日
 * Id: AccountService.java, 2017年11月9日 下午2:29:54 Administrator
 */
package com.yryz.quanhu.user.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yryz.quanhu.user.dto.AgentRegisterDTO;
import com.yryz.quanhu.user.dto.LoginDTO;
import com.yryz.quanhu.user.dto.RegisterDTO;
import com.yryz.quanhu.user.dto.ThirdLoginDTO;
import com.yryz.quanhu.user.dto.WebThirdLoginDTO;
import com.yryz.quanhu.user.entity.UserAccount;
import com.yryz.quanhu.user.entity.UserLoginLog;
import com.yryz.quanhu.user.vo.LoginMethodVO;
import com.yryz.quanhu.user.vo.ThirdLoginConfigVO;
import com.yryz.quanhu.user.vo.ThirdUser;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年11月9日 下午2:29:54
 * @Description 用户账户业务
 */
public interface AccountService {
	/**
	 * 手机号注册
	 * 
	 * @param registerDTO
	 * @return 用户id
	 */
	Long register(RegisterDTO registerDTO);

	/**
	 * 管理后台代理注册 包含后台马甲用户创建
	 * 
	 * @param registerDTO
	 * @return
	 * @Description 返回用户id
	 */
	void agentRegister(AgentRegisterDTO registerDTO);

	/**
	 * 根据活动参与者信息生成正常用户<br/>
	 * 1.根据用户id查询老用户，兼容老用户不存在手机号的情况,执行绑定手机号<br/>
	 * 2.查询观察者信息，存在就根据手机号创建用户，删除观察者信息，写入第三方账户信息表，否则提示错误<br/>
	 * @param userId
	 * @param phone
	 */
	void mergeActivityUser(Long userId, String phone);
	
	/**
	 * 手机号密码登录
	 * 
	 * @param loginDTO
	 * @param appId
	 * @return 用户id
	 */
	Long login(LoginDTO loginDTO,String appId);

	/**
	 * 手机号验证码登录
	 * 
	 * @param registerDTO
	 * @param appId
	 * @return 用户id
	 */
	Long loginByVerifyCode(RegisterDTO registerDTO,String appId);

	/**
	 * 第三方登录<br/>
	 * 1.判断第三方账户是否存在，不存在即返回需要绑定手机号<br/>
	 * 2.判断不存在手机号的老用户是否存在，存在即返回需要绑定手机号<br/>
	 * 3.允许登录，更新用户设备号
	 * @param loginDTO
	 * @param thirdUser
	 * @param userId
	 * @return 用户id
	 */
	Long loginThird(ThirdLoginDTO loginDTO, ThirdUser thirdUser, Long userId);

	/**
	 * （强制绑定手机号）第三方登录绑定手机号<br/>
	 * 查询活动参与者是否存在，存在就用参与者信息注册用户信息，删除参与者信息，创建第三方账号信息<br/>
	 * @param loginDTO
	 * @param thirdUser
	 * @return 用户id
	 * @Description
	 */
	Long loginThirdBindPhone(ThirdLoginDTO loginDTO, ThirdUser thirdUser);
	
	/**
	 * 保存登录日志
	 * 
	 * @param loginLog
	 */
	public void saveLoginLog(UserLoginLog loginLog);
	
	/**
	 * 获取登录方式<br/>
	 * 1.查询第三方账号表信息<br/>
	 * 2.查询用户账号表信息<br/>
	 * 3.补全不存在的第三方账户信息展示
	 * @param userId
	 * @return
	 */
	List<LoginMethodVO> getLoginMethod(Long userId);
	/**
	 * 获取用户最后登录时间
	 * @param userIds
	 * @return
	 */
	Map<String,Date> getLastLoginTime(List<String> userIds); 
	/**
	 * (web)第三方登录
	 * 
	 * @param loginType
	 *            类型 weibo:微博 weixin:微信
	 * @param returnUrl
	 *            web端登录成功返回地址
	 * @Description 暂未实现
	 * @return
	 */
	String webLoginThird(String loginType, String returnUrl,ThirdLoginConfigVO configVO);

	/**
	 * (web)第三方登录回调
	 * 
	 * @param thirdUser
	 *            第三方用户对象
	 * @param loginType
	 *            第三方登录类型
	 * @Description 暂未实现           
	 * @return
	 */
	Long webThirdLoginNotify(ThirdUser thirdUser, String loginType);
	
	/**
	 * 微信授权登录 <br/>
	 * 1.根据入参给的页面域名得到后台api的域名
	 * 2.根据第三方配置信息和后台api域名拼装微信客户端授权地址
	 * @param loginDTO
	 * @return
	 */
	String wxOauthLogin(WebThirdLoginDTO loginD,ThirdLoginConfigVO configVO);
	
	/**
	 * 绑定手机号
	 * 
	 * @param userId
	 * @param phone
	 * @param password
	 *            登录密码
	 */
	void bindPhone(Long userId, String phone, String password);
	

	
	/**
	 * 绑定第三方
	 * 
	 * @param userId
	 * @param thirdUser
	 *            第三方用户对象
	 * @param type
	 *            10，微信 11，微博 12，qq
	 */
	void bindThird(Long userId, ThirdUser thirdUser, Integer type,String appId);

	/**
	 * 解绑 第三方账户<br/>
	 * 判断用户是否拥有手机号，没有就不允许解绑
	 * 
	 * @param userId
	 * @param thirdId
	 * @param type
	 *           10，微信 11，微博 12，qq
	 */
	void unbindThird(Long userId, String thirdId, Integer type,String appId);

	/**
	 * 修改密码
	 * 
	 * @param userId
	 * @param newPassword
	 * @param oldPassword
	 */
	void editPassword(Long userId, String newPassword, String oldPassword);

	/**
	 * 手机号重置密码
	 * 
	 * @param phone
	 * @param password
	 * @param verifyCode
	 *            验证码
	 *            @param appId
	 */
	void forgotPasswordByPhone(String phone, String password, String verifyCode,String appId);

	/**
	 * 根据手机号检查用户是否存在
	 * 
	 * @param phone
	 * @return
	 */
	boolean checkUserByPhone(String phone,String appId);

	/**
	 * 查询用户账号
	 * @param userId
	 * @return
	 */
	UserAccount getUserAccountByUserId(Long userId);
	/**
	 * 根据手机号查询用户账号,查不到的用户id返回空字符串
	 * @return
	 */
	List<Map<String,String>> getUserAccountByPhone(Set<String>phones,String appId);
}
