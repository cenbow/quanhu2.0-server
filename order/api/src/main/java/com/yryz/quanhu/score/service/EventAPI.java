package com.yryz.quanhu.score.service;

import java.util.List;

import com.yryz.common.response.Response;
import com.yryz.quanhu.score.vo.EventInfo;
import com.yryz.quanhu.score.vo.EventReportVo;

/**
 * @des 事件接口
 * @author xiepeng
 * @version 1.0
 * @date 2017年8月25日
 */
public interface EventAPI {

    /**
     * 提交积分事件
     * @param event
     */
    void commit(EventInfo event);

    /**
     * 提交事件列表
     * @param list
     */
    void commit(List<EventInfo> list);
    
    
    /**
     * 积分统计查询
     * @param EventReportVo
     */
    Response<EventReportVo> getScoreFlowList(EventInfo log); 


}
