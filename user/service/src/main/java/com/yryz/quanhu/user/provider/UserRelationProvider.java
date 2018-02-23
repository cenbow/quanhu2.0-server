package com.yryz.quanhu.user.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
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


    private static Object logWithOutNull(Response<?> out){
        try{
            if(out!=null){
                Object data = out.getData();
                if(data != null){
                    return JSON.toJSONString(data);
                }
            }
        }catch (Exception e){
            logger.error("logWithOutNull",e);
        }
        return "";
    }

    @Override
    public Response<UserRelationDto> setRelation(String sourceUserId,String targetUserId, UserRelationConstant.EVENT event){
        Response<UserRelationDto> out = null;
        try {
            logger.info("setRelation.start={}/{},eventType={}",sourceUserId,targetUserId,event);
            out = ResponseUtils.returnObjectSuccess(userRelationService.setRelation(sourceUserId,targetUserId,event));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("setRelation.finish={}/{},eventType={},out={}",sourceUserId,targetUserId,event,logWithOutNull(out));
        }
        return out;
    }

    @Override
    public Response<UserRelationDto> getRelation(String sourceUserId, String targetUserId) {
        Response<UserRelationDto> out = null;
        try {
            logger.info("getRelation.start={}/{}",sourceUserId,targetUserId);
            out = ResponseUtils.returnObjectSuccess(userRelationService.getRelation(sourceUserId,targetUserId));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            out = ResponseUtils.returnException(e);
        }finally {
            logger.info("getRelation.finish={}/{},out={}",sourceUserId,targetUserId,logWithOutNull(out));
        }
        return out;
    }

    @Override
    public Response<UserRelationDto> getRelationByTargetPhone(String sourceUserId, String targetPhoneNo) {
        Response<UserRelationDto> out = null;
        try {
            logger.info("getRelationByTargetPhone.start={}/{}",sourceUserId,targetPhoneNo);
            out = ResponseUtils.returnObjectSuccess(userRelationService.getRelationByTargetPhone(sourceUserId,targetPhoneNo));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("getRelationByTargetPhone.finish={}/{},out={}",sourceUserId,targetPhoneNo,logWithOutNull(out));
        }
        return out;
    }

    @Override
    public Response<PageList<UserRelationDto>> selectByPage(UserRelationDto dto,UserRelationConstant.STATUS status) {
        Response<PageList<UserRelationDto>> out = null;
        try {
            logger.info("selectByPage.start={}/{},page={}/{},status={}",dto.getSourceUserId(),dto.getTargetUserId(),dto.getCurrentPage(),dto.getPageSize(),status);
            out = ResponseUtils.returnObjectSuccess(userRelationService.selectByPage(dto,status));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("selectByPage.finish={}/{},page={}/{},status={},out={}",dto.getSourceUserId(),dto.getTargetUserId(),dto.getCurrentPage(),dto.getPageSize(),status,logWithOutNull(out));
        }
        return out;
    }

    @Override
    public Response<List<UserRelationDto>> selectByAll(UserRelationDto dto, UserRelationConstant.STATUS status) {
        Response<List<UserRelationDto>> out = null;
        try {
            logger.info("selectByAll.start={}/{},status={}",dto.getSourceUserId(),dto.getTargetUserId(),status);
            out = ResponseUtils.returnObjectSuccess(userRelationService.selectByAll(dto,status));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("selectByAll.finish={}/{},status={},out={}",dto.getSourceUserId(),dto.getTargetUserId(),status,logWithOutNull(out));
        }
        return out;
    }

    @Override
    public Response<Set<String>> selectBy(String sourceUserId, UserRelationConstant.STATUS status) {
        Response<Set<String>> out = null;
        try {
            logger.info("selectBy.start={},status={}",sourceUserId,status);
            out = ResponseUtils.returnObjectSuccess(userRelationService.selectBy(sourceUserId,status));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("selectBy.finish={},status={},out={}",sourceUserId,status,logWithOutNull(out));
        }
        return out;
    }

    @Override
    public Response<List<UserRelationDto>> selectBy(String userSourceKid, Set<String> userTargetKids) {
        Response<List<UserRelationDto>> out = null;
        try {
            logger.info("selectBy.start={}/{}",userSourceKid,userTargetKids);
            out = ResponseUtils.returnObjectSuccess(userRelationService.selectBy(userSourceKid,userTargetKids));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("selectBy.finish={}/{},out={}",userSourceKid,userTargetKids,logWithOutNull(out));
        }
        return out;
    }

    @Override
    public Response<UserRelationCountDto> totalBy(String sourceUserId,String targetUserId){
        Response<UserRelationCountDto> out = null;
        try {
            logger.info("totalBy.start={}/{}",sourceUserId,targetUserId);
            out = ResponseUtils.returnObjectSuccess(userRelationService.totalBy(sourceUserId,targetUserId));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("totalBy.finish={}/{},out={}",sourceUserId,targetUserId,logWithOutNull(out));
        }
        return out;
    }
}
