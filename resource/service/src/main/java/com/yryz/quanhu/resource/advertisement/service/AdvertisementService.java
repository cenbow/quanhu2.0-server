package com.yryz.quanhu.resource.advertisement.service;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.resource.advertisement.dto.AdvertisementAdminDto;
import com.yryz.quanhu.resource.advertisement.dto.AdvertisementDto;
import com.yryz.quanhu.resource.advertisement.vo.AdvertisementAdminVo;
import com.yryz.quanhu.resource.advertisement.vo.AdvertisementVo;

import java.util.List;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description:
 * @Date: Created in 2018 2018/1/22 10:10
 * @Author: pn
 */
public interface AdvertisementService {

    List<AdvertisementVo> list(AdvertisementDto advertisementDto);

    PageList<AdvertisementAdminVo> listAdmin(AdvertisementAdminDto advertisementAdminDto);

    Integer add(AdvertisementAdminDto advertisementAdminDto);

    Integer update(AdvertisementAdminDto advertisementAdminDto);

    AdvertisementVo detail(Long kid);

    List<AdvertisementVo> selectDefaultAdvertisement();
}
