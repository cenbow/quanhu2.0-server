package com.yryz.quanhu.message.message.redis;

import com.yryz.common.exception.RedisOptException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description:
 * @Date: Created in 2018 2018/1/22 16:29
 * @Author: pn
 */
@Service
public class MessageRedis {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageRedis.class);

    public final static int LOCK_EXPIRE_DEFAULT = 60;

    public static final String SOURCE_CIRCLE = "CIRCLE";

    //public static final String MESSAGE = "qh.message.";

    public static final String MESSAGE = "message.";

    public static String getMessagekey(String userId) {
        return MESSAGE + userId;
    }

    @Autowired
    private StringRedisTemplate redisTemplate;

    public Map<String, String> getUnread(String userId) {
        Map<String, String> map = null;
        try {
            Map<Object, Object> objectMap = redisTemplate.opsForHash().entries(getMessagekey(userId));
            map = new LinkedHashMap<>();
            if (!CollectionUtils.isEmpty(objectMap)) {
                for (Object o : objectMap.keySet()) {
                    if (o != null && objectMap.get(o) != null) {
                        map.put(o.toString(), objectMap.get(o).toString());
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("[addUnread] redis获取未读消息异常", e);
            throw new RedisOptException(e);
        }
        return map;
    }

    public Boolean clearUnread(String userId, String readType) {
        //组装redis锁的hash的key，hashkey
        String key = getMessagekey(userId);
        String lockKey = key + ".lock";
        //上锁
        try {
            Boolean lock = redisTemplate.opsForValue().setIfAbsent(lockKey, "LOCK");
            if (lock) {
                //设置锁失效时间,如果不成功则抛异常
                if (!redisTemplate.expire(lockKey, LOCK_EXPIRE_DEFAULT, TimeUnit.SECONDS)) {
                    redisTemplate.delete(lockKey);
                    throw new RedisOptException("++++++++++++++++++++++++cleanUnread exception++++++++++++++++");
                }
                redisTemplate.opsForHash().put(key, readType, "0");
                redisTemplate.delete(lockKey);
                return true;
            } else {
                return false;
            }
        } catch (RedisOptException e) {
            redisTemplate.delete(lockKey);
            LOGGER.error("addUnread exception");
            throw new RedisOptException(e);
        }
    }
}
