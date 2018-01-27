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

    /**
     * 提交聚合资源和动态
     * @param resourceTotal 请尽量把对象内的字段都填充上，若有不清楚的请咨询刘攀
     * @return
     */
    public Response<Object> commitResourceDymaic(ResourceTotal resourceTotal);

}
