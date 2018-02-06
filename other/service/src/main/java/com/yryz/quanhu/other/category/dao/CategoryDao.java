/*
 * WorkerNodeMapper.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2017-08-23 Created
 */
package com.yryz.quanhu.other.category.dao;

import com.yryz.quanhu.other.category.entity.Category;
import com.yryz.quanhu.other.category.vo.CategoryAdminVo;
import com.yryz.quanhu.other.category.vo.CategorySearchAdminVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryDao {

    List<Category> selectByPid(@Param("pid") Long parentKid, @Param("limit") Integer limit);

    Category selectByKid(@Param("kid") Long kid);

    List<Category> selectByRecommend();

    List<Category> findAll();

    List<Category> selectBySearch(CategorySearchAdminVo search);
    Integer selectCountBySearch(CategorySearchAdminVo search);

    Integer insert(CategoryAdminVo category);

    Integer update(CategoryAdminVo category);

}