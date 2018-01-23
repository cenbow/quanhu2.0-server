/*
 * UserImgAuditMapper.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2018-01-12 Created
 */
package com.yryz.quanhu.user.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yryz.quanhu.user.entity.UserImgAudit;

@Mapper
public interface UserImgAuditDao {
	/**
	 * 保存用户头像审核信息
	 * 
	 * @param id
	 * @return
	 */
	int save(UserImgAudit record);

	/**
	 * 批量保存用户头像审核信息
	 * 
	 * @param record
	 * @return
	 */
	int batchSave(@Param("imgAudits")List<UserImgAudit> imgAudits);

	/**
	 * 查询头像审核信息
	 * 
	 * @param id
	 * @return
	 */
	List<UserImgAudit> listByUserId(@Param("userId") Long userId, @Param("auditStatus") Integer auditStatus);

	/**
	 * 更新头像审核信息
	 * 
	 * @param record
	 * @return
	 */
	int update(UserImgAudit record);

	/**
	 * 批量更新头像审核信息
	 * 
	 * @param userIds
	 * @param auditStatus
	 * @param updateTime
	 * @return
	 */
	int batchUpdate(@Param("userIds") List<Long> userIds, @Param("auditStatus") Integer auditStatus,
			@Param("updateTime") Date updateTime);
	
	/**
	 * 删除头像审核信息
	 * @param userId
	 * @return
	 */
	int delete(@Param("userId")Long userId);
}