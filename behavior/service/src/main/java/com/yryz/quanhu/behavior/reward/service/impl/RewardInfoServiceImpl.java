package com.yryz.quanhu.behavior.reward.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.yryz.common.constant.CommonConstants;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.context.Context;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.utils.BeanUtils;
import com.yryz.common.utils.PageUtils;
import com.yryz.framework.core.cache.RedisTemplateBuilder;
import com.yryz.quanhu.behavior.reward.constants.RewardConstants;
import com.yryz.quanhu.behavior.reward.dao.RewardInfoDao;
import com.yryz.quanhu.behavior.reward.dto.RewardInfoDto;
import com.yryz.quanhu.behavior.reward.entity.RewardInfo;
import com.yryz.quanhu.behavior.reward.service.RewardInfoService;
import com.yryz.quanhu.behavior.reward.vo.RewardFlowVo;
import com.yryz.quanhu.behavior.reward.vo.RewardInfoVo;

/**
* @author wangheng
* @date 2018年1月27日 上午11:47:30
*/
@Service
public class RewardInfoServiceImpl implements RewardInfoService {

    private Logger logger = LoggerFactory.getLogger(RewardInfoServiceImpl.class);

    @Autowired
    private RewardInfoDao rewardInfoDao;

    @Autowired
    private RedisTemplateBuilder redisTemplateBuilder;

    private RedisTemplate<String, Object> redisTemplate;

    private RewardInfoDao getDao() {
        return this.rewardInfoDao;
    }

    private String getCacheKey(Object key) {
        return Context.getProperty(CommonConstants.SPRING_APPLICATION_NAME) + ":RewardInfo:Key_" + key;
    }

    @PostConstruct
    public void init() {
        redisTemplate = redisTemplateBuilder.buildRedisTemplate(Object.class);
    }

    @Override
    public int insertSelective(RewardInfo record) {
        return this.getDao().insertSelective(record);
    }

    @Override
    public List<RewardInfoVo> selectByCondition(RewardInfoDto dto) {
        return this.getDao().selectByCondition(dto);
    }

    @Override
    public PageList<RewardInfoVo> pageByCondition(RewardInfoDto dto, boolean isCount) {
        RedisTemplate<String, PageList<RewardInfoVo>> redisTemplateP = redisTemplateBuilder
                .buildRedisTemplate(new TypeReference<PageList<RewardInfoVo>>() {});

        // 優先取緩存
        String cacheKey = this
                .getCacheKey("pageByCondition:" + BeanUtils.getNotNullPropertyValue(dto, "_") + "_" + isCount);
        PageList<RewardInfoVo> pageList = redisTemplateP.opsForValue().get(cacheKey);
        if (null != pageList) {
            logger.info("PageList<RewardInfoVo> pageByCondition() ,命中缓存数据直接返回; cacheKey:" + cacheKey);
            return pageList;
        } else {
            pageList = new PageList<>();
        }

        pageList.setCurrentPage(dto.getCurrentPage());
        pageList.setPageSize(dto.getPageSize());

        PageUtils.startPage(dto.getCurrentPage(), dto.getPageSize(), false);
        List<RewardInfoVo> list = this.getDao().selectByCondition(dto);
        pageList.setEntities(list);

        if (isCount && CollectionUtils.isNotEmpty(list)) {
            pageList.setCount(this.getDao().countByCondition(dto));
        }

        // db數據存儲至緩存
        redisTemplateP.opsForValue().set(cacheKey, pageList, 24, TimeUnit.HOURS);

        return pageList;
    }

    @Override
    public RewardInfo selectByKid(Long id) {
        return this.getDao().selectByKid(id);
    }

    @Override
    public int updateByKid(RewardInfo record) {
        int upRow = this.getDao().updateByKid(record);
        // 更新成功后清理緩存
        if (upRow > 0 && RewardConstants.reward_status_pay_success.equals(record.getRewardStatus())) {
            Set<String> keys = new HashSet<>();

            if (null != record.getCreateUserId()) {
                keys.addAll(redisTemplate
                        .keys(this.getCacheKey("pageByCondition:" + "*" + record.getCreateUserId() + "*")));
                keys.addAll(redisTemplate.keys(this.getCacheKey("selectRewardFlow:" + record.getCreateUserId() + "*")));
            }
            if (null != record.getToUserId()) {
                keys.addAll(redisTemplate
                        .keys(this.getCacheKey("pageByCondition:" + "*" + record.getToUserId() + "*")));
                keys.addAll(redisTemplate.keys(this.getCacheKey("selectRewardFlow:" + record.getToUserId() + "*")));
            }

            if (null != record.getResourceId()) {
                keys.addAll(
                        redisTemplate.keys(this.getCacheKey("pageByCondition:" + "*" + record.getResourceId() + "*")));
            }

            if (CollectionUtils.isNotEmpty(keys)) {
                redisTemplate.delete(keys);
            }
        }

        return upRow;
    }

    @Override
    public PageList<RewardFlowVo> selectRewardFlow(Long userId, Integer currentPage, Integer pageSize) {
        if (null == userId) {
            throw new QuanhuException(ExceptionEnum.ValidateException, "用户ID不能为空");
        }
        if (null == currentPage) {
            throw new QuanhuException(ExceptionEnum.ValidateException, "页码不能为空");
        }
        if (null == pageSize) {
            throw new QuanhuException(ExceptionEnum.ValidateException, "每页条数不能为空");
        }

        // 優先取緩存
        String cacheKey = this.getCacheKey("selectRewardFlow:" + userId + "_" + currentPage + "_" + pageSize);
        RedisTemplate<String, PageList<RewardFlowVo>> redisTemplateP = redisTemplateBuilder
                .buildRedisTemplate(new TypeReference<PageList<RewardFlowVo>>() {
                });
        PageList<RewardFlowVo> pageList = redisTemplateP.opsForValue().get(cacheKey);
        if (null != pageList) {
            logger.info("PageList<RewardFlowVo> selectRewardFlow() ,命中缓存数据直接返回; cacheKey:" + cacheKey);
            return pageList;
        } else {
            pageList = new PageList<>();
        }

        pageList.setCurrentPage(currentPage);
        pageList.setPageSize(pageSize);
        //开启分页
        PageUtils.startPage(currentPage, pageSize, false);
        pageList.setEntities(this.getDao().selectRewardFlow(userId));

        // 查询db后存储缓存
        redisTemplateP.opsForValue().set(cacheKey, pageList, 24, TimeUnit.DAYS);

        return pageList;
    }

}
