package com.yryz.common.annotation;

import java.lang.annotation.*;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/25 10:20
 * Created by huangxy
 */
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserBehaviorArgs {

    String loginUserId() default "request.head.userId";

    String loginToken() default "request.head.token";

    String sourceType() default "";

    String sourceId() default "";

    String sourceUserId() default "";

    String sourceTitle() default "";

    String sourceContext() default "";

}
