package com.yryz.quanhu.openapi.validation.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.quanhu.openapi.validation.BehaviorValidFilterChain;
import com.yryz.quanhu.openapi.validation.IBehaviorValidFilter;
import com.yryz.quanhu.user.contants.UserRelationConstant;
import com.yryz.quanhu.user.dto.UserRelationDto;
import com.yryz.quanhu.user.service.UserRelationApi;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/25 13:39
 * Created by huangxy
 *
 * 用户操作对象黑名单操作
 */
@Service
public class UserBlacklistValidFilter implements IBehaviorValidFilter{

    private static final Logger logger = LoggerFactory.getLogger(UserBlacklistValidFilter.class);

    @Reference(check = false)
    private UserRelationApi userRelationApi;

    @Override
    public void filter(BehaviorValidFilterChain filterChain) {
        logger.info("验证用户关系黑名单={}",filterChain.getContext());

        //获取资源作者
        Object objValue = filterChain.getContext().get("sourceUserId");
        if(objValue==null){
            throw new QuanhuException(ExceptionEnum.SysException.getCode(),ExceptionEnum.SysException.getShowMsg(),"该资源作者ID参数异常");
        }

        String  sourceUserId = String.valueOf(objValue);
        //从上下文获取request 获取当前登录用户
        String loginUserId = String.valueOf(filterChain.getLoginUserId());

        //当前登录用户，和作者的关系
        Response<UserRelationDto> rpc = userRelationApi.getRelation(loginUserId,sourceUserId);
        UserRelationDto dto = rpc.getData();
        if(dto!=null){
            int status = dto.getRelationStatus();
            //登录用户把作者拉黑了,或者互相拉黑
            if(status == UserRelationConstant.STATUS.TO_BLACK.getCode()||
                    status == UserRelationConstant.STATUS.BOTH_BLACK.getCode()){
                throw new QuanhuException(ExceptionEnum.USER_BLACK_TARGETUSER_ERROR);
            }
            //作者把登录用户拉黑了
            if(status==UserRelationConstant.STATUS.FROM_BLACK.getCode()){
                throw new QuanhuException(ExceptionEnum.TARGETUSER_BLACK_USER_ERROR);
            }
        }
        filterChain.execute();
    }
}
