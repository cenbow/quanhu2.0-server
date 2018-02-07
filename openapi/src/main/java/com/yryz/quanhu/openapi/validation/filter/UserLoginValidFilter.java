package com.yryz.quanhu.openapi.validation.filter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.quanhu.openapi.service.AuthService;
import com.yryz.quanhu.openapi.validation.BehaviorValidFilterChain;
import com.yryz.quanhu.openapi.validation.IBehaviorValidFilter;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/25 13:35
 * Created by huangxy
 *
 * 用户登录权限认证
 * 以异常返回
 *
 */
@Service
public class UserLoginValidFilter implements IBehaviorValidFilter {

    private static final Logger logger = LoggerFactory.getLogger(UserLoginValidFilter.class);

    @Autowired
    private AuthService authService;

    @Override
    public void filter(BehaviorValidFilterChain filterChain) {
        logger.info("验证用户登录信息={}",filterChain.getContext());
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        if(request==null){
            throw new QuanhuException(ExceptionEnum.SysException.getCode(),ExceptionEnum.SysException.getShowMsg(),"服务器异常，缺失参数:HttpServletRequest");
        }
        //执行验证
        authService.checkToken(request);
        //下个filter
        filterChain.execute();
    }

}
