package com.yryz.quanhu.behavior.count.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.behavior.count.api.CountStatisticsApi;
import com.yryz.quanhu.behavior.count.dto.CountStatisticsDto;
import com.yryz.quanhu.behavior.count.service.CountStatisticsService;
import com.yryz.quanhu.behavior.count.vo.CountStatisticsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Service(interfaceClass=CountStatisticsApi.class)
public class CountStatisticsProvider implements CountStatisticsApi {

    private static final Logger logger = LoggerFactory.getLogger(CountStatisticsProvider.class);

    @Autowired
    private CountStatisticsService countStatisticsService;

    /**
     * 获取活动每天的浏览数统计
     * @param   countStatisticsDto
     * @return
     * */
    public Response<Map<String, Long>> countModelMap(CountStatisticsDto countStatisticsDto) {
        try {
            return ResponseUtils.returnObjectSuccess(countStatisticsService.countModelMap(countStatisticsDto));
        } catch (Exception e) {
            logger.error("获取活动每天的浏览数统计 失败", e);
            return ResponseUtils.returnException(e);
        }
    }

}
