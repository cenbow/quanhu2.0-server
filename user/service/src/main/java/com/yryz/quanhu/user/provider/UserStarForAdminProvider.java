package com.yryz.quanhu.user.provider;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.user.dto.UserStarAuthDto;
import com.yryz.quanhu.user.dto.UserTagDTO;
import com.yryz.quanhu.user.service.UserStarForAdminApi;
import com.yryz.quanhu.user.service.UserStarForAdminService;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/2/3 15:10
 * Created by huangxy
 */
@Service(interfaceClass=UserStarForAdminApi.class)
public class UserStarForAdminProvider implements UserStarForAdminApi{

    private static final Logger logger = LoggerFactory.getLogger(UserStarForAdminProvider.class);

    @Autowired
    private UserStarForAdminService userStarForAdminService;

    @Override
    public Response<Boolean> updateTags(UserStarAuthDto dto) {
        try {
            logger.info("updateTags={} start", JSON.toJSON(dto));
            return ResponseUtils.returnObjectSuccess(userStarForAdminService.updateTags(dto));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("updateTags={} finish",JSON.toJSON(dto));
        }
    }

    @Override
    public Response<Boolean> updateAuth(UserStarAuthDto dto) {
        try {
            logger.info("updateAuth={} start", JSON.toJSON(dto));
            return ResponseUtils.returnObjectSuccess(userStarForAdminService.updateAuth(dto));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("updateAuth={} finish",JSON.toJSON(dto));
        }
    }

    @Override
    public Response<Boolean> updateRecmd(UserStarAuthDto dto) {
        try {
            logger.info("updateRecmd={} start", JSON.toJSON(dto));
            return ResponseUtils.returnObjectSuccess(userStarForAdminService.updateRecmd(dto,false));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("updateRecmd={} finish",JSON.toJSON(dto));
        }
    }

    @Override
    public Response<Boolean> updateRecmdDesc(UserStarAuthDto dto) {
        try {
            logger.info("updateRecmdDesc={} start", JSON.toJSON(dto));
            return ResponseUtils.returnObjectSuccess(userStarForAdminService.updateRecmdDesc(dto));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("updateRecmdDesc={} finish",JSON.toJSON(dto));
        }
    }


    @Override
    public Response<Boolean> updateRecmdTop(UserStarAuthDto dto) {
        try {
            logger.info("updateRecmdTop={} start", JSON.toJSON(dto));
            return ResponseUtils.returnObjectSuccess(userStarForAdminService.updateRecmd(dto,true));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("updateRecmdTop={} finish",JSON.toJSON(dto));
        }
    }

    @Override
    public Response<Boolean> updateRecmdsort(List<UserStarAuthDto> dtos) {
        try {
            logger.info("updateRecmdsort={} start", JSON.toJSON(dtos));
            return ResponseUtils.returnObjectSuccess(userStarForAdminService.updateRecmdsort(dtos));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("updateRecmdsort={} finish",JSON.toJSON(dtos));
        }
    }

    @Override
    public Response<List<UserTagDTO>> getTags(UserStarAuthDto dto) {
        try {
            logger.info("updateRecmdTop={} start", JSON.toJSON(dto));
            return ResponseUtils.returnObjectSuccess(userStarForAdminService.getTags(dto));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("updateRecmdTop={} finish",JSON.toJSON(dto));
        }
    }

    @Override
    public Response<UserStarAuthDto> getAuth(UserStarAuthDto dto) {
        try {
            logger.info("getAuth={} start", JSON.toJSON(dto));
            return ResponseUtils.returnObjectSuccess(userStarForAdminService.getAuth(dto));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("getAuth={} finish",JSON.toJSON(dto));
        }
    }

    @Override
    public Response<PageList<UserStarAuthDto>> listByAuth(UserStarAuthDto dto) {
        try {
            logger.info("listByAuth={} start", JSON.toJSON(dto));
            return ResponseUtils.returnObjectSuccess(userStarForAdminService.listByAuth(dto));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("listByAuth={} finish",JSON.toJSON(dto));
        }
    }

    @Override
    public Response<PageList<UserStarAuthDto>> listByAuthLog(UserStarAuthDto dto) {
        try {
            logger.info("listByAuth={} start", JSON.toJSON(dto));
            return ResponseUtils.returnObjectSuccess(userStarForAdminService.listByAuthLog(dto));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("listByAuth={} finish",JSON.toJSON(dto));
        }
    }

    @Override
    public Response<PageList<UserStarAuthDto>> listByRecmd(UserStarAuthDto dto) {
        try {
            logger.info("listByRecmd={} start", JSON.toJSON(dto));
            return ResponseUtils.returnObjectSuccess(userStarForAdminService.listByRecmd(dto));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("listByRecmd={} finish",JSON.toJSON(dto));
        }
    }
}
