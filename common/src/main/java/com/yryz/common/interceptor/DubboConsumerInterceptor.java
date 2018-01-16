package com.yryz.common.interceptor;

import com.alibaba.dubbo.rpc.*;
import com.yryz.common.context.Context;
import org.springframework.stereotype.Service;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description:
 * @Date: Created in 2017 2017/11/24 17:43
 * @Author: pn
 */
public class DubboConsumerInterceptor implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext.getContext().setAttachment("appId", Context.getProperty("appId"));
        return invoker.invoke(invocation);
    }
}
