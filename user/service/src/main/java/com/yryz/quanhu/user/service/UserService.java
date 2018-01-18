/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年11月9日
 * Id: UserService.java, 2017年11月9日 下午12:02:52 Administrator
 */
package com.yryz.quanhu.user.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.pagehelper.Page;
import com.yryz.quanhu.user.dto.AdminUserInfoDTO;
import com.yryz.quanhu.user.entity.UserBaseInfo;
import com.yryz.quanhu.user.vo.UserBaseInfoVO;
import com.yryz.quanhu.user.vo.UserLoginSimpleVO;
import com.yryz.quanhu.user.vo.UserSimpleVO;

/**
 * @author 
 * @version 1.0
 * @date 2017年11月9日 下午12:02:52
 * @Description 用户信息管理
 */
public interface UserService {
	/**
	 * 查询用户简要信息
	 * @param userId
	 * @return
	 */
	UserSimpleVO getUserSimple(String userId);
	/**
	 * 用户登录返回的用户信息
	 * @param userId
	 * @return
	 */
	UserLoginSimpleVO getUserLoginSimpleVO(String userId);
	
	/**
	 * 根据电话检索用户简要信息
	 * 
	 * @param phone
	 * @return
	 */
	UserSimpleVO getUserSimpleByPhone(String phone,String appId);

	
	/**
	 * 根据电话检索用户简要信息
	 * 
	 * @param phones
	 * @return
	 */
	Map<String, UserSimpleVO> getUserSimpleByPhone(Set<String> phones,String appId);
	
	/**
	 * 查询用户简要信息
	 * @param userIds
	 * @return
	 */
	Map<String,UserSimpleVO> getUserSimple(Set<String> userIds);
	
	/**
	 * 查询用户基本信息
	 * @param userId
	 * @return
	 */
	UserBaseInfo getUser(String userId);
	/**
	 * 查询用户基本信息
	 * @param userIds
	 * @return
	 */
	Map<String,UserBaseInfoVO> getUser(Set<String> userIds);
	
	/**
	 * 根据电话检索用户
	 * 
	 * @param phones
	 * @return
	 */
	UserBaseInfoVO getUserInfoByPhone(String phone,String appId);
	
	
	/**
	 * 根据电话检索用户
	 * 
	 * @param phones
	 * @return
	 */
	Map<String, UserBaseInfoVO> getUserInfoByPhone(Set<String> phones,String appId);
	
	
	/**
	 * 根据手机号、昵称、注册时间模糊查询用户id
	 * @param custInfoDTO
	 * @return 昵称需要加特殊字符转义
	 */
	List<String> getUserIdByParams(AdminUserInfoDTO custInfoDTO);

	/**
	 * 获取用户推送设备号
	 * @param userId
	 * @return
	 */
	String getDeviceIdByUserId(String userId);
	
	/**
	 * 获取用户推送设备号
	 * @param userId
	 * @return
	 */
	List<String> getDeviceIdByUserId(List<String> userId);
	
	/**
	 * 管理后台查询用户列表
	 * @param pageNo
	 * @param pageSize
	 * @param custInfoDTO
	 * @return
	 * @Description 昵称需要加特殊字符转义
	 */
	Page<UserBaseInfo> listUserInfo(int pageNo,int pageSize,AdminUserInfoDTO custInfoDTO);
	
	/**
	 * 创建用户信息
	 * @param baseInfo
	 * @Description 1.创建用户基础信息 2.初始化头像审核信息 3.生成二维码并上传二维码
	 */
	void createUser(UserBaseInfo baseInfo);
	
	/**
	 * 更新用户信息
	 * @param custBaseInfo
	 * @return void
	 * @Description 更新头像需要更新头像审核信息
	 */
	int updateUserInfo(UserBaseInfo custBaseInfo ) ;


}
