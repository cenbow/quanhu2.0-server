/*
 * UserThirdLoginMapper.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2018-01-12 Created
 */
package com.yryz.quanhu.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yryz.quanhu.user.entity.UserThirdLogin;
@Mapper
public interface UserThirdLoginDao {
	/**
	 * 根据用户id删除第三方账户
	 * @param userId
	 * @param thirdId
	 * @return
	 */
    int delete(@Param("userId")String userId,@Param("thirdId")String thirdId);
    
    /**
     * 新增第三方账户信息
     * @param record
     * @return
     */
    int insert(UserThirdLogin record);

    /**
     * 查询用户所有第三方账户
     * @param userId
     * @return
     */
    List<UserThirdLogin> selectByUserId(String userId);
    
    /**
     * 根据第三方id查询第三方账户信息
     * @param thirdId
     * @param appId
     * @return
     */
    UserThirdLogin selectByThirdId(@Param("thirdId")String thirdId,@Param("appId")String appId);
}