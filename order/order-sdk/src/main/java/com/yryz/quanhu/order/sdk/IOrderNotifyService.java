package com.yryz.quanhu.order.sdk;

import com.yryz.quanhu.order.sdk.dto.OutputOrder;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/23 10:26
 * Created by lifan
 */
public interface IOrderNotifyService {

    /**
     * 业务的功能编码，
     * 队列接收到的消息会根据该方法的返回值作匹配，
     * 对应的功能编码只能处理对应的消息OutputOrder
     *
     * @return
     */
    String getModuleEnum();

    /**
     * 业务回调，业务方自己实现
     *
     * @param outputOrder
     */
    void notify(OutputOrder outputOrder);
}
