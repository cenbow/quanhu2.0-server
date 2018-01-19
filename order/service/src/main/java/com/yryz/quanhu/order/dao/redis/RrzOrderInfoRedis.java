/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年9月12日
 * Id: RrzOrderInfoRedis.java, 2017年9月12日 下午1:21:40 yehao
 */
package com.yryz.quanhu.order.dao.redis;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.yryz.common.exception.RedisOptException;
import com.yryz.common.utils.GsonUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.framework.core.lock.DistributedLockManager;
import com.yryz.quanhu.order.entity.RrzOrderVO;

/**
 * @author yehao
 * @version 1.0
 * @date 2017年9月12日 下午1:21:40
 * @Description 订单详情处理redis
 */
@Service
public class RrzOrderInfoRedis {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	private RedisTemplate<String, String> redisTemplate;
	
	@Resource
	private DistributedLockManager distributedLockManager;
	
	public static final String RRZORDER_INFO_REDIS_PREFIX = "RrzOrderInfoRedis";
	
	/**
	 * 保存订单
	 * @param rrzOrderVO
	 * @return
	 */
	public boolean saveOrderVO(RrzOrderVO rrzOrderVO){
		String key = RedisKeyEnum.getOrderInfo(rrzOrderVO.getOrderInfo().getOrderId());
		try {
			distributedLockManager.lock(RRZORDER_INFO_REDIS_PREFIX, key);
			redisTemplate.opsForValue().set(key, GsonUtils.parseJson(rrzOrderVO));
			distributedLockManager.unlock(RRZORDER_INFO_REDIS_PREFIX, key);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RedisOptException("save OrderVO setting fault");
		}
//		ShardedJedis jedis = null;
//		String key = RedisKeyEnum.getOrderInfo(rrzOrderVO.getOrderInfo().getOrderId());
//		String lockKey = key + "_LOCK";
//		try {
//			jedis = shardedPool.getSession("ORDER");
//			long lock = RedisString.setNx(jedis, lockKey, "LOCK");
//			if (lock > 0) {
//				// 设置失效时间失败，则删除lock，抛出异常
//				if (!RedisString.expire(jedis, lockKey, RedisKeyEnum.LOCK_EXPIRE_DEFAULT)) {
//					RedisCommon.delKey(jedis, lockKey);
//					throw new RedisOptException("save OrderVO's lock setting fault");
//				}
//				
//				//保存orderinfo
//				RedisString.set(jedis, key, GsonUtils.parseJson(rrzOrderVO));
//				
//				RedisCommon.delKey(jedis, lockKey);
//				return true;
//			} 
//		} catch (Exception e) {
//			RedisCommon.delKey(jedis, lockKey);
//			logger.error("save OrderVO error", e);
//			throw new RedisOptException(e);
//		} finally {
//			shardedPool.releaseSession(jedis, "ORDER");
//		}
//		return false;
	}
	
	/**
	 * 获取订单
	 * @param orderId
	 * @return
	 */
	public RrzOrderVO getOrderVO(String orderId){
		String key = RedisKeyEnum.getOrderInfo(orderId);
		String val = redisTemplate.opsForValue().get(key);
		if(StringUtils.isNotEmpty(val)){
			RrzOrderVO orderVO = GsonUtils.json2Obj(val, RrzOrderVO.class);
			return orderVO;
		}
		
//		ShardedJedis jedis = null;
//		String key = RedisKeyEnum.getOrderInfo(orderId);
//		try {
//			jedis = shardedPool.getSession("ORDER");
//			String val = RedisString.get(jedis, key);
//			if(StringUtils.isNotEmpty(val)){
//				RrzOrderVO orderVO = GsonUtils.json2Obj(val, RrzOrderVO.class);
//				return orderVO;
//			}
//		} finally {
//			shardedPool.releaseSession(jedis, "ORDER");
//		}
		return null;
	}

}
