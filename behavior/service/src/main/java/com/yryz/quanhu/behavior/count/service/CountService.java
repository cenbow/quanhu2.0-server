package com.yryz.quanhu.behavior.count.service;

import com.yryz.quanhu.behavior.count.contants.BehaviorEnum;

/**
 * @Author: liupan
 * @Path: com.yryz.quanhu.behavior.count.service
 * @Desc: 计数服务接口类
 * @Date: 2018/1/23.
 */
public interface CountService {

    void countCommit(BehaviorEnum behaviorEnum, String kid, Long count);

    Long getCount(String kid, String code);
}
