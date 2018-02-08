package com.yryz.quanhu.behavior.gift.service.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.yryz.common.response.PageList;
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

    @Autowired
    private GiftInfoDao giftInfoDao;

    protected RedisTemplate<String, GiftInfo> redisTemplateV;

    protected RedisTemplate<String, PageList> redisTemplateL;

    @Autowired
    protected RedisTemplateBuilder redisTemplateBuilder;

    @PostConstruct
    public void init() {
        redisTemplateV = redisTemplateBuilder.buildRedisTemplate(GiftInfo.class);
        redisTemplateL = redisTemplateBuilder.buildRedisTemplate(PageList.class);
    }

    public String getCacheKey(Object key) {
        return "QH:GiftInfo:Key_" + key;
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

    @Override
    public PageList<GiftInfo> pageByCondition(GiftInfoDto dto, boolean isCount) {
        PageList<GiftInfo> pageList = new PageList<>();
        pageList = redisTemplateL.opsForValue().get(
                this.getCacheKey("pageByCondition:" + dto.getCurrentPage() + "_" + dto.getPageSize() + "_" + isCount));
        if (null != pageList) {
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

        redisTemplateL.opsForValue().set(
                this.getCacheKey("pageByCondition:" + dto.getCurrentPage() + "_" + dto.getPageSize() + "_" + isCount),
                pageList);

        return pageList;
    }

    @Override
    public List<GiftInfo> selectByKids(Set<Long> kids) {
        GiftInfoDto dto = new GiftInfoDto();
        dto.setKids((Long[]) kids.toArray());
        return this.getDao().selectByCondition(dto);
    }

    @Override
    public GiftInfo selectByKid(Long kid) {
        GiftInfo giftInfo = redisTemplateV.opsForValue().get(this.getCacheKey("kid_" + kid));
        if (null != giftInfo) {
            return giftInfo;
        }

        giftInfo = this.getDao().selectByKid(kid);
        redisTemplateV.opsForValue().set(this.getCacheKey("kid_" + kid), giftInfo);

        return giftInfo;
    }

    @Override
    public int updateByKid(GiftInfo record) {
        redisTemplateV.delete(this.getCacheKey("kid_" + record.getKid()));
        redisTemplateL.delete(this.getCacheKey("pageByCondition"));
        return this.getDao().updateByKid(record);
    }

}
