package com.yryz.quanhu.openapi.validation;

import com.yryz.common.annotation.UserBehaviorArgs;
import com.yryz.common.annotation.UserBehaviorValidation;
import com.yryz.quanhu.openapi.validation.filter.*;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/24 11:39
 * Created by huangxy
 *
 *
 *
 */
@Aspect
@Component
public class BehaviorEventValidAspect {

    private static final Logger logger = LoggerFactory.getLogger(BehaviorEventValidAspect.class);

    @Autowired
    private BehaviorArgsBuild behaviorArgsBuild;

    /**
     * 相关验证过滤器实现
     */
    @Autowired
    private UserBlacklistValidFilter userBlacklistValidFilter;
    @Autowired
    private UserIllegalWordsFilter userIllegalWordsFilter;
    @Autowired
    private UserLoginValidFilter userLoginValidFilter;
    @Autowired
    private UserMuteByCoterieValidFilter userMuteByCoterieValidFilter;
    @Autowired
    private UserMuteValidFilter userMuteValidFilter;



    /**
     * 定义切面 扫描注解切面
     */
    @Pointcut("@annotation(com.yryz.common.annotation.UserBehaviorArgs) && @annotation(com.yryz.common.annotation.UserBehaviorValidation)")
    public void behaviorValid(){

    }
    /**
     * 方法执行前校验
     * @param joinPoint
     */
    @Before("behaviorValid()")
    public void beforeValid(JoinPoint joinPoint){

        //获取注解参数
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        //校验规则
        UserBehaviorValidation validation = method.getDeclaredAnnotation(UserBehaviorValidation.class);
        //取值方式{注解中都用占位符标识，这需要通过切面方法参数中获取指定值}
        UserBehaviorArgs args = method.getDeclaredAnnotation(UserBehaviorArgs.class);

        //如果注解中自定义实现，validClass参数，则调用之定义实现（暂不做实现）


        //获取必要参数
        Object [] joinPointArgs = joinPoint.getArgs();

        //装载filterChain 循环验证
        BehaviorValidFilterChain filterChain = new BehaviorValidFilterChain();

        /**
         * 获取公共参数（后续注解中有参数，则在此添加）
         */
        filterChain.setContextValue("loginUserId",behaviorArgsBuild.getParameterValue(args.loginUserId(),joinPointArgs));
        filterChain.setContextValue("loginToken",behaviorArgsBuild.getParameterValue(args.loginToken(),joinPointArgs));
        filterChain.setContextValue("sourceType",behaviorArgsBuild.getParameterValue(args.sourceType(),joinPointArgs));
        filterChain.setContextValue("sourceId",behaviorArgsBuild.getParameterValue(args.sourceId(),joinPointArgs));
        filterChain.setContextValue("sourceUserId",behaviorArgsBuild.getParameterValue(args.sourceUserId(),joinPointArgs));
        filterChain.setContextValue("sourceTitle",behaviorArgsBuild.getParameterValue(args.sourceTitle(),joinPointArgs));
        filterChain.setContextValue("sourceContext",behaviorArgsBuild.getParameterValue(args.sourceContext(),joinPointArgs));

        //后续相关过滤器实现可以从切面参数中获取自定义参数，
        filterChain.setJoinPoint(joinPoint);
        filterChain.setUserBehaviorArgs(args);
        filterChain.setUserBehaviorValidation(validation);


        //是否校验登录
        if (validation.login()){
            filterChain.addFilter(userLoginValidFilter);
        }
        //是否校验平台禁言
        if(validation.mute()){
            filterChain.addFilter(userMuteValidFilter);
        }
        //是否校验黑名单
        if(validation.blacklist()){
            filterChain.addFilter(userBlacklistValidFilter);
        }
        //是否校验私圈禁言
        if(validation.muteByCoterie()){
            filterChain.addFilter(userMuteByCoterieValidFilter);
        }
        //是否校验敏感词
        if(validation.illegalWords()){
            filterChain.addFilter(userIllegalWordsFilter);
        }
        //执行
        filterChain.execute();
        logger.info("beforeValid.annotation={}");
    }
}
