/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月18日
 * Id: RrzOrderUserPhyDao.java, 2018年1月18日 上午11:20:20 yehao
 */
package com.yryz.quanhu.order.dao.persistence;

import org.apache.ibatis.annotations.Mapper;

import com.yryz.quanhu.order.entity.RrzOrderUserPhy;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月18日 上午11:20:20
 * @Description 用户安全信息数据操作接口
 */
@Mapper
public interface RrzOrderUserPhyDao {
	
	/**
	 * 保存用户安全信息
	 * @param rrzOrderUserPhy
	 */
	public void insert(RrzOrderUserPhy rrzOrderUserPhy);
	
	/**
	 * 更新用户安全信息
	 * @param rrzOrderUserPhy
	 */
	public void update(RrzOrderUserPhy rrzOrderUserPhy);
	
	/**
	 * 获取用户安全信息
	 * @param custId
	 * @return
	 */
	public RrzOrderUserPhy get(String custId);
	
	/**
	 * 检查用户的密码验证权限是否超过次数
	 * @param custId
	 * @return
	 */
	public boolean banCheck(String custId);
	
	/**
	 * 增加一次密码输错的次数
	 * @param custId
	 * @return 操作结果
	 */
	public int increaseBan(String custId);
	
	/**
	 * 解锁账号锁定状态
	 * @param custId
	 */
	public void clearBan(String custId);

}
