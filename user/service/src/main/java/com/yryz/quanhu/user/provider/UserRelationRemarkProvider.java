package com.yryz.quanhu.user.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
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
    public Response<Boolean> setRemarkName(UserRelationRemarkDto dto) {
        Response<Boolean> out = null;
        try {
            logger.info("setRemarkName.start={}",JSON.toJSON(dto));
            out = ResponseUtils.returnObjectSuccess(userRelationRemarkService.setRemarkName(dto));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("setRemarkName.finish={},out={}",JSON.toJSON(dto),logWithOutNull(out));
        }
        return out;
    }

    @Override
    public Response<Boolean> resetRemarkName(UserRelationRemarkDto dto) {
        Response<Boolean> out = null;
        try {
            logger.info("resetRemarkName.start={}",JSON.toJSON(dto));
            out = ResponseUtils.returnObjectSuccess(userRelationRemarkService.resetRemarkName(dto));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("resetRemarkName.finish={},out={}",JSON.toJSON(dto),logWithOutNull(out));
        }
        return out;
    }

    @Override
    public Response<UserRelationRemarkDto> getRemarkDto(String sourceUserId, String targetUserId, UserRelationConstant.TYPE type) {
        Response<UserRelationRemarkDto> out = null;
        try {
            logger.info("getRemarkDto.start={}/{}/{}",sourceUserId,targetUserId,type);
            out = ResponseUtils.returnObjectSuccess(userRelationRemarkService.getRemarkDto(sourceUserId,targetUserId,type));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("getRemarkDto.finish={}/{}/{},out={}",sourceUserId,targetUserId,type,logWithOutNull(out));
        }
        return out;
    }

    @Override
    public Response<List<UserRelationRemarkDto>> selectBy(String sourceUserId, UserRelationConstant.TYPE type) {
        Response<List<UserRelationRemarkDto>> out = null;
        try {
            logger.info("selectBy.start={}/{} ",sourceUserId,type);
            out = ResponseUtils.returnObjectSuccess(userRelationRemarkService.selectBy(sourceUserId,type));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("selectBy.finish={}/{},out={}",sourceUserId,type,logWithOutNull(out));
        }
        return out;
    }


}
