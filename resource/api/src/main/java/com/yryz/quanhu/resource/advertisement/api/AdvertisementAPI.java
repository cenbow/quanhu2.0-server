package com.yryz.quanhu.resource.advertisement.api;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.resource.advertisement.dto.AdvertisementDto;
import com.yryz.quanhu.resource.advertisement.entity.Advertisement;
import com.yryz.quanhu.resource.advertisement.vo.AdvertisementVo;

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
     * @param id
     * @return
     */
    Response<Advertisement> get(Long id);

    /**
     * 获取Advertisement明细
     *
     * @param id
     * @return
     */
    Response<AdvertisementVo> detail(Long id);

    /**
     * 获取Advertisement列表
     *
     * @param advertisementDto
     * @return
     */
    Response<PageList<AdvertisementVo>> list(AdvertisementDto advertisementDto);

}
