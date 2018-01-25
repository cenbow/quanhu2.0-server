package com.yryz.quanhu.resource.advertisement.service;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.resource.advertisement.dto.AdvertisementAdminDto;
import com.yryz.quanhu.resource.advertisement.dto.AdvertisementDto;
import com.yryz.quanhu.resource.advertisement.entity.Advertisement;
import com.yryz.quanhu.resource.advertisement.vo.AdvertisementAdminVo;
import com.yryz.quanhu.resource.advertisement.vo.AdvertisementVo;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description:
 * @Date: Created in 2018 2018/1/22 10:10
 * @Author: pn
 */
public interface AdvertisementService {

    PageList<AdvertisementVo> list(AdvertisementDto advertisementDto);

    PageList<AdvertisementAdminVo> listAdmin(AdvertisementAdminDto advertisementAdminDto);

    Integer add(Advertisement advertisement);

    Integer update(Advertisement advertisement);

    AdvertisementVo detail(Long kid);
}
