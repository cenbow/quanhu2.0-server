package com.yryz.quanhu.support.config.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.support.config.api.BasicConfigForAdminApi;
import com.yryz.quanhu.support.config.dto.BasicConfigDto;
import com.yryz.quanhu.support.config.service.BasicConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/27 15:38
 * Created by huangxy
 */
@Service(interfaceClass = BasicConfigForAdminApi.class)
public class BasicConfigForAdminProvider implements BasicConfigForAdminApi{

    private static final Logger logger = LoggerFactory.getLogger(BasicConfigForAdminProvider.class);

    @Autowired
    private BasicConfigService basicConfigService;

    @Override
    public Response<Boolean> save(BasicConfigDto dto) {
        try {
            logger.info("save={} start", JSON.toJSON(dto));
            return ResponseUtils.returnObjectSuccess(basicConfigService.save(dto));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("save={} finish", JSON.toJSON(dto));
        }
    }

    @Override
    public Response<BasicConfigDto> get(BasicConfigDto dto) {
        try {
            logger.info("get={} start", JSON.toJSON(dto));
            return ResponseUtils.returnObjectSuccess(basicConfigService.get(dto));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("get={} finish", JSON.toJSON(dto));
        }
    }

    @Override
    public Response<Boolean> delete(BasicConfigDto dto) {
        try {
            logger.info("delete={} start", JSON.toJSON(dto));
            return ResponseUtils.returnObjectSuccess(basicConfigService.delete(dto));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("delete={} finish", JSON.toJSON(dto));
        }
    }

    @Override
    public Response<Boolean> updateStatus(BasicConfigDto dto) {
        try {
            logger.info("updateStatus={} start", JSON.toJSON(dto));
            return ResponseUtils.returnObjectSuccess(basicConfigService.updateStatus(dto));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("updateStatus={} finish", JSON.toJSON(dto));
        }
    }

    @Override
    public Response<List<BasicConfigDto>> list(BasicConfigDto dto) {
        try {
            logger.info("list={} start", JSON.toJSON(dto));
            return ResponseUtils.returnObjectSuccess(basicConfigService.list(dto));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("list={} finish", JSON.toJSON(dto));
        }
    }

    @Override
    public Response<PageList<BasicConfigDto>> pages(BasicConfigDto dto) {
        try {
            logger.info("pages={} start", JSON.toJSON(dto));
            return ResponseUtils.returnObjectSuccess(basicConfigService.pages(dto));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("pages={} finish", JSON.toJSON(dto));
        }
    }
}
