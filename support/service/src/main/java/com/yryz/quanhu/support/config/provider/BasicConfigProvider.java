package com.yryz.quanhu.support.config.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.support.config.api.BasicConfigApi;
import com.yryz.quanhu.support.config.service.BasicConfigService;
import com.yryz.quanhu.user.service.UserRelationApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/27 15:38
 * Created by huangxy
 */
@Service(interfaceClass = BasicConfigApi.class)
public class BasicConfigProvider implements BasicConfigApi{

    private static final Logger logger = LoggerFactory.getLogger(BasicConfigProvider.class);

    @Autowired
    private BasicConfigService basicConfigService;

    @Override
    public Response<String> getValue(String key) {
        try {
            logger.info("getValue={} start",key);
            return ResponseUtils.returnObjectSuccess(basicConfigService.getValue(key));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("getValue={} finish",key);
        }
    }

    @Override
    public Response<String> getValue(String key, String defaultValue) {
        try {
            logger.info("getValue={}/{} start",key,defaultValue);
            return ResponseUtils.returnObjectSuccess(basicConfigService.getValue(key));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }finally {
            logger.info("getValue={}/{} finish",key,defaultValue);
            return ResponseUtils.returnObjectSuccess(defaultValue);
        }
    }

    @Override
    public Response<Map<String, String>> getValues(String key) {
        try {
            logger.info("getValues={} start",key);
            return ResponseUtils.returnObjectSuccess(basicConfigService.getValues(key));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("getValues={} finish",key);
        }
    }
}
