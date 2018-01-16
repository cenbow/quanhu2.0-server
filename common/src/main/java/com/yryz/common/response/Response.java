/*
 * Copyright (c) 2016-2018 Wuhan Yryz Network Company LTD.
 */
package com.yryz.common.response;

import java.io.Serializable;

/**
 * @Description: rpc返回数据订阅
 * @author suyongcheng
 * @date 2017年10月27日15:08:03
 * @version 1.0
 */
public interface Response<T> extends Serializable {

    /**
     * 调用结果
     * @return boolean
     */
    Boolean success();

    /**
     * 错误编码
     * @return
     */
    String getCode();

    /**
     * 业务提示信息
     * @return
     */
    String getMsg();

    /**
     * 错误信息
     * @return
     */
    String getErrorMsg();

    /**
     * 实体数据
     * @return
     */
    T getData();

}
