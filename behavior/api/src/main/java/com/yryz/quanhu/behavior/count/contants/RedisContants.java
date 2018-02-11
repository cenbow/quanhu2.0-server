package com.yryz.quanhu.behavior.count.contants;

import com.yryz.common.constant.CommonConstants;
import com.yryz.common.context.Context;

/**
 * @Author:liupan
 * @Path: com.yryz.quanhu.behavior.count.contants
 * @Desc:
 * @Date: 2018/1/23.
 */
public class RedisContants {

    public static final String READ_COUNT_KEY = Context.getProperty(CommonConstants.SPRING_APPLICATION_NAME)+":COUNT:READ:";

    public static final String WRITE_COUNT_KEY = Context.getProperty(CommonConstants.SPRING_APPLICATION_NAME)+":COUNT:WRITE:";

    /**
     * 读key的失效时间
     */
    public static final Long READ_COUNT_KEY_EXPIRE = 1440L;

    /**
     * 写key的失效时间
     */
    public static final Long WRITE_COUNT_KEY_EXPIRE = 90L;

    /**
     * 计数查询key
     *
     * @param kid  用户ID或者资源ID
     * @param code 行为枚举code
     * @return
     */
    public static String getReadCountKey(String kid, String code, String page) {
        return READ_COUNT_KEY + kid + ":" + code + "_" + page;
    }

    /**
     * 计数写值key
     *
     * @param kid  用户ID或者资源ID
     * @param code 行为枚举code
     * @return
     */
    public static String getWriteCountKey(String kid, String code, String page) {
        return WRITE_COUNT_KEY + kid + ":" + code + "_" + page;
    }
}
