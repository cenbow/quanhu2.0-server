/*
 * ActivityTempUserMapper.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2018-01-30 Created
 */
package com.yryz.quanhu.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yryz.quanhu.user.entity.ActivityTempUser;

/**
 * 活动临时用户管理
 * @author danshiyu
 *
 */
@Mapper
public interface ActivityTempUserDao {
    int delete(Long id);

    int insert(ActivityTempUser record);

    ActivityTempUser selectOne(@Param("kid")Long kid,@Param("thirdId")String thirdId);
    
    List<ActivityTempUser> getByUserIds(List<String> userIds);

    int update(ActivityTempUser record);
}