/*
 * Copyright (c) 2016-2018 Wuhan Yryz Network Company LTD.
 */
package com.yryz.common.response;

import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.rpc.DubboResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.springframework.web.bind.MissingServletRequestParameterException;

/**
 * 返回对象工具
 *
 * @author pxie
 * @version 1.0
 * @data 2017/10/30 0030 50
 */
public class ResponseUtils {
	
    public static <T> Response<T> returnSuccess() {
        return new DubboResponse(true, ResponseConstant.SUCCESS.getCode(), ResponseConstant.SUCCESS.getShowMsg(), "", new HashMap());
    }

    public static <T> Response<T> returnObjectSuccess(T t) {
        if (t == null) {
            return new DubboResponse(true, ResponseConstant.SUCCESS.getCode(), ResponseConstant.SUCCESS.getShowMsg(), "", new HashMap());
        } else {
            return new DubboResponse(true, ResponseConstant.SUCCESS.getCode(), ResponseConstant.SUCCESS.getShowMsg(), "", t);
        }
    }

    public static <T> Response<T> returnListSuccess(Collection<?> collection) {
        if (collection == null) {
            return new DubboResponse(true, ResponseConstant.SUCCESS.getCode(), ResponseConstant.SUCCESS.getShowMsg(), "", new ArrayList());
        } else {
            return new DubboResponse(true, ResponseConstant.SUCCESS.getCode(), ResponseConstant.SUCCESS.getShowMsg(), "", collection);
        }
    }
    
    public static <T> Response<T> returnException(Exception e) {
        if (e instanceof QuanhuException) {
        	QuanhuException qe = (QuanhuException) e;
            return new DubboResponse<T>(false, qe.getCode(), qe.getMsg(), qe.getErrorMsg(), null);
        } else if (e instanceof IllegalArgumentException) {
            IllegalArgumentException ll = (IllegalArgumentException) e;
            return new DubboResponse<T>(false,
            		ResponseConstant.VALIDATE_EXCEPTION.getCode(),
            		ResponseConstant.VALIDATE_EXCEPTION.getShowMsg(),
                    ll.getMessage(),
                    null);
        } else if (e instanceof MissingServletRequestParameterException) {
            MissingServletRequestParameterException msrp = (MissingServletRequestParameterException) e;
            return new DubboResponse<T>(false,
            		ResponseConstant.VALIDATE_EXCEPTION.getCode(),
            		ResponseConstant.VALIDATE_EXCEPTION.getShowMsg(),
                    msrp.getMessage(),
                    null);
        } else {
            return new DubboResponse<T>(false,
            		ResponseConstant.EXCEPTION.getCode(),
            		ResponseConstant.EXCEPTION.getShowMsg(),
            		ResponseConstant.EXCEPTION.getErrorMsg(),
                    null);
        }
    }

}
