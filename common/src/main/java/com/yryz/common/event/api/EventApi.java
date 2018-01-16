package com.yryz.common.event.api;

import com.yryz.common.event.entity.EventMessage;
import com.yryz.common.event.entity.EventUpdate;
import com.yryz.component.rpc.RpcResponse;
import com.yryz.component.rpc.internal.DubboResponse;

import java.util.List;

public interface EventApi {

    /**
     * 用于更新业务表数据
     * 需要实现举报
     * @param   list
     * @param   type    1、举报下架    2、更新业务表统计数据
     * @return
     * */
    RpcResponse<Integer> eventUpdate(List<EventUpdate> list, Integer type);

    /**
     * 通过kid与moduleEnum获得资源实体
     * @param kid
     * @param moduleEnum
     * @return
     * */
    default RpcResponse<EventMessage> eventSelect(Long kid, String moduleEnum) {
        return new DubboResponse<EventMessage>(false, null);
    }

}
