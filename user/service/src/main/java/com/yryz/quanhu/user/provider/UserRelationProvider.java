package com.yryz.quanhu.user.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.user.contants.UserRelationConstant;
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
@Service(interfaceClass = UserRelationApi.class)
public class UserRelationProvider implements UserRelationApi{

    private static final Logger logger = LoggerFactory.getLogger(UserRelationProvider.class);

    @Autowired
    private UserRelationService userRelationService;

    @Override
    public Response<UserRelationDto> setRelation(String sourceUserId,String targetUserId, UserRelationConstant.EVENT event){
        try {
            logger.info("setRelation={}/{},eventType={} start",sourceUserId,targetUserId,event);
            return ResponseUtils.returnObjectSuccess(userRelationService.setRelation(sourceUserId,targetUserId,event));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("setRelation={}/{},eventType={} finish",sourceUserId,targetUserId,event);
        }
    }

    @Override
    public Response<UserRelationDto> getRelation(String sourceUserId, String targetUserId) {
        try {
            logger.info("getRelation={}/{} start",sourceUserId,targetUserId);
            return ResponseUtils.returnObjectSuccess(userRelationService.getForceRelation(sourceUserId,targetUserId));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("getRelation={}/{} finish",sourceUserId,targetUserId);
        }
    }

    @Override
    public Response<PageList<UserRelationDto>> selectByPage(UserRelationDto dto,UserRelationConstant.STATUS status) {
        try {
            logger.info("selectByPage={}/{},status={} start",dto.getSourceUserId(),dto.getTargetUserId(),status);
            return ResponseUtils.returnObjectSuccess(userRelationService.selectByPage(dto,status));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("selectByPage={}/{},status={} finish",dto.getSourceUserId(),dto.getTargetUserId(),status);
        }
    }

    @Override
    public Response<Set<String>> selectBy(String sourceUserId, UserRelationConstant.STATUS status) {
        try {
            logger.info("selectBy={},status={} start",sourceUserId,status);
            return ResponseUtils.returnObjectSuccess(userRelationService.selectBy(sourceUserId,status));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("selectBy={},status={} finish",sourceUserId,status);
        }
    }

    @Override
    public Response<List<UserRelationDto>> selectBy(String userSourceKid, String[] userTargetKids) {
        try {
            logger.info("selectBy={}/{} start",userSourceKid,userTargetKids);
            return ResponseUtils.returnObjectSuccess(userRelationService.selectBy(userSourceKid,userTargetKids));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("selectBy={}/{} finish",userSourceKid,userTargetKids);
        }
    }

    @Override
    public Response<UserRelationCountDto> totalBy(String userSourceKid) {
        try {
            logger.info("totalBy={} start",userSourceKid);
            return ResponseUtils.returnObjectSuccess(userRelationService.totalBy(userSourceKid));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("totalBy={} finish",userSourceKid);
        }
    }
}
