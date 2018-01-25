package com.yryz.quanhu.resource.coterie.release.info.service.impl;

import org.springframework.stereotype.Service;

import com.yryz.quanhu.order.sdk.IOrderNotifyService;
import com.yryz.quanhu.order.sdk.dto.OutputOrder;

/**
* @Description: 付费阅读文章，订单回调
* @author wangheng
* @date 2018年1月24日 下午9:06:17
*/
@Service
public class CoterieReleaseOrderNotifyService implements IOrderNotifyService {

    @Override
    public void notify(OutputOrder outputOrder) {
    }
}
