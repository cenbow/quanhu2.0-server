package com.yryz.quanhu.resource.api;

import com.yryz.common.response.Response;
import com.yryz.quanhu.resource.vo.ResourceTotal;

/**
 * @Author: liupan
 * @Path: com.yryz.quanhu.resource.service
 * @Desc:
 * @Date: 2018/1/27.
 */
public interface ResourceDymaicApi {

    public Response<Object> commitResourceDymaic(ResourceTotal resourceTotal);

}
