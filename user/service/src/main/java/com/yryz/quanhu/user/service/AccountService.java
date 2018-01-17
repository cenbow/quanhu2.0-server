/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年11月9日
 * Id: AccountService.java, 2017年11月9日 下午2:29:54 Administrator
 */
package com.yryz.quanhu.user.service;

import java.util.List;

import com.yryz.quanhu.user.dto.AgentRegisterDTO;
import com.yryz.quanhu.user.dto.LoginDTO;
import com.yryz.quanhu.user.dto.RegisterDTO;
import com.yryz.quanhu.user.dto.ThirdLoginDTO;
import com.yryz.quanhu.user.entity.UserAccount;
import com.yryz.quanhu.user.entity.UserLoginLog;
import com.yryz.quanhu.user.vo.LoginMethodVO;
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
	 * 管理后台代理注册
	 * 
	 * @param registerDTO
	 * @return
	 * @Description 返回用户id
	 */
	String agentRegister(AgentRegisterDTO registerDTO);

	/**
	 * 手机号密码登录
	 * 
	 * @param loginDTO
	 * @return 用户id
	 */
	Long login(LoginDTO loginDTO);

	/**
	 * 手机号验证码登录
	 * 
	 * @param loginDTO
	 * @param appId
	 * @return 用户id
	 */
	Long loginByVerifyCode(LoginDTO loginDTO,String appId);

	/**
	 * 第三方登录
	 * 
	 * @param loginDTO
	 * @param thirdUser
	 * @param custId
	 * @return 用户id
	 * @Description 第三方登录两种实现，分为非强绑和强绑两种模式
	 */
	Long loginThird(ThirdLoginDTO loginDTO, ThirdUser thirdUser, Long custId);

	/**
	 * （强制绑定手机号）第三方登录绑定手机号
	 * 
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
	 * 获取登录方式
	 * 
	 * @param custId
	 * @return
	 */
	List<LoginMethodVO> getLoginMethod(String custId);

	/**
	 * (web)第三方登录
	 * 
	 * @param loginType
	 *            类型 weibo:微博 weixin:微信
	 * @param returnUrl
	 *            web端登录成功返回地址
	 * @return
	 */
	String webLoginThird(String loginType, String returnUrl);

	/**
	 * (web)第三方登录回调
	 * 
	 * @param thirdUser
	 *            第三方用户对象
	 * @param loginType
	 *            第三方登录类型
	 * @return
	 */
	Long webThirdLoginNotify(ThirdUser thirdUser, String loginType);

	/**
	 * 绑定手机号
	 * 
	 * @param custId
	 * @param phone
	 * @param password
	 *            登录密码
	 */
	void bindPhone(Long custId, String phone, String password);

	/**
	 * 绑定第三方
	 * 
	 * @param custId
	 * @param thirdUser
	 *            第三方用户对象
	 * @param type
	 *            1，微信 2，微博 3，qq
	 */
	void bindThird(Long custId, ThirdUser thirdUser, Integer type);

	/**
	 * 解绑 第三方账户
	 * 
	 * @param custId
	 * @param thirdId
	 * @param type
	 *            1，微信 2，微博 3，qq
	 */
	void unbindThird(Long custId, String thirdId, Integer type,String appId);

	/**
	 * 修改密码
	 * 
	 * @param custId
	 * @param newPassword
	 * @param oldPassword
	 */
	void editPassword(String custId, String newPassword, String oldPassword);

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
	 * 根据手机号,登录密码查询用户账户信息<br/>
	 * 不传密码就根据手机号查询
	 * 
	 * @param phone
	 * @param password
	 * @return
	 */
	UserAccount checkUserByPhonePassword(String phone, String password,String appId);

}
