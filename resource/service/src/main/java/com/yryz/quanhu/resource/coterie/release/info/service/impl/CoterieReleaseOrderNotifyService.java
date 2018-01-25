package com.yryz.quanhu.resource.coterie.release.info.service.impl;

import com.alibaba.fastjson.JSON;
import com.yryz.quanhu.order.sdk.IOrderNotifyService;
import com.yryz.quanhu.order.sdk.dto.OutputOrder;
import org.springframework.stereotype.Service;


/**
 * @author wangheng
 * @Description: 付费阅读文章，订单回调
 * @date 2018年1月24日 下午9:06:17
 */
@Service
public class CoterieReleaseOrderNotifyService implements IOrderNotifyService {

    @Override
    public String getModuleEnum() {
        return "私圈文章的业务编码";
    }

    @Override
    public void notify(OutputOrder outputOrder) {
        System.out.println("Receive order notify, data = " + JSON.toJSONString(outputOrder));
    }
}
