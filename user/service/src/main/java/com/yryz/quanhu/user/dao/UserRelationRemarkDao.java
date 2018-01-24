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
 * Created on 2018/1/19 14:04
 * Created by huangxy
 */
@Mapper
public interface UserRelationRemarkDao extends BaseDao{

    <T> T selectByUser(T t);

    <T> int resetByUser(T t);

    <T> List<T> selectByUserIds(Class<T> t, @Param("remarkType")int remarkType,
                                            @Param("userId")String userId,
                                            @Param("targetUserIds")Set<String> targetUserIds);

}
