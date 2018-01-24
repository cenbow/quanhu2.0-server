package com.yryz.quanhu.behavior.count.service.impl;

import com.google.common.collect.Maps;
import com.yryz.quanhu.behavior.count.contants.BehaviorEnum;
import com.yryz.quanhu.behavior.count.contants.RedisContants;
import com.yryz.quanhu.behavior.count.dao.CountRedisDao;
import com.yryz.quanhu.behavior.count.service.CountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.GC;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author: liupan
 * @Path: com.yryz.quanhu.behavior.count.service.impl
 * @Desc: 计数服务实现类
 * @Date: 2018/1/23.
 */
@Service
public class CountServiceImpl implements CountService {

    @Autowired
    private static CountRedisDao countRedisDao;

    /**
     * mapA和mapB的开关，true mapA ，false mapB
     */
    private static boolean flag = true;

    private static Map<String, Long> mapA = Maps.newConcurrentMap();

    private static Map<String, Long> mapB = Maps.newConcurrentMap();

    private static ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    static {
        executorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                Map<String, Long> map;
                if (flag) {
                    flag = false;
                    map = Maps.newHashMap(mapA);
                    mapA.clear();
                } else {
                    flag = true;
                    map = Maps.newHashMap(mapB);
                    mapB.clear();
                }
                //从一级缓存map中数据同步至redis中
                map.forEach((key, count) -> {
                    countRedisDao.setCount(key.split("_")[0], key.split("_")[1], count);
                });
            }
        }, 1, 3, TimeUnit.MINUTES);
    }


    /**
     * @param behaviorEnum
     * @param kid
     * @param count
     */
    @Override
    public void countCommit(BehaviorEnum behaviorEnum, String kid, Long count) {
        String memoryCountKey = kid + "_" + behaviorEnum.getCode();
        // TODO 考虑下是否需要sycn，1.开关切换的过程中是否有问题。2.map的value值递增时的原子性
        if (flag) {
            mapA.put(memoryCountKey, mapA.getOrDefault(memoryCountKey, 0L) + count);
        } else {
            mapB.put(memoryCountKey, mapB.getOrDefault(memoryCountKey, 0L) + count);
        }
    }

    @Override
    public Long getCount(String kid, String code) {
        return countRedisDao.getCount(kid, code);
    }
}
