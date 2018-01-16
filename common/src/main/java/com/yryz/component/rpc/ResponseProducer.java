/*
 * Copyright (c) 2016-2018 Wuhan Yryz Network Company LTD.
 */
package com.yryz.component.rpc;

import com.yryz.component.rpc.internal.DubboResponse;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 返回对象工具
 *
 * @author pxie
 * @version 1.0
 * @data 2017/10/30 0030 50
 */
public class ResponseProducer {

    public static <T> RpcResponse<T> returnObjectSuccess(T t) {
        if (t == null) {
            return new DubboResponse<>(true, ResponseCode.INVOKE_SUCCESS, ResponseCode.INVOKE_SUCCESS_MSG, "", new HashMap());
        } else {
            return new DubboResponse<>(true, ResponseCode.INVOKE_SUCCESS, ResponseCode.INVOKE_SUCCESS_MSG, "", t);
        }
    }

    public static <T> RpcResponse<T> returnListSuccess(T t) {
        if (t == null) {
            return new DubboResponse<>(true, ResponseCode.INVOKE_SUCCESS, ResponseCode.INVOKE_SUCCESS_MSG, "", new ArrayList());
        } else {
            return new DubboResponse<>(true, ResponseCode.INVOKE_SUCCESS, ResponseCode.INVOKE_SUCCESS_MSG, "", t);
        }
    }

}
