package com.yryz.quanhu.behavior.read.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.behavior.read.api.ReadApi;
import com.yryz.quanhu.behavior.read.service.ReadService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: liupan
 * @Path: com.yryz.quanhu.behavior.read.provider
 * @Desc:
 * @Date: 2018/1/30.
 */
@Service(interfaceClass = ReadApi.class)
public class ReadProvider implements ReadApi {

    @Autowired
    private ReadService readService;

    @Override
    public Response<Object> read(Long kid) {
        readService.read(kid);
        return ResponseUtils.returnSuccess();
    }

}
