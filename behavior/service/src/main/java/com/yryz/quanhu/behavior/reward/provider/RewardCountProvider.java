package com.yryz.quanhu.behavior.reward.provider;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.behavior.reward.api.RewardCountApi;
import com.yryz.quanhu.behavior.reward.entity.RewardCount;
import com.yryz.quanhu.behavior.reward.service.RewardCountService;

/**
* @author wangheng
* @date 2018年1月29日 下午11:11:00
*/
@Service(interfaceClass = RewardCountApi.class)
public class RewardCountProvider implements RewardCountApi {

    @Autowired
    private RewardCountService rewardCountService;

    @Override
    public Response<RewardCount> selectByTargetId(Long targetId) {
        return ResponseUtils.returnObjectSuccess(rewardCountService.selectByTargetId(targetId));
    }

}
