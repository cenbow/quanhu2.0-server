package com.yryz.quanhu.message.common.filter;

import com.alibaba.dubbo.rpc.*;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.utils.StringUtils;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/1/26
 * @description
 */
public class AppInfoFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String appId = RpcContext.getContext().getAttachment("appId");
        if (StringUtils.isBlank(appId)) {
            throw QuanhuException.busiError("appId can not be blank");
        }
        return invoker.invoke(invocation);
    }
}
