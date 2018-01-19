package com.yryz.quanhu.order.dao.redis;

import java.util.Date;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.yryz.common.exception.RedisOptException;
import com.yryz.common.utils.GsonUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.order.entity.RrzOrderUserAccount;
import com.yryz.quanhu.order.enums.AccountEnum;
import com.yryz.quanhu.order.exception.SourceNotEnoughException;

/**
 * @author yehao
 * @version 1.0
 * @date 2017年10月16日 下午5:29:11
 * @Description 用户账户缓存redis操作
 */
@Service
public class RrzOrderUserAccountRedis{
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	
//	@Autowired
//	ShardedRedisPool shardedPool;

	public void saveUserAccount(RrzOrderUserAccount rrzOrderUserAccount) {
		redisTemplate.opsForValue().set(RedisKeyEnum.getRrzOrderUserAccount(rrzOrderUserAccount.getCustId()), GsonUtils.parseJson(rrzOrderUserAccount));
		redisTemplate.opsForValue().set(RedisKeyEnum.getUserAccountSum(rrzOrderUserAccount.getCustId()), rrzOrderUserAccount.getAccountSum().toString());
		redisTemplate.opsForValue().set(RedisKeyEnum.getUserCostSum(rrzOrderUserAccount.getCustId()), rrzOrderUserAccount.getCostSum().toString());
		redisTemplate.opsForValue().set(RedisKeyEnum.getUserIntegralSum(rrzOrderUserAccount.getCustId()), rrzOrderUserAccount.getIntegralSum().toString());
		
//		ShardedJedis jedis = null;
//		try {
//			jedis = shardedPool.getSession("ORDER");
//			RedisString.set(jedis, RedisKeyEnum.getRrzOrderUserAccount(rrzOrderUserAccount.getCustId()), GsonUtils.parseJson(rrzOrderUserAccount));
//			RedisString.set(jedis, RedisKeyEnum.getUserAccountSum(rrzOrderUserAccount.getCustId()), rrzOrderUserAccount.getAccountSum().toString());
//			RedisString.set(jedis, RedisKeyEnum.getUserCostSum(rrzOrderUserAccount.getCustId()), rrzOrderUserAccount.getCostSum().toString());
//			RedisString.set(jedis, RedisKeyEnum.getUserIntegralSum(rrzOrderUserAccount.getCustId()), rrzOrderUserAccount.getIntegralSum().toString());
//		} finally {
//			shardedPool.releaseSession(jedis, "ORDER");
//		}
	}

	public RrzOrderUserAccount getUserAccount(String custId) {
		String val = redisTemplate.opsForValue().get(RedisKeyEnum.getRrzOrderUserAccount(custId));
		if(StringUtils.isNotEmpty(val)){
			return GsonUtils.json2Obj(val,RrzOrderUserAccount.class);
		} else {
			return null;
		}
		
//		ShardedJedis jedis = null;
//		try {
//			jedis = shardedPool.getSession("ORDER");
//			return (RrzOrderUserAccount) GsonUtils.json2Obj(RedisString.get(jedis, RedisKeyEnum.getRrzOrderUserAccount(custId)),RrzOrderUserAccount.class);
//		}  finally {
//			shardedPool.releaseSession(jedis, "ORDER");
//		}
	}

	public void update(RrzOrderUserAccount rrzOrderUserAccount) {
		redisTemplate.opsForValue().set(RedisKeyEnum.getRrzOrderUserAccount(rrzOrderUserAccount.getCustId()), GsonUtils.parseJson(rrzOrderUserAccount));
		
//		ShardedJedis jedis = null;
//		try {
//			jedis = shardedPool.getSession("ORDER");
//			RedisString.set(jedis, RedisKeyEnum.getRrzOrderUserAccount(rrzOrderUserAccount.getCustId()), GsonUtils.parseJson(rrzOrderUserAccount));
//		} finally {
//			shardedPool.releaseSession(jedis, "ORDER");
//		}
	}

