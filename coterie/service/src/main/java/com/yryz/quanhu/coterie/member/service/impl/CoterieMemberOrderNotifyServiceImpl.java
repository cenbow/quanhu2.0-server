package com.yryz.quanhu.coterie.member.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yryz.common.message.MessageConstant;
import com.yryz.quanhu.coterie.member.constants.MemberConstant;
import com.yryz.quanhu.coterie.member.entity.CoterieMemberNotify;
import com.yryz.quanhu.coterie.member.event.CoterieMemberMessageManager;
import com.yryz.quanhu.coterie.member.service.CoterieMemberService;
import com.yryz.quanhu.order.sdk.IOrderNotifyService;
import com.yryz.quanhu.order.sdk.dto.OutputOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author chengyunfei
 */
@Service(value = "coterieMemberOrderNotifyServiceImpl")
public class CoterieMemberOrderNotifyServiceImpl implements IOrderNotifyService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private CoterieMemberService coterieMemberService;

    @Resource
    private CoterieMemberMessageManager coterieMemberMessageManager;

    @Override
    public String getModuleEnum() {
        return null;
    }

    @Override
    public void notify(OutputOrder outputOrder) {

        CoterieMemberNotify coterieMemberNotify = JSONObject.parseObject(outputOrder.getBizContent(), CoterieMemberNotify.class);

        coterieMemberService.audit(coterieMemberNotify.getUserId(), coterieMemberNotify.getCoterieId(),
                MemberConstant.MemberStatus.PASS.getStatus(), MemberConstant.JoinType.NOTFREE.getStatus());

        // 消息推送

        /**
         * 付费加入私圈
         */
        logger.info("付费加入私圈的推送信息开始");
        coterieMemberMessageManager.sendMessageToJoinCoteriePayed(coterieMemberNotify, MessageConstant.JOIN_COTERIE_PAYED);
        logger.info("付费加入私圈的推送信息完成");

        /**
         * 私圈奖励
         */
        logger.info("私圈圈主奖励的推送信息开始");
        coterieMemberMessageManager.sendMessageToJoinCoterieReward(coterieMemberNotify, MessageConstant.JOIN_COTERIE_REWARD);
        logger.info("私圈圈主奖励的推送信息完成");

    }
}
