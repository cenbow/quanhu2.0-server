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

    /**
     * 当前登录ID
     * @return
     */
    String loginUserId() default "request.head.userId";

    /**
     * 当前登录token
     * @return
     */
    String loginToken() default "request.head.token";

    /**
     * 操作资源类型
     * @return
     */
    String sourceType() default "";

    /**
     * 操作资源ID
     * @return
     */
    String sourceId() default "";

    /**
     * 操作资源所属着
     * @return
     */
    String sourceUserId() default "";

    /**
     * 操作资源内容
     * @return
     */
    String[] sourceContexts() default {};

    /**
     * 私圈ID
     */
    String coterieId() default "";

}
