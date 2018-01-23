package com.yryz.common.interceptor;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xiepeng
 * @version 1.0
 */
//@Activate扩展点无条件自动激活时，需要加上group = {"provider","consumer"}
@Activate(group={"consumer"})
public class ConsumerDemoFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerDemoFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext.getContext().setAttachment("appId", "hello rpcContext");
        return invoker.invoke(invocation);
    }
}
