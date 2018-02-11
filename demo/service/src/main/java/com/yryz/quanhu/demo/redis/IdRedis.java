/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年2月11日
 * Id: IdRedis.java, 2018年2月11日 上午9:15:36 yehao
 */
package com.yryz.quanhu.demo.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.framework.core.cache.RedisTemplateBuilder;
import com.yryz.framework.core.lock.DistributedLockManager;
//import com.yryz.quanhu.dymaic.dao.DymaicDao;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年2月11日 上午9:15:36
 * @Description TODO (这里用一句话描述这个方法的作用)
 */
@Service
public class IdRedis {
	
    private static final Long INIT = 100000L;

    private static final String cacheKey = "dymaic:kid";

    private static final String cacheKeyLok = "dymaic:kid:lock";

//    @Autowired
//    private DymaicDao dymaicDao;

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

        // from cache
        RedisTemplate<String, Long> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Long.class);
        Long result = redisTemplate.opsForValue().get(cacheKey);

        if (result != null && result > INIT) {
            return redisTemplate.opsForValue().increment(cacheKey, 1L);
        }

        // init
        try {
            lockManager.lock(LockPrefix, LockName);

            //double check
            result = redisTemplate.opsForValue().get(cacheKey);

            if (result == null || result < 1) {
                //from db
                Long maxKid = INIT;
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
