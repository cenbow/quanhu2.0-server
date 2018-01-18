/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月18日
 * Id: UserPhyService.java, 2018年1月18日 上午11:10:03 yehao
 */
package com.yryz.quanhu.order.service;

import com.yryz.common.response.Response;
import com.yryz.quanhu.order.entity.RrzOrderUserPhy;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月18日 上午11:10:03
 * @Description 用户账户安全服务管理
 */
public interface UserPhyService {
	
	/**
	 * 检查是否已经超过密码输入错误次数
	 * @param custId
	 * @return
	 */
	public boolean banCheck(String custId);
	
	/**
	 * 添加密码输入错误的次数判断
	 * @param custId
	 * @return
	 */
	public int increaseBan(String custId);
	
	/**
	 * 在用户输入正确密码后，清理安全输入次数的错误次数
	 * @param custId
	 */
	public void clearBan(String custId);
	
	/**
	 * 保存用户安全信息
	 * @param rrzOrderUserPhy
	 */
	public void saveUserPhy(RrzOrderUserPhy rrzOrderUserPhy);
	
	/**
	 * 更新用户安全信息
	 * @param rrzOrderUserPhy
	 */
	public void updateUserPhy(RrzOrderUserPhy rrzOrderUserPhy);
	
	/**
	 * 更新用户安全信息缓存
	 * @param custId
	 */
	public void updateUserPhyCache(String custId);

	/**
	 * 处理用户安全信息
	 * @param rrzOrderUserPhy
	 * @param oldPassword
	 * @return
	 */
	public Response<?> dealUserPhy(RrzOrderUserPhy rrzOrderUserPhy, String oldPassword);

	/**
	 * 获取用户安全信息
	 * @param custId
	 * @return
	 */
	public RrzOrderUserPhy getUserPhy(String custId);
	
	/**
	 * 验证安全信息
	 * @param rrzOrderUserPhy
	 * @return
	 */
	public Response<?> checkSecurityProblem(RrzOrderUserPhy rrzOrderUserPhy);
	
	/**
	 * 检查支付密码
	 * @param custId
	 * @param payPassword
	 * @return
	 */
	public Response<?> checkPayPassword(String custId, String payPassword);

}
