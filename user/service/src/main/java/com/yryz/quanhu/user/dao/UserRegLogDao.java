/*
 * UserRegLogMapper.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2018-01-12 Created
 */
package com.yryz.quanhu.user.dao;

import org.apache.ibatis.annotations.Mapper;

import com.yryz.quanhu.user.entity.UserRegLog;
@Mapper
public interface UserRegLogDao {
	int insert(UserRegLog record);
}