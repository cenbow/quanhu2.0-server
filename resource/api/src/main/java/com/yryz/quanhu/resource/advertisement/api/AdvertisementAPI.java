package com.yryz.quanhu.resource.advertisement.api;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.resource.advertisement.dto.AdvertisementAdminDto;
import com.yryz.quanhu.resource.advertisement.dto.AdvertisementDto;
import com.yryz.quanhu.resource.advertisement.vo.AdvertisementAdminVo;
import com.yryz.quanhu.resource.advertisement.vo.AdvertisementVo;

import java.util.List;

/**
 * @author pengnian
 * @ClassName: AdvertisementAPI
 * @Description: AdvertisementApi接口
 * @date 2018-01-20 14:41:26
 */
public interface AdvertisementAPI {

    /**
     * 获取Advertisement明细
     *
     * @param kid
     * @return
     */
    Response<AdvertisementVo> detail(Long kid);

    /**
     * 获取Advertisement列表
     *
     * @param advertisementDto
     * @return
     */
    Response<List<AdvertisementVo>> list(AdvertisementDto advertisementDto);

    /**
     * 获取Advertisement列表（admin）
     *
     * @param advertisementAdminDto
     * @return
     */
    Response<PageList<AdvertisementAdminVo>> listAdmin(AdvertisementAdminDto advertisementAdminDto);

    /**
     * 添加广告
     *
     * @param advertisementAdminDto
     * @return
     */
    Response<Integer> add(AdvertisementAdminDto advertisementAdminDto);

    /**
     * 更新广告
     *
     * @param advertisementAdminDto
     * @return
     */
    Response<Integer> update(AdvertisementAdminDto advertisementAdminDto);

    /**
     * 查询默认广告
     *
     * @return
     */
    Response<List<AdvertisementVo>> selectDefaultAdvertisement();
}
