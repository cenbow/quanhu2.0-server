package com.yryz.quanhu.openapi.validation.filter;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.annotation.UserBehaviorArgs;
import com.yryz.common.response.Response;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.openapi.validation.BehaviorArgsBuild;
import com.yryz.quanhu.openapi.validation.BehaviorValidFilterChain;
import com.yryz.quanhu.openapi.validation.IBehaviorValidFilter;
import com.yryz.quanhu.support.illegalWord.api.IllegalWordsApi;
import com.yryz.quanhu.user.service.UserRelationApi;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/25 13:40
 * Created by huangxy
 *
 * 用户提交敏感词校验
 */
@Service
public class UserIllegalWordsFilter implements IBehaviorValidFilter {

    private static final Logger logger = LoggerFactory.getLogger(UserIllegalWordsFilter.class);

    @Reference(check = false)
    private IllegalWordsApi illegalWordsApi;

    @Autowired
    private BehaviorArgsBuild behaviorArgsBuild;

    @Override
    public void filter(BehaviorValidFilterChain filterChain) {

        logger.info("验证用户提交内容敏感词={}",filterChain.getContext());

        UserBehaviorArgs args = filterChain.getUserBehaviorArgs();
        JoinPoint joinPoint = filterChain.getJoinPoint();
        /**
         * 敏感词，不做强校验，异常不抛出，继续执行
          */
        try{

            String sourceTitle = (String) filterChain.getContext().get("sourceTitle");
            String sourceContext = (String) filterChain.getContext().get("sourceContext");
            //标题
            Response<String> rpc = illegalWordsApi.replaceIllegalWords(sourceTitle,"*");
            if(rpc.success()){
                //重新赋值
                behaviorArgsBuild.replaceParameterValue(args.sourceTitle(),joinPoint.getArgs(),rpc.getData());
            }
            //正文
            rpc = illegalWordsApi.replaceIllegalWords(sourceContext,"*");
            if(rpc.success()){
                behaviorArgsBuild.replaceParameterValue(args.sourceContext(),joinPoint.getArgs(),rpc.getData());
            }

        }catch (Exception e){
            logger.warn("敏感词接口校验异常",e);
        }finally {
            filterChain.execute();
        }

    }
}
