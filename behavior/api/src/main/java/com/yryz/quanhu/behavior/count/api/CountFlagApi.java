package com.yryz.quanhu.behavior.count.api;

import com.yryz.common.response.Response;

import java.util.Map;

/**
 * @Author:sun
 * @version:
 * @Description:
 * @Date:Created in 13:22 2018/1/27
 */
public interface CountFlagApi {

    Response<Map<String, Long>> getAllCountFlag(String countType, Long kid, String page,Map<String,Object> map);

}
