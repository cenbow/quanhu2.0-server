/*
 * Copyright (c) 2016-2018 Wuhan Yryz Network Company LTD.
 */
package com.yryz.common.response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.springframework.web.bind.MissingServletRequestParameterException;

import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;

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
     *
     * @return
     */
    public static <T> Response<T> returnSuccess() {
        return new Response(true, ResponseConstant.SUCCESS.getCode(), ResponseConstant.SUCCESS.getShowMsg(), "",
                null);
    }

    /**
     * 返回对象结果
     *
     * @param t
     * @return
     */
    public static <T> Response<T> returnObjectSuccess(T t) {
        if (t == null) {
            return new Response(true, ResponseConstant.SUCCESS.getCode(), ResponseConstant.SUCCESS.getShowMsg(), "",
                    null);
        } else {
            return new Response(true, ResponseConstant.SUCCESS.getCode(), ResponseConstant.SUCCESS.getShowMsg(), "", t);
        }
    }

    /**
     * 返回对象结果
     *
     * @param t
     * @return
     */
    public static <T> Response<T> returnApiObjectSuccess(T t) {
        if (t == null) {
            return new Response(true, ResponseConstant.SUCCESS.getCode(), ResponseConstant.SUCCESS.getShowMsg(), "",
                    new HashMap<>());
        } else {
            return new Response(true, ResponseConstant.SUCCESS.getCode(), ResponseConstant.SUCCESS.getShowMsg(), "", t);
        }
    }
    
    /**
     * 返回列表结果
     *
     * @param collection
     * @return
     */
    public static <T> Response<T> returnListSuccess(Collection<?> collection) {
        if (collection == null) {
            return new Response(true, ResponseConstant.SUCCESS.getCode(), ResponseConstant.SUCCESS.getShowMsg(), "",
                    new ArrayList());
        } else {
            return new Response(true, ResponseConstant.SUCCESS.getCode(), ResponseConstant.SUCCESS.getShowMsg(), "",
                    collection);
        }
    }

    /**
     * 返回异常信息
     *
     * @param e
     * @return
     */
    public static <T> Response<T> returnException(Exception e) {
        if (e instanceof QuanhuException) {
            QuanhuException qe = (QuanhuException) e;
            return new Response<T>(false, qe.getCode(), qe.getMsg(), qe.getErrorMsg() + " [Exception]:" + e, null);
        } else if (e instanceof IllegalArgumentException) {
            IllegalArgumentException ll = (IllegalArgumentException) e;
            return new Response<T>(false, ResponseConstant.VALIDATE_EXCEPTION.getCode(),
                    ResponseConstant.VALIDATE_EXCEPTION.getShowMsg(), ll.getMessage() + " [Exception]:" + e, null);
        } else if (e instanceof MissingServletRequestParameterException) {
            MissingServletRequestParameterException msrp = (MissingServletRequestParameterException) e;
            return new Response<T>(false, ResponseConstant.VALIDATE_EXCEPTION.getCode(),
                    ResponseConstant.VALIDATE_EXCEPTION.getShowMsg(), msrp.getMessage() + " [Exception]:" + e, null);
        } else {
            return new Response<T>(false, ResponseConstant.EXCEPTION.getCode(), ResponseConstant.EXCEPTION.getShowMsg(),
                    ResponseConstant.EXCEPTION.getErrorMsg() + " [Exception]:" + e, null);
        }
    }

    /**
     * 简化消息提示，客户端得到该错误只会直接显示消息内容
     *
     * @param msg
     * @return
     */
    public static <T> Response<T> returnCommonException(String msg) {
        return new Response<T>(false, ResponseConstant.SYS_EXCEPTION.getCode(), msg, msg, null);
    }

    /**
     * @param  res
     * @return T
     * @throws
     * @Description: 获取返回对象中对象
     * @author wangheng
     */
    public static <T> T getResponseData(Response<T> res) {
        if (null == res) {
            throw QuanhuException.busiError("getResponseObject() , res is null !");
        }

        if (!res.success() || !ResponseConstant.SUCCESS.getCode().equals(res.getCode())) {
            throw new QuanhuException(res.getCode(), res.getMsg(), res.getErrorMsg());
        }

        return res.getData();
    }

    /**
     * @param  res
     * @return Collection<?>
     * @throws
     * @Description: 获取返回对象中List对象
     * @author wangheng
     */
    public static <T> Collection<?> getResponseDataList(Response<Collection<?>> res) {
        if (null == res) {
            throw QuanhuException.busiError("getResponseObject() , res is null !");
        }

        if (!res.success() || !ResponseConstant.SUCCESS.getCode().equals(res.getCode())) {
            throw new QuanhuException(res.getCode(), res.getMsg(), res.getErrorMsg());
        }

        return res.getData();
    }

    /**
     * 检查Response对象且检查data数据对象
     *
     * @param response
     * @param <T>
     * @return
     */
    public static <T> T getResponseNotNull(Response<T> response) {
        if (response != null && response.success()) {
            T t = response.getData();
            if (t == null) {
                throw new QuanhuException(ExceptionEnum.RPC_RESPONSE_DATA_EXCEPTION);
            }
            return t;
        }
        throw new QuanhuException(response.getCode(), response.getMsg(), response.getErrorMsg());
    }

}
