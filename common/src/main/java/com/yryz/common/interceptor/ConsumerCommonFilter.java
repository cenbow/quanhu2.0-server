package com.yryz.common.interceptor;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.yryz.common.constant.AppConstants;
import com.yryz.common.context.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/2/7
 * @description
 */
@Activate(group = {"consumer"})
public class ConsumerCommonFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerCommonFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext.getContext().setAttachment(AppConstants.APP_ID, Context.getProperty(AppConstants.APP_ID));
        return invoker.invoke(invocation);
    }
}
