package com.yryz.quanhu.resource.advertisement.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.resource.advertisement.api.AdvertisementAPI;
import com.yryz.quanhu.resource.advertisement.dto.AdvertisementAdminDto;
import com.yryz.quanhu.resource.advertisement.dto.AdvertisementDto;
import com.yryz.quanhu.resource.advertisement.entity.Advertisement;
import com.yryz.quanhu.resource.advertisement.service.AdvertisementService;
import com.yryz.quanhu.resource.advertisement.vo.AdvertisementAdminVo;
import com.yryz.quanhu.resource.advertisement.vo.AdvertisementVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description:
 * @Date: Created in 2018 2018/1/22 10:08
 * @Author: pn
 */
@Service(interfaceClass = AdvertisementAPI.class)
public class AdvertisementProvider implements AdvertisementAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdvertisementProvider.class);

    @Autowired
    private AdvertisementService advertisementService;

    public Response<AdvertisementVo> detail(Long kid) {
        try {
            return ResponseUtils.returnObjectSuccess(advertisementService.detail(kid));
        } catch (QuanhuException e) {
            LOGGER.error("查询广告详情异常", e);
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            LOGGER.error("查询广告详情异常", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<PageList<AdvertisementVo>> list(AdvertisementDto advertisementDto) {
        try {
            return ResponseUtils.returnObjectSuccess(advertisementService.list(advertisementDto));
        } catch (QuanhuException e) {
            LOGGER.error("查询广告列表异常", e);
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            LOGGER.error("查询广告列表异常", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<PageList<AdvertisementAdminVo>> listAdmin(AdvertisementAdminDto advertisementAdminDto) {
        try {
            return ResponseUtils.returnObjectSuccess(advertisementService.listAdmin(advertisementAdminDto));
        } catch (QuanhuException e) {
            LOGGER.error("查询广告列表异常", e);
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            LOGGER.error("查询广告列表异常", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Integer> add(Advertisement advertisement) {
        try {
            return ResponseUtils.returnObjectSuccess(advertisementService.add(advertisement));
        } catch (QuanhuException e) {
            LOGGER.error("添加广告列表异常", e);
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            LOGGER.error("添加广告列表异常", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Integer> update(Advertisement advertisement) {
        try {
            return ResponseUtils.returnObjectSuccess(advertisementService.update(advertisement));
        } catch (QuanhuException e) {
            LOGGER.error("更新广告列表异常", e);
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            LOGGER.error("更新广告列表异常", e);
            return ResponseUtils.returnException(e);
        }
    }
}
