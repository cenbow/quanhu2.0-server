/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年12月5日
 * Id: ConfigAPI.java, 2017年12月5日 上午10:44:50 Administrator
 */
package com.yryz.quanhu.support.id.api;

import com.yryz.common.response.Response;
import com.yryz.quanhu.support.id.constants.RedisConstants;

/**
 * 系统配置管理
 * @author danshiyu
 * @version 1.0
 * @date 2017年12月5日 上午10:44:50
 */
public interface ConfigApi {
	/**
	 * 配置基本信息key
	 * @param configId
	 * @return
	 */
	static String getConfigInfoKey(String configId)
	{
		 StringBuffer keyBuffer = new StringBuffer();
		 keyBuffer.append(RedisConstants.TABLESPACE_CONFIGINFO).append("_");
		 keyBuffer.append(configId);
		 return keyBuffer.toString();
	}
	
	/**
	 * 按配置类型配置信息队列key
	 * @param configType
	 * @return
	 */
	static String getConfigTypeListKey(String configType)
	{
		 StringBuffer keyBuffer = new StringBuffer();
		 keyBuffer.append(RedisConstants.TABLESPACE_CONFIGTYPESORTSET).append("_");
		 keyBuffer.append(configType);
		 return keyBuffer.toString();
	}

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
	public <T> Response<Boolean> updateCongfigs(String configType,String operate,T configValue,T configDesc);
	
	/**
	 * 根据类型查询配置
	 * @param configType 配置类型(接入方自行维护唯一性)
	 * @param configValue 要获取的配置对象值 仅支持简单实体对象(不能是集合、数组、嵌套类型)类型和Map<String,String>类型
	 * @return
	 * @Description RpcContext需要传平台分配的appId(应用id)
	 */
	public <T> Response<T> getConfig(String configType,T configValue);


	public <T> Response<Boolean> addCongfig(String configType, String operate,T configValue,T configDesc);
}
