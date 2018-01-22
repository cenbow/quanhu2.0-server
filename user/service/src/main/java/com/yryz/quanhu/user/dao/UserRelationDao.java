package com.yryz.quanhu.user.dao;

import com.yryz.common.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/18 19:42
 * Created by huangxy
 */
@Mapper
public interface UserRelationDao extends BaseDao{

    <T> T selectByUser(Class<T> t, @Param("sourceUserId") String sourceUserId,@Param("targetUserId")String targetUserId);

}
