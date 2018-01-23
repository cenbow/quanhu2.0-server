/*
 * WorkerNodeMapper.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2017-08-23 Created
 */
package com.yryz.quanhu.support.category.dao;

import com.yryz.quanhu.support.category.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryDao {

//    Integer save(Category category);
//    Integer update(Category category);
//    Integer selectByKid(@Param("kid") Long kid);
//    List<Category> selectByParentKid(@Param("parentKid") Long parentKid);

    List<Category> selectByPid(@Param("pid") Long parentKid, @Param("limit") Integer limit);

    Category selectByKid(@Param("kid") Long kid);

    List<Category> selectByRecommend();

}