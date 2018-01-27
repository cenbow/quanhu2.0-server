package com.yryz.quanhu.behavior.reward.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yryz.common.response.PageList;
import com.yryz.common.utils.PageUtils;
import com.yryz.quanhu.behavior.reward.dao.RewardInfoDao;
import com.yryz.quanhu.behavior.reward.dto.RewardInfoDto;
import com.yryz.quanhu.behavior.reward.entity.RewardInfo;
import com.yryz.quanhu.behavior.reward.service.RewardInfoService;
import com.yryz.quanhu.behavior.reward.vo.RewardInfoVo;

/**
* @author wangheng
* @date 2018年1月27日 上午11:47:30
*/
@Service
public class RewardInfoServiceImpl implements RewardInfoService {

    private Logger logger = LoggerFactory.getLogger(RewardInfoServiceImpl.class);

    @Autowired
    private RewardInfoDao rewardInfoDao;

    private RewardInfoDao getDao() {
        return this.rewardInfoDao;
    }

    @Override
    public int insertSelective(RewardInfo record) {
        return this.getDao().insertSelective(record);
    }

    @Override
    public List<RewardInfoVo> selectByCondition(RewardInfoDto dto) {
        return this.getDao().selectByCondition(dto);
    }

    @Override
    public PageList<RewardInfoVo> pageByCondition(RewardInfoDto dto, boolean isCount) {
        PageList<RewardInfoVo> pageList = new PageList<>();
        pageList.setCurrentPage(dto.getCurrentPage());
        pageList.setPageSize(dto.getPageSize());

        PageUtils.startPage(dto.getCurrentPage(), dto.getPageSize(), isCount);
        List<RewardInfoVo> list = this.getDao().selectByCondition(dto);
        pageList.setEntities(list);

        if (isCount && CollectionUtils.isNotEmpty(list)) {
            pageList.setCount(this.getDao().countByCondition(dto));
        }

        return pageList;
    }

    @Override
    public RewardInfo selectByKid(Long id) {
        return this.getDao().selectByKid(id);
    }

    @Override
    public int updateByKid(RewardInfo record) {
        return this.getDao().updateByKid(record);
    }

}
