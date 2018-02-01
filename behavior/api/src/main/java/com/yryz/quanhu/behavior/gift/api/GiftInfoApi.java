package com.yryz.quanhu.behavior.gift.api;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.behavior.gift.dto.GiftInfoDto;
import com.yryz.quanhu.behavior.gift.entity.GiftInfo;

/**
 * @author wangheng
 * @Description: 礼物
 * @date 2018年1月26日 下午1:41:28
 */
public interface GiftInfoApi {

    /**
     * @param @param  dto
     * @param isCount 是否算 总数
     * @return Response<PageList<GiftInfo>>
     * @throws
     * @Description: 分页条件查询
     * @author wangheng
     */
    Response<PageList<GiftInfo>> pageByCondition(GiftInfoDto dto, boolean isCount);

    /**
     * 根据唯一ID查询礼物详情
     *
     * @param kid
     * @return
     */
    Response<GiftInfo> selectByKid(long kid);
}