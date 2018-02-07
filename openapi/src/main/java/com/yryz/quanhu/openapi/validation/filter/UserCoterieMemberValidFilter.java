package com.yryz.quanhu.openapi.validation.filter;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.quanhu.coterie.member.constants.MemberConstant;
import com.yryz.quanhu.coterie.member.service.CoterieMemberAPI;
import com.yryz.quanhu.openapi.validation.BehaviorArgsBuild;
import com.yryz.quanhu.openapi.validation.BehaviorValidFilterChain;
import com.yryz.quanhu.openapi.validation.IBehaviorValidFilter;

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
        
        // 根据业务场景，若入参中有私圈ID，就做私圈成员鉴权
        if(coterieValue==null){
            return;
        }
        long coterieId = NumberUtils.toLong(coterieValue.toString());
        if(coterieId <= 0 ){
            return;
        }
        
        Response<Integer> rpc = coterieMemberAPI.permission(userId,coterieId);
        int status = rpc.getData().intValue();
        logger.info("权限状态：{}",status);
        //非成员，或者圈主
        if(status!=MemberConstant.Permission.MEMBER.getStatus().intValue()
                &&status!=MemberConstant.Permission.OWNER.getStatus().intValue()){
            throw new QuanhuException(ExceptionEnum.COTERIE_NOT_MEMBER);
        }
        filterChain.execute();
    }
}
