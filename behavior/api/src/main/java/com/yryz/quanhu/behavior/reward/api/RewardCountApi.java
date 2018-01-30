package com.yryz.quanhu.behavior.reward.api;

import com.yryz.common.response.Response;
import com.yryz.quanhu.behavior.reward.entity.RewardCount;

/**
* @Description: 资源打赏金额 统计
* @author wangheng
* @date 2018年1月27日 上午11:53:31
*/
public interface RewardCountApi {

    Response<RewardCount> selectByTargetId(Long targetId);
}
