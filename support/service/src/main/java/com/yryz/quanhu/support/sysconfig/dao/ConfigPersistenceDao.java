/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年7月10日
 * Id: ConfigPersistenceDao.java, 2017年7月10日 下午3:24:39 Administrator
 */
package com.yryz.quanhu.support.sysconfig.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yryz.quanhu.support.sysconfig.entity.Config;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年7月10日 下午3:24:39
 * @Description 系统配置
 */
@Mapper
public interface ConfigPersistenceDao {
	/**
	 * 保存配置
	 * 
	 * @param config
	 */
	public void save(Config config);

	/**
	 * 更新配置
	 * 
	 * @param config
	 */
	public void update(Config config);

	/**
	 * 删除配置
	 * 
	 * @param configType
	 */
	public void delete(String configType);

	/**
	 * 根据配置id得到配置
	 * 
	 * @param configId
	 * @return
	 */
	public Config get(String configId);

	/**
	 * 根据配置类型和配置名称得到配置id
	 * 
	 * @param configType
	 * @param configName
	 * @return
	 */
	public String getIdByConfigTypeConfigName(@Param("configType") String configType,
			@Param("configName") String configName);

	/**
	 * 根据配置类型得到配置
	 * 
	 * @param configType
	 * @return
	 */
	public List<Config> getByConfigType(String configType);

	/**
	 * 批量保存配置信息
	 * 
	 * @param configs
	 */
	public void saveConfigs(List<Config> list);

	/**
	 * 统计配置类型个数
	 * 
	 * @return
	 */
	public Integer countConfigType();

	/**
	 * 根据序号得到配置类型
	 * 
	 * @param index
	 * @return
	 */
	public String getByIndex(Integer index);

}
