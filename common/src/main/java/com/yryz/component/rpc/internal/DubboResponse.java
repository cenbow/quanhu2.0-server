/*
 * Copyright (c) 2016-2018 Wuhan Yryz Network Company LTD.
 */
package com.yryz.component.rpc.internal;

import com.yryz.component.rpc.RpcResponse;

import java.io.Serializable;

/**
 * @author suyongcheng
 * @version 1.0
 * @Description: Dubbo实体返回对象
 * @date 2017年10月27日15:08:03
 */
public class DubboResponse<T> implements RpcResponse, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5079397072005472183L;

    /**
     * 调用结果
     */
    private Boolean status;
    /**
     * 错误编码
     */
    private String code;
    /**
     * 业务提示信息
     */
    private String msg;
    /**
     * 错误信息
     */
    private String errorMsg;
    /**
     * 数据
     */
    private T data;

    public DubboResponse() {}

    public DubboResponse(Boolean status, T data) {
        this.status = status;
        this.data = data;
    }

    /**
     * @param status
     * @param code
     * @param msg
     * @param errorMsg
     * @param data
     */
    public DubboResponse(Boolean status, String code, String msg, String errorMsg, T data) {
        this.status = status;
        this.code = code;
        this.msg = msg;
        this.errorMsg = errorMsg;
        this.data = data;
    }

    @Override
    public Boolean success() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DubboResponse{" +
                "status=" + status +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", data=" + data +
                '}';
    }



}
