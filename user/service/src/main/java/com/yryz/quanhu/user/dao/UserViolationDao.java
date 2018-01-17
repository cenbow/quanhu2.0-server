/*
 * UserViolationMapper.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2018-01-12 Created
 */
package com.yryz.quanhu.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yryz.quanhu.user.entity.UserViolation;

@Mapper
public interface UserViolationDao {
	/**
	 * 保存违规记录
	 * 
	 * @param violation
	 */
	public void saveViolation(UserViolation violation);

	/**
	 * 设置旧的违规记录过期
	 * 
	 * @param userId
	 */
	public void updateViolation(@Param("userId")String userId);

	/**
	 * 查询用户违规记录
	 * 
	 * @param userId
	 * @return
	 */
	public List<UserViolation> listViolation(@Param("userId")String userId);

	/**
	 * 查询用户最新的违规记录
	 * 
	 * @param userId
	 * @return
	 */
	public UserViolation getCustViolation(@Param("userId")Long userId);

	
	/**
	 * 管理端分页查询违规用户
	 * 
	 * @param params
	 * @return
	 */
	public List<UserViolation> listPageViolation(Map<String,Object>params);
}