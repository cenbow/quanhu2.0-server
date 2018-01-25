/*
 * UserOperateInfoMapper.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2018-01-12 Created
 */
package com.yryz.quanhu.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yryz.quanhu.user.dto.UserRegQueryDTO;
import com.yryz.quanhu.user.entity.UserOperateInfo;

@Mapper
public interface UserOperateInfoDao {
	/**
	 * 注册信息写入
	 * 
	 * @param record
	 * @return
	 */
	int save(UserOperateInfo record);
	
	/**
	 * 根据用户id查询本人邀请码
	 * 
	 * @param userId
	 * @return
	 */
	String selectInviterByUserId(Long userId);

	/**
	 * 根据本人邀请码获取用户id
	 * 
	 * @param inviterCode
	 *            本人邀请码
	 * @return
	 */
	String selectUserIdByInviter(String inviterCode);

	/**
	 * 根据用户id查询被邀请者信息
	 * 
	 * @param userId
	 * @param limit
	 * @param inviterId
	 *            主键
	 * @return
	 */
	List<UserOperateInfo> listByUserId(@Param("userId") Long userId, @Param("limit") Integer limit,
			@Param("inviterId") Integer inviterId);

	/**
	 * 查询注册信息
	 * 
	 * @param queryDTO
	 * @return
	 */
	List<UserOperateInfo> listByParams(UserRegQueryDTO queryDTO);

	/**
	 * @param inviterCode 邀请码
	 * @Description 更新邀请人数
	 */
	void updateInviterNum(@Param("inviterCode") String inviterCode);

	/**
	 * @param userId
	 * @return
	 * @Description 查询用户邀请人数
	 */
	int getInviterNum(@Param("userId") Long userId);
}