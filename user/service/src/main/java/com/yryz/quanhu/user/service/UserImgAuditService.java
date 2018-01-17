/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年11月9日
 * Id: UserImgAuditService.java, 2017年11月9日 下午2:30:51 Administrator
 */
package com.yryz.quanhu.user.service;

import java.util.List;

import com.github.pagehelper.Page;
import com.yryz.quanhu.user.entity.UserImgAudit;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年11月9日 下午2:30:51
 * @Description TODO (用户头像审核)
 */
public interface UserImgAuditService {
	/**
	 * 审核用户头像审核信息
	 * 
	 * @param record
	 * @param aduitActionStatus 0:初始化 1:通过 2:拒绝
	 * @return
	 */
	int auditImg(UserImgAudit record,Integer aduitActionStatus);

	/**
	 * 批量审核用户头像审核信息
	 * 
	 * @param record
	 * @param aduitActionStatus 1:通过 2:拒绝
	 * @return
	 */
	int batchAuditImg(List<UserImgAudit> record,Integer aduitActionStatus);
	/**
	 * 查询头像审核信息
	 * @param pageNo
	 * @param pageSize
	 * @param userId
	 * @param auditStatus 0:待审核 1:通过 2:拒绝
	 * @return
	 */
	Page<UserImgAudit> listByUserId(Integer pageNo,Integer pageSize,String userId, Integer auditStatus);
	
	/**
	 * 更新头像审核信息
	 * @param userId
	 * @return
	 */
	public int delete(String userId);
}
