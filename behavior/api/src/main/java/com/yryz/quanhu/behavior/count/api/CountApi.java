package com.yryz.quanhu.behavior.count.api;

import com.yryz.common.response.Response;
import com.yryz.quanhu.behavior.count.contants.BehaviorEnum;
import com.yryz.quanhu.behavior.report.entity.Report;

import java.util.Map;

/**
 * @Author: liupan
 * @Path: com.yryz.quanhu.behavior.count.api
 * @Desc:
 * @Date: 2018/1/23.
 */
public interface CountApi {

    /**
     * @param behaviorEnum
     * @param kid
     * @param page
     * @param count
     * @return
     */
    Response<Object> commitCount(BehaviorEnum behaviorEnum, Long kid, String page, Long count);

    Response<Map<String, Long>> getCount(String countType, Long kid, String page);

}
