package com.yryz.quanhu.behavior.common.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.behavior.common.service.RemoteResourceService;
import com.yryz.quanhu.behavior.common.util.RemoteResourceUtils;
import com.yryz.quanhu.behavior.common.vo.RemoteResource;
import com.yryz.quanhu.resource.api.ResourceApi;
import com.yryz.quanhu.resource.vo.ResourceVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/2/1 15:25
 * Created by lifan
 * 远程资源的聚合查询服务
 */
@Service
public class RemoteResourceServiceImpl implements RemoteResourceService {

    private static final Logger logger = LoggerFactory.getLogger(RemoteResourceServiceImpl.class);

    @Reference
    private ResourceApi resourceApi;

    @Override
    public RemoteResource get(Long resourceId) {
        try {
            ResourceVo resourceVo = ResponseUtils.getResponseData(resourceApi.getResourcesById(String.valueOf(resourceId)));
            return RemoteResourceUtils.convert(resourceVo);
        } catch (Exception e) {
            logger.error("查询远程聚合资源失败", e);
        }
        return null;
    }

}
