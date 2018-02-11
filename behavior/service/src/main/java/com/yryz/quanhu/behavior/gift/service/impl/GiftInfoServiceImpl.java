package com.yryz.quanhu.behavior.gift.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.yryz.common.constant.CommonConstants;
import com.yryz.common.context.Context;
import com.yryz.common.response.PageList;
import com.yryz.common.utils.BeanUtils;
import com.yryz.common.utils.PageUtils;
import com.yryz.framework.core.cache.RedisTemplateBuilder;
import com.yryz.quanhu.behavior.gift.dao.GiftInfoDao;
import com.yryz.quanhu.behavior.gift.dto.GiftInfoDto;
import com.yryz.quanhu.behavior.gift.entity.GiftInfo;
import com.yryz.quanhu.behavior.gift.service.GiftInfoService;

/**
* @author wangheng
* @date 2018年1月26日 下午2:55:05
*/
@Service
public class GiftInfoServiceImpl implements GiftInfoService {

    private Logger logger = LoggerFactory.getLogger(GiftInfoServiceImpl.class);

    @Autowired
    private GiftInfoDao giftInfoDao;

    private RedisTemplate<String, GiftInfo> redisTemplate;

    @Autowired
    private RedisTemplateBuilder redisTemplateBuilder;

    @PostConstruct
    public void init() {
        redisTemplate = redisTemplateBuilder.buildRedisTemplate(GiftInfo.class);
    }

    private String getCacheKey(Object key) {
        return Context.getProperty(CommonConstants.SPRING_APPLICATION_NAME) + ":GiftInfo:Key_" + key;
    }

    private GiftInfoDao getDao() {
        return this.giftInfoDao;
    }

    @Override
    public int insertSelective(GiftInfo record) {
        return this.getDao().insertSelective(record);
    }

    @Override
    public List<GiftInfo> selectByCondition(GiftInfoDto dto) {
        return this.getDao().selectByCondition(dto);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public PageList<GiftInfo> pageByCondition(GiftInfoDto dto, boolean isCount) {
        // 优先取缓存数据
        RedisTemplate<String, PageList> redisTemplateP = redisTemplateBuilder.buildRedisTemplate(PageList.class);
        final String cacheKey = this
                .getCacheKey("pageByCondition:" + BeanUtils.getNotNullPropertyValue(dto, "_") + "_" + isCount);
        PageList<GiftInfo> pageList = redisTemplateP.opsForValue().get(cacheKey);
        if (null != pageList) {
            logger.info("PageList<GiftInfo> pageByCondition() ,命中缓存数据直接返回; cacheKey:" + cacheKey);
            return pageList;
        }
        pageList = new PageList<>();

        pageList.setCurrentPage(dto.getCurrentPage());
        pageList.setPageSize(dto.getPageSize());

        PageUtils.startPage(dto.getCurrentPage(), dto.getPageSize(), false);
        List<GiftInfo> list = this.getDao().selectByCondition(dto);
        pageList.setEntities(list);

        if (isCount && CollectionUtils.isNotEmpty(list)) {
            pageList.setCount(this.getDao().countByCondition(dto));
        } else {
            pageList.setCount(0L);
        }

        redisTemplateP.opsForValue().set(cacheKey, pageList, 30, TimeUnit.DAYS);

        return pageList;
    }

    /**  
    * @Override
    * @Description: 查询出来的结果集无排序【注意】
    * @author wangheng
    * @param @param kids
    * @param @return
    * @throws  
    */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public List<GiftInfo> selectByKids(Set<Long> kids) {
        List<GiftInfo> result = new ArrayList<>();
        if (CollectionUtils.isEmpty(kids)) {
            return result;
        }

        // 优先从缓存中取，没有再从数据库查
        RedisTemplate<String, List> redisTemplateL = redisTemplateBuilder.buildRedisTemplate(List.class);
        final String cacheKey = this.getCacheKey("kids:" + StringUtils.join(kids, ","));
        result = redisTemplateL.opsForValue().get(cacheKey);
        if (CollectionUtils.isNotEmpty(result)) {
            logger.info("List<GiftInfo> selectByKids() ,命中缓存数据直接返回; cacheKey:" + cacheKey);
            return result;
        } else {
            result = new ArrayList<>();
        }

        // 取缓存 单条记录，得到待取数据库id集合
        Iterator<Long> it = kids.iterator();
        while (it.hasNext()) {
            Long kid = it.next();
            GiftInfo giftInfo = redisTemplate.opsForValue().get(this.getCacheKey("kid:" + kid));
            if (null != giftInfo) {
                result.add(giftInfo);
                it.remove();
            }
        }

        // 部分id，需要從DB取記錄
        if (CollectionUtils.isNotEmpty(kids)) {
            GiftInfoDto dto = new GiftInfoDto();
            dto.setKids(kids.toArray(new Long[] {}));
            List<GiftInfo> dbList = this.getDao().selectByCondition(dto);
            result.addAll(dbList);
        }

        // 放置緩存
        redisTemplateL.opsForValue().set(cacheKey, result, 24, TimeUnit.HOURS);
        return result;
    }

    @Override
    public GiftInfo selectByKid(Long kid) {
        GiftInfo giftInfo = redisTemplate.opsForValue().get(this.getCacheKey("kid:" + kid));
        if (null != giftInfo) {
            return giftInfo;
        }

        giftInfo = this.getDao().selectByKid(kid);
        redisTemplate.opsForValue().set(this.getCacheKey("kid:" + kid), giftInfo, 30, TimeUnit.DAYS);

        return giftInfo;
    }

    @Override
    public int updateByKid(GiftInfo record) {
        int upRow = this.getDao().updateByKid(record);
        if (upRow > 0) {
            // 删除相关 缓存
            Set<String> keys = new HashSet<>();
            keys.add(this.getCacheKey("kid:" + record.getKid()));
            keys.addAll(redisTemplate.keys(this.getCacheKey("pageByCondition:" + "*")));
            keys.addAll(redisTemplate.keys(this.getCacheKey("kids:" + "*" + record.getKid() + "*")));

            if (CollectionUtils.isNotEmpty(keys)) {
                redisTemplate.delete(keys);
            }
        }
        return upRow;
    }

}
