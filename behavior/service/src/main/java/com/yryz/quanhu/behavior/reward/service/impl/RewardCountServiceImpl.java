package com.yryz.quanhu.behavior.reward.service.impl;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.yryz.common.constant.CommonConstants;
import com.yryz.common.context.Context;
import com.yryz.framework.core.cache.RedisTemplateBuilder;
import com.yryz.quanhu.behavior.reward.dao.RewardCountDao;
import com.yryz.quanhu.behavior.reward.entity.RewardCount;
import com.yryz.quanhu.behavior.reward.service.RewardCountService;

/**
* @author wangheng
* @date 2018年1月29日 下午10:52:42
*/
@Service
public class RewardCountServiceImpl implements RewardCountService {

    @Autowired
    private RewardCountDao rewardCountDao;

    private RedisTemplate<String, RewardCount> redisTemplate;

    @Autowired
    private RedisTemplateBuilder redisTemplateBuilder;

    @PostConstruct
    public void init() {
        redisTemplate = redisTemplateBuilder.buildRedisTemplate(RewardCount.class);
    }
    
    private String getCacheKey(Object key) {
        return Context.getProperty(CommonConstants.SPRING_APPLICATION_NAME) + ":RewardCount:Key_" + key;
    }

    public RewardCountDao getDao() {
        return this.rewardCountDao;
    }

    @Override
    public int addCountByTargetId(RewardCount record) {
        Assert.notNull(record.getTargetId(), "record.getTargetId() is null !");
        if (null == this.selectByTargetId(record.getTargetId())) {
            return this.getDao().insertSelective(record);
        }

        int upRow = this.getDao().addCountByTargetId(record);
        if (upRow > 0) {
            redisTemplate.delete(this.getCacheKey("targetId:" + record.getTargetId()));
        }

        return upRow;
    }

    @Override
    public RewardCount selectByTargetId(Long targetId) {
        // 优先取缓存
        String cacheKey = this.getCacheKey("targetId:" + targetId);
        RewardCount rewardCount = redisTemplate.opsForValue().get(cacheKey);
        if (null != rewardCount) {
            return rewardCount;
        }
        // 查询db
        rewardCount = this.getDao().selectByTargetId(targetId);
        // 放置缓存
        redisTemplate.opsForValue().set(cacheKey, rewardCount, 24, TimeUnit.HOURS);
        return rewardCount;
    }
}
