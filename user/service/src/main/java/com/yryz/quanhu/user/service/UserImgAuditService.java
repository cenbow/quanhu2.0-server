/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年11月9日
 * Id: UserImgAuditService.java, 2017年11月9日 下午2:30:51 Administrator
 */
package com.yryz.quanhu.user.service;

import com.github.pagehelper.Page;
import com.yryz.quanhu.user.dto.UserImgAuditDTO;
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
	 * @param aduitActionStatus 10:初始化 11:通过 12:拒绝
	 * @return
	 */
	int auditImg(UserImgAudit record,Integer aduitActionStatus);

	/**
	 * 批量审核用户头像审核信息
	 * 
	 * @param auditDTO
	 * @return
	 */
	int batchAuditImg(UserImgAuditDTO auditDTO);
	/**
	 * 查询头像审核信息
	 * @param pageNo
	 * @param pageSize
	 * @param userId
	 * @param auditStatus 10:待审核 11:通过 12:拒绝
	 * @return
	 */
	Page<UserImgAudit> listByUserId(Integer pageNo,Integer pageSize,Long userId, Integer auditStatus);
	
	/**
	 * 更新头像审核信息
	 * @param userId
	 * @return
	 */
	public int delete(Long userId);
}
