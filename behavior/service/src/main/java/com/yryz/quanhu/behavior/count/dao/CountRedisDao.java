package com.yryz.quanhu.behavior.count.dao;

import com.google.common.collect.Lists;
import com.yryz.framework.core.cache.RedisTemplateBuilder;
import com.yryz.quanhu.behavior.count.contants.RedisContants;
import com.yryz.quanhu.behavior.count.entity.CountModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private CountMongoDao countMongoDao;

    /**
     * 设置redis里的count值
     *
     * @param kid
     * @param code
     * @param count
     * @return 返回写入key是否命中
     */
    public boolean setCount(String kid, String code, String page, Long count) {
        boolean flag = true;
        String writeCountKey = RedisContants.getWriteCountKey(kid, code, page);
        String readCountKey = RedisContants.getReadCountKey(kid, code, page);
        RedisTemplate<String, Long> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Long.class);
        CountModel countModel = null;
        // 若writeCountKey不存在，从mongdb中查询出值再increment。
        if (!redisTemplate.hasKey(writeCountKey)) {
            countModel = countMongoDao.getLastData(code, kid, "");
            logger.info("setCount code:" + code + ".kid:" + kid);
            if (countModel != null) {
                logger.info("setCount countModel:" + countModel.toString());
                redisTemplate.opsForValue().increment(writeCountKey, countModel.getCount());
            }
            flag = false;
        }
        logger.debug("CountRedisDao setCount. writeCountKey : " + count);
        redisTemplate.opsForValue().increment(writeCountKey, count);
        redisTemplate.expire(writeCountKey, RedisContants.WRITE_COUNT_KEY_EXPIRE, TimeUnit.MINUTES);

        // 若readCountKey不存在，从mongdb中查询出值再increment。
        if (!redisTemplate.hasKey(readCountKey)) {
            countModel = countMongoDao.getLastData(code, kid, "");
            if (countModel != null) {
                redisTemplate.opsForValue().increment(readCountKey, countModel.getCount());
            }
        }
        logger.debug("CountRedisDao setCount. readCountKey : " + count);
        redisTemplate.opsForValue().increment(readCountKey, count);
        redisTemplate.expire(readCountKey, RedisContants.READ_COUNT_KEY_EXPIRE, TimeUnit.MINUTES);
        return flag;
    }

    /**
     * 从redis里查询count值
     *
     * @param kid
     * @param code
     * @return
     */
    public Long getCount(String kid, String code, String page) {
        String readCountKey = RedisContants.getReadCountKey(kid, code, page);
        RedisTemplate<String, Long> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Long.class);
        Long count = 0L;
        if (!redisTemplate.hasKey(readCountKey)) {
            //redis中不存在key，从mongodb中查询
            CountModel countModel = countMongoDao.getLastData(code, kid, "");
            if (countModel != null) {
                count = countModel.getCount();
            }
            redisTemplate.opsForValue().increment(readCountKey, count);
            redisTemplate.expire(readCountKey, RedisContants.READ_COUNT_KEY_EXPIRE, TimeUnit.MINUTES);
        }
        count = redisTemplate.opsForValue().get(readCountKey);
        return count;
    }

    /**
     * 从redis里查询count值
     *
     * @param kids
     * @param code
     * @return
     */
    public List<Long> getCount(List<Long> kids, String code, String page) {
        List<String> keys = Lists.newArrayList();
        for (Long kid : kids) {
            if (kid == null) {
                keys.add(RedisContants.getReadCountKey("", code, page));
                continue;
            }
            keys.add(RedisContants.getReadCountKey(kid.toString(), code, page));
        }
        RedisTemplate<String, Long> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Long.class);
        //批量查询redis的keys
        List<Long> list = redisTemplate.opsForValue().multiGet(keys);
        //补redis查不到值
        for (int i = 0; i < list.size(); i++) {
            Long count = list.get(i);
            //count=null表示未从redis中查询到值
            if (count == null) {
                String kid = kids.get(i).toString();
                //redis中不存在key，从mongodb中查询
                CountModel countModel = countMongoDao.getLastData(code, kid, "");
                if (countModel != null) {
                    count = countModel.getCount();
                }
                redisTemplate.opsForValue().increment(RedisContants.getReadCountKey(kid, code, page), count);
                redisTemplate.expire(RedisContants.getReadCountKey(kid, code, page), RedisContants.READ_COUNT_KEY_EXPIRE, TimeUnit.MINUTES);
            }
            list.set(i, count);
        }
        return list;
    }
}