	public RrzOrderUserAccount optAccountSource(String custId , long amount, int type) {
		RrzOrderUserAccount rrzOrderUserAccount = getUserAccount(custId);
		if(rrzOrderUserAccount == null){
			logger.warn("系统异常，当前用户不存在，custId:" + custId);
			throw new RedisOptException("系统异常，当前用户不存在，custId:" + custId);
		}
		//加费
		if(type == 1){ 
			long sum = redisTemplate.opsForValue().increment(RedisKeyEnum.getUserAccountSum(custId), amount);
			rrzOrderUserAccount.setAccountSum(sum);
			rrzOrderUserAccount.setUpdateTime(new Date());
		} else {
			long sum = redisTemplate.execute(new RedisCallback<Long>() {

				public Long doInRedis(RedisConnection connection) {
					return connection.decrBy(redisTemplate.getStringSerializer().serialize(RedisKeyEnum.getUserAccountSum(custId)), amount);
				}
			}, true);
			if(!AccountEnum.SYSID.equals(custId) &&!AccountEnum.OPTID.equals(custId) && sum < 0){
				//余额不足之后，将数据恢复
				sum = redisTemplate.opsForValue().increment(RedisKeyEnum.getUserAccountSum(custId), amount);
				throw new SourceNotEnoughException(custId + "余额不足");
			}
			long costSum = redisTemplate.opsForValue().increment(RedisKeyEnum.getUserCostSum(custId), amount);
			rrzOrderUserAccount.setAccountSum(sum);
			rrzOrderUserAccount.setCostSum(costSum);
			rrzOrderUserAccount.setUpdateTime(new Date());
		}
		redisTemplate.opsForValue().set(RedisKeyEnum.getRrzOrderUserAccount(rrzOrderUserAccount.getCustId()), GsonUtils.parseJson(rrzOrderUserAccount));
		return rrzOrderUserAccount;
		
		
//		ShardedJedis jedis = null;
//		try {
//			jedis = shardedPool.getSession("ORDER");
//			RrzOrderUserAccount rrzOrderUserAccount = getUserAccount(custId);
//			if(rrzOrderUserAccount == null){
//				logger.warn("系统异常，当前用户不存在，custId:" + custId);
//				throw new RedisOptException("系统异常，当前用户不存在，custId:" + custId);
//			}
//			//加费
//			if(type == 1){ 
//				long sum = RedisString.incrBy(jedis, RedisKeyEnum.getUserAccountSum(custId), amount);
//				rrzOrderUserAccount.setAccountSum(sum);
//				rrzOrderUserAccount.setUpdateTime(new Date());
//			} else {
//				long sum = RedisString.decyBy(jedis, RedisKeyEnum.getUserAccountSum(custId), amount);
//				if(!AccountEnum.SYSID.equals(custId) &&!AccountEnum.OPTID.equals(custId) && sum < 0){
//					//余额不足之后，将数据恢复
//					sum = RedisString.incrBy(jedis, RedisKeyEnum.getUserAccountSum(custId), amount);
//					throw new SourceNotEnoughException(custId + "余额不足");
//				}
//				long costSum = RedisString.incrBy(jedis, RedisKeyEnum.getUserCostSum(custId), amount);
//				rrzOrderUserAccount.setAccountSum(sum);
//				rrzOrderUserAccount.setCostSum(costSum);
//				rrzOrderUserAccount.setUpdateTime(new Date());
//			}
//			RedisString.set(jedis, RedisKeyEnum.getRrzOrderUserAccount(rrzOrderUserAccount.getCustId()), GsonUtils.parseJson(rrzOrderUserAccount));
//			return rrzOrderUserAccount;
//		} finally {
//			shardedPool.releaseSession(jedis, "ORDER");
//		}
	}

	public RrzOrderUserAccount optIntegralSource(String custId , long amount, int type) {
		RrzOrderUserAccount rrzOrderUserAccount = getUserAccount(custId);
		if(rrzOrderUserAccount == null){
			logger.warn("系统异常，当前用户不存在，custId:" + custId);
			throw new RedisOptException("系统异常，当前用户不存在，custId:" + custId);
		}
		//加费
		if(type == 1){ 
			long sum = redisTemplate.opsForValue().increment(RedisKeyEnum.getUserIntegralSum(custId), amount);
			rrzOrderUserAccount.setIntegralSum(sum);
			rrzOrderUserAccount.setUpdateTime(new Date());
		} else {
			long sum = redisTemplate.execute(new RedisCallback<Long>() {

				public Long doInRedis(RedisConnection connection) {
					return connection.decrBy(redisTemplate.getStringSerializer().serialize(RedisKeyEnum.getUserIntegralSum(custId)), amount);
				}
			}, true);
			if(!AccountEnum.SYSID.equals(custId) &&!AccountEnum.OPTID.equals(custId) && sum < 0){
				//余额不足之后，将数据恢复
				sum = redisTemplate.opsForValue().increment(RedisKeyEnum.getUserIntegralSum(custId), amount); 
				throw new SourceNotEnoughException(custId + "积分不足");
			}
			rrzOrderUserAccount.setIntegralSum(sum);
			rrzOrderUserAccount.setUpdateTime(new Date());
		}
		redisTemplate.opsForValue().set(RedisKeyEnum.getRrzOrderUserAccount(rrzOrderUserAccount.getCustId()), GsonUtils.parseJson(rrzOrderUserAccount));
		return rrzOrderUserAccount;
		
		
//		ShardedJedis jedis = null;
//		try {
//			jedis = shardedPool.getSession("ORDER");
//			RrzOrderUserAccount rrzOrderUserAccount = getUserAccount(custId);
//			if(rrzOrderUserAccount == null){
//				logger.warn("系统异常，当前用户不存在，custId:" + custId);
//				throw new RedisOptException("系统异常，当前用户不存在，custId:" + custId);
//			}
//			//加费
//			if(type == 1){ 
//				long sum = RedisString.incrBy(jedis, RedisKeyEnum.getUserIntegralSum(custId), amount);
//				rrzOrderUserAccount.setIntegralSum(sum);
//				rrzOrderUserAccount.setUpdateTime(new Date());
//			} else {
//				long sum = RedisString.decyBy(jedis, RedisKeyEnum.getUserIntegralSum(custId), amount);
//				if(!AccountEnum.SYSID.equals(custId) &&!AccountEnum.OPTID.equals(custId) && sum < 0){
//					//余额不足之后，将数据恢复
//					sum = RedisString.incrBy(jedis, RedisKeyEnum.getUserIntegralSum(custId), amount); 
//					throw new SourceNotEnoughException(custId + "积分不足");
//				}
//				rrzOrderUserAccount.setIntegralSum(sum);
//				rrzOrderUserAccount.setUpdateTime(new Date());
//			}
//			RedisString.set(jedis, RedisKeyEnum.getRrzOrderUserAccount(rrzOrderUserAccount.getCustId()), GsonUtils.parseJson(rrzOrderUserAccount));
//			return rrzOrderUserAccount;
//		} finally {
//			shardedPool.releaseSession(jedis, "ORDER");
//		}
//		return null;
	}

	public void refreshUserAccount(Set<String> custIds) {
		throw new RuntimeException("不支持操作");
	}
	
}
