package com.yryz.quanhu.behavior.reward.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.behavior.reward.constants.RewardConstants;
import com.yryz.quanhu.behavior.reward.entity.RewardInfo;
import com.yryz.quanhu.behavior.reward.service.RewardInfoService;
import com.yryz.quanhu.order.sdk.IOrderNotifyService;
import com.yryz.quanhu.order.sdk.constant.BranchFeesEnum;
import com.yryz.quanhu.order.sdk.dto.OutputOrder;

/**
* @Description: 打赏 订单回调
* @author wangheng
* @date 2018年1月28日 下午10:48:42
*/
@Service
public class RewardOrderNotifyService implements IOrderNotifyService {

    @Autowired
    private RewardInfoService rewardInfoService;

    @Override
    public String getModuleEnum() {
        return BranchFeesEnum.REWARD.toString();
    }

    @Override
    public void notify(OutputOrder outputOrder) {
        RewardInfo info = JsonUtils.fromJson(outputOrder.getBizContent(), RewardInfo.class);

        // 更新 打赏记录

        // 受赏 金额【存入 分费后的金额】
        // TODO 分费比例 
        double fee = BranchFeesEnum.REWARD.getFee().get(1).getFee() / BranchFeesEnum.REWARD.getFee().get(0).getFee();
        info.setRewardPrice(info.getGiftNum() * info.getGiftPrice() * (1L));
        info.setRewardStatus(RewardConstants.reward_status_pay_success);

        rewardInfoService.updateByKid(info);

        // TODO 给打赏者 、 被打赏者 发送消息
        // TODO 更新打赏统计数据

    }

}
