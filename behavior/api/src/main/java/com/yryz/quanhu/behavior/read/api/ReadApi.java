package com.yryz.quanhu.behavior.read.api;

import com.yryz.common.response.Response;

/**
 * @Author: liupan
 * @Path: com.yryz.quanhu.behavior.read.api
 * @Desc: 浏览数API
 * @Date: 2018/1/30.
 */
public interface ReadApi {

    /**
     * 增加阅读数
     *
     * @param kid    资源ID
     * @param userId 资源作者ID
     * @return
     */
    public Response<Object> read(Long kid, Long userId);

}
