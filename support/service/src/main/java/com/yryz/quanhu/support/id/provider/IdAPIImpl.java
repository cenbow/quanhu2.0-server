package com.yryz.quanhu.support.id.provider;


import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.support.id.service.DefaultUidService;
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
    /**
     * 基于本地DB发号缓冲池的发号服务
     */
    @Autowired
    @Qualifier("idService")
    private IIdService idService;

    /**
     * 基于Twitter的分布式自增ID服务
     */
    @Autowired
    private DefaultUidService uidService;

    @Override
    public String getId(String type) {
        String orderId = idService.getId(type);
        return orderId;
    }

    @Override
    public Long getKid(String type) {
        return idService.getKid(type);
    }

    /**
     * 基于Twitter的分布式自增ID服务
     *
     */

    @Override
    public String getUserId() {
        String userId = String.valueOf(uidService.getUID());
        return userId;
    }

    @Override
    public Long getSnowflakeId() {
        return uidService.getUID();
    }
}
