package com.yryz.quanhu.support.config.dao;

import com.yryz.common.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/27 15:37
 * Created by huangxy
 */
@Mapper
public interface BasicConfigDao extends BaseDao{

    <T> T selectByKey(Class<T> t, @Param("configKey") String key);

    <T> List<T> selectChildByKid(Class<T> t, @Param("kid") Long kid);

    <T> int deleteChildByKid(Class<T> t, @Param("kids")Set<Long> kids,@Param("lastUpdateUserId") Long lastUpdateUserId);

    <T> int updateStatus(T t);
}
