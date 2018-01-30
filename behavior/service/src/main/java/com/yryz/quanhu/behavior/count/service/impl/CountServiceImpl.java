package com.yryz.quanhu.behavior.count.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yryz.common.utils.DateUtils;
import com.yryz.framework.core.cache.RedisTemplateBuilder;
import com.yryz.quanhu.behavior.count.contants.BehaviorEnum;
import com.yryz.quanhu.behavior.count.contants.RedisContants;
import com.yryz.quanhu.behavior.count.dao.CountMongoDao;
import com.yryz.quanhu.behavior.count.dao.CountRedisDao;
import com.yryz.quanhu.behavior.count.entity.CountModel;
import com.yryz.quanhu.behavior.count.service.CountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: liupan
 * @Path: com.yryz.quanhu.behavior.count.service.impl
 * @Desc: 计数服务实现类
 * @Date: 2018/1/23.
 */
@Service
public class CountServiceImpl implements CountService {

    private static Logger logger = LoggerFactory.getLogger(CountServiceImpl.class);

    @Autowired
    private CountRedisDao countRedisDao;

    @Autowired
    private CountMongoDao countMongoDao;

    @Resource
    private RedisTemplateBuilder redisTemplateBuilder;

    /**
     * mapA和mapB的开关，true mapA ，false mapB
     */
    private static boolean flag = true;

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");

    private Lock lock = new ReentrantLock();

    private static Map<String, AtomicLong> mapA = Maps.newConcurrentMap();

    private static Map<String, AtomicLong> mapB = Maps.newConcurrentMap();

    private static Map<String, Long> hitMap = Maps.newConcurrentMap();

    private static ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @PostConstruct
    private void init() {
        executorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, AtomicLong> map;
                    if (flag) {
                        flag = false;
                        map = Maps.newHashMap(mapA);
                        mapA.clear();
                    } else {
                        flag = true;
                        map = Maps.newHashMap(mapB);
                        mapB.clear();
                    }
                    final String date = SDF.format(new Date());
                    final String allKey = date + "ALL";
                    final String hitKey = date + "HIT";
                    //从一级缓存map中数据同步至redis中
                    map.forEach((key, count) -> {
                        hitMap.put(allKey, hitMap.getOrDefault(allKey, 0L) + 1);
                        if (countRedisDao.setCount(key.split("_")[0], key.split("_")[1], key.split("_")[2], count.get())) {
                            hitMap.put(hitKey, hitMap.getOrDefault(hitKey, 0L) + 1);
                        }
                    });
                    map = null;
                } catch (Exception e) {
                    logger.error("scheduleWithFixedDelay error!", e);
                }
            }
        }, 1, 1, TimeUnit.MINUTES);
    }

    /**
     * @param behaviorEnum
     * @param kid
     * @param count
     */
    @Override
    public void commitCount(BehaviorEnum behaviorEnum, String kid, String page, Long count) {
        //判断是否走一级缓存
        if (behaviorEnum.getMemoryFlag()) {
            //先放入内存中
            String memoryCountKey = kid + "_" + behaviorEnum.getCode() + "_" + page;
            // 通过falg判断行为计入mapA还是mapB
            if (flag) {
                if (!mapA.containsKey(memoryCountKey)) {
                    lock.lock();
                    mapA.put(memoryCountKey, new AtomicLong(count));
                    lock.unlock();
                } else {
                    mapA.get(memoryCountKey).addAndGet(count);
                }
            } else {
                if (!mapB.containsKey(memoryCountKey)) {
                    lock.lock();
                    mapB.put(memoryCountKey, new AtomicLong(count));
                    lock.unlock();
                } else {
                    mapB.get(memoryCountKey).addAndGet(count);
                }
            }
        } else {
            final String date = SDF.format(new Date());
            final String allKey = date + "ALL";
            final String hitKey = date + "HIT";
            //直接放入redis中
            hitMap.put(allKey, hitMap.getOrDefault(allKey, 0L) + 1);
            if (countRedisDao.setCount(kid, behaviorEnum.getCode(), page, count)) {
                hitMap.put(hitKey, hitMap.getOrDefault(hitKey, 0L) + 1);
            }

        }
    }

    @Override
    public Long getCount(String kid, String code, String page) {
        return countRedisDao.getCount(kid, code, page);
    }

    @Override
    public void excuteCountSyncMongoJob() {
        List<CountModel> list = Lists.newArrayList();
        try {
            Integer date = new Integer(SDF.format(new Date()));
            LocalDateTime dateTime = LocalDateTime.now();
            RedisTemplate<String, Long> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Long.class);
            for (String key : redisTemplate.keys(RedisContants.WRITE_COUNT_KEY + "*")) {
                String str = key.replace(RedisContants.WRITE_COUNT_KEY, "");
                String[] arr = str.split("_");
                CountModel countModel = new CountModel(arr[1], arr[0], arr[2], redisTemplate.opsForValue().get(key), date, dateTime.getHour());
                list.add(countModel);
            }
        } catch (Exception e) {
            logger.error("excuteCountSyncMongoJob get redis keys error!", e);
        }
        try {
            countMongoDao.batchInsert(list);
        } catch (Exception e) {
            logger.error("excuteCountSyncMongoJob set mongodb error!", e);
        }

    }

    public static void main(String[] args) {
        LocalDateTime dateTime = LocalDateTime.now();
        System.out.print(dateTime.getHour());
    }

}
