package com.yryz.quanhu.support.id.provider;


import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.support.id.service.IIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2017/12/8
 * @description
 */
@Service
public class IdAPIImpl implements IdAPI {
    @Autowired
    @Qualifier("idService")
    private IIdService idService;

    @Override
    public String getId(String type) {
        String orderId = idService.getId(type);
        return orderId;
    }

    @Override
    public Long getKid(String type) {
        return idService.getKid(type);
    }

    @Override
    public String getUserId() {
        String userId = idService.getUserId();
        return userId;
    }
}
