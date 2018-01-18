/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年9月12日
 * Id: RrzOrderInfoRedis.java, 2017年9月12日 下午1:21:40 yehao
 */
package com.yryz.quanhu.order.dao.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.yryz.quanhu.order.entity.RrzOrderVO;

import redis.clients.jedis.ShardedJedis;

/**
 * @author yehao
 * @version 1.0
 * @date 2017年9月12日 下午1:21:40
 * @Description 订单详情处理redis
 */
@Service
public class RrzOrderInfoRedis {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	RedisTemplate<String, String> redisTemplate;
	
	/**
	 * 保存订单
	 * @param rrzOrderVO
	 * @return
	 */
	public boolean saveOrderVO(com.yryz.quanhu.order.entity.RrzOrderVO rrzOrderVO){
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
		return false;
	}
	
	/**
	 * 获取订单
	 * @param orderId
	 * @return
	 */
	public RrzOrderVO getOrderVO(String orderId){
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
