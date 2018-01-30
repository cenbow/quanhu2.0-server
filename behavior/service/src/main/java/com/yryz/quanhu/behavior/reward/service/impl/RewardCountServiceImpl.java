package com.yryz.quanhu.behavior.reward.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yryz.quanhu.behavior.reward.dao.RewardCountDao;
import com.yryz.quanhu.behavior.reward.entity.RewardCount;
import com.yryz.quanhu.behavior.reward.service.RewardCountService;

/**
* @author wangheng
* @date 2018年1月29日 下午10:52:42
*/
@Service
public class RewardCountServiceImpl implements RewardCountService {

    @Autowired
    private RewardCountDao rewardCountDao;

    public RewardCountDao getDao() {
        return this.rewardCountDao;
    }

    @Override
    public int addCountByTargetId(RewardCount record) {
        if (null == this.selectByTargetId(record.getTargetId())) {
            return this.getDao().insertSelective(record);
        }
        return this.getDao().addCountByTargetId(record);
    }

    @Override
    public RewardCount selectByTargetId(Long targetId) {
        return this.getDao().selectByTargetId(targetId);
    }
}
