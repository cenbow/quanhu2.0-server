package com.yryz.quanhu.coterie.member.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yryz.quanhu.coterie.member.entity.CoterieMemberNotify;
import com.yryz.quanhu.coterie.member.service.CoterieMemberService;
import com.yryz.quanhu.order.sdk.IOrderNotifyService;
import com.yryz.quanhu.order.sdk.dto.OutputOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chengyunfei
 */
@Service(value = "coterieMemberOrderNotifyServiceImpl")
public class CoterieMemberOrderNotifyServiceImpl implements IOrderNotifyService {


    @Autowired
    private CoterieMemberService coterieMemberService;

    @Override
    public String getModuleEnum() {
        return null;
    }

    @Override
    public void notify(OutputOrder outputOrder) {

        CoterieMemberNotify coterieMemberNotify = JSONObject.parseObject(outputOrder.getBizContent(), CoterieMemberNotify.class);

//        coterieMemberService.toJoin(orderInfo.getOrderId(), coterieMemberNotify.getCircleId(), coterieMemberNotify.getCustId(), coterieMemberNotify.getCoterieId(), coterieMemberNotify.getReason(), false);

        // 消息推送
//        coterieMemberService.sendMessageToJoinCoterie(coterieMemberNotify);

    }
}
