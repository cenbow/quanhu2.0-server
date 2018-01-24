package com.yryz.quanhu.user.dao;

import com.yryz.common.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/18 19:42
 * Created by huangxy
 */
@Mapper
public interface UserRelationDao extends BaseDao{

    <T> T selectByUser(Class<T> t,
                        @Param("sourceUserId") String sourceUserId,@Param("targetUserId")String targetUserId);

    <T> List<T> selectByUserAll(Class<T> t,
                       @Param("sourceUserId") String sourceUserId,@Param("targetUserIds")Set<String> targetUserIds);

    <T> List<T> selectByUserStatus(Class<T> t,
                        @Param("userId")String userId,@Param("statusValue")int statusValue);

    <T> List<T> selectUserToTarget(Class<T> t,
                        @Param("userId")String userId,@Param("targetUserIds")Set<String> targetUserIds);

    <T> List<T> selectTargetToUser(Class<T> t,
                        @Param("targetUserIds")Set<String> targetUserIds,@Param("userId")String userId);

    long selectTotalCount(
                        @Param("userId")String userId,@Param("statusValue")int statusValue);

}
