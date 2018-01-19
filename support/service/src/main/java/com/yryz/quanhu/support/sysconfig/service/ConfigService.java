package com.yryz.quanhu.support.sysconfig.service;

import com.yryz.quanhu.support.sysconfig.entity.Config;

public interface ConfigService {
//	/**
//	 * 添加配置
//	 * @param config
//	 * @throws DataBaseAccessException
//	 */
	public void addConfig(Config config);
	
	/**
	 * 按类型更新配置<br/>
	 * 1.RpcContext需要传平台分配的appId(应用id)<br/>
	 * 2.支持新增配置和更新配置,更新做法删除旧配置，插入新配置<br/>
	 * 3.配置项描述和配置项值要保持一致
	 * @param configType 配置类型(接入方自行维护唯一性)
	 * @param operate 操作人名称
	 * @param configValue 要设置的配置值 仅支持简单实体对象(不能是集合、数组、嵌套类型)类型和Map<String,String>类型
	 * @param configDesc 要设置的配置说明(可不传) 仅支持简单实体对象(不能是集合、数组、嵌套类型)类型和Map<String,String>类型
	 */
	public <T> void updateCongfigs(String configType,String operate,T configValue,T configDesc);
	
	/**
	 * 根据类型查询配置
	 * @param configType
	 * @param t
	 * @return
	 */
	public <T> void getConfig(String configType,T t);
}
