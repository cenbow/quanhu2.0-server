package com.yryz.quanhu.support.config.api;

import com.yryz.common.response.Response;

import java.util.Map;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/27 14:54
 * Created by huangxy
 */
public interface BasicConfigApi {

    public Response<String> getValue(String key);

    public Response<String> getValue(String key,String defaultValue);

    public Response<Map<String,String>> getValues(String key);


}
