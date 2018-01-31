package com.yryz.quanhu.openapi.validation.filter;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.quanhu.coterie.member.constants.MemberConstant;
import com.yryz.quanhu.coterie.member.service.CoterieMemberAPI;
import com.yryz.quanhu.openapi.validation.BehaviorArgsBuild;
import com.yryz.quanhu.openapi.validation.BehaviorValidFilterChain;
import com.yryz.quanhu.openapi.validation.IBehaviorValidFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/31 9:49
 * Created by huangxy
 * 验证成员是否在私圈内
 *
 */
@Service
public class UserCoterieMemberValidFilter implements IBehaviorValidFilter{

    private static final Logger logger = LoggerFactory.getLogger(UserCoterieMemberValidFilter.class);

    @Autowired
    private BehaviorArgsBuild behaviorArgsBuild;

    @Reference(check = false)
    private CoterieMemberAPI coterieMemberAPI;


    @Override
    public void filter(BehaviorValidFilterChain filterChain) {

        //当前登录ID
        long userId = filterChain.getLoginUserId();

        //私圈ID
        String coterieKey = filterChain.getUserBehaviorArgs().coterieId();
        Object coterieValue = behaviorArgsBuild.getParameterValue(coterieKey,filterChain.getJoinPoint().getArgs());
        if(coterieValue==null){
            throw new QuanhuException("","","私圈参数非法");
        }

        /**
         * 验证
         */
        long coterieId = Long.parseLong(String.valueOf(coterieValue));
        Response<Integer> rpc = coterieMemberAPI.permission(userId,coterieId);
        int status = rpc.getData().intValue();
        logger.info("权限状态：{}",status);
        //非成员，或者圈主
        if(status!=MemberConstant.Permission.MEMBER.getStatus().intValue()
                &&status!=MemberConstant.Permission.OWNER.getStatus().intValue()){
            throw new QuanhuException("","","您非本私圈成员，不允许操作");
        }
        filterChain.execute();
    }
}
