/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年7月10日
 * Id: NewConfigServiceImpl.java, 2017年7月10日 下午3:29:49 Administrator
 */
package com.yryz.quanhu.support.sysconfig.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.yryz.common.exception.MysqlOptException;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.utils.IdGen;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.support.sysconfig.dao.ConfigPersistenceDao;
import com.yryz.quanhu.support.sysconfig.dao.ConfigRedisDao;
import com.yryz.quanhu.support.sysconfig.entity.Config;
import com.yryz.quanhu.support.sysconfig.service.ConfigService;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年7月10日 下午3:29:49
 * @Description 系统配置
 */
@Service
public class ConfigServiceImpl implements ConfigService {
	private static final Logger logger = LoggerFactory.getLogger(ConfigServiceImpl.class);

	@Autowired
	private ConfigPersistenceDao persistenceDao;

	@Autowired
	private ConfigRedisDao redisDao;

	@Override
	public <T> void getConfig(String configType, T t) {
		List<Config> configs = getConfigByConfigType(configType, true);
		getObjectByConfigs(t, configs);
	}

	@Override
	public void addConfig(Config config) {
		persistenceDao.save(config);
	}

	@Override
	public <T> void updateCongfigs(String configType, String operate, T configValue, T configDesc) {
		List<Config> configValues = getListByObject(configValue, configDesc);
		List<Config> oldConfigs = getConfigByConfigType(configType, false);
		int oldConfigLength = oldConfigs == null ? 0 : oldConfigs.size();
		int configLength = configValues == null ? 0 : configValues.size();

		// 根据类型清除缓存配置
		redisDao.delete(configType);
		// 删除旧配置
		persistenceDao.delete(configType);

		for (int i = 0; i < configLength; i++) {
			Config config = configValues.get(i);
			config.setConfigType(configType);
			config.setOperationName(operate);
			for (int j = 0; j < oldConfigLength; j++) {
				Config oldConfig = oldConfigs.get(j);
				if (StringUtils.equals(config.getConfigName(), oldConfig.getConfigName())) {
					config.setConfigId(oldConfig.getConfigId());
					config.setCreateDate(oldConfig.getCreateDate());
					// 更新旧配置项说明
					if (StringUtils.isNotBlank(oldConfig.getConfigDesc())) {
						config.setConfigDesc(oldConfig.getConfigDesc());
					}
				}
			}
			if (StringUtils.isBlank(config.getConfigId())) {
				config.setCreateDate(new Date());
				config.setUpdateDate(config.getCreateDate());
				config.setConfigId(IdGen.uuid());
			} else {
				config.setUpdateDate(new Date());
			}
			saveConfig(config);
		}
	}

	/**
	 * 新增配置
	 * 
	 * @param config
	 */
	private void saveConfig(Config config) {
		config.setCreateDate(new Date());
		config.setUpdateDate(config.getCreateDate());
		try {
			persistenceDao.save(config);
		} catch (Exception e) {
			logger.error("[ConfigPersistenceDao.save]", e);
			throw new MysqlOptException("[ConfigPersistenceDao.save]", e);
		}
		redisDao.save(config);
	}

	/**
	 * 根据类型获取全部配置
	 * 
	 * @param configType
	 * @return
	 */
	private List<Config> getConfigByConfigType(String configType, boolean needCache) {
		List<Config> configs = null;
		if (needCache) {
			configs = redisDao.getByConfigType(configType);
			if (CollectionUtils.isNotEmpty(configs)) {
				return configs;
			}
		}

		try {
			configs = persistenceDao.getByConfigType(configType);
		} catch (Exception e) {
			logger.error("[ConfigPersistenceDao.getByConfigType]", e);
			throw new MysqlOptException("[ConfigPersistenceDao.getByConfigType]", e);
		}

		if (CollectionUtils.isNotEmpty(configs) && needCache) {
			redisDao.saveConfigs(configs);
		}
		return configs;
	}

