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
     * 业务回调，业务方自己实现
     *
     * @param outputOrder
     */
    void notify(OutputOrder outputOrder);
}
