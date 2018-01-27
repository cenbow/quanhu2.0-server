/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年11月10日
 * Id: CustOperatieService.java, 2017年11月10日 下午5:03:37 Administrator
 */
package com.yryz.quanhu.user.service;

import com.github.pagehelper.Page;
import com.yryz.quanhu.user.dto.UserRegLogDTO;
import com.yryz.quanhu.user.dto.UserRegQueryDTO;
import com.yryz.quanhu.user.entity.UserOperateInfo;
import com.yryz.quanhu.user.vo.MyInviterVO;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年11月10日 下午5:03:37
 * @Description 用户运营信息管理
 */
public interface UserOperateService {
	/**
	 * 邀请注册信息写入
	 * 
	 * @param record
	 * @return
	 */
	int save(UserOperateInfo record);
	
	/**
	 * 保存注册日志信息
	 * @param logModel
	 * @return
	 */
	int saveRegLog(UserRegLogDTO logModel);
	
	/**
	 * 根据用户id查询本人邀请码
	 * 
	 * @param userId
	 * @return
	 */
	String selectInviterByUserId(Long userId);
	/**
	 * 根据邀请码获取用户id
	 * @param inviterCode
	 * @return
	 */
	public String selectUserIdByInviter(String inviterCode);
	/**
	 * 查询我邀请的用户详情
	 * 
	 * @param userId
	 * @param limit
	 * @param inviterId
	 *            主键
	 * @return
	 */
	MyInviterVO getMyInviter(Long userId, Integer limit,
			Long inviterId);

	/**
	 * 查询注册信息
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param queryDTO
	 * @return
	 */
	Page<UserOperateInfo> listByParams(Integer pageNo,Integer pageSize,UserRegQueryDTO queryDTO);

	/**
	 * @param inviter 邀请码
	 * @Description 根据邀请码更新邀请人数
	 */
	void updateInviterNum(String inviterCode);
}
