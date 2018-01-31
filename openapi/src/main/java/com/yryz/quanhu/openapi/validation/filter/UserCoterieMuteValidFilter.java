package com.yryz.quanhu.openapi.validation.filter;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.quanhu.coterie.member.service.CoterieMemberAPI;
import com.yryz.quanhu.openapi.validation.BehaviorArgsBuild;
import com.yryz.quanhu.openapi.validation.BehaviorValidFilterChain;
import com.yryz.quanhu.openapi.validation.IBehaviorValidFilter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

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
public class UserCoterieMuteValidFilter implements IBehaviorValidFilter {

    private static final Logger logger = LoggerFactory.getLogger(UserCoterieMuteValidFilter.class);

    @Reference(check = false)
    private CoterieMemberAPI coterieMemberAPI;

    @Autowired
    private BehaviorArgsBuild behaviorArgsBuild;

    @Override
    public void filter(BehaviorValidFilterChain filterChain) {
        logger.info("验证用户私圈禁言={}",filterChain.getContext());

        //获取用户ID
        long loginUserId = filterChain.getLoginUserId();

        //获取私圈ID
        Object objValue = behaviorArgsBuild.getParameterValue(filterChain.getUserBehaviorArgs().coterieId(),
                filterChain.getJoinPoint().getArgs());
        if(objValue==null){
            throw new QuanhuException("","","缺少私圈参数ID");
        }

        long coterieId = Long.valueOf(String.valueOf(objValue));
        Response<Boolean> rpc = coterieMemberAPI.isBanSpeak(loginUserId,coterieId);
        if(rpc.success()&&!rpc.getData()){
            //执行下一个
            filterChain.execute();
        }else{
            throw new QuanhuException("","","您已被圈主禁言，不允许操作");
        }
    }

}
