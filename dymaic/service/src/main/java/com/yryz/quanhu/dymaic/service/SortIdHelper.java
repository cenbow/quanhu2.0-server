package com.yryz.quanhu.dymaic.service;

import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.framework.core.cache.RedisTemplateBuilder;
import com.yryz.framework.core.lock.DistributedLockManager;
import com.yryz.quanhu.dymaic.dao.DymaicDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author xiepeng
 * @version 1.0
 * @data 2018/2/1 0001 29
 */
@Service
public class SortIdHelper {

    private static final Long INIT = 100000L;

    private static final String cacheKey = "dymaic:kid";

    private static final String cacheKeyLok = "dymaic:kid:lock";

    @Autowired
    private DymaicDao dymaicDao;

    @Autowired
    private RedisTemplateBuilder redisTemplateBuilder;

    @Autowired
    private DistributedLockManager lockManager;

    private final String LockPrefix = "dymaic";
    private final String LockName = "id";

    /**
     * 获取有序ID
     * @return
     */
    public Long getKid() {
        Long result = null;

        try {
            lockManager.lock(LockPrefix, LockName);

            //from cache
            RedisTemplate<String, Long> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Long.class);
            result = redisTemplate.opsForValue().get(cacheKey);

            if (result == null || result < 1) {
                //from db
                Long maxKid = dymaicDao.getMaxKid();
                if (maxKid == null || maxKid < 1) {
                    result = INIT;
                } else {
                    result = maxKid + 1;
                }
                redisTemplate.opsForValue().set(cacheKey, result);
            } else {
                result = redisTemplate.opsForValue().increment(cacheKey, 1L);
            }

            lockManager.unlock(LockPrefix, LockName);
        } catch (Exception e) {
            lockManager.unlock(LockPrefix, LockName);
            throw new QuanhuException(ExceptionEnum.LockException);
        }

        return result;
    }
}
