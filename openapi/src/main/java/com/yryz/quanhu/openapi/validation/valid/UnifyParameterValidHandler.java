package com.yryz.quanhu.openapi.validation.valid;

import com.yryz.common.annotation.UserBehaviorArgs;
import com.yryz.common.annotation.UserBehaviorValidation;
import com.yryz.quanhu.openapi.validation.BehaviorArgsBuild;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/2/9 16:53
 * Created by huangxy
 * 统一参数校验 在UserBehaviorValidation注解上可以自定义
 *
 * 由于注解上的校验机制是代码写死的（true/false），
 * 可以自定义validClass进行动态加载，返回新的 UserBehaviorValidation 对象
 */
@Service
public class UnifyParameterValidHandler {

    @Autowired
    private BehaviorArgsBuild behaviorArgsBuild;

    /**
     * 重新设置UserBehaviorValidation的值
     * @param validation
     * @param args
     * @param joinPoint
     * @return
     */
    public void rebuild(UserBehaviorValidation validation, UserBehaviorArgs args, JoinPoint joinPoint){
        /**
         * 参数通过body传入
         */
        String [] requestBodyArray = args.contexts();
        if(requestBodyArray == null || requestBodyArray.length==0){
            return;
        }

        //获取注解代理类，根据前端传入参数进行赋值
        InvocationHandler handler = Proxy.getInvocationHandler(validation);
        //获取注解值
        Map<String,Object> memberMap = this.getAnnotationValue(handler);

        /**
         * 查询request传入的值，更新注解中的值
         */
        for(int i = 0 ; i < requestBodyArray.length ; i++){
            String annotationKey = requestBodyArray[i];

            //获取值
            boolean val = this.getBoolean(annotationKey,joinPoint);

            //获取属性
            int lastKey = annotationKey.lastIndexOf(".");
            //获取取值annotation 属性
            String fieldName = annotationKey.substring(lastKey+1,annotationKey.length());

            //覆盖原有值
            memberMap.put(fieldName,val);
        }
    }

    private boolean getBoolean(String key,JoinPoint joinPoint){
        Object val = behaviorArgsBuild.getParameterValue(key,joinPoint.getArgs());
        if(val != null){
            return Boolean.valueOf(String.valueOf(val));
        }else {
            return false;
        }
    }

    public Map<String,Object> getAnnotationValue(InvocationHandler handler){
        try {

            Field field = handler.getClass().getDeclaredField("memberValues");
            field.setAccessible(true);

            return (Map<String, Object>) field.get(handler);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


}
