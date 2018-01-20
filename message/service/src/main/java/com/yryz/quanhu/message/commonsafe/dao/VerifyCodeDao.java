/*
 * VerifyCodeModelMapper.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2017-12-10 Created
 */
package com.yryz.quanhu.message.commonsafe.dao;

import org.apache.ibatis.annotations.Mapper;

import com.yryz.quanhu.message.commonsafe.entity.VerifyCode;
/**
 * 验证码管理
 * @author danshiyu
 * @version 1.0
 * @date 2017年12月10日 上午10:33:14
 */
@Mapper
public interface VerifyCodeDao {
	/**
	 * 新增验证码
	 * @param record
	 * @return
	 */
    int insert(VerifyCode record);	
    
    /**
     * 检查验证码是否存在
     * @param record
     * @return
     */
    Integer checkCode(VerifyCode record);
}