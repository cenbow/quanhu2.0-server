package com.yryz.quanhu.resource.advertisement.service.impl;

import com.yryz.common.response.PageList;
import com.yryz.common.utils.DateUtils;
import com.yryz.common.utils.PageModel;
import com.yryz.common.utils.PageUtils;
import com.yryz.quanhu.resource.advertisement.dao.AdvertisementDao;
import com.yryz.quanhu.resource.advertisement.dto.AdvertisementDto;
import com.yryz.quanhu.resource.advertisement.service.AdvertisementService;
import com.yryz.quanhu.resource.advertisement.vo.AdvertisementVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

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

    /*@Resource
    private RedisTemplate<String, List<AdvertisementVo>> redisTemplate;*/

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
}
