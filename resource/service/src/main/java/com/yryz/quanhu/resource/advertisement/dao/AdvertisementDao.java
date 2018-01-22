package com.yryz.quanhu.resource.advertisement.dao;

import com.yryz.common.dao.BaseDao;
import com.yryz.quanhu.resource.advertisement.dto.AdvertisementDto;
import com.yryz.quanhu.resource.advertisement.vo.AdvertisementVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description:
 * @Date: Created in 2018 2018/1/22 10:11
 * @Author: pn
 */
@Mapper
public interface AdvertisementDao extends BaseDao {

    List<AdvertisementVo> selectList(AdvertisementDto advertisementDto);

    List<AdvertisementVo> selectDefaultAdvertisement();
}
