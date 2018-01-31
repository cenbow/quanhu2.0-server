package com.yryz.quanhu.user.dao;

import com.yryz.common.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
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

    <T> T selectUser(Class<T> t, @Param("sourceUserId") String sourceUserId,@Param("targetUserId")String targetUserId);

    <T> List<T> selectTargets(Class<T> t, @Param("sourceUserId") String sourceUserId,@Param("targetUserIds")Set<String> targetUserIds);

    <T> List<T> selectStatus(Class<T> t, @Param("userId")String userId,@Param("relationStatus")int relationStatus);

    List<Map<String,Object>> selectTotalCount(@Param("userId")String userId);

    long selectCount(@Param("userId")String userId,@Param("relationStatus")int relationStatus);

}
