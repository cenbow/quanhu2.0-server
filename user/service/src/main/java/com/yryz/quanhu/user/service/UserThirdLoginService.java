/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年11月10日
 * Id: CustThirdLoginService.java, 2017年11月10日 下午1:35:23 Administrator
 */
package com.yryz.quanhu.user.service;

import java.util.List;

import com.yryz.quanhu.user.contants.RegType;
import com.yryz.quanhu.user.entity.UserThirdLogin;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年11月10日 下午1:35:23
 * @Description 第三方账户业务
 */
public interface UserThirdLoginService {
	/**
	 * 根据用户id删除第三方账户
	 * @param userId
	 * @param thirdId
	 * @return
	 */
    int delete(Long userId,String thirdId);
    
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
    List<UserThirdLogin> selectByUserId(Long userId);
    
    /**
     * 根据第三方id查询第三方账户信息
     * @param thirdId
     * @param appId
     * @param type {@link RegType}
     * @return
     */
    UserThirdLogin selectByThirdId(String thirdId,String appId,Integer type);
    
}
