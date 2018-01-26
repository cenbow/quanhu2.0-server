package com.yryz.quanhu.openapi.validation.filter;

import com.yryz.quanhu.openapi.validation.BehaviorValidFilterChain;
import com.yryz.quanhu.openapi.validation.IBehaviorValidFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/25 13:41
 * Created by huangxy
 *
 * 用户私圈 禁言校验
 */
@Service
public class UserMuteByCoterieValidFilter implements IBehaviorValidFilter {

    private static final Logger logger = LoggerFactory.getLogger(UserLoginValidFilter.class);

    @Override
    public void filter(BehaviorValidFilterChain filterChain) {
        logger.info("验证用户私圈禁言={}",filterChain.getContext());
        filterChain.execute();
    }

}
