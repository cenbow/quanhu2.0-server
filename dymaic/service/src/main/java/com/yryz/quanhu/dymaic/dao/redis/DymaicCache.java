package com.yryz.quanhu.dymaic.dao.redis;

import com.yryz.framework.core.cache.RedisTemplateBuilder;
import com.yryz.quanhu.dymaic.vo.Dymaic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author xiepeng
 * @version 1.0
 * @data 2018/1/17 0017 35
 */
@Service
public class DymaicCache {

    //动态信息
    private final static String KEY_DYNAMIC_INFO = "dy.i.";
    //发布列表
    private final static String KEY_DYNAMIC_SENDLIST = "dy.s.";
    //timeLine列表
    private final static String KEY_DYNAMIC_TIMELINE = "dy.t.";

    //动态信息过期时间
    private final static int EXPIRE_DAY = 30;

    @Autowired
    private RedisTemplateBuilder redisTemplateBuilder;

    private static String cacheDynamicKey(Long kid) {
        return KEY_DYNAMIC_INFO + kid;
    }

    private static String cacheSendListKey(String userId) {
        return KEY_DYNAMIC_SENDLIST + userId;
    }

    private static String cacheTimeLineKey(String userId) {
        return KEY_DYNAMIC_TIMELINE + userId;
    }

    // dynamic

    /**
     * 添加动态
     *
     * @param dymaic
     */
    public void addDynamic(Dymaic dymaic) {
        RedisTemplate<String, Dymaic> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Dymaic.class);
        final String key = cacheDynamicKey(dymaic.getKid());
        redisTemplate.opsForValue().set(key, dymaic, EXPIRE_DAY, TimeUnit.DAYS);
    }

    /**
     * 批量添加动态
     *
     * @param dymaics
     */
    public void addDynamic(List<Dymaic> dymaics) {
        RedisTemplate<String, Dymaic> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Dymaic.class);
        for (Dymaic dymaic : dymaics) {
            final String key = cacheDynamicKey(dymaic.getKid());
            redisTemplate.opsForValue().set(key, dymaic, EXPIRE_DAY, TimeUnit.DAYS);
        }
    }

    /**
     * 查询动态
     *
     * @param kid
     * @return
     */
    public Dymaic getDynamic(Long kid) {
        RedisTemplate<String, Dymaic> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Dymaic.class);
        final String key = cacheDynamicKey(kid);
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 批量查询动态
     *
     * @param kids
     * @return
     */
    public List<Dymaic> getDynamic(List<Long> kids) {
        RedisTemplate<String, Dymaic> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Dymaic.class);
        List<String> keys = new ArrayList<String>();
        for (Long kid : kids) {
            keys.add(cacheDynamicKey(kid));
        }

        return redisTemplate.opsForValue().multiGet(keys);
    }


    // SendList

    /**
     * 添加sendList
     *
     * @param userId
     * @param kid
     */
    public void addSendList(String userId, Long kid) {
        RedisTemplate<String, Long> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Long.class);
        final String key = cacheSendListKey(userId);
        redisTemplate.opsForZSet().add(key, kid, kid);
    }

    /**
     * 批量添加sendList
     *
     * @param userId
     * @param kids
     */
    public void addSendList(String userId, Set<Long> kids) {
        RedisTemplate<String, Long> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Long.class);
        final String key = cacheSendListKey(userId);
        for (Long kid : kids) {
            redisTemplate.opsForZSet().add(key, kid, kid);
        }
    }

    /**
     * 移除SendList中的Item
     *
     * @param userId
     * @param kid
     */
    public void removeSendList(String userId, Long kid) {
        RedisTemplate<String, Long> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Long.class);
        final String key = cacheSendListKey(userId);
        redisTemplate.opsForZSet().remove(key, kid);
    }

    /**
     * 查询SendList
     *
     * @param userId
     * @param kid
     * @param limit
     * @return
     */
    public Set<Long> rangeSendList(String userId, Long kid, Long limit) {
        RedisTemplate<String, Long> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Long.class);
        final String key = cacheSendListKey(userId);
        Set<Long> sendList = redisTemplate.opsForZSet().reverseRangeByScore(key, 0, kid, 0, limit);
        return sendList;
    }

    /**
     * 全量查询SendList
     *
     * @param userId
     * @return
     */
    public Set<Long> rangeAllSendList(String userId) {
        RedisTemplate<String, Long> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Long.class);
        final String key = cacheSendListKey(userId);
        Set<Long> sendList = redisTemplate.opsForZSet().range(key, 0, -1);
        return sendList;
    }


    // TimeLine

    /**
     * 添加TimeLine
     *
     * @param userId
     * @param kid
     */
    public void addTimeLine(String userId, Long kid) {
        RedisTemplate<String, Long> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Long.class);
        final String key = cacheTimeLineKey(userId);
        redisTemplate.opsForZSet().add(key, kid, kid);
    }

    /**
     * 批量添加timeLine
     *
     * @param userId
     * @param kids
     */
    public void addTimeLine(String userId, Set<Long> kids) {
        RedisTemplate<String, Long> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Long.class);
        final String key = cacheTimeLineKey(userId);
        Set<ZSetOperations.TypedTuple<Long>> values = null;
        for (Long kid : kids) {
            redisTemplate.opsForZSet().add(key, kid, kid);
        }
    }

    /**
     * 移除TimeLine中的Item
     *
     * @param userId
     * @param kid
     */
    public void removeTimeLine(String userId, Long kid) {
        RedisTemplate<String, Long> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Long.class);
        final String key = cacheTimeLineKey(userId);
        redisTemplate.opsForZSet().remove(key, kid);
    }

    /**
     * 批量移除TimeLine中的Item
     *
     * @param userId
     * @param kids
     */
    public void removeTimeLine(String userId, Set<Long> kids) {
        RedisTemplate<String, Long> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Long.class);
        final String key = cacheTimeLineKey(userId);
        redisTemplate.opsForZSet().remove(key, kids.toArray());
    }

    /**
     * 查询TimeLine
     *
     * @param userId
     * @param kid
     * @param limit
     * @return
     */
    public Set<Long> rangeTimeLine(String userId, Long kid, Long limit) {
        RedisTemplate<String, Long> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Long.class);
        final String key = cacheTimeLineKey(userId);
        Set<Long> timeLine = redisTemplate.opsForZSet().reverseRangeByScore(key, 0, kid, 0, limit);
        return timeLine;
    }

}
