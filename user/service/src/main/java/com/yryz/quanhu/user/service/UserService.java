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
	UserSimpleVO getUserSimple(Long userId);
	
	/**
	 * 查询用户简要信息
	 * @param userId
	 * @return
	 */
	UserSimpleVO getUserSimple(Long userId,Long friendId);
	/**
	 * 查询用户简要信息
	 * @param userIds
	 * @return
	 */
	Map<String,UserSimpleVO> getUserSimple(Long userId,Set<String> friendIds);
	/**
	 * 查询用户简要信息
	 * @param userIds
	 * @return
	 */
	Map<String,UserSimpleVO> getUserSimple(Set<String> userIds);
	
	/**
	 * 用户登录返回的用户信息
	 * @param userId
	 * @param friend
	 * @return
	 */
	UserLoginSimpleVO getUserLoginSimpleVO(Long userId);
	
	/**
	 * 用户登录返回的用户信息
	 * @param userId
	 * @param friend
	 * @return
	 */
	UserLoginSimpleVO getUserLoginSimpleVO(Long userId,Long friendId);
	
	/**
	 * 根据电话检索用户
	 * 
	 * @param phone
	 * @return
	 */
	String getUserByPhone(String phone,String appId);

	
	/**
	 * 根据电话检索用户
	 * 
	 * @param phones
	 * @return
	 */
	Map<String, String> getUserByPhone(Set<String> phones,String appId);
	

	

	
	/**
	 * 查询用户基本信息
	 * @param userId
	 * @return
	 */
	UserBaseInfo getUser(Long userId);
	/**
	 * 查询用户基本信息
	 * @param userIds
	 * @return
	 */
	Map<String,UserBaseInfoVO> getUser(Set<String> userIds);
	/**
	 * 获取活动用户信息(包含观察者信息)
	 * @param userIds
	 * @return
	 */
	Map<String,UserBaseInfoVO> getActivityUser(Set<String>userIds);
	
	/**
	 * 根据手机号、昵称、注册时间模糊查询用户id
	 * @param custInfoDTO
	 * @return 昵称需要加特殊字符转义
	 */
	List<String> getUserIdByParams(AdminUserInfoDTO userInfoDTO);

	/**
	 * 获取用户推送设备号
	 * @param userId
	 * @return
	 */
	String getDeviceIdByUserId(Long userId);
	
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

	/**
	 * 查询一段时间的全部用户ID，不分状态
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Long> getUserIdByCreateDate(String startDate,String endDate);

	/**
	 * 查询一段时间的全部用户信息
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<UserBaseInfo> getUserListByCreateDate(String startDate,String endDate);

	/**
	 * 根据用户ID查询全部用户，不分状态
	 * @param userIds
	 * @return
	 */
    List<UserBaseInfo> getAllByUserIds(List<Long> userIds);
    
    /**
     * 根据昵称查询用户
     * @param appId
     * @param nickName
     * @return
     */
    public  UserBaseInfo getUserByNickName(String appId, String nickName);
}
