package com.yryz.quanhu.order.dao.redis;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.order.entity.RrzOrderUserPhy;

import redis.clients.jedis.ShardedJedis;

/**
 * @author yehao
 * @version 1.0
 * @date 2017年10月16日 下午5:29:52
 * @Description 用户安全信息缓存
 */
@Service
public class RrzOrderUserPhyDaoRedis {

	private Logger logger = Logger.getLogger(getClass());
	
	@Resource
	private RedisTemplate<String, String> redisTemplate;
	
	public static final int BANCHECK_UPPER = 5;

	public void save(RrzOrderUserPhy rrzOrderUserPhy) {
		redisTemplate.opsForValue().set(RedisKeyEnum.getRrzOrderUserPhy(rrzOrderUserPhy.getCustId()),
					GsonUtils.parseJson(rrzOrderUserPhy));
		
//		ShardedJedis jedis = null;
//		try {
//			jedis = shardedPool.getSession("ORDER");
//			RedisString.set(jedis, RedisKeyEnum.getRrzOrderUserPhy(rrzOrderUserPhy.getCustId()),
//					GsonUtils.parseJson(rrzOrderUserPhy));
//		} finally {
//			shardedPool.releaseSession(jedis, "ORDER");
//		}
	}

	public void update(RrzOrderUserPhy rrzOrderUserPhy) {
		redisTemplate.opsForValue().set(RedisKeyEnum.getRrzOrderUserPhy(rrzOrderUserPhy.getCustId()),
				GsonUtils.parseJson(rrzOrderUserPhy));
		
//		ShardedJedis jedis = null;
//		try {
//			jedis = shardedPool.getSession("ORDER");
//			RedisString.set(jedis, RedisKeyEnum.getRrzOrderUserPhy(rrzOrderUserPhy.getCustId()),
//					GsonUtils.parseJson(rrzOrderUserPhy));
//		} finally {
//			shardedPool.releaseSession(jedis, "ORDER");
//		}
	}

	public RrzOrderUserPhy get(String custId) {
		String val = redisTemplate.opsForValue().get(RedisKeyEnum.getRrzOrderUserPhy(custId));
		if(StringUtils.isNotEmpty(val)){
			return GsonUtils.json2Obj(val, RrzOrderUserPhy.class);
		} else {
			return null;
		}
		
//		ShardedJedis jedis = null;
//		try {
//			jedis = shardedPool.getSession("ORDER");
//			return (RrzOrderUserPhy) GsonUtils.json2Obj(RedisString.get(jedis, RedisKeyEnum.getRrzOrderUserPhy(custId)),
//					RrzOrderUserPhy.class);
//		} finally {
//			shardedPool.releaseSession(jedis, "ORDER");
//		}
	}

	public boolean banCheck(String custId) {
		boolean flag = false;
		String val = redisTemplate.opsForValue().get(RedisKeyEnum.getBanCheck(custId));
		if (StringUtils.isEmpty(val)) {
			flag = true;
		} else {
			int i = Integer.parseInt(val);
			if (i >= BANCHECK_UPPER) {
				flag = false;
			} else {
				flag = true;
			}
		}
		return flag;
		
		
//		boolean flag = false;
//		ShardedJedis jedis = null;
//		try {
//			jedis = shardedPool.getSession("ORDER");
//			String val = RedisString.get(jedis, RedisKeyEnum.getBanCheck(custId));
//			if (StringUtils.isEmpty(val)) {
//				flag = true;
//			} else {
//				int i = Integer.parseInt(val);
//				if (i >= BANCHECK_UPPER) {
//					flag = false;
//				} else {
//					flag = true;
//				}
//			}
//		} finally {
//			shardedPool.releaseSession(jedis, "ORDER");
//		}
//		return flag;
	}

	public int increaseBan(String custId) {
		int i = 0;
		String val = redisTemplate.opsForValue().get(RedisKeyEnum.getBanCheck(custId));
		if (StringUtils.isEmpty(val)) {
			i = 1;
		} else {
			i = Integer.parseInt(val) + 1;
		}
		redisTemplate.opsForValue().set(RedisKeyEnum.getBanCheck(custId), i + "", getExTime(),TimeUnit.SECONDS);
		return i;
		
//		ShardedJedis jedis = null;
//		int i = 0;
//		try {
//			jedis = shardedPool.getSession("ORDER");
//			String val = RedisString.get(jedis, RedisKeyEnum.getBanCheck(custId));
//			
//			if (StringUtils.isEmpty(val)) {
//				i = 1;
//			} else {
//				i = Integer.parseInt(val) + 1;
//			}
//			RedisString.setExpire(jedis, RedisKeyEnum.getBanCheck(custId), (int) getExTime(), i + "");
//		} finally {
//			shardedPool.releaseSession(jedis, "ORDER");
//		}
//		return i;
	}

	private static long getExTime() {
		Date date = new Date();
		Date date2 = null;
		date2 = getTimeOf12AmOfNextDay();
		return (date2.getTime() - date.getTime()) / 1000;
	}

	/**
	 * @author zhangkun
	 * @date 2014年6月7日
	 * @return
	 * @throws ParseException
	 * @Description 获取第二天凌晨零点零分零秒
	 */
	public static Date getTimeOf12AmOfNextDay() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	public void clearBan(String custId) {
		redisTemplate.delete(RedisKeyEnum.getBanCheck(custId));
		
//		ShardedJedis jedis = null;
//		try {
//			jedis = shardedPool.getSession("ORDER");
//			if (RedisString.exists(jedis, RedisKeyEnum.getBanCheck(custId))) {
//				RedisCommon.delKey(jedis, RedisKeyEnum.getBanCheck(custId));
//			}
//		} finally {
//			shardedPool.releaseSession(jedis, "ORDER");
//		}
	}

}
