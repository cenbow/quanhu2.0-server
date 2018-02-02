package com.yryz.quanhu.other.activity.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.behavior.count.api.CountStatisticsApi;
import com.yryz.quanhu.other.activity.api.AdminActivityCountApi;
import com.yryz.quanhu.other.activity.dto.AdminActivityCountDto;
import com.yryz.quanhu.other.activity.service.AdminActivityCountService;
import com.yryz.quanhu.other.activity.vo.AdminActivityCountVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Service(interfaceClass=AdminActivityCountApi.class)
public class AdminActivityCountProvider implements AdminActivityCountApi {

    private static final Logger logger = LoggerFactory.getLogger(AdminActivityCountProvider.class);

    @Reference(check = false)
    CountStatisticsApi countStatisticsApi;

    @Autowired
    AdminActivityCountService adminActivityCountService;

    /**
     * 获取活动统计数量
     * @param   adminActivityCountDto
     * @return
     * */
    public Response<PageList<AdminActivityCountVo>> activityCount(AdminActivityCountDto adminActivityCountDto) {
        try {
            return ResponseUtils.returnObjectSuccess(adminActivityCountService.activityCount(adminActivityCountDto));
        } catch (Exception e) {
            logger.error("获取活动详情 失败", e);
            return ResponseUtils.returnException(e);
        }
    }

    /**
     * 获取活动统计数量总和
     * @param   adminActivityCountDto
     * @return
     * */
    public Response<AdminActivityCountVo> activityTotalCount(AdminActivityCountDto adminActivityCountDto) {
        try {
            return ResponseUtils.returnObjectSuccess(adminActivityCountService.activityTotalCount(adminActivityCountDto));
        } catch (Exception e) {
            logger.error("获取活动统计数量总和 失败", e);
            return ResponseUtils.returnException(e);
        }
    }

}
