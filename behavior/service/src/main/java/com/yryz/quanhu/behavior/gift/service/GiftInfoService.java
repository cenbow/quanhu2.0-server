package com.yryz.quanhu.behavior.gift.service;

import java.util.List;
import java.util.Set;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.behavior.gift.dto.GiftInfoDto;
import com.yryz.quanhu.behavior.gift.entity.GiftInfo;

/**
* @author wangheng
* @date 2018年1月26日 下午2:51:30
*/
public interface GiftInfoService {
    int insertSelective(GiftInfo record);

    List<GiftInfo> selectByCondition(GiftInfoDto dto);

    PageList<GiftInfo> pageByCondition(GiftInfoDto dto, boolean isCount);

    List<GiftInfo> selectByKids(Set<Long> kids);

    GiftInfo selectByKid(Long id);

    int updateByKid(GiftInfo record);
}
