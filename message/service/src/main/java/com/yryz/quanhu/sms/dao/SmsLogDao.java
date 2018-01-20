/*
 * SmsLogMapper.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2018-01-20 Created
 */
package com.yryz.quanhu.sms.dao;

import org.apache.ibatis.annotations.Mapper;

import com.yryz.quanhu.sms.entity.SmsLog;

@Mapper
public interface SmsLogDao {
    int insert(SmsLog record);
}