package com.yryz.common.distributed.lock;

import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 基于Redis的分布式锁
 * @author xiepeng
 * @version 1.0
 * @date 2018年1月17日15:11:41
 */
@Service
public class DistributedLockManager {

    private static final Logger LOG = LoggerFactory.getLogger(DistributedLockManager.class);

    //纳秒和毫秒之间的转换率
    private static final long MILLI_NANO_TIME = 1000 * 1000L;

    private static final Random RANDOM = new Random();

    private static final String LOCKED = "LOCKED";

    //锁超时时间，默认500ms
    private static final long DEFAULT_TIMEOUT = 500;
    //锁失效时间，默认2s
    private static final int DEFAULT_EXPIRED_TIME = 2;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

     /**
     * 组装分布式锁前缀
     *
     * @param prefix
     * @return
     */
    private String buildPrefix(String prefix) {
        return "QSOURCE_LOCK_" + prefix;
    }

    /**
     * 加锁
     *
     * @param prefix 业务前缀
     * @param ids    加锁的业务ID，多个用英文逗号分隔
     * @return 已经加锁的ids
     */
    public String lock(String prefix, String ids) {
        return lock(prefix, ids, DEFAULT_TIMEOUT);
    }

    /**
     * 加锁
     *
     * @param prefix  业务前缀
     * @param ids     加锁的业务ID，多个用英文逗号分隔
     * @param timeout 加锁超时时间
     * @return 已经加锁的ids
     */
    public String lock(String prefix, String ids, long timeout) {
        return lock(prefix, ids, timeout, DEFAULT_EXPIRED_TIME);
    }

    /**
     * 加锁
     *
     * @param prefix      业务前缀
     * @param ids         加锁的业务ID，多个用英文逗号分隔
     * @param timeout     加锁超时时间
     * @param expiredTime 锁失效时间
     * @return 已经加锁的ids
     */
    public String lock(String prefix, String ids, long timeout, int expiredTime) {
        String lockPrefix = buildPrefix(prefix);
        //批量
        if (ids.contains(",")) {
            List<String> lockedList = new ArrayList<>();
            try {
                for (String id : ids.split(",")) {
                    String key = lockPrefix + "_" + id + "_lock";
                    //加锁
                    lockByKey(key, timeout, expiredTime);
                    lockedList.add(key);
                }
            } catch (Exception e) {
                if (!CollectionUtils.isEmpty(lockedList)) {
                    for (String key : lockedList) {
                        //解锁
                        unlockByKey(key);
                    }
                }
                throw new QuanhuException(ExceptionEnum.LockException.getCode(), ExceptionEnum.LockException.getShowMsg(),
                        "批量获取分布式锁失败");
            }
        } else {
            String key = lockPrefix + "_" + ids + "_lock";
            //加锁
            lockByKey(key, timeout, expiredTime);
        }
        return ids;
    }

    /**
     * 解锁
     *
     * @param prefix 业务前缀
     * @param ids    加锁的业务ID，多个用英文逗号分隔
     */
    public void unlock(String prefix, String ids) {
        String lockPrefix = buildPrefix(prefix);
        //批量
        if (ids.contains(",")) {
            for (String id : ids.split(",")) {
                String key = lockPrefix + "_" + id + "_lock";
                //解锁
                unlockByKey(key);
            }
        } else {
            String key = lockPrefix + "_" + ids + "_lock";
            //解锁
            unlockByKey(key);
        }
    }

    /**
     * 加锁方法
     *
     * @param key     redis的key
     * @param timeout 超时时间
     * @param expire  失效时间
     */
    private void lockByKey(String key, long timeout, int expire) {
        try {
            long nanoTime = System.nanoTime();
            long nanoTimeout = timeout * MILLI_NANO_TIME;
            //在timeout的时间范围内不断轮询锁
            while (System.nanoTime() - nanoTime < nanoTimeout) {
                //锁不存在的话，设置锁并设置锁过期时间，即加锁
                //设置锁过期时间，REDIS到期自动释放，不会造成永久阻塞
                if (setnx(key, LOCKED, expire)) {
                    LOG.info("获取分布式锁成功，KEY={}" + key);
                    return;
                }
                LOG.warn("分布式锁获取等待中...，KEY={}", key);
                //短暂休眠，避免一直轮询，CPU消耗太高
                Thread.sleep(10, RANDOM.nextInt(100));
            }
        } catch (Exception e) {
            LOG.info("获取分布式锁失败，KEY={}", key);
        }
        throw new QuanhuException(ExceptionEnum.LockException.getCode(), ExceptionEnum.LockException.getShowMsg(),
                "获取分布式锁失败，KEY=" + key);
    }

    /**
     * 解锁方法
     *
     * @param key redis的key
     */
    private void unlockByKey(String key) {
        try {
            //删除KEY解锁
            redisTemplate.delete(key);
        } catch (Throwable e) {
            LOG.error("释放分布式锁失败,KEY={}", key);
        }
    }

    /**
     * 判断key是否存在，不存在则设置缓存返回true，存在则返回false
     * @param key 键
     * @param value 值
     * @param cacheSeconds 失效时间，0为不失效
     * @return
     */
    private boolean setnx(String key, String value, int cacheSeconds) {
        boolean result = false;
        try {
            result = redisTemplate.opsForValue().setIfAbsent(key, value);
            if (result && cacheSeconds != 0) {
                redisTemplate.expire(key, cacheSeconds, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            LOG.warn("setnx {} = {}", key, value, e);
        }
        return result;
    }

}
