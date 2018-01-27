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
     * 提交行为
     *
     * @param behaviorEnum 行为类型枚举类
     * @param kid          业务ID，可以是用户ID，也可以是资源ID
     * @param page         页面，非必填，给平台活动统计pv专用。
     * @param count        增长量
     * @return
     */
    Response<Object> commitCount(BehaviorEnum behaviorEnum, Long kid, String page, Long count);

    /**
     * 查询count
     *
     * @param countType 行为的类型集合，例：10,11,12
     * @param kid       业务ID，可以是用户ID，也可以是资源ID
     * @param page      页面，非必填，给平台活动统计pv专用。
     * @return 返回对应查询的count集合 例：如果传入countType为10,11,12，返回{commentCount:123,likeCount:123,readCount:123}
     */
    Response<Map<String, Long>> getCount(String countType, Long kid, String page);

}
