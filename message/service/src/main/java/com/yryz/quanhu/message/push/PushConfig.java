package com.yryz.quanhu.message.push;

import com.yryz.quanhu.message.push.entity.PushConfigDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/1/20
 * @description
 */
@Configuration
public class PushConfig {
    @Value("${quanhu.appId}")
    private String quanHuAppId;

    @Value("${quanhu.pushKey}")
    private String pushKey;

    @Value("${quanhu.pushSecret}")
    private String pushSecret;

    @Value("${quanhu.pushType}")
    private Integer pushType;

    @Value("${quanhu.pushDesc}")
    private String pushDesc;

    /** 推送环境 true:生产 false:测试 */
    @Value("${quanhu.pushEvn}")
    private Boolean pushEvn;


    @Bean
    PushConfigDTO pushConfigDTO() {
        PushConfigDTO pushConfigDTO = new PushConfigDTO();
       /* pushConfigDTO.setPushKey(pushKey);
        pushConfigDTO.setPushSecret(pushSecret);
        pushConfigDTO.setPushType(pushType);
        pushConfigDTO.setPushDesc(pushDesc);*/
        return pushConfigDTO;
    }

}
