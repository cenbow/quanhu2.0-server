package com.yryz.quanhu.user.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yryz.quanhu.user.entity.UserAccount;

/**
 * 账户管理
 * 
 * @author danshiyu
 * @version 1.0
 * @date 2017年11月10日 上午11:34:32
 */
@Mapper
public interface UserAccountDao {

	/**
	 * 新增用户账户
	 * 
	 * @param record
	 * @return
	 */
	int insert(UserAccount record);

	/**
	 * 查询账户信息
	 * 
	 * @param userId
	 * @param userPhone
	 * @param userPwd
	 *            登录密码
	 * @param appId
	 * @return
	 * @Description 分别根据用户id、手机号、邮箱查询用户账户 可用于登录验证
	 */
	UserAccount selectOne(@Param("userId") String userId, @Param("userPhone") String userPhone,
			 @Param("userPwd") String userPwd,@Param("appId")String appId);

	/**
	 * 更新账户信息
	 * 
	 * @param record
	 * @return
	 */
	int update(UserAccount record);

}