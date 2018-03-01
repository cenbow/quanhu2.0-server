package com.yryz.quanhu.behavior.count.service;

import com.yryz.quanhu.behavior.count.dto.CountStatisticsDto;

import java.util.Map;

public interface CountStatisticsService {

    /**
     * 获取活动每天的浏览数统计
     * @param   countStatisticsDto
     * @return
     * */
    Map<String, Long> countModelMap(CountStatisticsDto countStatisticsDto);

    /**
     * 统计参与者在活动中的浏览总数
     * @param   countStatisticsDto
     * @return
     * */
    Map<String, Long> countDetailModelMap(CountStatisticsDto countStatisticsDto);

}
