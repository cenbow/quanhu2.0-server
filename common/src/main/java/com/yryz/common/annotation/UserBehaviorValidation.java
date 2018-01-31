package com.yryz.common.annotation;

import java.lang.annotation.*;

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

    String event() default "";

    Class<?> validClass() default void.class;

    /**
     * 基于项目全局校验，登录，禁言，黑名单，敏感词
     * @return
     */
    boolean login() default false;
    boolean mute() default false;
    boolean blacklist() default false;
    boolean illegalWords() default false;

    /**
     * 基于私圈的禁言
     * @return
     */
    @Deprecated
    boolean muteByCoterie() default false;

    boolean isCoterieMute() default false;

    boolean isCoterieMember() default false;
}
