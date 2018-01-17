/*
 * UserStarAuthLogMapper.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2018-01-12 Created
 */
package com.yryz.quanhu.user.dao;

import java.util.List;



import org.apache.ibatis.annotations.Mapper;

import com.yryz.quanhu.user.entity.UserStarAuthLog;

@Mapper
public interface UserStarAuthLogDao {
	/**
	 * 保存日志
	 * @param record
	 * @return
	 */
    int insert(UserStarAuthLog record);
    
    /**
     * 查询达人申请日志
     * @param userId
     * @return
     */
    List<UserStarAuthLog> selectByUserId(String userId);
}