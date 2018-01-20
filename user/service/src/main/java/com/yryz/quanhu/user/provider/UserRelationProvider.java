package com.yryz.quanhu.user.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.user.dto.UserRelationCountDto;
import com.yryz.quanhu.user.dto.UserRelationDto;
import com.yryz.quanhu.user.service.UserRelationApi;
import com.yryz.quanhu.user.service.UserRelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/18 15:06
 * Created by huangxy
 */
//@Service(interfaceClass = UserRelationApi.class)
public class UserRelationProvider implements UserRelationApi{

    private static final Logger logger = LoggerFactory.getLogger(UserRelationProvider.class);

    @Autowired
    private UserRelationService userRelationService;

    @Override
    public Response<Boolean> setRelation(String sourceUserId,String targetUserId, UserRelationApi.EVENT event){
        try {
            logger.info("user:{}","");
            return ResponseUtils.returnObjectSuccess(userRelationService.setRelation(sourceUserId,targetUserId,event));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Boolean> checkRelation(UserRelationDto dto) {
        try {
            return ResponseUtils.returnObjectSuccess(false);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<PageList<UserRelationDto>> selectByPage(UserRelationDto dto) {
        try {
            return ResponseUtils.returnObjectSuccess(userRelationService.selectByPage(dto));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Set<String>> selectBy(String sourceUserId, STATUS status) {
        try {
            return ResponseUtils.returnObjectSuccess(userRelationService.selectBy(sourceUserId,status));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<List<UserRelationDto>> selectBy(String userSourceKid, String[] userTargetKids) {
        try {
            return ResponseUtils.returnObjectSuccess(userRelationService.selectBy(userSourceKid,userTargetKids));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<UserRelationCountDto> totalBy(String userSourceKid) {
        try {
            return ResponseUtils.returnObjectSuccess(userRelationService.totalBy(userSourceKid));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }
    }
}
