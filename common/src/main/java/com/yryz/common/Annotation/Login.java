package com.yryz.common.Annotation;

import java.lang.annotation.*;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description: 用户登陆认证注解，该注解用在Resource接口类，用以标识需要进行登陆认证的接口，标注该注解的接口下的所有方法都需要认证
 *               与适用于方法上的{@link NotLogin}注解配合使用时，NotLogin注解会排除具体某方法的认证，如详情接口，不需要认证，可加该注解
 * @Date: Created in 2017 2017/11/16 15:19
 * @Author: pn
 */
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Login {

}
