package com.yryz.quanhu.dymaic.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author xiepeng
 * @version 1.0
 * @data 2018/2/1 0001 48
 */
@Mapper
public interface DymaicTopDao {

    void add(@Param("userId") Long userId, @Param("kid") Long kid);

    void update(@Param("userId") Long userId, @Param("kid") Long kid);

    Long get(@Param("userId") Long userId);

    void delete(@Param("userId") Long userId);

}
