package com.yryz.quanhu.resource.advertisement.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.DateUtils;
import com.yryz.common.utils.PageModel;
import com.yryz.common.utils.PageUtils;
import com.yryz.framework.core.cache.RedisTemplateBuilder;
import com.yryz.quanhu.resource.advertisement.constants.AdvertisementConstants;
import com.yryz.quanhu.resource.advertisement.dao.AdvertisementDao;
import com.yryz.quanhu.resource.advertisement.dto.AdvertisementAdminDto;
import com.yryz.quanhu.resource.advertisement.dto.AdvertisementDto;
import com.yryz.quanhu.resource.advertisement.entity.Advertisement;
import com.yryz.quanhu.resource.advertisement.service.AdvertisementService;
import com.yryz.quanhu.resource.advertisement.vo.AdvertisementAdminVo;
import com.yryz.quanhu.resource.advertisement.vo.AdvertisementVo;
import com.yryz.quanhu.support.id.api.IdAPI;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description:
 * @Date: Created in 2018 2018/1/22 10:10
 * @Author: pn
 */
@Service
public class AdvertisementServiceImpl implements AdvertisementService {

    @Autowired
    private AdvertisementDao advertisementDao;

    @Reference(check = false, timeout = 30000, lazy = true)
    private IdAPI idAPI;

    @Autowired
    private RedisTemplateBuilder redisTemplateBuilder;

    //public static final String QH_ADV = "qh_adv";

    //redis key
    public static final String QH_ADVERTISEMENT_ = "qh:advertisement:";

    public static final String QH_ADVERTISEMENT_LIST = "qh:advertisement:list:";

    public static final Integer EXPIRE_DAYS = 7;

    public static final Logger LOGGER = LoggerFactory.getLogger(AdvertisementServiceImpl.class);

    @Override
    public PageList<AdvertisementVo> list(AdvertisementDto advertisementDto) {
        PageUtils.startPage(advertisementDto.getCurrentPage(), advertisementDto.getPageSize());
        advertisementDto.setNowDate(DateUtils.formatDate(new Date(), "yyyy-MM-dd"));
        advertisementDto.setNowTime(DateUtils.formatDate(new Date(), "HH:mm:ss"));
        List<AdvertisementVo> list = null;
        list = advertisementDao.selectList(advertisementDto);
        if (CollectionUtils.isEmpty(list)) {
            list = advertisementDao.selectDefaultAdvertisement();
        }
        return new PageModel<AdvertisementVo>().getPageList(list);
    }

    @Override
    public PageList<AdvertisementAdminVo> listAdmin(AdvertisementAdminDto advertisementAdminDto) {
        if(advertisementAdminDto.getAdvType() == null){
            throw QuanhuException.busiError("广告类型不能为空！");
        }
        RedisTemplate<String, String> redisTemplate = redisTemplateBuilder.buildRedisTemplate(String.class);
        String s = redisTemplate.opsForValue().
                get(QH_ADVERTISEMENT_LIST + advertisementAdminDto.getAdvType() + ":" + advertisementAdminDto.getCurrentPage() + ":" + advertisementAdminDto.getPageSize());
        if (StringUtils.isNotBlank(s)) {
            return JSON.parseObject(s, new TypeReference<PageList<AdvertisementAdminVo>>(){});
        }

        PageUtils.startPage(advertisementAdminDto.getCurrentPage(), advertisementAdminDto.getPageSize());
        List<AdvertisementAdminVo> list = advertisementDao.selectListAdmin(advertisementAdminDto);
        PageList<AdvertisementAdminVo> voPageList = new PageModel<AdvertisementAdminVo>().getPageList(list);

        if (!CollectionUtils.isEmpty(list) && voPageList != null) {
            redisTemplate.opsForValue().set(
                    QH_ADVERTISEMENT_LIST + advertisementAdminDto.getAdvType() + ":" + advertisementAdminDto.getCurrentPage() + ":" + advertisementAdminDto.getPageSize(),
                    JSON.toJSONString(voPageList),
                    EXPIRE_DAYS,
                    TimeUnit.DAYS);
        }

        return voPageList;
    }

    @Override
    @Transactional
    public Integer add(Advertisement advertisement) {
        try {
            Response<Long> response = idAPI.getSnowflakeId();
            Long kid = ResponseUtils.getResponseNotNull(response);
            advertisement.setKid(kid);
            Integer integer = advertisementDao.add(advertisement);

            if (integer != null && integer == 1) {
                AdvertisementVo advertisementVo = advertisementDao.detail(kid);
                if (advertisementVo != null) {
                    RedisTemplate<String, AdvertisementVo> redisTemplate = redisTemplateBuilder.buildRedisTemplate(AdvertisementVo.class);
                    deleteRedisList(redisTemplate, QH_ADVERTISEMENT_LIST + "*");
                    redisTemplate.opsForValue().set(QH_ADVERTISEMENT_ + kid, advertisementVo, EXPIRE_DAYS, TimeUnit.DAYS);
                }
            }

            return integer;
        } catch (Exception e) {
            LOGGER.error("添加失败！", e);
            throw QuanhuException.busiError("添加失败！");
        }
    }

    private void deleteRedisList(RedisTemplate redisTemplate, String key) {
        Set<String> keys = redisTemplate.keys(key);
        if (!CollectionUtils.isEmpty(keys)) {
            redisTemplate.delete(keys);
        }
    }

    @Override
    @Transactional
    public Integer update(Advertisement advertisement) {
        try {
            Long kid = advertisement.getKid();
            if (kid == null) {
                throw QuanhuException.busiError("kid不能为空！");
            }

            int update = advertisementDao.update(advertisement);
            if (update == 1) {
                AdvertisementVo advertisementVo = advertisementDao.detail(kid);
                if (advertisementVo != null) {
                    RedisTemplate<String, AdvertisementVo> redisTemplate = redisTemplateBuilder.buildRedisTemplate(AdvertisementVo.class);
                    deleteRedisList(redisTemplate, QH_ADVERTISEMENT_LIST + "*");
                    redisTemplate.opsForValue().set(QH_ADVERTISEMENT_ + kid, advertisementVo, EXPIRE_DAYS, TimeUnit.DAYS);
                }
            }

            return update;
        } catch (Exception e) {
            LOGGER.error("更新失败！", e);
            throw QuanhuException.busiError("更新失败！");
        }
    }

    @Override
    public AdvertisementVo detail(Long kid) {
        try {
            RedisTemplate<String, AdvertisementVo> redisTemplate = redisTemplateBuilder.buildRedisTemplate(AdvertisementVo.class);
            AdvertisementVo redisVo = redisTemplate.opsForValue().get(QH_ADVERTISEMENT_ + kid);
            if (redisVo != null) {
                return redisVo;
            }

            AdvertisementVo advertisementVo = advertisementDao.detail(kid);
            if (advertisementVo != null) {
                redisTemplate.opsForValue().set(QH_ADVERTISEMENT_ + kid, advertisementVo, EXPIRE_DAYS, TimeUnit.DAYS);
            }

            return advertisementVo;
        } catch (Exception e) {
            LOGGER.error("获取详情失败！", e);
            throw QuanhuException.busiError("获取公告详情失败！");
        }
    }
}
