/*
 * Copyright (c) 2016-2018 Wuhan Yryz Network Company LTD.
 */
package com.yryz.common.response;

import com.yryz.common.exception.QuanhuException;

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
	
	/**
	 * 返回成功
	 * @return
	 */
    public static <T> Response<T> returnSuccess() {
        return new Response(true, ResponseConstant.SUCCESS.getCode(), ResponseConstant.SUCCESS.getShowMsg(), "", new HashMap());
    }

    /**
     * 返回对象结果
     * @param t
     * @return
     */
    public static <T> Response<T> returnObjectSuccess(T t) {
        if (t == null) {
            return new Response(true, ResponseConstant.SUCCESS.getCode(), ResponseConstant.SUCCESS.getShowMsg(), "", new HashMap());
        } else {
            return new Response(true, ResponseConstant.SUCCESS.getCode(), ResponseConstant.SUCCESS.getShowMsg(), "", t);
        }
    }

    /**
     * 返回列表结果
     * @param collection
     * @return
     */
    public static <T> Response<T> returnListSuccess(Collection<?> collection) {
        if (collection == null) {
            return new Response(true, ResponseConstant.SUCCESS.getCode(), ResponseConstant.SUCCESS.getShowMsg(), "", new ArrayList());
        } else {
            return new Response(true, ResponseConstant.SUCCESS.getCode(), ResponseConstant.SUCCESS.getShowMsg(), "", collection);
        }
    }
    
    /**
     * 返回异常信息
     * @param e
     * @return
     */
    public static <T> Response<T> returnException(Exception e) {
        if (e instanceof QuanhuException) {
        	QuanhuException qe = (QuanhuException) e;
            return new Response<T>(false, qe.getCode(), qe.getMsg(), qe.getErrorMsg(), null);
        } else if (e instanceof IllegalArgumentException) {
            IllegalArgumentException ll = (IllegalArgumentException) e;
            return new Response<T>(false,
            		ResponseConstant.VALIDATE_EXCEPTION.getCode(),
            		ResponseConstant.VALIDATE_EXCEPTION.getShowMsg(),
                    ll.getMessage(),
                    null);
        } else if (e instanceof MissingServletRequestParameterException) {
            MissingServletRequestParameterException msrp = (MissingServletRequestParameterException) e;
            return new Response<T>(false,
            		ResponseConstant.VALIDATE_EXCEPTION.getCode(),
            		ResponseConstant.VALIDATE_EXCEPTION.getShowMsg(),
                    msrp.getMessage(),
                    null);
        } else {
            return new Response<T>(false,
            		ResponseConstant.EXCEPTION.getCode(),
            		ResponseConstant.EXCEPTION.getShowMsg(),
            		ResponseConstant.EXCEPTION.getErrorMsg(),
                    null);
        }
    }
    
    /**
     * 简化消息提示，客户端得到该错误只会直接显示消息内容
     * @param msg
     * @return
     */
    public static <T> Response<T> returnCommonException(String msg){
    	return new Response<T>(false, ResponseConstant.SYS_EXCEPTION.getCode(), msg, msg, null);
    }

}
