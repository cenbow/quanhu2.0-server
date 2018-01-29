package com.yryz.quanhu.behavior.gift.service.impl;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yryz.common.response.PageList;
import com.yryz.common.utils.PageUtils;
import com.yryz.quanhu.behavior.gift.dao.GiftInfoDao;
import com.yryz.quanhu.behavior.gift.dto.GiftInfoDto;
import com.yryz.quanhu.behavior.gift.entity.GiftInfo;
import com.yryz.quanhu.behavior.gift.service.GiftInfoService;

/**
* @author wangheng
* @date 2018年1月26日 下午2:55:05
*/
@Service
public class GiftInfoServiceImpl implements GiftInfoService {

    @Autowired
    private GiftInfoDao giftInfoDao;

    private GiftInfoDao getDao() {
        return this.giftInfoDao;
    }

    @Override
    public int insertSelective(GiftInfo record) {
        return this.getDao().insertSelective(record);
    }

    @Override
    public List<GiftInfo> selectByCondition(GiftInfoDto dto) {
        return this.getDao().selectByCondition(dto);
    }

    @Override
    public PageList<GiftInfo> pageByCondition(GiftInfoDto dto, boolean isCount) {
        PageList<GiftInfo> pageList = new PageList<>();
        pageList.setCurrentPage(dto.getCurrentPage());
        pageList.setPageSize(dto.getPageSize());

        PageUtils.startPage(dto.getCurrentPage(), dto.getPageSize() , false);
        List<GiftInfo> list = this.getDao().selectByCondition(dto);
        pageList.setEntities(list);

        if (isCount && CollectionUtils.isNotEmpty(list)) {
            pageList.setCount(this.getDao().countByCondition(dto));
        } else {
            pageList.setCount(0L);
        }
        return pageList;
    }

    @Override
    public List<GiftInfo> selectByKids(Set<Long> kids) {
        GiftInfoDto dto = new GiftInfoDto();
        dto.setKids((Long[]) kids.toArray());
        return this.getDao().selectByCondition(dto);
    }

    @Override
    public GiftInfo selectByKid(Long id) {
        return this.getDao().selectByKid(id);
    }

    @Override
    public int updateByKid(GiftInfo record) {
        return this.getDao().updateByKid(record);
    }

}
