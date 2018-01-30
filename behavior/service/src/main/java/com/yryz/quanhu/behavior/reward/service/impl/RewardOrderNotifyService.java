package com.yryz.quanhu.behavior.reward.service.impl;

import com.alibaba.dubbo.common.utils.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.behavior.reward.constants.RewardConstants;
import com.yryz.quanhu.behavior.reward.entity.RewardCount;
import com.yryz.quanhu.behavior.reward.entity.RewardInfo;
import com.yryz.quanhu.behavior.reward.service.RewardCountService;
import com.yryz.quanhu.behavior.reward.service.RewardInfoService;
import com.yryz.quanhu.order.sdk.IOrderNotifyService;
import com.yryz.quanhu.order.sdk.constant.BranchFeesEnum;
import com.yryz.quanhu.order.sdk.dto.OutputOrder;
import com.yryz.quanhu.resource.api.ResourceApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangheng
 * @Description: 打赏 订单回调
 * @date 2018年1月28日 下午10:48:42
 */
@Service
public class RewardOrderNotifyService implements IOrderNotifyService {

    private Logger logger = LoggerFactory.getLogger(RewardOrderNotifyService.class);

    @Autowired
    private RewardInfoService rewardInfoService;

    @Autowired
    private RewardCountService rewardCountService;

    @Reference(check = false, lazy = true, timeout = 1000)
    private ResourceApi resourceApi;

    @Override
    public String getModuleEnum() {
        return BranchFeesEnum.REWARD.toString();
    }

    @Override
    public void notify(OutputOrder outputOrder) {
        RewardInfo info = JsonUtils.fromJson(outputOrder.getBizContent(), RewardInfo.class);
        logger.debug("资源打赏 订单回调，RewardInfo==>>" + outputOrder.getBizContent());
        Assert.notNull(info, "回调订单 RewardInfo is null ！");

        // 更新 打赏记录
        RewardInfo upInfo = new RewardInfo();
        // 受赏 金额【存入 分费后的金额】
        Long rewardedPrice = info.getGiftNum() * info.getGiftPrice() * BranchFeesEnum.REWARD.getFee().get(1).getFee() / 100L;
        upInfo.setRewardPrice(rewardedPrice);
        upInfo.setRewardStatus(RewardConstants.reward_status_pay_success);

        rewardInfoService.updateByKid(upInfo);

        // 更新打赏者 统计
        RewardCount uCount = new RewardCount();
        uCount.setTargetId(info.getCreateUserId());
        uCount.setTargetType(RewardConstants.target_type_user);
        uCount.setTotalRewardAmount(info.getGiftNum() * info.getGiftPrice());
        uCount.setTotalRewardCount(1);
        rewardCountService.addCountByTargetId(uCount);

        // 更新被打赏者 统计
        RewardCount uBeCount = new RewardCount();
        uBeCount.setTargetId(info.getToUserId());
        uBeCount.setTargetType(RewardConstants.target_type_user);
        uBeCount.setTotalRewardedAmount(rewardedPrice);
        uBeCount.setTotalRewardedCount(1);
        rewardCountService.addCountByTargetId(uCount);

        // 更新资源被打赏 统计
        RewardCount rCount = new RewardCount();
        rCount.setTargetId(info.getResourceId());
        rCount.setTargetType(RewardConstants.target_type_resource);
        rCount.setTotalRewardedAmount(info.getGiftNum() * info.getGiftPrice());
        rCount.setTotalRewardedCount(1);
        rewardCountService.addCountByTargetId(uCount);

        // TODO 给打赏者 、 被打赏者 发送消息
    }
}
