package com.yryz.common.distributed.lock;

import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.redis.utils.JedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 分布式锁的动态代理，用于方法级别的分布式加解锁<br/>
 * 为@DistributedLock注解服务
 *
 * @author lifan
 */
public class DistributedLockUtils {

    private static final Logger LOG = LoggerFactory.getLogger(DistributedLockUtils.class);

    //纳秒和毫秒之间的转换率
    private static final long MILLI_NANO_TIME = 1000 * 1000L;

    private static final Random RANDOM = new Random();

    private static final String LOCKED = "LOCKED";

    //锁超时时间，默认500ms
    private static final long DEFAULT_TIMEOUT = 500;
    //锁失效时间，默认2s
    private static final int DEFAULT_EXPIRED_TIME = 2;

    /**
     * 组装分布式锁前缀
     *
     * @param prefix
     * @return
     */
    private static String buildPrefix(String prefix) {
        return "QSOURCE_LOCK_" + prefix;
    }

    /**
     * 加锁
     *
     * @param prefix 业务前缀
     * @param ids    加锁的业务ID，多个用英文逗号分隔
     * @return 已经加锁的ids
     */
    public static String lock(String prefix, String ids) {
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
    public static String lock(String prefix, String ids, long timeout) {
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
    public static String lock(String prefix, String ids, long timeout, int expiredTime) {
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
        }
        //单条
        else {
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
    public static void unlock(String prefix, String ids) {
        String lockPrefix = buildPrefix(prefix);
        //批量
        if (ids.contains(",")) {
            for (String id : ids.split(",")) {
                String key = lockPrefix + "_" + id + "_lock";
                //解锁
                unlockByKey(key);
            }
        }
        //单条
        else {
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
    private static void lockByKey(String key, long timeout, int expire) {
        try {
            long nanoTime = System.nanoTime();
            long nanoTimeout = timeout * MILLI_NANO_TIME;
            //在timeout的时间范围内不断轮询锁
            while (System.nanoTime() - nanoTime < nanoTimeout) {
                //锁不存在的话，设置锁并设置锁过期时间，即加锁
                //设置锁过期时间，REDIS到期自动释放，不会造成永久阻塞
                if (JedisUtils.setnx(key, LOCKED, expire) == 1) {
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
    private static void unlockByKey(String key) {
        try {
            //删除KEY解锁
            JedisUtils.del(key);
            LOG.info("释放分布式锁成功，KEY={}", key);
        } catch (Throwable e) {
            LOG.error("释放分布式锁失败,KEY={}", key);
        }
    }

}
