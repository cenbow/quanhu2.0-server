/*
 * UserLoginLogMapper.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2018-01-12 Created
 */
package com.yryz.quanhu.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yryz.quanhu.user.entity.UserLoginLog;
@Mapper
public interface UserLoginLogDao {

    int insert(UserLoginLog record);

	/**
	 * 得到用户最后登录事件
	 * @param custIds
	 * @return
	 */
	public List<UserLoginLog> getLastLoginTime(@Param("userIds")List<String> userIds);
}