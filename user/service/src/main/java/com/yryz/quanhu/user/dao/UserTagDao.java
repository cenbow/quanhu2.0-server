/*
 * UserTagMapper.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2018-01-24 Created
 */
package com.yryz.quanhu.user.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yryz.quanhu.user.dto.UserTagDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yryz.quanhu.user.entity.UserTag;

@Mapper
public interface UserTagDao {
	/**
	 * 用户标签删除
	 * @param userId
	 * @return
	 */
    int delete(@Param("userId")Long userId,@Param("tagType")Integer tagType);
    /**
     * 用户标签写入
     * @param record
     * @return
     */
    int insert(UserTag record);
    /**
     * 用户标签批量写入
     * @param userTags
     */
    void batchSave(@Param("userTags")List<UserTag> userTags);
    /**
     * 查询用户tag
     * @param userId
     * @param tagType
     * @return
     */
    List<String> selectTagByUserId(@Param("userId")Long userId,@Param("tagType")Integer tagType);

    /**
     * 批量查询
     * @param userIds
     * @return
     */
    List<UserTag> getUserTags(@Param("userIds") List<Long> userIds);


    /**
     * 查询用户标签集合，带标签名
     * @param userId
     * @return
     */
    List<UserTagDTO> getUserUnionTags(@Param("userId") Long userId);

    /**
     * 查询用户标签，格式化标签组合，用于管理端显示
     * @param userIds
     * @return
     */
    List<UserTagDTO> getUserGroupConcatTags(@Param("userIds") Set<Long> userIds);

    List<Map<String, Long>> getTagCountByUser(@Param("tagIds") Set<String> tagIds);
}