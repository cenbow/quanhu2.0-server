/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年11月10日
 * Id: CustViolationService.java, 2017年11月10日 下午5:50:20 Administrator
 */
package com.yryz.quanhu.user.service;

import java.util.List;

import com.github.pagehelper.Page;
import com.yryz.quanhu.user.entity.UserViolation;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年11月10日 下午5:50:20
 * @Description 用户违规管理
 */
public interface UserViolationService {
	/**
	 * 保存违规记录
	 * @param violation
	 * @param appId
	 */
	public void saveViolation(UserViolation violation, String appId);

	/**
	 *  解冻、解除禁言
	 * @param violation
	 * @param appId
	 */
	public void updateViolation(UserViolation violation, String appId);

	/**
	 * 查询用户违规记录
	 * 
	 * @param userId
	 * @return
	 */
	public List<UserViolation> getCustViolationList(String userId);

	/**
	 * 管理端分页查询违规用户
	 * @param pageNo
	 * @param pageSize
	 * @param userIds
	 * @param type  违规类型 1:禁言 2:冻结
	 * @param startDate 违规时间（起始）
	 * @param endDate 违规时间（结束）
	 * @return
	 */
	public Page<UserViolation> getCustViolationList(int pageNo, int pageSize, List<String> userIds, Integer type,
			String startDate, String endDate);
}
