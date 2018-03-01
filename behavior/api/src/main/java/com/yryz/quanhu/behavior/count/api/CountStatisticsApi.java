package com.yryz.quanhu.behavior.count.api;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.behavior.count.dto.CountStatisticsDto;
import com.yryz.quanhu.behavior.count.vo.CountStatisticsVo;

import java.util.Map;

public interface CountStatisticsApi {

    /**
     * 获取活动每天的浏览数统计
     * @param   countStatisticsDto
     * @return
     * */
    Response<Map<String, Long>> countModelMap(CountStatisticsDto countStatisticsDto);

    /**
     * 统计参与者在活动中的浏览总数
     * @param   countStatisticsDto
     * @return
     * */
    Response<Map<String, Long>> countDetailModelMap(CountStatisticsDto countStatisticsDto);

}
