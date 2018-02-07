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
	int batchSave(@Param("imgAudits") List<UserImgAudit> imgAudits);

	/**
	 * 查询用户头像审核信息
	 * 
	 * @param kid
	 * @return
	 */
	UserImgAudit findByUserId(@Param("kid") Long kid, @Param("userId") Long userId);

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
	int batchUpdate(@Param("kids") List<Long> kids, @Param("auditStatus") Integer auditStatus,
			@Param("operational") String operational, @Param("lastUpdateUserId") Long lastUpdateUserId);

	/**
	 * 删除头像审核信息
	 * 
	 * @param userId
	 * @return
	 */
	int delete(@Param("userId") Long userId);
}