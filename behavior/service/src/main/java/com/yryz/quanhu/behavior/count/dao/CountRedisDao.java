package com.yryz.quanhu.behavior.count.dao;

import com.yryz.framework.core.cache.RedisTemplateBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yryz.quanhu.behavior.count.contants.RedisContants;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: liupan
 * @Path: com.yryz.quanhu.behavior.count.dao
 * @Desc:
 * @Date: 2018/1/23.
 */
@Service
public class CountRedisDao {
    private static Logger logger = LoggerFactory.getLogger(CountRedisDao.class);

    @Resource
    private RedisTemplateBuilder redisTemplateBuilder;

    public void setCount(String kid, String code, Long count) {
        String writeCountKey = RedisContants.getWriteCountKey(kid, code);
        String readCountKey = RedisContants.getReadCountKey(kid, code);
        RedisTemplate<String, Long> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Long.class);
        //TODO 若key不存在，从mongdb中查询出值再increment。
        redisTemplate.opsForValue().increment(writeCountKey, count);
        redisTemplate.opsForValue().increment(readCountKey, count);
    }

    public Long getCount(String kid, String code) {
        String readCountKey = RedisContants.getReadCountKey(kid, code);
        RedisTemplate<String, Long> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Long.class);
        return redisTemplate.opsForValue().get(readCountKey);
    }
}
