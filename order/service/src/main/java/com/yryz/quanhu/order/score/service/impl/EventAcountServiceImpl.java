package com.yryz.quanhu.order.score.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yryz.quanhu.order.score.dao.persistence.EventAcountDao;
import com.yryz.quanhu.order.score.service.EventAcountService;
import com.yryz.quanhu.score.vo.EventAcount;

import redis.clients.jedis.JedisCommands;

@Transactional
@Service
public class EventAcountServiceImpl implements EventAcountService {
	
	@Autowired
	EventAcountDao eventAcountDao;

	@Override
	public Long save(EventAcount ea) {
		eventAcountDao.save(ea);
		return ea.getId();
	}

	@Override
	public int update(EventAcount ea) {
		return eventAcountDao.update(ea);
	}

	@Override
	public EventAcount getLastAcount(String userId) {
		return eventAcountDao.getLastAcount(userId);
	}

	@Override
	public Long initAcount(String userId) {
		Date now = new Date();
		EventAcount ea = new EventAcount(userId);
		ea.setCreateTime(now);
		ea.setUpdateTime(now);
		ea.setGrowLevel("1");
		eventAcountDao.save(ea);
		return ea.getId();
	}
	
	//一般情况下，这样的实现就能够满足锁的需求了，但是如果在调用 setIfAbsent 方法之后线程挂掉了，即没有给锁定的资源设置过期时间，默认是永不过期，那么这个锁就会一直存在。
	// 所以需要保证设置锁及其过期时间两个操作的原子性， spring data的 RedisTemplate 当中并没有这样的方法。但是在jedis当中是有这种原子操作的方法的，
	//需要通过 RedisTemplate 的 execute 方法获取到jedis里操作命令的对象，代码如下：
	@Override
	public String redislocksset(RedisTemplate<String, String> redisTemplate, String key, String value, String nxxx,
			String expx, long time) {
		String result = null;
		result = redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				JedisCommands commands = (JedisCommands) connection.getNativeConnection();
				return commands.set(key, String.valueOf(value), nxxx, expx, time);
			}
		});
		return result;
	}

	
	

}
