package com.yryz.common.web;

import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.component.rpc.ResponseCode;
import com.yryz.component.rpc.ResponseProducer;
import com.yryz.component.rpc.RpcResponse;
import com.yryz.component.rpc.internal.DubboResponse;
import org.springframework.web.bind.MissingServletRequestParameterException;

public class ResponseModel extends ResponseProducer {

    public static <T> RpcResponse<T> returnObjectSuccess(T t) {
        return new DubboResponse<>(true, ResponseCode.INVOKE_SUCCESS, ResponseCode.INVOKE_SUCCESS_MSG, "", t);
    }

    public static <T> RpcResponse<T> returnListSuccess(T t) {
        return new DubboResponse<>(true, ResponseCode.INVOKE_SUCCESS, ResponseCode.INVOKE_SUCCESS_MSG, "", t);
    }

    public static <T> RpcResponse<T> returnException(Exception e) {
        if (e instanceof QuanhuException) {
        	QuanhuException qe = (QuanhuException) e;
            return new DubboResponse<T>(false, qe.getCode(), qe.getMsg(), qe.getErrorMsg(), null);
        } else if (e instanceof IllegalArgumentException) {
            IllegalArgumentException ll = (IllegalArgumentException) e;
            return new DubboResponse<T>(false,
                    ExceptionEnum.ValidateException.getCode(),
                    ExceptionEnum.ValidateException.getShowMsg(),
                    ll.getMessage(),
                    null);
        } else if (e instanceof MissingServletRequestParameterException) {
            MissingServletRequestParameterException msrp = (MissingServletRequestParameterException) e;
            return new DubboResponse<T>(false,
                    ExceptionEnum.ValidateException.getCode(),
                    ExceptionEnum.ValidateException.getShowMsg(),
                    msrp.getMessage(),
                    null);
        } else {
            return new DubboResponse<T>(false,
                    ExceptionEnum.Exception.getCode(),
                    ExceptionEnum.Exception.getShowMsg(),
                    ExceptionEnum.Exception.getErrorMsg(),
                    null);
        }
    }


}
