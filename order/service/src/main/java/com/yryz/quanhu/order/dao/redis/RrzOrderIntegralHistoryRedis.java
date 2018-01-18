/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年6月14日
 * Id: RrzOrderIntegralHistoryRedis.java, 2017年6月14日 下午1:53:22 yehao
 */
package com.yryz.quanhu.order.dao.redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;

/**
 * @author yehao
 * @version 1.0
 * @date 2017年6月14日 下午1:53:22
 * @Description 用户积分统计
 */
@Service
public class RrzOrderIntegralHistoryRedis {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String LASTTIME_COLUMN = "_LASTTIME";
	
	/**
	 * 获取开始时间
	 * @return
	 */
	public String getStartTime(){
//		ShardedJedis jedis = null;
//		try {
//			jedis = shardedPool.getSession("ORDER");
//			if(RedisCommon.isExists(jedis, RedisKeyEnum.integralSum()) && RedisHashs.hExists(jedis, RedisKeyEnum.integralSum(), LASTTIME_COLUMN)){
//				String startTime = RedisHashs.hget(jedis, RedisKeyEnum.integralSum(), LASTTIME_COLUMN);
//				return startTime;
//			}
//		} finally {
//			shardedPool.releaseSession(jedis, "ORDER");
//		}
//		return null;
		return null;
	}
	
	/**
	 * 更新初始时间
	 * @param nowTime
	 */
	public void setStartTime(String nowTime){
//		ShardedJedis jedis = null;
//		try {
//			jedis = shardedPool.getSession("ORDER");
//			RedisHashs.hset(jedis, RedisKeyEnum.integralSum(), "_LASTTIME", nowTime);
//		} finally {
//			shardedPool.releaseSession(jedis, "ORDER");
//		}
	}
	
	/**
	 * 积分统计更新缓存
	 * @param list
	 */
	public void integralSum(List<Map<String, Object>> list) {
//		ShardedJedis jedis = null;
//		try {
//			jedis = shardedPool.getSession("ORDER");
//			if(CollectionUtils.isNotEmpty(list)){
//				for (Map<String, Object> map : list) {
//					String productType = map.get("PRODUCT_TYPE").toString();
//					String custId = map.get("CUST_ID").toString();
//					long cost = Long.parseLong(map.get("COST").toString());
//					String val = RedisHashs.hget(jedis, RedisKeyEnum.integralSum(), custId);
//					Map<String, String> custSum = null;
//					if(StringUtils.isNotEmpty(val)){
//						custSum = (Map<String, String>) GsonUtils.json2Obj(val, HashMap.class);
//						if(custSum.containsKey(productType)){
//							long remainCost = Long.parseLong(custSum.get(productType));
//							custSum.put(productType, (remainCost + cost) + "");
//						} else {
//							custSum.put(productType, (cost) + "");
//						}
//					} else {
//						custSum = new HashMap<>(1);
//						custSum.put(productType, cost + "");
//					}
//					RedisHashs.hset(jedis, RedisKeyEnum.integralSum(), custId, GsonUtils.parseJson(custSum));
//				}
//			}
//		} finally {
//			shardedPool.releaseSession(jedis, "ORDER");
//		}
	}
	
	/**
	 * 清理用户统计信息
	 * @param custId
	 */
	public void cleanUserSum(String custId){
//		ShardedJedis jedis = null;
//		try {
//			jedis = shardedPool.getSession("ORDER");
//			//先清除以前旧信息，重新构建数据
//			RedisHashs.hdel(jedis, RedisKeyEnum.integralSum(), custId);
//		} finally {
//			shardedPool.releaseSession(jedis, "ORDER");
//		}
	}

	/**
	 * 获取用户统计信息
	 * @param custId
	 * @return
	 */
	public Map<String, String> getUserIntegralSum(String custId) {
//		String val = null;
//		ShardedJedis jedis = null;
//		try {
//			jedis = shardedPool.getSession("ORDER");
//			val = RedisHashs.hget(jedis, RedisKeyEnum.integralSum(), custId);
//		} finally {
//			shardedPool.releaseSession(jedis, "ORDER");
//		}
//		if(StringUtils.isEmpty(val)){
//			return new HashMap<>(1);
//		} else {
//			return (Map<String, String>) GsonUtils.json2Obj(val, HashMap.class);
//		}
		return null;
	}

}
