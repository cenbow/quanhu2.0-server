package com.yryz.quanhu.message.push.remote;

import com.yryz.quanhu.message.push.entity.PushConfigDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/1/20
 * @description
 */
@Service
public class ConfigRemote {

//    @Reference(retries = 0)
//    private ConfigApi configApi;
    @Autowired
    private PushConfigDTO pushConfigDTO;



    public PushConfigDTO getPushConfig(String appId) {
        PushConfigDTO configDTO = new PushConfigDTO();
        configDTO = pushConfigDTO;
        return configDTO;
    }
}
