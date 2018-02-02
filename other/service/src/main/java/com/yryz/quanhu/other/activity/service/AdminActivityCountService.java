package com.yryz.quanhu.other.activity.service;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.other.activity.dto.AdminActivityCountDto;
import com.yryz.quanhu.other.activity.vo.AdminActivityCountVo;

public interface AdminActivityCountService {

    /**
     * 获取活动统计数量
     * @param   adminActivityCountDto
     * @return
     * */
    PageList<AdminActivityCountVo> activityCount(AdminActivityCountDto adminActivityCountDto);

    /**
     * 获取活动统计数量总和
     * @param   adminActivityCountDto
     * @return
     * */
    AdminActivityCountVo activityTotalCount(AdminActivityCountDto adminActivityCountDto);

}
