package com.yryz.quanhu.dymaic.service;

import com.yryz.framework.core.cache.RedisTemplateBuilder;
import com.yryz.quanhu.dymaic.dao.DymaicTopDao;
import com.yryz.quanhu.dymaic.vo.DymaicVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author xiepeng
 * @version 1.0
 * @data 2018/2/1 0001 53
 */
@Service
public class DymaicTopServiceImpl {

    /**
     * 动态信息
     */
    private final static String KEY_DYNAMIC_TOP = "dymaic:top:";

    /**
     * 动态信息过期时间
     */
    private final static int EXPIRE_DAY = 30;

    @Autowired
    private DymaicTopDao dymaicTopDao;

    @Autowired
    private RedisTemplateBuilder redisTemplateBuilder;

    @Autowired
    private DymaicServiceImpl dymaicServiceImpl;

    private RedisTemplate<String, Long> getRedisTemplate() {
        return redisTemplateBuilder.buildRedisTemplate(Long.class);
    }

    private String getCacheKey(Long userId) {
        return KEY_DYNAMIC_TOP + userId;
    }

    /**
     * 添加置顶动态
     * @param userId
     * @param dymaicId
     */
    public Boolean add(Long userId, Long dymaicId) {
        Long existDymaicId = dymaicTopDao.get(userId);

        if(existDymaicId == null) {
            dymaicTopDao.add(userId, dymaicId);
        } else {
            dymaicTopDao.update(userId, dymaicId);
        }

        String cacheKey = getCacheKey(userId);
        getRedisTemplate().opsForValue().set(cacheKey, dymaicId, EXPIRE_DAY, TimeUnit.DAYS);
        return true;
    }

    /**
     * 获取置顶动态
     * @param userId
     * @return
     */
    public DymaicVo get(Long userId) {
        DymaicVo vo = null;
        String cacheKey = getCacheKey(userId);

        Long dymaicId = getRedisTemplate().opsForValue().get(cacheKey);
        if (dymaicId == null) {
            dymaicId = dymaicTopDao.get(userId);
            if (dymaicId == null) {
                dymaicId = 0L;
            }
            getRedisTemplate().opsForValue().set(cacheKey, dymaicId, EXPIRE_DAY, TimeUnit.DAYS);
        }

        if (dymaicId != null && dymaicId > 0) {
            vo = dymaicServiceImpl.mergeDymaicVo(userId, dymaicId);
        }

        return (vo == null) ? new DymaicVo() : vo;
    }

    /**
     * 删除置顶动态
     * @param userId
     * @param dymaicId
     */
    public Boolean delete(Long userId, Long dymaicId) {
        dymaicTopDao.delete(userId);

        String cacheKey = getCacheKey(userId);
        getRedisTemplate().opsForValue().set(cacheKey, 0L, EXPIRE_DAY, TimeUnit.DAYS);
        return true;
    }

}