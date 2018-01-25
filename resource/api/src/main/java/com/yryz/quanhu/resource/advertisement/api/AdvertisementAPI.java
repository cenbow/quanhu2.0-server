package com.yryz.quanhu.resource.advertisement.api;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.resource.advertisement.dto.AdvertisementAdminDto;
import com.yryz.quanhu.resource.advertisement.dto.AdvertisementDto;
import com.yryz.quanhu.resource.advertisement.entity.Advertisement;
import com.yryz.quanhu.resource.advertisement.vo.AdvertisementAdminVo;
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
    Response<PageList<AdvertisementVo>> list(AdvertisementDto advertisementDto);

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
     * @param advertisement
     * @return
     */
    Response<Integer> add(Advertisement advertisement);

    /**
     * 更新广告
     *
     * @param advertisement
     * @return
     */
    Response<Integer> update(Advertisement advertisement);
}
