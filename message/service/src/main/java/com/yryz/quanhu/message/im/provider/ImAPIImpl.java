package com.yryz.quanhu.message.im.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.Response;
import com.yryz.quanhu.message.im.api.ImAPI;
import com.yryz.quanhu.message.im.entity.ImBlackListRequest;
import com.yryz.quanhu.message.im.entity.ImRequest;
import com.yryz.quanhu.message.im.service.ImService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/1/20
 * @description
 */
@Service
public class ImAPIImpl implements ImAPI {
    @Autowired
    private ImService imService;

    @Override
    public Response<Void> syncUserInfo(ImRequest imRequest) {
        return null;
    }

    @Override
    public Response<Boolean> putToBlackList(ImBlackListRequest imBlackListRequest) {
        return null;
    }
}
