/*
 * UserRegLogMapper.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2018-01-12 Created
 */
package com.yryz.quanhu.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yryz.quanhu.user.entity.UserRegLog;
@Mapper
public interface UserRegLogDao {
	int insert(UserRegLog record);
	/**
	 * 根据创建时间查询用户id
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Long> getUserIdByCreateDate(@Param("startDate")String startDate,@Param("endDate")String endDate);
	/**
	 * 根据用户id查询
	 * @param userIds
	 * @return
	 */
	List<UserRegLog> listByUserId(@Param("userIds") List<Long> userIds);
}