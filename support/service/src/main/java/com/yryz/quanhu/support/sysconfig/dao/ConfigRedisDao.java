package com.yryz.quanhu.support.sysconfig.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.yryz.common.exception.RedisOptException;
import com.yryz.framework.core.cache.RedisTemplateBuilder;
import com.yryz.quanhu.support.id.api.ConfigApi;
import com.yryz.quanhu.support.id.constants.Constants;
import com.yryz.quanhu.support.sysconfig.entity.Config;

/**
 * 系统配置缓存管理
 * 
 * @version 1.0
 * @date 2017年12月5日 上午9:53:39
 */
@Service
public class ConfigRedisDao {
	private static final Logger logger = LoggerFactory.getLogger(ConfigRedisDao.class);
	@Autowired
	private RedisTemplateBuilder templateBuilder;
	
	/**
	 * 更新配置
	 * @param config
	 */
	public void save(Config config) {
		try {
		String key = ConfigApi.getConfigInfoKey(config.getConfigId());
		RedisTemplate<String, Config> redisTemplate = templateBuilder.buildRedisTemplate(Config.class);
		redisTemplate.opsForValue().set(key, config);
		if (redisTemplate.getExpire(key) < 0) {
			redisTemplate.expire(key, Constants.CONFIG_EXPIRE_TIME,TimeUnit.SECONDS);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("saveConfigInfo------------------key:" + key);
			logger.debug("saveConfigInfo------------------config:" + config.toString());
		}
		}catch (Exception e) {
			logger.error("配置保存失败", e);
			throw new RedisOptException("配置保存失败", e);
		}
	}
	
	/**
	 * 批量保存配置
	 * @param configs
	 */
	public void saveConfigs(List<Config> configs) {
		try {
			for (int i = 0; i < configs.size(); i++) {
				Config config = configs.get(i);
				save(config);
				saveConfigTypeSortSet(config.getConfigType(), config.getConfigId());
			}
		} catch (Exception e) {
			logger.error("配置保存失败", e);
			throw new RedisOptException("配置保存失败", e);
		}
	}
	
	/**
	 * 更新配置
	 * @param config
	 */
	public void update(Config config) {
		try {
			save(config);
		} catch (Exception e) {
			logger.error("配置更新失败", e);
			throw new RedisOptException("配置更新失败", e);
		}
	}
	
	/**
	 * 获取单条配置
	 * @param configId
	 * @return
	 */
	public Config get(String configId) {
		try {
		String key = ConfigApi.getConfigInfoKey(configId);
		RedisTemplate<String, Config> redisTemplate = templateBuilder.buildRedisTemplate(Config.class);
		Config config = redisTemplate.opsForValue().get(key);
		if (logger.isDebugEnabled()) {
			logger.debug("getConfigInfo------------------key:" + key);
			logger.debug("getConfigInfo------------------config:" + config.toString());
		}
		return config;
		} catch (Exception e) {
			logger.error("配置查询失败", e);
			return null;
		}
	}
	
	/**
	 * 根据类型获取配置
	 * @param configType
	 * @return
	 */
	public List<Config> getByConfigType(String configType) {
		List<Config> configs = new ArrayList<>();
		try {
			Set<String> set = getConfigTypeSortSet(configType);
			if (set != null && !set.isEmpty()) {
				for (Iterator<String> iter = set.iterator(); iter.hasNext();) {
					String configId = iter.next();
					Config config = get(configId);
					configs.add(config);
				}
			}
		} catch (Exception e) {
			logger.error("配置查询失败", e);
			return null;
		}
		return configs;
	}
	
	/**
	 * 根据配置类型删除全部配置缓存
	 * @param configType
	 */
	public void delete(String configType) {
		try{
			deleteConfigTypeSortSet(configType);
			deleteConfigByType(configType);
		} catch (Exception e) {
			logger.error("配置删除失败", e);
			throw new RedisOptException("配置删除失败", e);
		} 
	}

	/**
	 * 按类型保存配置队列信息
	 * 
	 * @param jedis
	 * @param configType
	 * @param configId
	 */
	private void saveConfigTypeSortSet(String configType, String configId) {
		String key = ConfigApi.getConfigTypeListKey(configType);
		RedisTemplate<String, String> redisTemplate = templateBuilder.buildRedisTemplate(String.class);
		redisTemplate.opsForSet().add(key, configId);
		if (redisTemplate.getExpire(key) < 0) {
			redisTemplate.expire(key, Constants.CONFIG_EXPIRE_TIME,TimeUnit.SECONDS);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("saveConfigTypeSortSet------------------key:" + key);
			logger.debug("saveConfigTypeSortSet------------------configId:" + configId);
		}
	}

	/**
	 * 按类型保存配置队列信息
	 * 
	 * @param jedis
	 * @param configType
	 * @param configId
	 */
	private Set<String> getConfigTypeSortSet(String configType) {
		String key = ConfigApi.getConfigTypeListKey(configType);
		RedisTemplate<String, String> redisTemplate = templateBuilder.buildRedisTemplate(String.class);
		Set<String> set = redisTemplate.opsForSet().members(key);
		if (logger.isDebugEnabled()) {
			logger.debug("getConfigTypeSortSet------------------key:" + key);
			logger.debug("getConfigTypeSortSet------------------set:" + set.toString());
		}
		return set;
	}

	/**
	 * 按类型删除配置队列信息
	 * 
	 * @param jedis
	 * @param configType
	 * @param configId
	 */
	private void deleteConfigTypeSortSet(String configType) {
		String key = ConfigApi.getConfigTypeListKey(configType);
		RedisTemplate<String, String> redisTemplate = templateBuilder.buildRedisTemplate(String.class);
		redisTemplate.delete(key);
		if (logger.isDebugEnabled()) {
			logger.debug("deleteConfigTypeSortSet------------------key:" + key);
		}
	}

	/**
	 * 根据配置类型删除全部配置
	 * 
	 * @param jedis
	 * @param configType
	 */
	private void deleteConfigByType(String configType) {
		Set<String> set = getConfigTypeSortSet(configType);
		if (CollectionUtils.isNotEmpty(set)) {
			for (Iterator<String> iter = set.iterator(); iter.hasNext();) {
				String configId = iter.next();
				String key = ConfigApi.getConfigInfoKey(configId);
				RedisTemplate<String, String> redisTemplate = templateBuilder.buildRedisTemplate(String.class);
				redisTemplate.delete(key);
			}
		}
	}

}
