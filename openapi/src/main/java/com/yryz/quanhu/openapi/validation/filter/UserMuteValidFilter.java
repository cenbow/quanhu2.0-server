package com.yryz.quanhu.openapi.validation.filter;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.Response;
import com.yryz.quanhu.openapi.validation.BehaviorValidFilterChain;
import com.yryz.quanhu.openapi.validation.IBehaviorValidFilter;
import com.yryz.quanhu.user.service.AccountApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public void filter(BehaviorValidFilterChain filterChain) {
        logger.info("验证用户平台禁言={}",filterChain.getContext());
        String loginUserId = (String) filterChain.getContext().get("loginUserId");
        /**
         * 平台禁言，不做强制性校验
         */
        try{
            Response<Boolean> rpc = accountApi.checkUserDisTalk(Long.parseLong(loginUserId));
        }catch (Exception e){
            logger.warn("");
        }finally {
            filterChain.execute();
        }
    }
}
