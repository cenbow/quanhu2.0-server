package com.yryz.quanhu.behavior.count.contants;

/**
 * @Author:liupan
 * @Path: com.yryz.quanhu.behavior.count.contants
 * @Desc:
 * @Date: 2018/1/23.
 */
public class RedisContants {

    public static final String READ_COUNT_KEY = "QH_COUNT_READ_";

    public static final String WRITE_COUNT_KEY = "QH_COUNT_WRITE_";

    /**
     * 计数查询key
     *
     * @param kid 用户ID或者资源ID
     * @param code 行为枚举code
     * @return
     */
    public static String getReadCountKey(String kid, String code) {
        return READ_COUNT_KEY + kid + "_" + code;
    }

    /**
     * 计数写值key
     *
     * @param kid 用户ID或者资源ID
     * @param code 行为枚举code
     * @return
     */
    public static String getWriteCountKey(String kid, String code) {
        return WRITE_COUNT_KEY + kid + "_" + code;
    }
}
