/*
 * BasicIllegalWordsMapper.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2017-09-12 Created
 */
package com.yryz.quanhu.support.illegalwords.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yryz.quanhu.support.illegalwords.entity.BasicIllegalWords;

@Mapper
public interface BasicIllegalWordsDao {
	/**
	 * 删除敏感词
	 * 
	 * @author jk
	 * @date 2017年9月12日
	 * @param id
	 * @return
	 * @Description 删除不需要的敏感词
	 */
	int delete(@Param("id") Long id);

	/**
	 * 保存敏感词
	 * 
	 * @author jk
	 * @date 2017年9月12日
	 * @param record
	 * @return
	 * @Description 新增敏感词
	 */
	int save(BasicIllegalWords record);

	/**
	 * 查询敏感词
	 * 
	 * @author jk
	 * @date 2017年9月12日
	 * @param word
	 * @return
	 * @Description 查询敏感词
	 */
	List<BasicIllegalWords> listByParam(@Param("word") String word);
	
	/**
	 * 查询全部敏感词
	 * @author jk
	 * @date 2017年9月12日
	 * @return
	 * @Description 查询全部敏感词
	 */
	List<String> listAllWords();
	
	/**
	 * 更新敏感词
	 * 
	 * @author jk
	 * @date 2017年9月12日
	 * @param record
	 * @return
	 * @Description 更新敏感词
	 */
	int update(BasicIllegalWords record);
	
	String selectMaxLastUpdateTime();
}