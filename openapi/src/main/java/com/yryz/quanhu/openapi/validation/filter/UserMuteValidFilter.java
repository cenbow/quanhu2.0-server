package com.yryz.quanhu.openapi.validation.filter;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.quanhu.openapi.validation.BehaviorArgsBuild;
import com.yryz.quanhu.openapi.validation.BehaviorValidFilterChain;
import com.yryz.quanhu.openapi.validation.IBehaviorValidFilter;
import com.yryz.quanhu.user.service.AccountApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/25 13:38
 * Created by huangxy
 *
 * 用户平台禁言操作
 */
@Service
public class UserMuteValidFilter implements IBehaviorValidFilter {

    private static final Logger logger = LoggerFactory.getLogger(UserMuteValidFilter.class);

    @Reference(check = false)
    private AccountApi accountApi;

    @Autowired
    private BehaviorArgsBuild behaviorArgsBuild;

    @Override
    public void filter(BehaviorValidFilterChain filterChain) {
        logger.info("验证用户平台禁言={}",filterChain.getContext());
        /**
         * 平台禁言，做强制性校验
         */

        //统一从request中获取，不在从注解中获取key进行查询
        HttpServletRequest request = (HttpServletRequest) behaviorArgsBuild.getObjByClass(HttpServletRequest.class,filterChain.getJoinPoint().getArgs());
        String loginUserId = request.getHeader("userId");

        logger.info("验证用户平台禁言={}",loginUserId);

        Response<Boolean> rpc = accountApi.checkUserDisTalk(Long.parseLong(loginUserId));
        if(rpc.success()&&rpc.getData()){
            filterChain.execute();
        }else{
            throw new QuanhuException("","","您已被平台管理员禁言，不允许操作");
        }
    }
}
