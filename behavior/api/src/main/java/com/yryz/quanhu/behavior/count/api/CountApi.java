package com.yryz.quanhu.behavior.count.api;

import com.yryz.common.response.Response;
import com.yryz.quanhu.behavior.count.contants.BehaviorEnum;

import java.util.Map;

/**
 * @Author: liupan
 * @Path: com.yryz.quanhu.behavior.count.api
 * @Desc:
 * @Date: 2018/1/23.
 */
public interface CountApi {

    /**
     * 提交类型
     *
     * @param behaviorEnum 行为类型枚举类
     * @param kid          业务ID，可以是用户ID，也可以是资源ID
     * @param page         页面，给活动pv统计专用
     * @param count        增长量
     * @return
     */
    Response<Object> commitCount(BehaviorEnum behaviorEnum, Long kid, String page, Long count);

    /**
     * 查询count
     *
     * @param countType
     * @param kid
     * @param page
     * @return
     */
    Response<Map<String, Long>> getCount(String countType, Long kid, String page);

}
