package com.yryz.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/24 20:01
 * Created by huangxy
 */
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserBehaviorValidation {

    /**  
    * @Description: 鉴权方法名称
    * @author wangheng
    * @param @return
    * @return String
    * @throws  
    */
    String event() default "";

    Class<?> validClass() default void.class;

    /**
     * 基于项目全局校验，登录，禁言，黑名单，敏感词
     * @return
     */
    /**  
    * @Description: 登录toke
    * @author wangheng
    * @param @return
    * @return boolean
    * @throws  
    */
    boolean login() default false;
    /**  
    * @Description: 平台禁言
    * @author wangheng
    * @param @return
    * @return boolean
    * @throws  
    */
    boolean mute() default false;
    /**  
    * @Description: 登录用户与目标用户拉黑
    * @author wangheng
    * @param @return
    * @return boolean
    * @throws  
    */
    boolean blacklist() default false;
    /**  
    * @Description: 铭感词 
    * @author wangheng
    * @param @return
    * @return boolean
    * @throws  
    */
    boolean illegalWords() default false;
    
    /**  
     * @Description: 私圈成员
     * @author wangheng
     * @param @return
     * @return boolean
     * @throws  
     */
    boolean isCoterieMember() default false;

    /**  
    * @Description: 私圈禁言
    * @author wangheng
    * @param @return
    * @return boolean
    * @throws  
    */
    boolean isCoterieMute() default false;
}
