package com.yryz.quanhu.message.push.remote;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.yryz.common.response.Response;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.message.push.entity.PushConfigDTO;
import com.yryz.quanhu.support.config.api.BasicConfigApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(ConfigRemote.class);

    public static final String PUSH_CONFIG = "push.config.";


    @Reference(check = false)
    private BasicConfigApi basicConfigApi;


    public PushConfigDTO getPushConfig(String appId) {
        PushConfigDTO configDTO = null;
        try {
            Response<String> configValue = basicConfigApi.getValue(PUSH_CONFIG + appId);
            configDTO = GsonUtils.json2Obj(configValue.getData(), PushConfigDTO.class);
            logger.info("getPushConfig: {}", GsonUtils.parseJson(configDTO));
        } catch (Exception e) {
            logger.error("getPushConfig error", e);
        }
        return configDTO;
    }
}
