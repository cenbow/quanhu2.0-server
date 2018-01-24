package com.yryz.quanhu.user.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.user.contants.UserRelationConstant;
import com.yryz.quanhu.user.dto.UserRelationRemarkDto;
import com.yryz.quanhu.user.service.UserRelationRemarkApi;
import com.yryz.quanhu.user.service.UserRelationRemarkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/19 19:18
 * Created by huangxy
 */
@Service(interfaceClass = UserRelationRemarkApi.class)
public class UserRelationRemarkProvider implements UserRelationRemarkApi{

    private static final Logger logger = LoggerFactory.getLogger(UserRelationRemarkProvider.class);

    @Autowired
    private UserRelationRemarkService userRelationRemarkService;

    @Override
    public Response<Boolean> setRemarkName(UserRelationRemarkDto dto) {
        try {
            logger.info("setRemarkName={}/{} > {}/{} start",dto.getSourceUserId(),dto.getTargetUserId(),dto.getRemarkType(),dto.getRemarkValue());
            return ResponseUtils.returnObjectSuccess(userRelationRemarkService.setRemarkName(dto));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("setRemarkName={}/{} > {}/{} finish",dto.getSourceUserId(),dto.getTargetUserId(),dto.getRemarkType(),dto.getRemarkValue());
        }
    }

    @Override
    public Response<Boolean> resetRemarkName(UserRelationRemarkDto dto) {
        try {
            logger.info("resetRemarkName={}/{} > {} start",dto.getSourceUserId(),dto.getTargetUserId(),dto.getRemarkType());
            return ResponseUtils.returnObjectSuccess(userRelationRemarkService.resetRemarkName(dto));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("resetRemarkName={}/{} > {} finish",dto.getSourceUserId(),dto.getTargetUserId(),dto.getRemarkType());
        }
    }

    @Override
    public Response<UserRelationRemarkDto> getRemarkDto(String sourceUserId, String targetUserId, UserRelationConstant.TYPE type) {
        try {
            logger.info("getRemarkDto={}/{} > {} start",sourceUserId,targetUserId,type);
            return ResponseUtils.returnObjectSuccess(userRelationRemarkService.getRemarkDto(sourceUserId,targetUserId,type));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("getRemarkDto={}/{} > {} finish",sourceUserId,targetUserId,type);
        }
    }

    @Override
    public Response<List<UserRelationRemarkDto>> selectBy(String sourceUserId, UserRelationConstant.TYPE type) {
        try {
            return ResponseUtils.returnObjectSuccess(userRelationRemarkService.selectBy(sourceUserId,type));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }
    }


}
