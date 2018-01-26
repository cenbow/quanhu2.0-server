package com.yryz.quanhu.resource.coterie.release.info.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.message.message.api.MessageAPI;
import com.yryz.quanhu.order.sdk.IOrderNotifyService;
import com.yryz.quanhu.order.sdk.dto.OutputOrder;
import com.yryz.quanhu.resource.enums.ResourceTypeEnum;

/**
 * @author wangheng
 * @Description: 付费阅读文章，订单回调
 * @date 2018年1月24日 下午9:06:17
 */
@Service
public class CoterieReleaseOrderNotifyService implements IOrderNotifyService {

    private Logger logger = LoggerFactory.getLogger(CoterieReleaseOrderNotifyService.class);

    @Reference
    MessageAPI messageAPI;

    @Override
    public String getModuleEnum() {
        return ResourceTypeEnum.RELEASE + "-";
    }

    @Override
    public void notify(OutputOrder outputOrder) {
        logger.info("付费阅读订单回调，outputOrder==>>" + JsonUtils.toFastJson(outputOrder));
        Assert.notNull(outputOrder, "outputOrder is null !");
        Assert.notNull(outputOrder.getBizContent(), "outputOrder is null !");
        Assert.notNull(outputOrder.getCoterieId(), "outputOrder is null !");
        // TODO 提交 资源购买记录

        try {
            // TODO 付费阅读成功 给文章作者发送奖励消息
        } catch (Exception e) {
            logger.error("payMessageToAuthor ==>> , 付费阅读成功 给文章作者发送奖励消息失败 !", e);
        }
        try {
            // TODO 付费阅读成功 给阅读者发送扣费消息
        } catch (Exception e) {
            logger.error("payMessageToSponsor ==>> , 付费阅读成功 给文章作者发送奖励消息失败 !", e);
        }
    }
}
