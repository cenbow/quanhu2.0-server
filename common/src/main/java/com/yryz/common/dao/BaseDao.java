package com.yryz.common.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseDao {

    /**
     * 通过kid获取实体
     * @param   t   返回实体类型
     * @param   kid
     * @return t
     * */
    <T> T selectByKid(Class<T> t, @Param("kid") Long kid);

    /**
     * 获取实体列表
     * @param   t   查询条件
     * @return t
     * */
    <T> List<T> getList(T t);

    /**
     * 新增
     * @param t     参数
     * */
    <T> int insert(T t);

    /**
     * 跟据参数新增
     * @param t     参数
     * */
    <T> int insertByPrimaryKeySelective(T t);

    /**
     * 更新
     * @param t     参数
     * @return
     * */
    <T> int update(T t);

    /**
     * 删除
     * @param kid   主键
     * @return
     * */
    int delete(Long kid);

}
