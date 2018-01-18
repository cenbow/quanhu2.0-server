
package com.yryz.quanhu.support.id.dao;

import com.yryz.quanhu.support.id.entity.PrimaryKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author
 * @version 1.0
 * @date 2017年7月11日 下午4:49:28
 * @Description id生成
 */
@Mapper
public interface YryzPrimaryKeyDao {
	/**
	 * 
	 * 获取id
	 * @param primaryKeyName
	 * @return
	 */
	PrimaryKey getPrimaryKey(@Param("type") String type);
	
	/**
	 * 
	 * 更新id生成器
	 * @param primaryKey
	 * @return
	 */
	Integer updatePrimaryKey(PrimaryKey primaryKey);

	/**
	 * 新增发号类型
	 * @param primaryKey
	 */
	void insertPrimaryKey(PrimaryKey primaryKey);
}