	/**
	 * 把配置对象转成配置集合
	 * 
	 * @param t
	 * @param valueOrDesc
	 *            true-设置value false-设置desc
	 * @param configs
	 */
	private static <T> void getListByObject(T t, boolean valueOrDesc, List<Config> configs) {
		Field[] fields = t.getClass().getFields();
		Field.setAccessible(fields, true);
		if (ArrayUtils.isNotEmpty(fields)) {
			for (Field f : fields) {
				String value = "";
				try {
					value = f.get(t).toString();
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw QuanhuException.busiError("不支持此类型配置");
				}
				Config config = new Config();
				config.setConfigName(f.getName());
				if (valueOrDesc) {
					config.setConfigValue(value);
				} else {
					config.setConfigDesc(value);
				}
				configs.add(config);
			}
		} else {
			if (t instanceof Map) {
				@SuppressWarnings("unchecked")
				Map<String, String> map = (Map<String, String>) t;
				for (Map.Entry<String, String> entry : map.entrySet()) {
					Config config = new Config();
					config.setConfigName(entry.getKey());
					if (valueOrDesc) {
						config.setConfigValue(entry.getValue() != null ? entry.getValue() : "");
					} else {
						config.setConfigDesc(entry.getValue() != null ? entry.getValue() : "");
					}
					configs.add(config);
				}
			} else {
				throw QuanhuException.busiError("不支持此类型配置");
			}
		}
	}

	/**
	 * 合并配置值和配置说明得到配置集合
	 * 
	 * @param configValue
	 * @param configDesc
	 * @return
	 */
	private static <T> List<Config> getListByObject(T configValue, T configDesc) {
		List<Config> configs = new ArrayList<>();
		getListByObject(configValue, true, configs);
		if (configDesc != null) {
			List<Config> configDescs = new ArrayList<>();
			getListByObject(configDesc, false, configDescs);
			if (CollectionUtils.isNotEmpty(configDescs) && CollectionUtils.isNotEmpty(configs)) {
				for (int i = 0; i < configs.size(); i++) {
					Config config = configs.get(i);
					for (int j = 0; j < configDescs.size(); j++) {
						Config configD = configDescs.get(j);
						if (StringUtils.equals(config.getConfigName(), configD.getConfigName())) {
							config.setConfigDesc(configD.getConfigDesc());
						}
					}
				}
			}
			return configs;
		} else {
			return configs;
		}
	}

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<>();
		List<Config> configs = Lists.newArrayList(new Config("1", "phone", "number", "12345"));
		ConfigServiceImpl.getObjectByConfigs(map, configs);
		configs = ConfigServiceImpl.getListByObject(map, map);
		ConfigServiceImpl.getListByObject("sdsd", "sdsd");
		System.out.println(JsonUtils.toFastJson(map));
	}

	/**
	 * 把配置集合转成配置对象
	 * 
	 * @param t
	 * @param configs
	 */
	private static <T> void getObjectByConfigs(T t, List<Config> configs) {
		Method[] methods = t.getClass().getMethods();
		if (ArrayUtils.isEmpty(methods)) {
			return;
		}

		if (t instanceof Map) {
			@SuppressWarnings("unchecked")
			Map<String, String> map = (Map<String, String>) t;
			for (int i = 0; i < configs.size(); i++) {
				Config config = configs.get(i);
				map.put(config.getConfigName(), config.getConfigValue());
			}
			return;
		}

		for (Method m : methods) {
			String methodName = m.getName();
			if (!methodName.startsWith("set")) {
				continue;
			}
			methodName = methodName.substring(3);
			// 获取属性名称
			methodName = methodName.substring(0, 1).toLowerCase() + methodName.substring(1);

			if (methodName.equalsIgnoreCase("class")) {
				continue;
			}
			for (int i = 0; i < configs.size(); i++) {
				Config config = configs.get(i);
				if (methodName.equals(config.getConfigName())) {
					try {
						if (m.getParameterTypes()[0].getName().equals("java.lang.Long")) {
							m.invoke(t, new Object[] { Long.valueOf(config.getConfigValue()) });
						} else if (m.getParameterTypes()[0].getName().equals("java.lang.Integer")) {
							m.invoke(t, new Object[] { Integer.parseInt(config.getConfigValue()) });
						} else if (m.getParameterTypes()[0].getName().equals("java.lang.Double")) {
							m.invoke(t, new Object[] { Double.parseDouble(config.getConfigValue()) });
						} else if (m.getParameterTypes()[0].getName().equals("java.lang.Boolean")) {
							m.invoke(t, new Object[] { Boolean.parseBoolean(config.getConfigValue()) });
						} else if (m.getParameterTypes()[0].getName().equals("java.lang.Float")) {
							m.invoke(t, new Object[] { Float.parseFloat(config.getConfigValue()) });
						} else if (m.getParameterTypes()[0].getName().equals("java.lang.String")) {
							m.invoke(t, new Object[] { config.getConfigValue() });
						} else {
							throw QuanhuException.busiError("不支持此类型配置");
						}
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						throw QuanhuException.busiError("不支持此类型配置");
					}
				}
			}
		}

	}

}
