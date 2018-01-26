package com.yryz.quanhu.openapi.validation.filter;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.quanhu.openapi.validation.BehaviorEventValidAspect;
import com.yryz.quanhu.openapi.validation.BehaviorValidFilterChain;
import com.yryz.quanhu.openapi.validation.IBehaviorValidFilter;
import com.yryz.quanhu.user.contants.UserRelationConstant;
import com.yryz.quanhu.user.dto.UserRelationDto;
import com.yryz.quanhu.user.service.UserRelationApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

        String sourceUserId = (String) filterChain.getContext().get("sourceUserId");
        String loginUserId = (String) filterChain.getContext().get("loginUserId");

        //当前登录用户，和作者的关系
        Response<UserRelationDto> rpc = userRelationApi.getRelation(loginUserId,sourceUserId);
        if(rpc.success()){
            UserRelationDto dto = rpc.getData();
            if(dto!=null){
                //登录用户把作者拉黑了
                if(dto.getRelationStatus()== UserRelationConstant.STATUS.TO_BLACK.getCode()){
                    throw new QuanhuException("","","您已把该资源作者拉黑，不允许操作");
                }
                //作者把登录用户拉黑了
                if(dto.getRelationStatus()==UserRelationConstant.STATUS.FROM_BLACK.getCode()){
                    throw new QuanhuException("","","该资源作者已将您拉黑，不允许操作");
                }
            }
        }else{
            throw new QuanhuException("","","调用验证服务异常");
        }
        filterChain.execute();
    }
}
